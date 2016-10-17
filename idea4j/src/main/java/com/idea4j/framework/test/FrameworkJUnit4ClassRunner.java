package com.idea4j.framework.test;

import com.idea4j.framework.HelperLoader;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.InitializationError;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 测试runner
 *
 * @author andaicheng
 */
public class FrameworkJUnit4ClassRunner extends BlockJUnit4ClassRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(FrameworkJUnit4ClassRunner.class);

    /**
     * Creates a BlockJUnit4ClassRunner to run {@code klass}
     * Constructs a new {@code FrameworkJUnit4ClassRunner}
     * and initializes a {@code HelperLoader} to provide Framework testing functionality to
     * standard JUnit tests.
     *
     * @param klass the test class to be run
     * @throws InitializationError if the test class is malformed.
     */
    public FrameworkJUnit4ClassRunner(Class<?> klass) throws InitializationError {
        super(klass);
        LOGGER.debug("FrameworkJUnit4ClassRunner constructor called with [" + klass + "].");
        HelperLoader.init();
    }
}
