package com.so4it.util;


import org.hamcrest.Description;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.function.Supplier;

public class SatisfiedWhenTrueReturned extends ProbeAdapter {

    private Supplier<Boolean> supplier;

    private Throwable thrownException;

    private SatisfiedWhenTrueReturned(Supplier<Boolean> runnable) {
        this.supplier = runnable;
    }

    public static Probe create(Supplier<Boolean> runnable)
    {
        return new SatisfiedWhenTrueReturned(runnable);
    }

    @Override
    public boolean isSatisfied() {
        try {
            Boolean result = supplier.get();
            this.thrownException = null;
            return result;
        } catch (Throwable throwable) {
            this.thrownException = throwable;
            return false;
        }
    }

    @Override
    public void describeFailureTo(Description description) {
        if (this.thrownException != null) {
            StringWriter stringWriter = new StringWriter();
            thrownException.printStackTrace(new PrintWriter(stringWriter));
            description.appendText(String.format("Expected function to return 'true' value, but threw exception: \n%s", stringWriter.toString()));
        }else {
            description.appendText("Expected function to return 'true' value");
        }
    }
}





