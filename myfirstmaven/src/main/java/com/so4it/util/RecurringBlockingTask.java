package com.so4it.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public abstract class RecurringBlockingTask<T> implements AutoCloseable {

    private static final Logger LOGGER = Logger.getLogger("");

    private Thread thread;

    private SingleDrainTaskRunnable taskRunnable;

    private BlockingQueue<T> queue;

    public RecurringBlockingTask(ThreadFactory threadFactory, BlockingQueue<T> queue) {
        this.taskRunnable = new SingleDrainTaskRunnable(this);
        this.thread = Objects.requireNonNull(threadFactory, "namedThreadFactory").newThread(this.taskRunnable);
        this.queue = Objects.requireNonNull(queue, "queue");
    }

    /**
     * Delegates to the implementing class to do what ever it is supposed to
     *
     * @return
     */
    protected abstract void doTask(T t);


    protected void doTask(Collection<T> ts) {
    }

    /**
     * Delegates to the
     *
     * @param throwable
     * @return
     */
    protected void onError(Throwable throwable, T t) {
        LOGGER.warning("Caught unhandled error in thread");
    }


    /**
     * Delegates to the implementing class to decide how long to sleep between runs.
     *
     * @return
     */
    protected abstract long getBackoff();
    /**
     * Starts the underlying thread so it starts running the task
     */
    public synchronized void start() {
        if (!this.taskRunnable.isRunning()) {
            this.taskRunnable.setRunning(true);
            this.thread.start();
        }

    }

    @Override
    public synchronized void close() {
        if (this.taskRunnable.isRunning()) {
            try {
                this.taskRunnable.setRunning(false);
                this.thread.join(500);
                LOGGER.info(String.format("Terminated thread: name=%s", this.thread.getName()));
            } catch (InterruptedException e) {
                LOGGER.severe(String.format("The termination of the thread failed. Resetting the interrupt flag. name=%s",this.thread.getName()));
                Thread.currentThread().interrupt();
            }
        }
    }

    public synchronized boolean isAlive() {
        return this.taskRunnable.isRunning();
    }

    private class SingleDrainTaskRunnable implements Runnable {

        private volatile boolean running = false;

        private RecurringBlockingTask<T> recurringTask;

        public SingleDrainTaskRunnable(RecurringBlockingTask recurringTask) {
            this.recurringTask = recurringTask;
        }

        public void setRunning(boolean running) {
            this.running = running;
        }

        public boolean isRunning() {
            return running;
        }

        @Override
        public void run() {
            T t = null;
            while (!Thread.currentThread().isInterrupted() && this.running) {
                try {
                    t = RecurringBlockingTask.this.queue.poll(getBackoff(), TimeUnit.MILLISECONDS);
                    recurringTask.doTask(t);
                } catch (Throwable e) {
                    recurringTask.onError(e,t);
                }
            }
        }
    }

    private class MultipleDrainTaskRunnable implements Runnable {

        private volatile boolean running = false;

        private RecurringBlockingTask<T> recurringTask;

        private int drain;

        public MultipleDrainTaskRunnable(RecurringBlockingTask recurringTask, int drain) {
            this.recurringTask = recurringTask;
            this.drain = drain;
        }

        public void setRunning(boolean running) {
            this.running = running;
        }

        public boolean isRunning() {
            return running;
        }

        @Override
        public void run() {
            T t = null;
            List<T> ts = new ArrayList<>();
            while (!Thread.currentThread().isInterrupted() && this.running) {
                try {
                    ts.clear();
                    t = RecurringBlockingTask.this.queue.poll(getBackoff(), TimeUnit.MILLISECONDS);
                    ts.add(t);
                    if(t != null){
                        RecurringBlockingTask.this.queue.drainTo(ts);
                    }
                    recurringTask.doTask(ts);
                } catch (Throwable e) {
                    recurringTask.onError(e,t);
                }
            }
        }
    }

}
