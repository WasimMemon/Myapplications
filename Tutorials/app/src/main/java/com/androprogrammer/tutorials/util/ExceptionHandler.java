package com.androprogrammer.tutorials.util;

import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.util.Log;

import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;

import ch.qos.logback.classic.Level;

/**
 * Created by Wasim on 05-08-2015.
 */
public class ExceptionHandler implements java.lang.Thread.UncaughtExceptionHandler {

    private Context myContext;
    private final String LINE_SEPARATOR = "\n";
    private static ExceptionHandler myHandler = null;
    private int i = 0;
    private String mString;

    org.slf4j.Logger log = LoggerFactory.getLogger(this.getClass().getSimpleName());

    ch.qos.logback.classic.Logger rootLogger = (ch.qos.logback.classic.Logger)
            LoggerFactory.getLogger(this.getClass().getSimpleName());

    public ExceptionHandler(Context myContext) {
        this.myContext = myContext;
        mString = "Hello";
        //rootLogger.setLevel(Level.ERROR);
    }

    public ExceptionHandler() {
    }

    public static ExceptionHandler getInstance(Context myContext)
    {
        if (myHandler == null)
        {
            myHandler = new ExceptionHandler(myContext);
        }

        return myHandler;
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        StringWriter stackTrace = new StringWriter();
        ex.printStackTrace(new PrintWriter(stackTrace));
        ex.printStackTrace();
        //Log.e(myContext.getClass().getSimpleName(), ex.getMessage());
        StringBuffer errorReport = new StringBuffer();
        errorReport.append("************ CAUSE OF ERROR ************\n\n");
        errorReport.append(stackTrace.toString());
        //stackTrace.close();


        errorReport.append("\n************ DEVICE INFORMATION ***********\n");
        errorReport.append("Brand: ");
        errorReport.append(Build.BRAND);
        errorReport.append(LINE_SEPARATOR);
        errorReport.append("Device: ");
        errorReport.append(Build.DEVICE);
        errorReport.append(LINE_SEPARATOR);
        errorReport.append("Model: ");
        errorReport.append(Build.MODEL);
        errorReport.append(LINE_SEPARATOR);
        errorReport.append("Id: ");
        errorReport.append(Build.ID);
        errorReport.append(LINE_SEPARATOR);
        errorReport.append("Product: ");
        errorReport.append(Build.PRODUCT);
        errorReport.append(LINE_SEPARATOR);
        errorReport.append("\n************ FIRMWARE ************\n");
        errorReport.append("SDK: ");
        errorReport.append(Build.VERSION.SDK_INT);
        errorReport.append(LINE_SEPARATOR);
        errorReport.append("Release: ");
        errorReport.append(Build.VERSION.RELEASE);
        errorReport.append(LINE_SEPARATOR);
        errorReport.append("Incremental: ");
        errorReport.append(Build.VERSION.INCREMENTAL);
        errorReport.append(LINE_SEPARATOR);

        errorReport.append("************ END OF ERROR ************");

        writeLogInFile(errorReport.toString());
        //log.error(errorReport.toString());


        /*Intent intent = new Intent(myContext, Listactivity.class);
        intent.putExtra("error", errorReport.toString());
        myContext.startActivity(intent);*/

        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(1);
    }

    public String getString(){
        return this.mString;
    }

    public void setString(String value){
        mString = value;
    }

    private void writeLogInFile(String log)
    {
        File filename = new File(Environment.getExternalStorageDirectory()+"/logfile.log");
        FileWriter writer = null;

        try {
            if (!filename.exists()){

                filename.createNewFile();
            }

            writer = new FileWriter(filename,true);
            writer.append(log);
            writer.append("\n");
            writer.flush();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } finally {
            try {
                writer.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }
}
