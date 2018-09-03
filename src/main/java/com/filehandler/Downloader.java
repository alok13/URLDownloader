package com.filehandler;

import com.exception.StreamException;
import com.exception.ValidationException;
import com.stream.InputStreamImpl;
import com.stream.OutputStreamImpl;
import com.stream.StreamService;

import java.io.*;
import java.util.concurrent.Callable;

/**
 * This class generates streams and download the data from input URL to given file location
 */

public class Downloader implements Callable<String> {
    private final String SOURCE_URL;
    private final String LOCATION;

    private StreamService<InputStream> inputStreamImpl=new InputStreamImpl();
    private StreamService<OutputStream> outputStreamImpl = new OutputStreamImpl();
    private FileUtil fileUtil =new FileUtil();


    @Override
    public String call() throws Exception {
        return execute();
    }


    public Downloader(String source_url, String location) {
        SOURCE_URL = source_url;
        LOCATION = location;

    }

    private String execute() throws StreamException, ValidationException {
        System.out.println("Downloading started for URL:" +SOURCE_URL);
        String localFileDestination = fileUtil.generateFileDestination(SOURCE_URL, LOCATION);
        System.out.println("Data will be saved in file :"+ localFileDestination);
        InputStream inputStream =inputStreamImpl.getStream(SOURCE_URL);;
        OutputStream outputStream = outputStreamImpl.getStream(localFileDestination);
        fileUtil.performCopy(localFileDestination, inputStream, outputStream);
        System.out.println("Downloading is successfully finished at :"+ localFileDestination);
        return localFileDestination;
    }


    @Override
    public String toString() {
        return "Downloader{" +
                "SOURCE_URL='" + SOURCE_URL + '\'' +
                ", LOCATION='" + LOCATION + '\'' +
                '}';
    }
}
