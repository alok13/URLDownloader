package com.stream;

import com.exception.StreamException;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.io.OutputStream;


public class OutputStreamImplTest {

    @Test
    public void test_OutputStream_ForPositiveScenario() throws IOException, StreamException {
        String input="test.txt";
        OutputStreamImpl outputStreamImpl=new OutputStreamImpl();
        OutputStream outputStream=outputStreamImpl.getStream(input);
        Assert.assertNotNull(input);
        outputStream.close();
    }
}
