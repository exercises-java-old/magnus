package com.so4it.util;

import org.hamcrest.Description;

public interface Probe {

    /**
     * Indicates whether this Probe is satisfied or note, will be called repeatedly
     * until timeout occurs. Timeout is determine by the Poller that polls the probe. <p>
     *
     * @return
     */
    boolean isSatisfied();

    /**
     * Sample the given resource. After each sample the isSatisfied() method will be queried
     * to see whether to condition i yet satisfied. <p>
     */
    void sample();

    /**
     * This method will be invoked in case of failure, i.e. the probe was not satisfied before
     * timeout occurs. <p>
     *
     * @param description
     */
    void describeFailureTo(Description description);

}

