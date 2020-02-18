package com.example.shreefgroup.elp_app;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.telephony.TelephonyManager;
import android.telephony.gsm.GsmCellLocation;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.location.LocationRequest;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

import static com.example.shreefgroup.elp_app.Registration.rotateImage;


public class Arrival extends Activity implements LocationManagerInterface {
    public static final String TAG = Configuration.class.getSimpleName();
    private static final int REQUEST_TAKE_PHOTO = 1;
    private final static String path = Environment.getExternalStorageDirectory() + "/ELP" + "/";
    private final static String offlineArrival = "offlineArrival.txt";
    private static final int REQUEST_READ_PHONE_STATE = 0;
    private static String strSDCardPathName = path + "/ELP_Arrival" + "/";
    private static String strSDCardPathName2 = path + "/ELP_Arrivalupload" + "/";
    private ArrayList<String> arrayList5 = new ArrayList<String>();
    private ArrayList<String> arrayList7 = new ArrayList<String>();
    private int max = 1000;
    private String CellLocation, GSM_CELL_ID, GSM_Location_Code;
    String DeviceId ="";
    private String fistname = "";
    private String secondname = "";
    private String therdname = "";
    private SmartLocationManager mLocationManager;
    private File image;
    private BadgeView badge;
    private AppController application;
    private double LAT=0.0, LNG=0.0;
    private String resMessage = "";
    private Button btnTakePhoto;
    private Button btnqueue;
    private String tv2, tv3, tv4, tv5, tv6, tv7, tv8;
    private File photoFile;
    private File storageDir;
    private Uri uri;
    private String myfileName;
    private String timeStamp;
    private EditText ed;
    private int n = 1000;
    private int count = 0;
    private String tv;
    private ProgressDialog mProgressDialog;
    private HttpURLConnection conn;
    private int counter = 0;
    private File RemoveFile = new File(path, offlineArrival);
    private String check;
    private ArrayList<String> imageArray = new ArrayList<String>();

    /**************************************************************/
    public static void createFolder() {
        File folder = new File(strSDCardPathName);
        try {
            // Create folder
            if (!folder.exists()) {
                folder.mkdir();
            }
        } catch (Exception ex) {
        }

    }

    public static void createFolder2() {
        File folder = new File(strSDCardPathName2);
        try {
            if (!folder.exists()) {
                folder.mkdir();

            }
        } catch (Exception ex) {
        }

    }

    public static void clearFolder() {
        File dir = new File(strSDCardPathName2);
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int n = 0; n < children.length; n++) {
                new File(dir, children[n]).delete();
            }

        }
    }

    public static void clearFolder2() {
        File dir = new File(strSDCardPathName);
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int n = 0; n < children.length; n++) {
                new File(dir, children[n]).delete();
            }
        }
    }

    public static boolean isNetworkAvailable(Context nContext) {
        boolean isNetAvailable = false;
        if (nContext != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) nContext
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            if (mConnectivityManager != null) {
                boolean mobileNetwork = false;
                boolean wifiNetwork = false;
                boolean mobileNetworkConnecetd = false;
                boolean wifiNetworkConnecetd = false;
                NetworkInfo mobileInfo = mConnectivityManager
                        .getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
                NetworkInfo wifiInfo = mConnectivityManager
                        .getNetworkInfo(ConnectivityManager.TYPE_WIFI);
                if (mobileInfo != null)
                    mobileNetwork = mobileInfo.isAvailable();
                if (wifiInfo != null)
                    wifiNetwork = wifiInfo.isAvailable();
                if (wifiNetwork == true || mobileNetwork == true) {
                    if (mobileInfo != null)
                        mobileNetworkConnecetd = mobileInfo
                                .isConnectedOrConnecting();
                    wifiNetworkConnecetd = wifiInfo.isConnectedOrConnecting();
                }
                isNetAvailable = (mobileNetworkConnecetd || wifiNetworkConnecetd);
            }
        }
        return isNetAvailable;

    }

    public static void clearTheFile_offlineArrival() throws IOException {
        FileWriter fwOb = new FileWriter(path + offlineArrival, false);
        PrintWriter pwOb = new PrintWriter(fwOb, false);
        pwOb.flush();
        pwOb.close();
        fwOb.close();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);


        outState.putParcelable("ImageUri", uri);

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_READ_PHONE_STATE:
                if ((grantResults.length > 0) && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    //TODO
                }
                break;

            default:
                break;
        }
    }

    @SuppressLint("ObsoleteSdkInt")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arrival);
        application = (AppController) getApplicationContext();
        application.clearApplicationData();
        mLocationManager = new SmartLocationManager(getApplicationContext(), this, this, SmartLocationManager.ALL_PROVIDERS, LocationRequest.PRIORITY_HIGH_ACCURACY, 10 * 1000, 1 * 1000, SmartLocationManager.LOCATION_PROVIDER_RESTRICTION_NONE); // init location manager
        final int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE);
        btnTakePhoto = findViewById(R.id.OK);
        btnqueue = findViewById(R.id.queue);
        ed = findViewById(R.id.editArrival);
        ed.setText("");
        application = (AppController) getApplicationContext();
       /* if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }*/
        readfile();
      //
        if(arrayList7.size()>0){
            clearFolder();
            clearFolder2();
            CountFolder();
        }
        createFolder();
        createFolder2();

        arrayList7.clear();
        readfile_Offline();

        File dir = new File(strSDCardPathName2);
        if (dir.exists()) {
            CountFolder();

        }
        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        GsmCellLocation cellLocation = (GsmCellLocation) telephonyManager.getCellLocation();
        DeviceId = telephonyManager.getDeviceId();
     //   if (isNetworkAvailable(this)) {
            if (cellLocation != null) {
                String cellid = String.valueOf(cellLocation.getCid());
                CellLocation = cellLocation.toString();
                GSM_CELL_ID = cellid;
                GSM_Location_Code = cellid;
            }
      /*  } else {
            Toast.makeText(this, "NO Internet available", Toast.LENGTH_SHORT).show();
        }*/

        btnTakePhoto.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(Arrival.this, new String[]{Manifest.permission.READ_PHONE_STATE}, REQUEST_READ_PHONE_STATE);
                } else {
                    check = ed.getText().toString();
                    if (!(check.matches("")) || (check.equals("null"))) {
                        count = 0;
                        imageArray.clear();
                        capturImage();
                    } else {
                        ed.setError("Enter Registration_No!!! ");
                    }
                }
            }
        });
        btnqueue.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
              /*  arrayList7.clear();
                 readfile_Offline();*/
                dilouge_pendingQueue();
                CountFolder();

            }

        });
        for (int i = 0; i < arrayList5.size(); i++) {
            if (arrayList5.indexOf(0) != 0) {
                tv = arrayList5.get(0);
                tv2 = arrayList5.get(1);
                tv3 = arrayList5.get(2);
                tv4 = arrayList5.get(3);
                tv5 = arrayList5.get(4);
                tv6 = arrayList5.get(5);
                tv7 = arrayList5.get(6);
                tv8 = arrayList5.get(7);
                i = i + 8;
            }
        }
    }

    protected void dilouge_pendingQueue() {
        new AlertDialog.Builder(this)
                .setTitle("Take Action")
                .setMessage("Do you want to upload or delete photos?")
                .setPositiveButton("Upload", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        readfile_Offline();

                        if(isNetworkAvailable(getApplicationContext())) {
                        if(arrayList7.size()>0){

                                new UploadAsync(Arrival.this).execute();
                            }else {
                            Toast.makeText(getApplicationContext(), "No Pending data " +
                                    "", Toast.LENGTH_SHORT).show();
                            }
                        }else {

                            Toast.makeText(getApplicationContext(), "No Internet available", Toast.LENGTH_SHORT).show();


                        }


                    }
                }).setNegativeButton("Delete", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                clearFolder2();
                clearFolder();
                CountFolder();
                try {

                    clearTheFile_offlineArrival();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                finish();
                startActivity(getIntent());
            }
        })

                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();

    }

    public void capturImage() {
        Intent takePictureIntent;
        takePictureIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            photoFile = null;
            try {
                photoFile = createImageFile();

            } catch (IOException ex) {
                ex.getMessage();
            }
            if (photoFile != null) {

                uri = Uri.fromFile(photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);


            }
            startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);

        }

    }

    private File createImageFile() throws IOException {
        count++;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_hhmmss", Locale.getDefault());
        timeStamp = dateFormat.format(new Date());
        Random generator = new Random();
        n = generator.nextInt((max - n) + 1) + n;
        if (count == 1) {
            for (int i = 0; i < arrayList5.size(); i++) {
                tv = arrayList5.get(0);
                tv2 = arrayList5.get(1);
                tv3 = arrayList5.get(2);
                tv4 = arrayList5.get(3);
                tv5 = arrayList5.get(4);
                tv6 = arrayList5.get(5);
                tv7 = arrayList5.get(6);
                tv8 = arrayList5.get(7);
                i = i + 8;
            }
        }
        String myfileName = tv5 + ("-") + tv6 + ("-") + timeStamp + ("-") + DeviceId.substring(10, 15) + ("-") + n /*+ ".PNG"*/;
        storageDir = new File(strSDCardPathName);
        image = File.createTempFile(myfileName, /* prefix */
                ".PNG", /* suffix */
                storageDir /* directory */
        );
        return image;
    }

    /*public void capturImage() {
        count++;
        Intent takePictureIntent;
        takePictureIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            photoFile = null;
            try {
                photoFile = createImageFile();

            } catch (IOException ex) {
                ex.getMessage();
            }
            if (photoFile != null) {
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);

            }

        }
    }

    private File createImageFile() throws IOException {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_hhmmss", Locale.getDefault());
        timeStamp = dateFormat.format(new Date());
        Random generator = new Random();
        n = generator.nextInt((max - n) + 1) + n;
        if (count == 1) {
            for (int i = 0; i < arrayList5.size(); i++) {
                tv = arrayList5.get(0).toString();
                tv2 = arrayList5.get(1).toString();
                tv3 = arrayList5.get(2).toString();
                tv4 = arrayList5.get(3).toString();
                tv5 = arrayList5.get(4).toString();
                tv6 = arrayList5.get(5).toString();
                tv7 = arrayList5.get(6).toString();
                tv8 = arrayList5.get(7).toString();
                i = i + 8;
            }
        }
        String myfileName = tv5 + ("-") + tv6 + ("-") + timeStamp + ("-") + DeviceId.substring(10, 15) + ("-") + n *//*+ ".PNG"*//*;
        storageDir = new File(strSDCardPathName);
        image = File.createTempFile(myfileName, *//* prefix *//*
                ".png", *//* suffix *//*
                storageDir *//* directory *//*
        );
        return image;
    }
*/
    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
        if ((requestCode == REQUEST_TAKE_PHOTO) && (resultCode == RESULT_OK)) {
            String path = image.getAbsolutePath().trim();
            if (path == null)
                this.recreate();
            else
                decodeFile(path, 640, 480);
            if (count > 2) {
                count = 0;
                write_file_oflineArrival();
                for (int i = 0; i < imageArray.size(); i++) {
                    String mfileName = imageArray.get(i).trim();
                    FileHelper_offlineArrival.saveToFile(mfileName);
                }
                FileHelper_REGNO.saveToFile(check);
                FileHelper_offlineArrival.saveToFile(check);
                if (isNetworkAvailable(Arrival.this)) {
                    dilouge();
                    CountFolder();
                } else {
                    finish();
                   /// startActivity(getIntent());
                    Intent i = new Intent(getApplicationContext(),Arrival.class);
                    startActivity(i);


                   // CountFolder();
                }
            } else {
                capturImage();
            }
        }
        if (resultCode == RESULT_CANCELED) {
            count = 0;
            FileHelper_REGNO.saveToFile(myfileName);
            Toast.makeText(getApplicationContext(),
                    "User cancelled image capture", Toast.LENGTH_SHORT)
                    .show();

        } else {
            Toast.makeText(getApplicationContext(),
                    "Save Image", Toast.LENGTH_SHORT)
                    .show();
        }
    }

    public void uploadFiletoServer(String strSDPath, String strUrlServer) {
        int bytesRead, bytesAvailable, bufferSize;
        byte[] buffer;
        int maxBufferSize = 1 * 1024 * 1024;
        int resCode = 0;
        String lineEnd = "\r\n";
        String twoHyphens = "--";
        String boundary = "*****";
        try {
            File file = new File(strSDPath);
            if (!file.exists()) {
                return;
            }

            FileInputStream fileInputStream = new FileInputStream(new File(strSDPath));
            URL url = new URL(strUrlServer);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoInput(true); // Allow Inputs
            conn.setDoOutput(true); // Allow Outputs
            conn.setUseCaches(false); // Don't use a Cached Copy
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Connection", "Keep-Alive");
            conn.setRequestProperty("ENCTYPE", "multipart/form-data");
            conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=" + boundary);
            conn.setRequestProperty("uploaded_file", strSDPath);
            DataOutputStream outputStream = new DataOutputStream(conn.getOutputStream());
            outputStream.writeBytes(twoHyphens + boundary + lineEnd);
            outputStream.writeBytes("Content-Disposition: form-data; name=\"image\";filename=\""
                    + strSDPath + "\"" + lineEnd);
            outputStream.writeBytes(lineEnd);
            bytesAvailable = fileInputStream.available();
            bufferSize = Math.min(bytesAvailable, maxBufferSize);
            buffer = new byte[bufferSize];
            // Read file
            bytesRead = fileInputStream.read(buffer, 0, bufferSize);

            while (bytesRead > 0) {
                outputStream.write(buffer, 0, bufferSize);
                bytesAvailable = fileInputStream.available();
                bufferSize = Math.min(bytesAvailable, maxBufferSize);
                bytesRead = fileInputStream.read(buffer, 0, bufferSize);
            }

            outputStream.writeBytes(lineEnd);
            outputStream.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd);
            resCode = conn.getResponseCode();
            Log.d("res_code",resCode+"");
         //   if (resCode == HttpURLConnection.HTTP_OK) {
                InputStream is = conn.getInputStream();
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                int read = 0;
                while ((read = is.read()) != -1) {
                    bos.write(read);
                }

                byte[] result = bos.toByteArray();
                bos.close();


                 file.delete();
                resMessage = new String(result);
                Log.d("image_result",result.toString());

           /* }else{
                Toast.makeText(getApplicationContext(), " res code "+ resCode, Toast.LENGTH_SHORT).show();
            }*/

            fileInputStream.close();
            outputStream.flush();
            outputStream.close();

        } catch (Exception ex) {

            ex.printStackTrace();
        }
           File ff = new File(strSDPath);
          ff.delete();

    }

    public void CountFolder() {
        try {
            File file = new File(path + "/ELP_Arrivalupload" + "/");
            File[] list = file.listFiles();
            assert list != null;
            for (File f : list) {
                String name = f.getName();
                counter++;

            }
            badge = new BadgeView(this, btnqueue);
            badge.setText("Pending:" + counter);
            badge.show();
        }catch (Exception e){e.printStackTrace();}
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    private void dilouge() {
        new AlertDialog.Builder(this)
                .setTitle("Upload pictures")
                .setMessage("Are you sure you want to upload photos?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        try {
                            if(isNetworkAvailable(getApplicationContext())) {
                                readfile_Offline();
                                new UploadAsync(Arrival.this).execute();
                            }else {
                                Toast.makeText(getApplicationContext(), "No internet available", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }
                }).setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                try {
                    CountFolder();
                    recreate();
                    dialog.cancel();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        })

                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();

    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.cancel();
        }
        count = 0;
    }

    private void decodeFile(String path, int DESIREDWIDTH, int DESIREDHEIGHT) {
        String strMyImagePath = null;
        Bitmap scaledBitmap = null;
        try {
            // Part 1: Decode image
            Bitmap unscaledBitmap = ScalingUtilities.decodeFile(path, DESIREDWIDTH, DESIREDHEIGHT, ScalingUtilities.ScalingLogic.FIT);
            if (!(unscaledBitmap.getWidth() <= DESIREDWIDTH && unscaledBitmap.getHeight() <= DESIREDHEIGHT)) {
                // Part 2: Scale image
                scaledBitmap = ScalingUtilities.createScaledBitmap(unscaledBitmap, DESIREDWIDTH, DESIREDHEIGHT, ScalingUtilities.ScalingLogic.FIT);
            } else {
                unscaledBitmap.recycle();
                return;
            }
            if (android.os.Build.VERSION.SDK_INT == 24) {
                scaledBitmap = rotateImage(scaledBitmap, 90);
            }
            String extr = Environment.getExternalStorageDirectory() + "/ELP";
            File mFolder = new File(extr + "/ELP_Arrivalupload");
            if (!mFolder.exists()) {
                mFolder.mkdir();
            }
            Random generator = new Random();
            n = generator.nextInt(n);
            for (int i = 0; i < arrayList5.size(); i++) {
                tv5 = arrayList5.get(4);
                tv6 = arrayList5.get(5);
                i = i + 8;
            }
            if (count == 1) {
                myfileName = tv5 + ("-") + tv6 + ("-") + timeStamp + ("-") + DeviceId + ("-") + n + (".jpg");
                fistname = myfileName;
            }
            if (count == 2) {
                myfileName = tv5 + ("-") + tv6 + ("-") + timeStamp + ("-") + DeviceId + ("-") + n + (".jpg");
                secondname = myfileName;
            }
            if (count == 3) {
                myfileName = tv5 + ("-") + tv6 + ("-") + timeStamp + ("-") + DeviceId + ("-") + n + (".jpg");
                this.therdname = myfileName;
            }
            File f = new File(mFolder.getAbsolutePath(), myfileName);
            strMyImagePath = f.getAbsolutePath();
            imageArray.add(myfileName);
            FileOutputStream fos = null;
            try {
                fos = new FileOutputStream(f);
                scaledBitmap.compress(Bitmap.CompressFormat.JPEG, 90, fos);
                fos.flush();
                fos.close();
                image.delete();
            } catch (FileNotFoundException e) {

                e.printStackTrace();
            } catch (Exception e) {

                e.printStackTrace();
            }

            scaledBitmap.recycle();
        } catch (Throwable e) {
            e.getMessage();
            e.printStackTrace();
        }

        if (strMyImagePath == null) {
        }
    }

    @Override
    public void locationFetched(Location mLocal, Location oldLocation, String time, String locationProvider) {
        LAT = mLocal.getLatitude();
        LNG = mLocal.getLongitude();
    }

    public void readfile() {
        File file = new File(path, "SettingsELP.txt");
        StringBuilder text = new StringBuilder();
        arrayList5.clear();
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null) {
                String[] lineWords = line.split("line.separator");
                arrayList5.addAll(Arrays.asList(lineWords));
            }
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void showAlert(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Server Response")
                .setMessage("Fill Upload SuccessFully!...")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                        startActivity(getIntent());

                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private void save_pendingdata() throws IOException {
        for (int i = 0; i < 1; i++) {
            String t_v = "";
            String t_v2 = "";
            String t_v3 = "";
            String t_v4 = "";
            String t_v5 = "";
            String t_v6 = "";
            String t_v7 = "";
            String t_v8 = "";
            String t_v9 = "";
            String t_v10 = "";
            if (  (arrayList7.size() > 0)) {
                t_v4 = arrayList7.get(i + 3);
                t_v5 = arrayList7.get(i + 4);
                t_v7 = arrayList7.get(i + 6);
                t_v8 = arrayList7.get(i + 7);
                t_v9 = arrayList7.get(i + 8);
                t_v10 = arrayList7.get(i + 9);
                final String cell = GSM_Location_Code;//tv3.toString();
                final String Lpcode = t_v5;
                final String Cpcode = t_v4;
                final String mfilename = t_v7;
                final String mfilename2 = t_v8;
                final String mfilename3 = t_v9;
                final String Reg_no = t_v10;
                Log.d("values_0", cell+"\n"+Lpcode+"\n"+Cpcode+"\n"+mfilename+"\n"+mfilename2+"\n"+mfilename3+"\n"+
                        Reg_no+"\n"+DeviceId+"\n"
                        +LAT+"\n"+LNG);
                String url = AppController.baseUrl + AppController.URL3;



                if(cell!=null && Lpcode!=null && Cpcode!=null && mfilename!=null && mfilename2!= null &&
                        mfilename3!=null && DeviceId!=null && LAT!=0.0 && LNG!=0.0 && Reg_no!=null) {

                    final StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                            new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    Log.d("res_result", response.toString());
                                    Toast.makeText(Arrival.this, "Your data saved successfully!",
                                            Toast.LENGTH_SHORT).show();
                                    removelines();
                                    readfile_Offline();
                                    if (arrayList7.size() > 0) {
                                        try {

                                            if (arrayList7.isEmpty()) {
                                                clearFolder2();
                                                clearFolder();
                                                CountFolder();

                                            }

                                            save_pendingdata();
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    } else {
                                        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.
                                                Builder(Arrival.this);
                                        alertDialogBuilder.setTitle("Record Enter Successfully!");
                                        alertDialogBuilder
                                                .setCancelable(false)
                                                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int id) {
                                                        //  Registration.this.finish();
                                                        finish();
                                                    }
                                                });
                                        AlertDialog alertDialog = alertDialogBuilder.create();
                                        alertDialog.show();
                                    }
                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError volleyError) {
                                    if (volleyError.networkResponse == null) {
                                        if (volleyError.getClass().equals(TimeoutError.class)) {
                                            Toast.makeText(getApplicationContext(), "oops! network timeout error!", Toast.LENGTH_LONG).show();
                                        }
                                    }
                                }
                            }
                    ) {
                        @Override
                        protected Map<String, String> getParams() {
                            Map<String, String> params = new HashMap<String, String>();
                            params.put(AppController.KEY_CompanyCode, Cpcode);
                            params.put(AppController.KEY_LPCODE, Lpcode);
                            params.put(AppController.KEY_LAT, String.valueOf(LAT));
                            params.put(AppController.KEY_LNG, String.valueOf(LNG));
                            params.put(AppController.KEY_KPI_CELLID, cell);
                            params.put(AppController.KEY_file, mfilename);
                            params.put(AppController.KEY_file2, mfilename2);
                            params.put(AppController.KEY_file3, mfilename3);
                            params.put(AppController.KEY_REG_NUM, Reg_no);
                            params.put(AppController.KEY_IME, DeviceId);
                            return params;
                        }


                    };
                    stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                            500000,
                            500000,
                            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                    i = i + 9;
                    MySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
                }else {
                    Toast.makeText(getApplicationContext(), "vlues is emty", Toast.LENGTH_SHORT).show();
                }

            }else {
                Toast.makeText(getApplicationContext(), "no data taken from file", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void write_file_oflineArrival() {
        if (!FileHelper_offlineArrival.saveToFile(tv)) {
            Toast.makeText(Arrival.this, "Error save file!!!", Toast.LENGTH_SHORT).show();
        }
        if (!FileHelper_offlineArrival.saveToFile(tv2)) {
            Toast.makeText(Arrival.this, "Error save file!!!", Toast.LENGTH_SHORT).show();
        }
        if (!FileHelper_offlineArrival.saveToFile(tv3)) {
            Toast.makeText(Arrival.this, "Error save file!!!", Toast.LENGTH_SHORT).show();
        }
        if (!FileHelper_offlineArrival.saveToFile(tv5)) {
            Toast.makeText(Arrival.this, "Error save file!!!", Toast.LENGTH_SHORT).show();
        }
        if (!FileHelper_offlineArrival.saveToFile(tv6)) {
            Toast.makeText(Arrival.this, "Error save file!!!", Toast.LENGTH_SHORT).show();
        }

        if (!FileHelper_offlineArrival.saveToFile(tv7)) {

            Toast.makeText(Arrival.this, "Error save file!!!", Toast.LENGTH_SHORT).show();
        }
            /*if (FileHelper_offlineArrival.saveToFile(ed.getText().toString())) {
            } else {
                Toast.makeText(Arrival.this, "Error save file!!!", Toast.LENGTH_SHORT).show();
            }*/


    }

    public void readfile_Offline() {
        File file = new File(path, "offlineArrival.txt");
        StringBuilder text = new StringBuilder();
        arrayList7.clear();

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null) {
              //  String[] lineWords = line.split("line.separator");
                arrayList7.add(line);
            }
            br.close();
        } catch (IOException ignored) {
        }

    }

    public void removelines() {
        for (int i = 0; i <= 9; i++) {
            try {

                removeLine(RemoveFile, 0);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }

    public void removeLine(final File file, final int lineIndex) throws IOException {
        final List<String> lines = new LinkedList<>();
        final Scanner reader = new Scanner(new FileInputStream(file), "UTF-8");
        while (reader.hasNextLine())
            lines.add(reader.nextLine());
        reader.close();
        assert lineIndex >= 0 && lineIndex <= lines.size() - 1;
        lines.remove(lineIndex);
        final BufferedWriter writer = new BufferedWriter(new FileWriter(file, false));
        for (final String line : lines)
            writer.write(line + System.getProperty("line.separator"));
        writer.flush();
        writer.close();
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this, Registration.class));
        finish();
        this.overridePendingTransition(R.anim.enter, R.anim.exit);
        //   super.onBackPressed();
    }

    // Upload Image in Background
    @SuppressLint("StaticFieldLeak")
    public class UploadAsync extends AsyncTask<String, Void, Void> {
        UploadAsync(Arrival activity) {
            mProgressDialog = new ProgressDialog(activity);
            mProgressDialog.setTitle("Uploading");
            mProgressDialog.setMessage("Uploading please wait.....");
            mProgressDialog.setCancelable(false);
            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            mProgressDialog.setCanceledOnTouchOutside(false);


        }

        protected void onPreExecute() {
            super.onPreExecute();
            if (mProgressDialog != null) {
                mProgressDialog.show();
            }
        }

        @Override
        protected Void doInBackground(String... par) {
            // *** Upload all file to Server
            File file = new File(strSDCardPathName2);//strSDCardPathName2
            File[] files = file.listFiles();
            assert files != null;
            for (File sfil : files) {
                if (sfil.isFile()) {
                    uploadFiletoServer(sfil.getAbsolutePath(), AppController.baseUrl + AppController.strURLUpload_Arrival);


                }
            }

            try {
                save_pendingdata();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }

        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            super.onPostExecute(result);
            try {
                URL url = new URL(AppController.baseUrl + AppController.strUrlServer);
                conn = (HttpURLConnection) url.openConnection();
                int resCode = conn.getResponseCode();
                if (resCode == HttpURLConnection.HTTP_OK) {

                    clearFolder2();
                    clearFolder();
                    //clearTheFile_offlineArrival();
                    if (resMessage == null) {
                        resMessage = "No Images Found";
                    }
                    showAlert(resMessage);

                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            mProgressDialog.cancel();

        }


    }
}



