package ru.javawebinar.topjava;

import org.junit.rules.Stopwatch;
import org.junit.runner.Description;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

public class JUnitTestTimeWatcher extends Stopwatch {
    private static final Logger log = LoggerFactory.getLogger(JUnitTestTimeWatcher.class);
    public static final StringBuilder result;

    static {
        result = new StringBuilder("ALL TEST\n");
    }

    @Override
    protected void finished(long nanos, Description description) {
        String test = String.format("%-25s - %5d ms\n", description.getMethodName(), TimeUnit.MILLISECONDS.convert(nanos, TimeUnit.NANOSECONDS));
        log.info(test);
        result.append(test);
    }
}
