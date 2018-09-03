package com.filehandler;

import com.exception.ValidationException;
import com.utils.Constants;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class UrlPoolHandlerTest {
    private final String testConfig="testConfig";
    private final String testConfig2="testConfig2.properties";
    private Properties properties=new Properties();
    private Properties properties2=new Properties();

    @Before
    public void setup(){
            InputStream input = null;
            try {
                input=Thread.currentThread().getContextClassLoader().getResourceAsStream(testConfig);
                this.properties.load(input);
                input=Thread.currentThread().getContextClassLoader().getResourceAsStream(testConfig2);
                this.properties2.load(input);
            } catch (IOException ex) {
                System.err.println("Exception while loading properties from config: " + ex.getMessage());
            } finally {
                if (input != null) {
                    try {
                        input.close();
                    } catch (IOException e) {
                        System.err.println("Exception while closing stream after reading properties from config " + e.getMessage());
                    }
                }
            }
    }

    @Test
    public void test_UrlProcessor_Constructor_NotNullValue() throws ValidationException {
        UrlPoolHandler urlPoolHandler =new UrlPoolHandler();
        Assert.assertNotNull(urlPoolHandler.props);
        Assert.assertNotNull(urlPoolHandler.urlList);
    }

    @Test(expected = ValidationException.class)
    public void test_UrlProcessorProcess_WhenPropertyNull_ThrowsException() throws ValidationException {
        UrlPoolHandler urlPoolHandler =new UrlPoolHandler( properties2, System.getProperty("user.dir"));
        urlPoolHandler.process();
    }

    @Test
    public void test_UrlProcessor_Process() throws ValidationException {
        String fileLocation=System.getProperty("user.dir")+"\\testDownloads1";
        UrlPoolHandler urlPoolHandler =new UrlPoolHandler( properties, fileLocation);
        urlPoolHandler.process();
        boolean exists = doesFileExists(fileLocation);
        Assert.assertEquals(true,exists);
    }

    private boolean doesFileExists(String fileLocation) {
        getFileLocation(fileLocation);
        File tmpDir = new File(getFileLocation(fileLocation));
        return tmpDir.exists();
    }

    private String getFileLocation(String fileLocation) {
        String urlString = (String) properties.get(Constants.URL);
        String localFileName = urlString.substring(urlString.lastIndexOf(Constants.SLASH) + 1);
        String finalLocation= fileLocation + File.separator + localFileName;
        return finalLocation;
    }

}
