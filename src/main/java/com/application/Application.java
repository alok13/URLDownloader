package com.application;

import com.exception.ValidationException;
import com.filehandler.UrlPoolHandler;

/**
 * This is the main class,point from where Application will be triggered
 */


public class Application {
    public static void main(String args[]) {
        UrlPoolHandler urlPoolHandler = new UrlPoolHandler();
        try {
            urlPoolHandler.process();
        }  catch (ValidationException e) {
            System.err.println("Invalid input  "+e.getMessage());
        }
    }
}
