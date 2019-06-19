package com.so4it.util;

import org.hamcrest.Description;

public class ProbeAdapter implements Probe {

    @Override
    public boolean isSatisfied() {
        return false;
    }

    @Override
    public void sample() {

    }

    @Override
    public void describeFailureTo(Description description) {
    }
}




