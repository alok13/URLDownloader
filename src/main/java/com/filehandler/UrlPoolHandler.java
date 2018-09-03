package com.filehandler;

import com.exception.ValidationException;
import com.utils.Constants;
import com.utils.Validator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.*;

/**
 * This class manages URLs and execute downloading using thread pool.
 */

public class UrlPoolHandler {
    private final String LOCATION;
    final Properties props;
    List<String> urlList = new ArrayList<>();

    public UrlPoolHandler() {
         props = ConfigReader.getInstance().getProperty();
        LOCATION = (String) props.get(Constants.FOLDER_DESTINATION);
    }

    public UrlPoolHandler(Properties properties, String location) {
        props = properties;
        LOCATION = location;
    }

    public void process() throws ValidationException {
        prepareInput();
        List<Callable<String>> tasks = new ArrayList<>();
        ThreadPoolExecutor executor = getThreadPoolExecutor(tasks);
        List<Future<String>> futureList=processTasks(tasks, executor);
        generateInfo(futureList);
        shutdownExecutor(executor);
    }

    private void prepareInput() throws ValidationException {
        String urlString = (String) props.get(Constants.URL);
        Validator validator=new Validator();
        if(!validator.validateNotNull(urlString) || !validator.validateNotNull(LOCATION))
            throw new ValidationException("Input from property is null. URL value: "+ urlString+ " and location value: "+LOCATION);
        urlList = Arrays.asList(urlString.split(Constants.COMMA));
    }

    private void shutdownExecutor(ThreadPoolExecutor executor) {
        executor.shutdown();
        try {
            executor.awaitTermination(10L, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void generateInfo(List<Future<String>> tasks) {
        System.out.println("Finished all processing threads");
        for(Future<String> future:tasks){
            try {
                String result=future.get();
                System.out.println("Final result at: "+result);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
    }

    private List<Future<String>> processTasks(List<Callable<String>> tasks, ThreadPoolExecutor executor) {
        List<Future<String>> list=new ArrayList<>();
        try {
            list=executor.invokeAll(tasks);
        } catch (InterruptedException e) {
            System.err.println("Exception in invoking all tasks");
            e.printStackTrace();
        }
        return list;
    }

    private ThreadPoolExecutor getThreadPoolExecutor(List<Callable<String>> tasks) {
        int nCpu=Runtime.getRuntime().availableProcessors();
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool( nCpu+ 1);
        for (int i = 0; i < urlList.size(); i++) {
            System.out.println(" URL being processed : " + urlList.get(i));
            Callable<String> downloader=new Downloader(urlList.get(i),LOCATION);
            tasks.add(downloader);
        }
        return executor;
    }
}
