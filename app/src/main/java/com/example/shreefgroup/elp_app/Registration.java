package com.example.shreefgroup.elp_app;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.location.Location;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;
import android.telephony.gsm.GsmCellLocation;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

/**
 * Created by ashfaq on 12/18/2016.
 */
@SuppressWarnings("unchecked")
public class Registration extends Activity implements LocationManagerInterface, ActivityCompat.OnRequestPermissionsResultCallback, View.OnClickListener {
    final static String path = Environment.getExternalStorageDirectory() + "/ELP" + "/";
    final static String fileSetting = "SettingsELP.txt";
    final static String file_IMEI = "IMEI_NO.txt";
    private static final int REQUEST_TAKE_PHOTO = 1;
    private final static String fileName = "Pendingdata.txt";
    private final static String fileMode = "AppMode.txt";
    private final static String CAPTURED_PHOTO_PATH_KEY = "mCurrentPhotoPath";
    private static final int REQUEST_READ_PHONE_STATE = 0;
    private static final int ACCESS_COARSE_LOCATION = 0;
    private static String strSDCardPathName = path + "/ELP_PICS" + "/";
    private static String strSDCardPathName2 = path + "/upload" + "/";
    private static String modefile = path + "AppMode.txt";
    private static String mCurrentPhotoPath;
    private static InputStream is = null;
    private static JSONObject jObj = null;
    private static String json = "";
    private String CellLocation, GSM_CELL_ID, GSM_Location_Code;
    private String DeviceId;
    private File image;
    private BadgeView badge;
    private AppController application;
    private int n = 1000;
    private String[] web = {
            "Registration Mode",
            "Arrival Mode",
            "Both Mode",

    };
    private ArrayList<String> arrayList5 = new ArrayList<String>();
    private ArrayList<String> arrayList7 = new ArrayList<String>();
    private ArrayList<String> arrayListMode = new ArrayList<String>();
    private ArrayList<String> arrayIndex = new ArrayList<String>();
    private String resMessage = "";
    private SmartLocationManager mLocationManager;
    private double LAT, LNG;
    private ProgressDialog loading = null;
    private int IMEI_NUM;
    private String indexid;
    private File file;
    private String end_date;
    private Date date;
    private String currunt_date;
    private Date date2;
    private String fistname;
    private String secondname;
    private String therdname;
    private Button btnconfig;
    private Button btnTakePhoto;
    private Button btnqueue;
    private String imageFileName;
    private File photoFile;
    private File storageDir;
    private Uri uri;
    private String myfileName;
    private String timeStamp;
    private Button btnArrival;
    private Button btnMode;
    private String user_text;
    private int count = 0;
    private ProgressDialog mProgressDialog;
    private String tv, tv2, tv3, tv4, tv5, tv6, tv7, tv8, tv9;
    private String txv, txv2, txv3, txv4, txv5, txv6, txv7, txv8, txv9, txv10;
    private HttpURLConnection conn;
    private int resCode = 0;
    private Intent takePictureIntent;
    private Button btn_Record;
    private Button btnupdate;
    private int counter = 0;
    private String md;
    private String md2;
    private String modecheck;
    private String mfilename, mfilename2, mfilename3;
    private File RemoveFile = new File(path, fileName);
    private int max = 1000;
    protected static final int REQUEST_CHECK_SETTINGS = 0x1;
    private int permissionCheck = 0;
    private int flage = 0;
    private int[] grantResults;
    private static final int MY_PERMISSIONS_REQUEST_READ_CONTACTS = 1;
    private ArrayList<String> imageArray = new ArrayList<String>();
    ;


    /****************************************************/
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

    public static void createFolder() {
        File folder = new File(strSDCardPathName);
        try {
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

    public static void createFolderElp() {
        File folder = new File(path);
        try {
            if (!folder.exists()) {
                folder.mkdir();
            }
        } catch (Exception ex) {
        }

    }

    public static void clearFolder() {
        File dir = new File(strSDCardPathName);
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int n = 0; n < children.length; n++) {
                new File(dir, children[n]).delete();
            }
        }
    }

    public static void clearFolder2() {
        File dir = new File(strSDCardPathName2);
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int n = 0; n < children.length; n++) {
                new File(dir, children[n]).delete();
            }
        }
    }

    protected boolean shouldAskPermissions() {
        return (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);
    }

    @TargetApi(android.os.Build.VERSION_CODES.M)
    protected void askPermissions() {
        String[] permissions = {
                "android.permission.READ_EXTERNAL_STORAGE",
                "android.permission.WRITE_EXTERNAL_STORAGE",
                "android.permission.READ_PHONE_STATE",
                "android.permission.ACCESS_COARSE_LOCATION",
                "android.permission.ACCESS_FINE_LOCATION",
                "android.permission.ACCESS_NETWORK_STATE",
                "android.permission.CONTROL_LOCATION_UPDATES",
                "android.permission.INTERNET",
                "android.permission.INSTALL_LOCATION_PROVIDER",
                "android.permission.INSTALL_PACKAGES",
                "android.permission.WRITE_SECURE_SETTINGS",
                "android.permission.WRITE_GSERVICES",
                "android.permission.CAMERA"
        };
        int requestCode = 200;
        requestPermissions(permissions, requestCode);
    }

    public void clearTheFile() throws IOException {
        FileWriter fwOb = new FileWriter(path + fileName, false);
        PrintWriter pwOb = new PrintWriter(fwOb, false);
        pwOb.flush();
        pwOb.close();
        fwOb.close();
    }

    public void clearMode() throws IOException {
        FileWriter fwOb = new FileWriter(path + fileMode, false);
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
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        uri = savedInstanceState.getParcelable("ImageUri");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_READ_PHONE_STATE:
                if ((grantResults.length > 0) && (grantResults[0] == PackageManager.PERMISSION_GRANTED)) {

                } else {
                    closeNow();
                }
                break;

            default:
                break;
        }
    }

    private void closeNow() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            finishAffinity();
        } else {
            finish();
        }
    }

    @TargetApi(Build.VERSION_CODES.M)
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        try {
            displayLocationSettingsRequest(this);
            this.overridePendingTransition(R.anim.left_right, R.anim.right_left);
            getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
            application = (AppController) getApplicationContext();
            application.clearApplicationData();
            if (shouldAskPermissions()) {
                askPermissions();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
/*
        if (checkSelfPermission(Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) {


            if (shouldShowRequestPermissionRationale(
                    Manifest.permission.READ_PHONE_STATE)) {

            }

            requestPermissions(new String[]{Manifest.permission.READ_PHONE_STATE},
                    REQUEST_READ_PHONE_STATE);


            return;
        }
*/
        final int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE);
        final int permissionCheck2 = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION);
        if ((permissionCheck != PackageManager.PERMISSION_GRANTED) && (permissionCheck2 != PackageManager.PERMISSION_GRANTED)) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, REQUEST_READ_PHONE_STATE);
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_COARSE_LOCATION}, ACCESS_COARSE_LOCATION);
        } else {
            config();
        }


    }

    private void config() {

        btnTakePhoto = (Button) findViewById(R.id.btnTakePhoto);
        btnqueue = (Button) findViewById(R.id.btnqueue);
        btnArrival = (Button) findViewById(R.id.btnArrival);
        btnMode = (Button) findViewById(R.id.mode);
        btnconfig = (Button) findViewById(R.id.btnConfigration);
        btn_Record = (Button) findViewById(R.id.btncheckvichle);
        btnupdate = (Button) findViewById(R.id.btnupdate);
        try {
            readfile_curruntdata();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        try {
            mLocationManager = new SmartLocationManager(getApplicationContext(), this, this, SmartLocationManager.ALL_PROVIDERS, LocationRequest.PRIORITY_HIGH_ACCURACY, 10 * 1000, 1 * 1000, SmartLocationManager.LOCATION_PROVIDER_RESTRICTION_NONE);
        } catch (Exception e) {
            e.getMessage();
        }
        try {
            final int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE);
            if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_PHONE_STATE}, REQUEST_READ_PHONE_STATE);
            }
            TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
            GsmCellLocation cellLocation = (GsmCellLocation) telephonyManager.getCellLocation();
            if (telephonyManager != null) {
                DeviceId = telephonyManager.getDeviceId();
            } else {
                Toast.makeText(this, "Device id Not Found", Toast.LENGTH_SHORT).show();

            }
            if (isNetworkAvailable(this)) {
                if (cellLocation != null) {
                    String cellid = String.valueOf(cellLocation.getCid());
                    CellLocation = cellLocation.toString();
                    GSM_CELL_ID = String.valueOf(cellid);
                    GSM_Location_Code = String.valueOf(cellid);
                }
            } else {
                Toast.makeText(this, "No Service Provider Available", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            e.getMessage();
        }
        String pattern = "dd-MMM-yy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        currunt_date = simpleDateFormat.format(new Date());
        try {
            date = simpleDateFormat.parse(currunt_date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        File dir = new File(strSDCardPathName2);
        if (dir.exists()) {
            CountFolder2();
        }
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            builder.detectFileUriExposure();
        }
        try {
            new File(path).mkdir();
            file = new File(path + file_IMEI);
            if (!file.exists()) {

                file.createNewFile();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            for (int i = 0; i < arrayIndex.size(); i++) {
                if (arrayIndex.indexOf(0) != 0) {
                    indexid = arrayIndex.get(0).toString();

                }
            }
            if ((indexid == null) || (indexid.equals(null)) || (indexid.equals(""))) {
                verifyIMEI();
            }
            for (int i = 0; i < arrayIndex.size(); i++) {
                if (arrayIndex.indexOf(0) != 0) {
                    indexid = arrayIndex.get(0).toString();

                }
            }
            if (indexid != null) {
                date2 = simpleDateFormat.parse(indexid);
            }


        } catch (ParseException e) {
            e.printStackTrace();
        }
        if ((indexid != null) && (date2.compareTo(date) < 0)) {
            Toast.makeText(this, "Sorry! No access\n Only Authenticated user allowed here ", Toast.LENGTH_LONG).show();
            finish();
        }
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {

        }
        readfileELPSatting();
        for (int i = 0; i < arrayList5.size(); i++) {
            txv = arrayList5.get(0).toString();
            txv2 = arrayList5.get(1).toString();
            txv3 = arrayList5.get(2).toString();
            txv4 = arrayList5.get(3).toString();
            txv5 = arrayList5.get(4).toString();
            txv6 = arrayList5.get(5).toString();
            txv7 = arrayList5.get(6).toString();
            txv8 = arrayList5.get(7).toString();
            i = i + 8;
            application.baseUrl = txv7.toString();
        }
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(path + fileSetting));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            File dir44 = new File(path + fileSetting);
            if (dir44.exists()) {
                if (br.readLine() != null) {
                    application.baseUrl = txv7.toString();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        createFolder();
        createFolder2();
        createFolderElp();
        btnTakePhoto.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(Registration.this, new String[]{Manifest.permission.READ_PHONE_STATE}, REQUEST_READ_PHONE_STATE);
                } else {
                    try {
                        imageArray.clear();
                        count = 0;
                        capturImage();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            }
        });

        btnconfig.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                password();

            }
        });
        btnupdate.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), UpdateApp.class));
                overridePendingTransition(R.anim.enter, R.anim.exit);


            }
        });
        btnArrival.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                startActivity(new Intent(Registration.this, Arrival.class));
                overridePendingTransition(R.anim.enter, R.anim.exit);

            }
        });
        btnqueue.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                dilouge_Queue();
            }

        });
        btnMode.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                lock();
            }
        });
        btn_Record.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isNetworkAvailable(Registration.this)) {
                    loading = new ProgressDialog(Registration.this);
                    loading.setCancelable(true);
                    loading.setMessage("Please wait...fetching data...");
                    loading.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    loading.show();
                    startActivity(new Intent(Registration.this, CheckRegistrations.class));
                    overridePendingTransition(R.anim.enter, R.anim.exit);

                } else {
                    Toast.makeText(Registration.this, "Not Connected to Internet", Toast.LENGTH_SHORT).show();
                }

            }
        });
        try {
            File dir3 = new File(modefile);
            if (dir3.exists()) {
                setmode();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void setmode() throws IOException {
        readfileAppMode();
        for (int i = 0; i < arrayListMode.size(); i++) {
            md = arrayListMode.get(0).toString();
            md2 = arrayListMode.get(1).toString();
            i = i + 2;
            modecheck = md.toString() + " " + md2.toString();
        }
        if (modecheck.equals(web[0])) {
            btnMode.setText(web[0]);
            btnArrival.setEnabled(false);
            btnTakePhoto.setEnabled(true);
            btn_Record.setEnabled(true);
        } else if (modecheck.equals(web[1])) {
            btnMode.setText(web[1]);
            btnArrival.setEnabled(true);
            btnTakePhoto.setEnabled(false);
            btn_Record.setEnabled(false);
        } else if (modecheck.equals(web[2])) {
            btnMode.setText(web[2]);
            btnArrival.setEnabled(true);
            btnTakePhoto.setEnabled(true);
            btn_Record.setEnabled(true);
        }
    }

    public void changeMode() {
        final String[] options = {web[0], web[1], web[2]};
        AlertDialog.Builder builder = new AlertDialog.Builder(Registration.this);
        builder.setTitle("Please select the mode to apply");
        builder.setItems(options, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (options[which].equals(web[0])) {
                    btnMode.setText(web[0]);
                    user_text = btnMode.getText().toString();
                    if (user_text.equals(web[0])) {
                        try {
                            clearMode();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        write_Mode();
                        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
                        btnArrival.setEnabled(false);
                        btnTakePhoto.setEnabled(true);
                    }
                } else if (options[which].equals(web[1])) {
                    btnMode.setText(web[1]);
                    user_text = btnMode.getText().toString();
                    if (user_text.equals(web[1])) {
                        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
                        try {
                            clearMode();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        write_Mode();
                        btnTakePhoto.setEnabled(false);
                        btn_Record.setEnabled(false);
                        btnArrival.setEnabled(true);
                    }
                } else if (options[which].equals(web[2])) {

                    btnMode.setText(web[2]);
                    user_text = btnMode.getText().toString();
                    if (user_text.equals(web[2])) {
                        try {
                            clearMode();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        write_Mode();
                        btnArrival.setEnabled(true);
                        btnTakePhoto.setEnabled(true);
                        btn_Record.setEnabled(true);
                    }
                }
            }
        });
        builder.show();
    }

    public void capturImage() {
        //count++;
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
        String myfileName = tv5 + ("-") + tv6 + ("-") + timeStamp + ("-") + DeviceId.substring(10, 15) + ("-") + n /*+ ".PNG"*/;
        storageDir = new File(strSDCardPathName);
        image = File.createTempFile(myfileName, /* prefix */
                ".PNG", /* suffix */
                storageDir /* directory */
        );
        return image;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if ((requestCode == REQUEST_TAKE_PHOTO) && (resultCode == RESULT_OK)) {
            try {
                String path = image.getAbsolutePath().trim();
                if (path == null)
                    this.recreate();
                else
                decodeFile(path, 640, 480);
                if ((count > 2) && (!(path.equals(null)))) {
                    write_file_offline();
                    for (int i = 0; i < imageArray.size(); i++) {
                        String mfileName = imageArray.get(i).trim();
                        FileHelper_offlineRegistration.saveToFile(mfileName);
                    }
                    if (isNetworkAvailable(this)) {
                        try {
                            dilouge();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    } else {
                        CountFolder2();
                        this.recreate();
                    }
                } else {
                    capturImage();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (resultCode == RESULT_CANCELED) {
            count = 0;
            try {
                CountFolder2();
                this.recreate();
            } catch (Exception e) {
                e.printStackTrace();
            }
            Toast.makeText(getApplicationContext(),
                    "User cancelled image capture", Toast.LENGTH_SHORT)
                    .show();

        } else {
            Toast.makeText(getApplicationContext(),
                    "save Image", Toast.LENGTH_SHORT)
                    .show();
        }

    }

    public boolean uploadFiletoServer(String strSDPath, String strUrlServer) {
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
                return false;
            }

            FileInputStream fileInputStream = new FileInputStream(new File(strSDPath));
            URL url = new URL(strUrlServer);
            conn = (HttpURLConnection) url.openConnection();
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setUseCaches(false);
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
            if (resCode == HttpURLConnection.HTTP_OK) {
                InputStream is = conn.getInputStream();
                ByteArrayOutputStream bos = new ByteArrayOutputStream();
                int read = 0;
                while ((read = is.read()) != -1) {
                    bos.write(read);
                }
                byte[] result = bos.toByteArray();
                bos.close();
                resMessage = new String(result);
            }
            fileInputStream.close();
            outputStream.flush();
            outputStream.close();
            return true;
        } catch (Exception ex) {
            return false;
        }
    }

    public void CountFolder2() {
        try {
            File file = new File(path + "/upload" + "/");
            File[] list = file.listFiles();
            for (File f : list) {
                String name = f.getName();
                counter++;
                if (name.endsWith(".jpg") || name.endsWith(".mp3") || name.endsWith(".some media extention")) {

                }
            }
            badge = new BadgeView(this, btnqueue);
            badge.setText("Pending:" + counter);
            badge.show();
        } catch (Exception e) {
            e.getMessage();
        }
    }

    private void dilouge() {
        new AlertDialog.Builder(this)
                .setTitle("Upload pictures")
                .setMessage("Are you sure you want to upload photos?")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        readfile_offline();
                        new UploadAsync(Registration.this).execute();

                    }
                }).setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                try {
                    CountFolder2();
                    recreate();
                    dialog.cancel();
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        }).setIcon(android.R.drawable.ic_dialog_alert)
                .show();

    }

    protected void dilouge_Queue() {
        new AlertDialog.Builder(this)
                .setTitle("Take Action")
                .setMessage("Are you sure you want to upload photos?")
                .setPositiveButton("Upload", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        readfile_offline();
                        new UploadAsync(Registration.this).execute();
                    }
                }).setNegativeButton("Delete", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                clearFolder2();
                clearFolder();
                CountFolder2();
                try {
                    clearTheFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                finish();
                startActivity(getIntent());
            }
        }).setIcon(android.R.drawable.ic_dialog_alert)
                .show();

    }

    public void password() {
        LayoutInflater li = LayoutInflater.from(Registration.this);
        View promptsView = li.inflate(R.layout.prompts, null);
        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                Registration.this);
        alertDialogBuilder.setView(promptsView);

        final EditText userInput = (EditText) promptsView
                .findViewById(R.id.editTextDialogUserInput);
        alertDialogBuilder
                .setCancelable(true)
                .setIcon(R.mipmap.lock)
                .setTitle("Authentication")
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                String user_text = (userInput.getText()).toString();
                                readfileELPSatting();
                                if (txv4 == null) {
                                    txv4 = "12345";
                                }
                                if (user_text.equals(txv4)) {
                                    startActivity(new Intent(Registration.this, Configuration.class));
                                    //overridePendingTransition(R.anim.enter, R.anim.exit);


                                } else {
                                    String message = "The password you have entered is incorrect." + " \n \n" + "Please try again!";
                                    final AlertDialog.Builder builder = new AlertDialog.Builder(Registration.this);
                                    builder.setTitle("Error");
                                    builder.setMessage(message);
                                    // builder.setPositiveButton("Cancel", null);
                                    builder.setNegativeButton("Retry", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int id) {
                                            password();
                                        }
                                    });
                                    builder.create().show();

                                }
                            }
                        });
                /*.setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });*/
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    public void lock() {
        LayoutInflater li = LayoutInflater.from(Registration.this);
        View promptsView = li.inflate(R.layout.prompts, null);

        final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                Registration.this);
        alertDialogBuilder.setView(promptsView);

        final EditText userInput = (EditText) promptsView
                .findViewById(R.id.editTextDialogUserInput);
        alertDialogBuilder
                .setCancelable(true)
                .setTitle("Authentication")
                .setIcon(R.mipmap.lock)
                .setPositiveButton("OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                String user_text = (userInput.getText()).toString();
                                readfileELPSatting();
                                for (int i = 0; i < arrayList5.size(); i++) {
                                    txv = arrayList5.get(0).toString();
                                    txv2 = arrayList5.get(1).toString();
                                    txv3 = arrayList5.get(2).toString();
                                    txv4 = arrayList5.get(3).toString();
                                    txv5 = arrayList5.get(4).toString();
                                    txv6 = arrayList5.get(5).toString();
                                    txv7 = arrayList5.get(6).toString();
                                    txv8 = arrayList5.get(7).toString();
                                    i = i + 8;
                                }
                                if (user_text.equals(txv4)) {
                                    changeMode();
                                } else {
                                    String message = "The password you entered is incorrect...Please Try again!";
                                    final AlertDialog.Builder builder = new AlertDialog.Builder(Registration.this);
                                    builder.setTitle("Error");
                                    builder.setMessage(message);
                                    builder.setNegativeButton("\n" +
                                            "Try again", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int id) {
                                            lock();
                                        }
                                    });
                                    builder.create().show();

                                }
                            }
                        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private void save_pendingdata() throws IOException {
        for (int i = 0; i < 1; i++) {
            String tev4 = null;
            String tev5 = null;
            String tev7 = null;
            String tev8 = null;
            String tev9 = null;
            if ((arrayList7.indexOf(0) != 0) && (arrayList7.size() > 0)) {
                String tev = arrayList7.get(i).toString();
                String tev2 = arrayList7.get(i + 1).toString();
                String tev3 = arrayList7.get(i + 2).toString();
                tev4 = arrayList7.get(i + 3).toString();
                tev5 = arrayList7.get(i + 4).toString();
                String tev6 = arrayList7.get(i + 5).toString();
                tev7 = arrayList7.get(i + 6).toString();
                tev8 = arrayList7.get(i + 7).toString();
                tev9 = arrayList7.get(i + 8).toString();


                final String cell = GSM_Location_Code;
                final String Cpcode = tev4.toString();
                final String Lpcode = tev5.toString();
                final String mfilename = tev7.toString();
                final String mfilename2 = tev8.toString();
                final String mfilename3 = tev9.toString();
                String URL = application.baseUrl + application.URL2;
                final StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Toast.makeText(Registration.this, "Your Data Saved Successfully!",
                                        Toast.LENGTH_LONG).show();
                                removelines();
                                readfile_offline();
                                if (arrayList7.size() > 0) {
                                    try {
                                        save_pendingdata();
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        },
                        new Response.ErrorListener() {

                            @Override
                            public void onErrorResponse(VolleyError volleyError) {
                                if (volleyError.networkResponse == null) {
                                    if (volleyError.getClass().equals(TimeoutError.class)) {
                                        Toast.makeText(getApplicationContext(), "Oops. Network Timeout error!", Toast.LENGTH_LONG).show();
                                    }
                                }
                            }
                        }) {
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
                        params.put(AppController.KEY_IME, DeviceId);
                        return params;
                    }


                };
                stringRequest.setRetryPolicy(new DefaultRetryPolicy(
                        500000,
                        500000,
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
                i = i + 8;
                MySingleton.getInstance(getApplicationContext()).addToRequestQueue(stringRequest);
            }
        }
    }

    private String decodeFile(String path, int DESIREDWIDTH, int DESIREDHEIGHT) {
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
                return path;
            }
            if (android.os.Build.VERSION.SDK_INT == 24) {
                scaledBitmap = rotateImage(scaledBitmap, 90);
            }
            // String extr = Environment.getExternalStorageDirectory().toString() + "/ELP";
            File mFolder = new File(strSDCardPathName2);
            if (!mFolder.exists()) {
                mFolder.mkdir();
            }
            Random generator = new Random();
            n = generator.nextInt(n);
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
            if (count == 1) {
                myfileName = tv5 + ("-") + tv6 + ("-") + timeStamp + ("-") + DeviceId + ("-") + n + (".jpg");
                fistname = myfileName.toString();
            }
            if (count == 2) {
                myfileName = tv5 + ("-") + tv6 + ("-") + timeStamp + ("-") + DeviceId + ("-") + n + (".jpg");
                secondname = myfileName.toString();
            }
            if (count == 3) {
                myfileName = tv5 + ("-") + tv6 + ("-") + timeStamp + ("-") + DeviceId + ("-") + n + (".jpg");
                this.therdname = myfileName.toString();
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
        }

        if (strMyImagePath == null) {
            return path;
        }
        return strMyImagePath;

    }

    public static Bitmap rotateImage(Bitmap source, float angle) {
        Matrix matrix = new Matrix();
        //matrix.postRotate(angle);
        matrix.setRotate(angle);
        return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(),
                matrix, true);
    }

    private void showAlert(String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Response from Servers")
                .setMessage(message)
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

    public void readfileELPSatting() {
        File file = new File(path, "SettingsELP.txt");
        StringBuilder text = new StringBuilder();
        arrayList5.clear();
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null) {
                String lineWords[] = line.split("line.separator");
                for (String singleWord : lineWords) {
                    arrayList5.add(singleWord);
                }
            }
            br.close();
        } catch (IOException e) {
        }
    }

    public void readfileAppMode() {
        File file = new File(path, "AppMode.txt");
        StringBuilder text = new StringBuilder();
        arrayListMode.clear();
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null) {
                String lineWords[] = line.split(" ");
                for (String singleWord : lineWords) {
                    arrayListMode.add(singleWord);
                }
            }
            br.close();
        } catch (IOException e) {
        }

    }

    public void write_file_offline() {
        if (FileHelper_offlineRegistration.saveToFile(tv.toString())) {
            Toast.makeText(Registration.this, "Saved to file", Toast.LENGTH_SHORT).show();
        }
        if (FileHelper_offlineRegistration.saveToFile(tv2.toString())) {
        }
        if (FileHelper_offlineRegistration.saveToFile(tv3.toString())) {
        }
        if (FileHelper_offlineRegistration.saveToFile(tv5.toString())) {
        }
        if (FileHelper_offlineRegistration.saveToFile(tv6.toString())) {
        }

        if (FileHelper_offlineRegistration.saveToFile(tv7.toString())) {
        } else {
            Toast.makeText(Registration.this, "Error save file!!!", Toast.LENGTH_SHORT).show();
        }
    }


    public void write_Mode() {
        FileHelper_Mode.saveToFile(user_text.toString());
    }

    public void readfile_offline() {
        File file = new File(path, fileName);
        StringBuilder text = new StringBuilder();
        arrayList7.clear();
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null) {
                String lineWords[] = line.split("line.separator");
                for (String singleWord : lineWords) {
                    arrayList7.add(singleWord);
                }
            }
            br.close();
        } catch (IOException e) {
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        turnGPSOn();
        try {
            setMobileDataEnabled(Registration.this, true);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (loading != null) {
            loading.cancel();
        }
    }

    protected void onStop() {
        super.onStop();

    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.cancel();
        }
        count = 0;
    }

    @Override
    public void locationFetched(Location mLocal, Location oldLocation, String time, String locationProvider) {
        LAT = mLocal.getLatitude();
        LNG = mLocal.getLongitude();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button1:
                break;


        }
    }

    @Override
    public void onBackPressed() {
        finish();
        this.overridePendingTransition(R.anim.enter, R.anim.exit);
        super.onBackPressed();
    }

    private void turnGPSOn() {
        String provider = Settings.Secure.getString(getContentResolver(), Settings.Secure.LOCATION_PROVIDERS_ALLOWED);

        if (!provider.contains("gps")) { //if gps is disabled

            final Intent poke = new Intent();
            poke.setClassName("com.android.settings", "com.android.settings.widget.SettingsAppWidgetProvider");
            poke.addCategory(Intent.CATEGORY_ALTERNATIVE);
            poke.setData(Uri.parse("3"));
            sendBroadcast(poke);
        }
    }


    private void setMobileDataEnabled(Context context, boolean enabled) throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        final ConnectivityManager conman = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        final Class conmanClass = Class.forName(conman.getClass().getName());
        final Field iConnectivityManagerField = conmanClass.getDeclaredField("mService");
        iConnectivityManagerField.setAccessible(true);
        final Object iConnectivityManager = iConnectivityManagerField.get(conman);
        final Class iConnectivityManagerClass = Class.forName(iConnectivityManager.getClass().getName());
        final Method setMobileDataEnabledMethod = iConnectivityManagerClass.getDeclaredMethod("setMobileDataEnabled", Boolean.TYPE);
        setMobileDataEnabledMethod.setAccessible(true);

        setMobileDataEnabledMethod.invoke(iConnectivityManager, enabled);
    }

    public void disableMobileData() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        final ConnectivityManager conman = (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        Method dataMtd = ConnectivityManager.class.getDeclaredMethod("setMobileDataEnabled", boolean.class);
        dataMtd.setAccessible(false);
        dataMtd.invoke(conman, false);
    }

    public void readfile_curruntdata() {
        File file = new File(path, file_IMEI);
        StringBuilder text = new StringBuilder();
        arrayIndex.clear();
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null) {
                String lineWords[] = line.split("line.separator");
                for (String singleWord : lineWords) {
                    arrayIndex.add(singleWord);
                }
            }
            br.close();
        } catch (IOException e) {
        }

    }

    public void SetConfigurations() {
        File myFile = new File(new StringBuilder(String.valueOf(Environment.getExternalStorageDirectory().getAbsolutePath())).append("/SettingsELP.txt").toString());
        if (!myFile.exists() || myFile.length() <= 0) {
            Toast.makeText(getApplicationContext(), "Configurations Not Set", Toast.LENGTH_LONG).show();
            startActivity(new Intent(this, Configuration.class));

        }
    }

    public void removelines() {
        for (int i = 0; i < 9; i++) {
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

    public void verifyIMEI() {
        final ProgressDialog pDialog = new ProgressDialog(this);
        pDialog.setMessage("Loading...");
        pDialog.setCancelable(false);
        pDialog.show();
        int app_id = 5;
        String url = "http://scorpio.sgroup.pk:8085" + application.IMEI + "?IMEI_NO=" + DeviceId + "&&mob_app_id=" + app_id;
        StringRequest strReq = new StringRequest(Request.Method.GET,
                url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, response.toString());
                try {
                    JSONObject object = new JSONObject(response);
                    JSONArray Jarray = object.getJSONArray("Result");

                    for (int i = 0; i < Jarray.length(); i++) {
                        JSONObject jsonObject = Jarray.getJSONObject(i);
                        IMEI_NUM = Integer.parseInt(jsonObject.getString("COUNT(*)"));
                        end_date = jsonObject.getString("END_DATE");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if (IMEI_NUM == 1) {
                    if (FileHelper_IMEI.saveToFile(String.valueOf(end_date))) {
                        Toast.makeText(Registration.this, "data write on file ",
                                Toast.LENGTH_SHORT).show();
                        pDialog.hide();

                    }
                } else {

                    new AlertDialog.Builder(Registration.this)
                            .setTitle("IMEI CHECK")
                            .setMessage("Sorry! your imei number is not registerd ")
                            .setIcon(R.mipmap.lock)
                            .setCancelable(false)
                            .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    finish();
                                }
                            }).show();
                    pDialog.hide();


                }

            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
                pDialog.hide();
            }
        });
        strReq.setRetryPolicy(new DefaultRetryPolicy(
                500000,
                500000,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getInstance(getApplicationContext()).addToRequestQueue(strReq);

    }


    public class UploadAsync extends AsyncTask<String, Void, Void> {

        public UploadAsync(Registration activity) {
            mProgressDialog = new ProgressDialog(activity);
            mProgressDialog.setTitle("Uploading");
            mProgressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            mProgressDialog.setCancelable(false);
            mProgressDialog.setMessage("Uploading please wait.....");
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

            File file = new File(strSDCardPathName2);
            File[] files = file.listFiles();
            for (File sfil : files) {
                if (sfil.isFile()) {
                    uploadFiletoServer(sfil.getAbsolutePath(), application.baseUrl + application.strURLUpload);

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
            try {
                URL url = new URL(application.baseUrl + application.strUrlServer);
                conn = (HttpURLConnection) url.openConnection();
                resCode = conn.getResponseCode();
                if (resCode == HttpURLConnection.HTTP_OK) {
                    clearFolder2();
                    clearFolder();
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

    private void displayLocationSettingsRequest(Context context) {
        GoogleApiClient googleApiClient = new GoogleApiClient.Builder(context)
                .addApi(LocationServices.API).build();
        googleApiClient.connect();

        LocationRequest locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(10000);
        locationRequest.setFastestInterval(10000 / 2);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder().addLocationRequest(locationRequest);
        builder.setAlwaysShow(true);

        PendingResult<LocationSettingsResult> result = LocationServices.SettingsApi.checkLocationSettings(googleApiClient, builder.build());
        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(LocationSettingsResult result) {
                final Status status = result.getStatus();
                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        Log.i(TAG, "All location settings are satisfied.");
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        Log.i(TAG, "Location settings are not satisfied. Show the user a dialog to upgrade location settings ");

                        try {
                            // Show the dialog by calling startResolutionForResult(), and check the result
                            // in onActivityResult().
                            status.startResolutionForResult(Registration.this, REQUEST_CHECK_SETTINGS);
                        } catch (IntentSender.SendIntentException e) {
                            Log.i(TAG, "PendingIntent unable to execute request.");
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        Log.i(TAG, "Location settings are inadequate, and cannot be fixed here. Dialog not created.");
                        break;
                }
            }
        });

    }
}
