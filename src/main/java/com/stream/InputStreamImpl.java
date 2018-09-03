package com.stream;

import com.exception.StreamException;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

/**
 * This class generate a inputStream from provided input
 */

public class InputStreamImpl implements StreamService<InputStream> {
    @Override
    public InputStream getStream(String input) throws StreamException {
        InputStream inputStream ;
        try {
            URL url = new URL(input);
            URLConnection urlConnection = url.openConnection();
            urlConnection.connect();
            inputStream = urlConnection.getInputStream();
        } catch (IOException e) {
            System.err.println(" Error while creating input stream from "+ input);
           throw new StreamException("Exception while creating input stream from String:  "+ input,e);
        }
        return inputStream;
    }
}
