package com.gmail.bogatyr.alexander.deployment.tool.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Alexander Bogatyrenko on 28.07.16.
 * <p>
 * This class represents...
 */
public final class ProcessPrintUtil {

    private static Logger logger = LoggerFactory.getLogger(ProcessPrintUtil.class);

    private ProcessPrintUtil() {
        // nothing
    }

    public static void printInBackground(final Process process) {
        final Thread print = new Thread(new Runnable() {
            @Override
            public void run() {
                print(process);
            }
        });
        print.setDaemon(true);
        print.start();
    }

    public static void print(Process process) {
        //we are specifically suspend the main thread for operations without an explicit result
        try (InputStream is = process.getInputStream();
             InputStreamReader isr = new InputStreamReader(is);
             BufferedReader reader = new BufferedReader(isr)) {
            String line;
            while ((line = reader.readLine()) != null) {
                logger.info(line);
            }
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }
}
