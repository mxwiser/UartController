package com.ybm.uart;

import static android.content.ContentValues.TAG;
import static android.content.Context.TELEPHONY_SERVICE;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.RandomAccessFile;
import java.text.BreakIterator;

public class SimController {


    public static String getICCID(String dev) {
        String ccid = "";
        int readSize = 0;
        String cmd = "at+qccid\r\n";
        OutputStream out;
        InputStream in;
        try {
            out = new FileOutputStream(dev);
            in = new FileInputStream(dev);
            out.write(cmd.getBytes());
            out.flush();
            byte[] arrayOfByte = new byte[1024];
            while ((readSize = in.read(arrayOfByte)) == -1) {
            }
            out.close();
            in.close();
            ccid = new String(arrayOfByte).substring(0, readSize);
            ccid = ccid.substring(ccid.indexOf(":") + 1, ccid.indexOf(":") + 22);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ccid;
    }
}



