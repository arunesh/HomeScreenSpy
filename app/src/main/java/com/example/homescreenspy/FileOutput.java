package com.example.homescreenspy;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by arunesh on 10/21/15.
 */
public class FileOutput {
    private static final String TAG = "ScreenSpy";
    private static final String FILE_PREFIX = "screen_spy";
    private static final String FILE_SUFFIX = "txt";
    private Context mContext;
    private static FileOutput INSTANCE;
    private OutputStream mOs;


    public static FileOutput get() {
        return INSTANCE;
    }

    public static FileOutput initialize(Context context) {
        try {
            File tempFile = createOutputFile(context);
            OutputStream os = new FileOutputStream(tempFile);
            INSTANCE = new FileOutput(context, os);
            return INSTANCE;
        } catch (IOException e) {
            Log.e(TAG, "Unable to create FileOutput instance: " + e.toString());
        }
        return null;
    }

    FileOutput(Context context, OutputStream os) {
        mContext = context;
        mOs = os;
    }

    private static File createOutputFile(Context context) throws IOException {
        File dir = context.getExternalFilesDir(null);
        File tempFile = File.createTempFile(FILE_PREFIX, FILE_SUFFIX, dir);
        return tempFile;
    }

    public void write(String text) {
        try {
            mOs.write(text.getBytes());
        } catch (IOException e) {
            Log.e(TAG, "Unable to write to file, string to write:" + text);
        }
    }

    public void close() {
        try {
            mOs.close();
        } catch (IOException e) {
            Log.e(TAG, "Unable to close file" + e.toString());
        }
    }

    public void writeScreenOnEvent() {
        write("Screen On: " + System.currentTimeMillis());
    }

    public void writeScreenOffEvent() {
        write("Screen Off: " + System.currentTimeMillis());
    }


    public void writeHomeScreenVisibileEvent() {
        write("Home screen visible: " + System.currentTimeMillis());
    }

    public void writeHomeScreenInvisibleEvent() {
        write("Home screen invisible: " + System.currentTimeMillis());
    }
}
