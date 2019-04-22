package com.example.shreefgroup.elp_app;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import java.io.File;
import java.net.HttpURLConnection;

public class ConnectionChangeReceiver extends BroadcastReceiver {
    static HttpURLConnection connection = null;
    static String existingFileName = new StringBuilder(String.valueOf(Environment.getExternalStorageDirectory().getAbsolutePath())).append("/Pending Images").toString();
    static int res_code;

    class C00281 extends Thread {
        C00281() {
        }

        public void run() {
            ConnectionChangeReceiver.upload_image();
        }
    }

    public void onReceive(Context context, Intent intent) {
        @SuppressLint("WrongConstant")
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService("connectivity");
        NetworkInfo activeNetInfo = connectivityManager.getActiveNetworkInfo();
        NetworkInfo WIFI = connectivityManager.getNetworkInfo(1);
        NetworkInfo mobNetInfo = connectivityManager.getNetworkInfo(0);
        if (activeNetInfo != null || mobNetInfo != null || WIFI != null) {
            String[] file_len = new File(existingFileName).list();
            if (file_len != null && file_len.length > 0) {
                new C00281().start();
            }
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public static void upload_image() {
        /*
        r20 = 1;
        r14 = 0;
        r9 = 0;
        r24 = new java.lang.StringBuilder;
        r25 = "http://";
        r24.<init>(r25);
        r25 = sample.projectList.Project_ListActivity.server_host;
        r24 = r24.append(r25);
        r25 = "/upload_file.php";
        r24 = r24.append(r25);
        r23 = r24.toString();
        r18 = "\r\n";
        r21 = "--";
        r2 = "*****";
        r19 = 1048576; // 0x100000 float:1.469368E-39 double:5.180654E-318;
        r8 = new java.io.File;
        r24 = existingFileName;
        r0 = r24;
        r8.<init>(r0);
        r24 = r8.isDirectory();
        if (r24 == 0) goto L_0x0041;
    L_0x0032:
        r7 = r8.list();
        r16 = 0;
    L_0x0038:
        r0 = r7.length;
        r24 = r0;
        r0 = r16;
        r1 = r24;
        if (r0 < r1) goto L_0x0042;
    L_0x0041:
        return;
    L_0x0042:
        r24 = 1;
        r0 = r20;
        r1 = r24;
        if (r0 != r1) goto L_0x0094;
    L_0x004a:
        r22 = new java.net.URL;	 Catch:{ MalformedURLException -> 0x01ec, IOException -> 0x01ce, Exception -> 0x01d6 }
        r22.<init>(r23);	 Catch:{ MalformedURLException -> 0x01ec, IOException -> 0x01ce, Exception -> 0x01d6 }
        r24 = r22.openConnection();	 Catch:{ MalformedURLException -> 0x01ec, IOException -> 0x01ce, Exception -> 0x01d6 }
        r24 = (java.net.HttpURLConnection) r24;	 Catch:{ MalformedURLException -> 0x01ec, IOException -> 0x01ce, Exception -> 0x01d6 }
        connection = r24;	 Catch:{ MalformedURLException -> 0x01ec, IOException -> 0x01ce, Exception -> 0x01d6 }
        r24 = connection;	 Catch:{ MalformedURLException -> 0x01ec, IOException -> 0x01ce, Exception -> 0x01d6 }
        r25 = 1;
        r24.setDoInput(r25);	 Catch:{ MalformedURLException -> 0x01ec, IOException -> 0x01ce, Exception -> 0x01d6 }
        r24 = connection;	 Catch:{ MalformedURLException -> 0x01ec, IOException -> 0x01ce, Exception -> 0x01d6 }
        r25 = 1;
        r24.setDoOutput(r25);	 Catch:{ MalformedURLException -> 0x01ec, IOException -> 0x01ce, Exception -> 0x01d6 }
        r24 = connection;	 Catch:{ MalformedURLException -> 0x01ec, IOException -> 0x01ce, Exception -> 0x01d6 }
        r25 = 0;
        r24.setUseCaches(r25);	 Catch:{ MalformedURLException -> 0x01ec, IOException -> 0x01ce, Exception -> 0x01d6 }
        r24 = connection;	 Catch:{ MalformedURLException -> 0x01ec, IOException -> 0x01ce, Exception -> 0x01d6 }
        r25 = "POST";
        r24.setRequestMethod(r25);	 Catch:{ MalformedURLException -> 0x01ec, IOException -> 0x01ce, Exception -> 0x01d6 }
        r24 = connection;	 Catch:{ MalformedURLException -> 0x01ec, IOException -> 0x01ce, Exception -> 0x01d6 }
        r25 = "Connection";
        r26 = "Keep-Alive";
        r24.setRequestProperty(r25, r26);	 Catch:{ MalformedURLException -> 0x01ec, IOException -> 0x01ce, Exception -> 0x01d6 }
        r24 = connection;	 Catch:{ MalformedURLException -> 0x01ec, IOException -> 0x01ce, Exception -> 0x01d6 }
        r25 = "Content-Type";
        r26 = new java.lang.StringBuilder;	 Catch:{ MalformedURLException -> 0x01ec, IOException -> 0x01ce, Exception -> 0x01d6 }
        r27 = "multipart/form-data;boundary=";
        r26.<init>(r27);	 Catch:{ MalformedURLException -> 0x01ec, IOException -> 0x01ce, Exception -> 0x01d6 }
        r0 = r26;
        r26 = r0.append(r2);	 Catch:{ MalformedURLException -> 0x01ec, IOException -> 0x01ce, Exception -> 0x01d6 }
        r26 = r26.toString();	 Catch:{ MalformedURLException -> 0x01ec, IOException -> 0x01ce, Exception -> 0x01d6 }
        r24.setRequestProperty(r25, r26);	 Catch:{ MalformedURLException -> 0x01ec, IOException -> 0x01ce, Exception -> 0x01d6 }
    L_0x0094:
        r10 = new java.io.DataOutputStream;	 Catch:{ MalformedURLException -> 0x01ec, IOException -> 0x01ce, Exception -> 0x01d6 }
        r24 = connection;	 Catch:{ MalformedURLException -> 0x01ec, IOException -> 0x01ce, Exception -> 0x01d6 }
        r24 = r24.getOutputStream();	 Catch:{ MalformedURLException -> 0x01ec, IOException -> 0x01ce, Exception -> 0x01d6 }
        r0 = r24;
        r10.<init>(r0);	 Catch:{ MalformedURLException -> 0x01ec, IOException -> 0x01ce, Exception -> 0x01d6 }
        r24 = new java.lang.StringBuilder;	 Catch:{ MalformedURLException -> 0x01ee, IOException -> 0x01e5, Exception -> 0x01de }
        r25 = java.lang.String.valueOf(r21);	 Catch:{ MalformedURLException -> 0x01ee, IOException -> 0x01e5, Exception -> 0x01de }
        r24.<init>(r25);	 Catch:{ MalformedURLException -> 0x01ee, IOException -> 0x01e5, Exception -> 0x01de }
        r0 = r24;
        r24 = r0.append(r2);	 Catch:{ MalformedURLException -> 0x01ee, IOException -> 0x01e5, Exception -> 0x01de }
        r0 = r24;
        r1 = r18;
        r24 = r0.append(r1);	 Catch:{ MalformedURLException -> 0x01ee, IOException -> 0x01e5, Exception -> 0x01de }
        r24 = r24.toString();	 Catch:{ MalformedURLException -> 0x01ee, IOException -> 0x01e5, Exception -> 0x01de }
        r0 = r24;
        r10.writeBytes(r0);	 Catch:{ MalformedURLException -> 0x01ee, IOException -> 0x01e5, Exception -> 0x01de }
        r24 = new java.lang.StringBuilder;	 Catch:{ MalformedURLException -> 0x01ee, IOException -> 0x01e5, Exception -> 0x01de }
        r25 = "Content-Disposition: form-data; name=\"file";
        r24.<init>(r25);	 Catch:{ MalformedURLException -> 0x01ee, IOException -> 0x01e5, Exception -> 0x01de }
        r0 = r24;
        r1 = r20;
        r24 = r0.append(r1);	 Catch:{ MalformedURLException -> 0x01ee, IOException -> 0x01e5, Exception -> 0x01de }
        r25 = "\";filename=\"";
        r24 = r24.append(r25);	 Catch:{ MalformedURLException -> 0x01ee, IOException -> 0x01e5, Exception -> 0x01de }
        r25 = r7[r16];	 Catch:{ MalformedURLException -> 0x01ee, IOException -> 0x01e5, Exception -> 0x01de }
        r24 = r24.append(r25);	 Catch:{ MalformedURLException -> 0x01ee, IOException -> 0x01e5, Exception -> 0x01de }
        r25 = "\"";
        r24 = r24.append(r25);	 Catch:{ MalformedURLException -> 0x01ee, IOException -> 0x01e5, Exception -> 0x01de }
        r0 = r24;
        r1 = r18;
        r24 = r0.append(r1);	 Catch:{ MalformedURLException -> 0x01ee, IOException -> 0x01e5, Exception -> 0x01de }
        r24 = r24.toString();	 Catch:{ MalformedURLException -> 0x01ee, IOException -> 0x01e5, Exception -> 0x01de }
        r0 = r24;
        r10.writeBytes(r0);	 Catch:{ MalformedURLException -> 0x01ee, IOException -> 0x01e5, Exception -> 0x01de }
        r0 = r18;
        r10.writeBytes(r0);	 Catch:{ MalformedURLException -> 0x01ee, IOException -> 0x01e5, Exception -> 0x01de }
        r13 = new java.io.File;	 Catch:{ MalformedURLException -> 0x01ee, IOException -> 0x01e5, Exception -> 0x01de }
        r24 = new java.lang.StringBuilder;	 Catch:{ MalformedURLException -> 0x01ee, IOException -> 0x01e5, Exception -> 0x01de }
        r25 = existingFileName;	 Catch:{ MalformedURLException -> 0x01ee, IOException -> 0x01e5, Exception -> 0x01de }
        r25 = java.lang.String.valueOf(r25);	 Catch:{ MalformedURLException -> 0x01ee, IOException -> 0x01e5, Exception -> 0x01de }
        r24.<init>(r25);	 Catch:{ MalformedURLException -> 0x01ee, IOException -> 0x01e5, Exception -> 0x01de }
        r25 = "/";
        r24 = r24.append(r25);	 Catch:{ MalformedURLException -> 0x01ee, IOException -> 0x01e5, Exception -> 0x01de }
        r25 = r7[r16];	 Catch:{ MalformedURLException -> 0x01ee, IOException -> 0x01e5, Exception -> 0x01de }
        r24 = r24.append(r25);	 Catch:{ MalformedURLException -> 0x01ee, IOException -> 0x01e5, Exception -> 0x01de }
        r24 = r24.toString();	 Catch:{ MalformedURLException -> 0x01ee, IOException -> 0x01e5, Exception -> 0x01de }
        r0 = r24;
        r13.<init>(r0);	 Catch:{ MalformedURLException -> 0x01ee, IOException -> 0x01e5, Exception -> 0x01de }
        r15 = new java.io.FileInputStream;	 Catch:{ MalformedURLException -> 0x01ee, IOException -> 0x01e5, Exception -> 0x01de }
        r15.<init>(r13);	 Catch:{ MalformedURLException -> 0x01ee, IOException -> 0x01e5, Exception -> 0x01de }
        r5 = r15.available();	 Catch:{ MalformedURLException -> 0x01c4, IOException -> 0x01e8, Exception -> 0x01e1 }
        r0 = r19;
        r4 = java.lang.Math.min(r5, r0);	 Catch:{ MalformedURLException -> 0x01c4, IOException -> 0x01e8, Exception -> 0x01e1 }
        r3 = new byte[r4];	 Catch:{ MalformedURLException -> 0x01c4, IOException -> 0x01e8, Exception -> 0x01e1 }
        r24 = 0;
        r0 = r24;
        r6 = r15.read(r3, r0, r4);	 Catch:{ MalformedURLException -> 0x01c4, IOException -> 0x01e8, Exception -> 0x01e1 }
    L_0x0133:
        if (r6 > 0) goto L_0x01a4;
    L_0x0135:
        r0 = r18;
        r10.writeBytes(r0);	 Catch:{ MalformedURLException -> 0x01c4, IOException -> 0x01e8, Exception -> 0x01e1 }
        r24 = new java.lang.StringBuilder;	 Catch:{ MalformedURLException -> 0x01c4, IOException -> 0x01e8, Exception -> 0x01e1 }
        r25 = java.lang.String.valueOf(r21);	 Catch:{ MalformedURLException -> 0x01c4, IOException -> 0x01e8, Exception -> 0x01e1 }
        r24.<init>(r25);	 Catch:{ MalformedURLException -> 0x01c4, IOException -> 0x01e8, Exception -> 0x01e1 }
        r0 = r24;
        r24 = r0.append(r2);	 Catch:{ MalformedURLException -> 0x01c4, IOException -> 0x01e8, Exception -> 0x01e1 }
        r0 = r24;
        r1 = r21;
        r24 = r0.append(r1);	 Catch:{ MalformedURLException -> 0x01c4, IOException -> 0x01e8, Exception -> 0x01e1 }
        r0 = r24;
        r1 = r18;
        r24 = r0.append(r1);	 Catch:{ MalformedURLException -> 0x01c4, IOException -> 0x01e8, Exception -> 0x01e1 }
        r24 = r24.toString();	 Catch:{ MalformedURLException -> 0x01c4, IOException -> 0x01e8, Exception -> 0x01e1 }
        r0 = r24;
        r10.writeBytes(r0);	 Catch:{ MalformedURLException -> 0x01c4, IOException -> 0x01e8, Exception -> 0x01e1 }
        r15.close();	 Catch:{ MalformedURLException -> 0x01c4, IOException -> 0x01e8, Exception -> 0x01e1 }
        r24 = 3;
        r0 = r20;
        r1 = r24;
        if (r0 != r1) goto L_0x019c;
    L_0x016d:
        r0 = r7.length;	 Catch:{ MalformedURLException -> 0x01c4, IOException -> 0x01e8, Exception -> 0x01e1 }
        r24 = r0;
        r24 = r24 + -1;
        r0 = r16;
        r1 = r24;
        if (r0 != r1) goto L_0x017e;
    L_0x0178:
        r10.flush();	 Catch:{ MalformedURLException -> 0x01c4, IOException -> 0x01e8, Exception -> 0x01e1 }
        r10.close();	 Catch:{ MalformedURLException -> 0x01c4, IOException -> 0x01e8, Exception -> 0x01e1 }
    L_0x017e:
        r24 = connection;	 Catch:{ IOException -> 0x01bf, MalformedURLException -> 0x01c4, Exception -> 0x01e1 }
        r24 = r24.getResponseCode();	 Catch:{ IOException -> 0x01bf, MalformedURLException -> 0x01c4, Exception -> 0x01e1 }
        res_code = r24;	 Catch:{ IOException -> 0x01bf, MalformedURLException -> 0x01c4, Exception -> 0x01e1 }
        r24 = res_code;	 Catch:{ IOException -> 0x01bf, MalformedURLException -> 0x01c4, Exception -> 0x01e1 }
        r25 = 200; // 0xc8 float:2.8E-43 double:9.9E-322;
        r0 = r24;
        r1 = r25;
        if (r0 != r1) goto L_0x019c;
    L_0x0190:
        r24 = new java.io.File;	 Catch:{ IOException -> 0x01bf, MalformedURLException -> 0x01c4, Exception -> 0x01e1 }
        r25 = existingFileName;	 Catch:{ IOException -> 0x01bf, MalformedURLException -> 0x01c4, Exception -> 0x01e1 }
        r24.<init>(r25);	 Catch:{ IOException -> 0x01bf, MalformedURLException -> 0x01c4, Exception -> 0x01e1 }
        deleteLastStep(r24);	 Catch:{ IOException -> 0x01bf, MalformedURLException -> 0x01c4, Exception -> 0x01e1 }
        r20 = 0;
    L_0x019c:
        r20 = r20 + 1;
        r16 = r16 + 1;
        r9 = r10;
        r14 = r15;
        goto L_0x0038;
    L_0x01a4:
        r24 = 0;
        r0 = r24;
        r10.write(r3, r0, r4);	 Catch:{ MalformedURLException -> 0x01c4, IOException -> 0x01e8, Exception -> 0x01e1 }
        r5 = r15.available();	 Catch:{ MalformedURLException -> 0x01c4, IOException -> 0x01e8, Exception -> 0x01e1 }
        r0 = r19;
        r4 = java.lang.Math.min(r5, r0);	 Catch:{ MalformedURLException -> 0x01c4, IOException -> 0x01e8, Exception -> 0x01e1 }
        r24 = 0;
        r0 = r24;
        r6 = r15.read(r3, r0, r4);	 Catch:{ MalformedURLException -> 0x01c4, IOException -> 0x01e8, Exception -> 0x01e1 }
        goto L_0x0133;
    L_0x01bf:
        r11 = move-exception;
        r11.printStackTrace();	 Catch:{ MalformedURLException -> 0x01c4, IOException -> 0x01e8, Exception -> 0x01e1 }
        goto L_0x019c;
    L_0x01c4:
        r12 = move-exception;
        r9 = r10;
        r14 = r15;
    L_0x01c7:
        r24 = connection;
        r24.disconnect();
        goto L_0x0041;
    L_0x01ce:
        r17 = move-exception;
    L_0x01cf:
        r24 = connection;
        r24.disconnect();
        goto L_0x0041;
    L_0x01d6:
        r11 = move-exception;
    L_0x01d7:
        r24 = connection;
        r24.disconnect();
        goto L_0x0041;
    L_0x01de:
        r11 = move-exception;
        r9 = r10;
        goto L_0x01d7;
    L_0x01e1:
        r11 = move-exception;
        r9 = r10;
        r14 = r15;
        goto L_0x01d7;
    L_0x01e5:
        r17 = move-exception;
        r9 = r10;
        goto L_0x01cf;
    L_0x01e8:
        r17 = move-exception;
        r9 = r10;
        r14 = r15;
        goto L_0x01cf;
    L_0x01ec:
        r12 = move-exception;
        goto L_0x01c7;
    L_0x01ee:
        r12 = move-exception;
        r9 = r10;
        goto L_0x01c7;
        */
        throw new UnsupportedOperationException("Method not decompiled: sample.projectList.ConnectionChangeReceiver.upload_image():void");
    }

    public static void delete_queue(File pic_queue) {
        if (pic_queue.isDirectory()) {
            String[] children = pic_queue.list();
            for (String file : children) {
                new File(pic_queue, file).delete();
            }
        }
    }

    public static void deleteLastStep(File pic_queue) {
        if (pic_queue.isDirectory()) {
            String[] children = pic_queue.list();
            for (int i = 0; i < 3; i++) {
                new File(pic_queue, children[i]).delete();
            }
        }
    }
}
