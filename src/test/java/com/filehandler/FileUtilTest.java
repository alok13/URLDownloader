package com.filehandler;

import com.exception.StreamException;
import com.exception.ValidationException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.*;

public class FileUtilTest {
    FileUtil fileUtil;

    @Before
    public void setup() {
        fileUtil = new FileUtil();
    }

    @Test
    public void test_GenerateFileDestination_WithPositiveScenarios() throws ValidationException, StreamException {
        String test = "testURl";
        String location = "testLocation";
        String result = fileUtil.generateFileDestination(test, location);
        Assert.assertEquals(result, "testLocation\\testURl");
    }

    @Test(expected = ValidationException.class)
    public void test_GenerateFileDestination_WithNullURL_ThrowsValidationException() throws ValidationException, StreamException {
        String test = (null);
        String location = "testLocation";
        fileUtil.generateFileDestination(test, location);

    }

    @Test(expected = ValidationException.class)
    public void test_GenerateFileDestination_NullLocation_thowsException() throws ValidationException, StreamException {
        String test = "test";
        String location = null;
        fileUtil.generateFileDestination(test, location);
    }

    @Test
    public void test_PerformCopy_Successfully() throws IOException, StreamException, ValidationException {
        String fileLocation = System.getProperty("user.dir");
        String filePath = fileLocation + "//testFile1.txt";
        String testString = "this is test string";
        InputStream is = new ByteArrayInputStream(testString.getBytes());
        OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(filePath));
        fileUtil.performCopy(filePath, is, outputStream);
        is.close();
        outputStream.close();
        Assert.assertTrue(doesFileExists(filePath));
    }

    private boolean doesFileExists(String fileLocation) {
        File tmpDir = new File((fileLocation));
        return tmpDir.exists();
    }


    @Test(expected = ValidationException.class)
    public void test_PerformCopy_WhenInputStreamNull_ThrowException() throws FileNotFoundException, StreamException, ValidationException {
        String fileLocation = System.getProperty("user.dir");
        String filePath = fileLocation + "\\" + "testFile1.txt";
        InputStream is = null;
        OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(filePath));
        fileUtil.performCopy(filePath, is, outputStream);
    }


    @Test(expected = ValidationException.class)
    public void test_PerformCopy_WhenOutputStreamNull_ThrowException() throws FileNotFoundException, StreamException, ValidationException {
        String fileLocation = System.getProperty("user.dir");
        String filePath = fileLocation + "\\" + "testFile1.txt";
        String testString = "this is test string";
        InputStream is = new ByteArrayInputStream(testString.getBytes());
        OutputStream outputStream = null;
        fileUtil.performCopy(filePath, is, outputStream);
    }
}
