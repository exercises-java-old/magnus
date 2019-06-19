package com.so4it.util;

import org.hamcrest.StringDescription;

import java.util.function.Predicate;

public class Poller {

    public static final long DEFAULT_TIMEOUT = 5000L;

    public static final int DEFAULT_POLL_INTERVALL = 20;

    private long timeoutMillis;

    private long pollDelayMillis;

    private Poller(long timeoutMillis, long pollDelayMillis) {
        this.timeoutMillis = timeoutMillis;
        this.pollDelayMillis = pollDelayMillis;
    }

    /**
     * Polls the given probe until it is satisfied or timeout occurs. <p>
     * <p>
     * This method will return as soon as the Probe is satisfied. <p>
     * <p>
     * If timeout occurs an AssertionError will be thrown.<p>
     *
     * @param probe
     * @throws InterruptedException - if the current thread is interrupted.
     * @throws AssertionError       - if timeout occurs before the Probe is satisfied.
     */
    public void check(Probe probe) throws InterruptedException {
        Timeout timeout = newTimeout(timeoutMillis);
        probe.sample();
        while (!probe.isSatisfied()) {
            if (timeout.hasTimeout()) {
                throw new AssertionError(describeFailureOf(probe));
            }
            sleep(pollDelayMillis);
            probe.sample();
        }
    }

    public <T> void check(Predicate<T> predicate, T t) throws InterruptedException {

        Timeout timeout = newTimeout(timeoutMillis);

        while (!predicate.test(t)) {
            if (timeout.hasTimeout()) {
                throw new AssertionError(t);
            }
            sleep(pollDelayMillis);
        }
    }

    // test hook
    Timeout newTimeout(long timeout) {
        return new Timeout(timeout);
    }

    // test hook
    void sleep(long sleepTimeMillis) throws InterruptedException {
        Thread.sleep(sleepTimeMillis);
    }

    private String describeFailureOf(Probe probe) {
        StringDescription description = new StringDescription();
        probe.describeFailureTo(description);
        return description.toString();
    }

    public static void pollAndCheck(Probe probe) throws InterruptedException {
        new Poller(DEFAULT_TIMEOUT, DEFAULT_POLL_INTERVALL).check(probe);
    }

    public static <T> void pollAndCheck(long pollDelayMillis,Probe probe) throws InterruptedException {
        new Poller(DEFAULT_TIMEOUT, pollDelayMillis).check(probe);
    }

    public static void pollAndCheck(long timeoutMillis, long pollDelayMillis, Probe probe) throws InterruptedException {
        new Poller(timeoutMillis, pollDelayMillis).check(probe);
    }



    public static <T> void pollAndCheckPredicate(Predicate<T> probe, T t) throws InterruptedException {
        new Poller(DEFAULT_TIMEOUT, DEFAULT_POLL_INTERVALL).check(probe, t);
    }
    public static <T> void pollAndCheckPredicate(long timeoutMillis, long pollDelayMillis,Predicate<T> probe, T t) throws InterruptedException {
        new Poller(timeoutMillis, pollDelayMillis).check(probe, t);
    }





    private static class Timeout {

        private long endTimeMillis;

        public Timeout(long timeoutMillis) {
            this.endTimeMillis = currentTimeMillis() + timeoutMillis;
        }

        public boolean hasTimeout() {
            return currentTimeMillis() >= endTimeMillis;
        }

        long currentTimeMillis() {
            return System.currentTimeMillis();
        }

    }



}





