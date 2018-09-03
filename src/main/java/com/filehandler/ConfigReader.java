package com.filehandler;

import com.utils.Constants;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
/*
This class loads property required from given location
 */

public class ConfigReader {

    private static ConfigReader readerInstance = null;
    private Properties prop = new Properties();

    public static synchronized ConfigReader getInstance() {
        if (readerInstance == null) {
            readerInstance = new ConfigReader();
        }
        return readerInstance;
    }

    public Properties getProperty() {
        return this.prop;
    }

    private ConfigReader() {
        readConfig(Constants.CONFIG_PROPERTIES);
    }

    private void readConfig(String config) {
        InputStream input = null;
        try {
            input = new FileInputStream(config);
            this.prop.load(input);
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
}
