package com.example.shreefgroup.elp_app;

/**
 * Created by ashfaq on 11/27/2016.
 */


import android.content.Context;
import android.os.Environment;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class FileHelper_IMEI {
    final static String fileName22 = "IMEI_NO.txt";
    final static String path = Environment.getExternalStorageDirectory()+"/ELP" + "/";
    final static String TAG = FileHelper_Settings.class.getName();

    public static String ReadFile(Context context) {
        String line = null;

        try {
            FileInputStream fileInputStream = new FileInputStream(new File(path + fileName22));
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream);
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            StringBuilder stringBuilder = new StringBuilder();

            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line + System.getProperty("line.separator"));
            }
            fileInputStream.close();
            line = stringBuilder.toString();

            bufferedReader.close();
        } catch (FileNotFoundException ex) {
            Log.d(TAG, ex.getMessage());
        } catch (IOException ex) {
            Log.d(TAG, ex.getMessage());
        }
        return line;
    }

    public static boolean saveToFile(String data) {
        try {
            new File(path).mkdir();
            File file = new File(path + fileName22);
            if (!file.exists()) {
                file.createNewFile();
            }
            FileOutputStream fileOutputStream = new FileOutputStream(file, true);
            fileOutputStream.write((data + System.getProperty("line.separator")).getBytes());

            return true;
        } catch (FileNotFoundException ex) {
            Log.d(TAG, ex.getMessage());
        } catch (IOException ex) {
            Log.d(TAG, ex.getMessage());
        }
        return false;


    }

}