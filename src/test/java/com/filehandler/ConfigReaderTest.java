package com.filehandler;

import com.exception.StreamException;
import org.junit.Assert;
import org.junit.Test;


import java.util.Properties;

public class ConfigReaderTest {

    @Test
    public void testConfigReaderPositive() throws StreamException {
        Properties properties=ConfigReader.getInstance().getProperty();
        Assert.assertNotNull(properties);
    }

}
