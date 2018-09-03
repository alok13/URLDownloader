package com.filehandler;

import com.exception.StreamException;
import com.exception.ValidationException;
import com.utils.Constants;
import com.utils.Validator;
import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * This class contains methods related to file and streams.
 */


public class FileUtil {

    private Validator validator = new Validator();

    public String generateFileDestination(String url, String location) throws ValidationException, StreamException {
        validateInput(url, location);
        createLocation(location);
        String localFileName = url.substring(url.lastIndexOf(Constants.SLASH) + 1);
        return location + File.separator + localFileName;
    }

    public void performCopy(String localFileDestination, InputStream inputStream, OutputStream outputStream) throws StreamException, ValidationException {
        if(!validator.validateNotNull(localFileDestination) || !validator.validateNotNull(inputStream) || !validator.validateNotNull(outputStream)){
            throw new ValidationException("Input to perform copy are invalid, localFileDestination "+ localFileDestination + " inputStream : "+ inputStream+ " outputStream: "+outputStream);
        }
        try {
            copyData(inputStream, outputStream);
        } catch (Exception e) {
            try {
                if (inputStream != null)
                    inputStream.close();
                if (outputStream != null)
                    outputStream.close();
            } catch (IOException ex) {
                throw new StreamException("Exception while closing streams after downloading " + e.getMessage(), e);
            }
            System.out.println("Downloading of data has failed,so falling back");
            deleteFile(localFileDestination);
        }
    }

    private void createLocation(String location) throws StreamException {
        Path path = Paths.get(location);
        if(!Files.exists(path)) {
            try {
                Files.createDirectories(path);
            } catch (IOException e) {
                System.err.println("Error while creating directories for location :"+ location);
                throw new StreamException("Error while creating directories for location :"+ e);
            }
        }
    }

    private void copyData(InputStream inputStream, OutputStream outputStream) throws StreamException {
        try {
            IOUtils.copy(inputStream, outputStream);
        } catch (IOException e) {
            System.err.println("Exception while while copying data ");
            throw new StreamException("Exception while coping data from input to output stream" + e.getMessage(), e);
        } finally {
            try {
                inputStream.close();
                outputStream.close();
            } catch (IOException e) {
                System.err.println("Exception while closing streams after downloading");
                throw new StreamException("Exception while closing streams after downloading " + e.getMessage(), e);
            }
        }
    }



    private void deleteFile(String localFileDestination) {
        File file = new File(localFileDestination);
        if (!file.exists()) {
            System.out.println(localFileDestination + " does not exists");
            return;
        }
        if (file.delete()) {
            System.out.println(localFileDestination + " has been successfully deleted");
        } else {
            System.out.println(localFileDestination + " has not been deleted");
        }


    }

    private void validateInput(String url, String location) throws ValidationException {
        if (!validator.validateNotNull(url))
            throw new ValidationException("Source Url is null");

        if (!validator.validateNotNull(location))
            throw new ValidationException("Location is null");
    }
}
