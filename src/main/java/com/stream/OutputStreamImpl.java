package com.stream;

import com.exception.StreamException;

import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;

/**
 * This class generate a outputStream from provided input
 */


public class OutputStreamImpl implements StreamService<OutputStream> {
    @Override
    public OutputStream getStream(String input) throws StreamException {
        OutputStream outputStream;
        try {
            outputStream = new BufferedOutputStream(new FileOutputStream(input));
        } catch (FileNotFoundException e) {
            System.err.println(" Error while creating output stream from "+ input);
           throw new StreamException(" Error while creating output stream from input: "+input);
        }
        return outputStream;
    }
}
