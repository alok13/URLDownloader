package com.stream;

import com.exception.StreamException;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.io.InputStream;


public class InputStreamImplTest {

    @Test(expected = StreamException.class)
    public void test_InputStream_Invalid_ThrowsException() throws IOException, StreamException {
        String input="testConfig";
        InputStreamImpl inputStreamImpl=new InputStreamImpl();
        InputStream inputStream=inputStreamImpl.getStream(input);
    }

    @Test
    public void test_InputStream_For_PositiveScenario() throws IOException, StreamException {
        String input="http://www.google.com";
        InputStreamImpl inputStreamImpl=new InputStreamImpl();
        InputStream inputStream=inputStreamImpl.getStream(input);
        Assert.assertNotNull(input);
        inputStream.close();
    }
}
