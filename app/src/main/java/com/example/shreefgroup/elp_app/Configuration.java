package com.example.shreefgroup.elp_app;

/**
 * Created by ashfaq on 11/18/2016.
 */

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;
import android.telephony.gsm.GsmCellLocation;
import android.text.format.Formatter;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.LocationRequest;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;


public class Configuration extends Registration implements LocationManagerInterface {

    public static final String TAG = Configuration.class.getSimpleName();
    final static String fileName = "SettingsELP.txt";
    private static final int MY_NOTIFICATION_ID = 0;
    public String CellLocation, GSM_CELL_ID, GSM_Location_Code;
    public EditText editTextCTID;
    public EditText editTextServerIP;
    public EditText editTextcompanycode;
    public EditText editTextLPCODE;
    public EditText editTextServerHost;
    public EditText editTextLNG;
    public EditText editTextLAT;
    public String DeviceId;
    protected EditText editTextPassword;
    AppController application;
    ArrayList<String> arrayList5 = new ArrayList<String>();
    double LAT, LNG;
    TextView mLocalTV, mLocationProviderTV, mlocationTimeTV;
    SmartLocationManager mLocationManager;
    Button btnGPS;
    Location mLocal;
    Location oldLocation;
    String time;
    String locationProvider;
    private TelephonyManager m_manager;
    private PhoneStateListener m_listener;
    private Button btnMode;
    private ListView list;
    private Context context = Configuration.this;
    private String getSimNumber;
    private Button btnRead;
    private TextView txtContent;
    private String tv;
    private String tv2;
    private String tv3;
    private String tv4;
    private String tv5;
    private String tv6;
    private String tv7;
    private String tv8;
    private String tv9;
    private String mydir;
    private File file2 = null;
    private int READ_BLOCK_SIZE = 100;
    private TextView tv22;
    private GPSTracker gps;


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

    public void clearTheFile() throws IOException {
        FileWriter fwOb = new FileWriter(path + fileName, false);
        PrintWriter pwOb = new PrintWriter(fwOb, false);
        pwOb.flush();
        pwOb.close();
        fwOb.close();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.configration);
        overridePendingTransition(R.anim.enter, R.anim.exit);
        application = (AppController) getApplicationContext();
        mLocationManager = new SmartLocationManager(getApplicationContext(), this, this, SmartLocationManager.ALL_PROVIDERS, LocationRequest.PRIORITY_HIGH_ACCURACY, 10 * 1000, 1 * 1000, SmartLocationManager.LOCATION_PROVIDER_RESTRICTION_NONE); // init location manager
        readfile();
        editTextLNG = (EditText) findViewById(R.id.LNG);
        editTextLAT = (EditText) findViewById(R.id.LAT);
        editTextCTID = (EditText) findViewById(R.id.CID);
        editTextPassword = (EditText) findViewById(R.id.password);
        editTextcompanycode = (EditText) findViewById(R.id.companycode);
        editTextServerIP = (EditText) findViewById(R.id.ServerIP);
        editTextLPCODE = (EditText) findViewById(R.id.LPCODE);
        editTextServerHost = (EditText) findViewById(R.id.ServerHost);
        editTextServerHost.setEnabled(false);
        btnGPS = (Button) findViewById(R.id.btnGPS);
        application = (AppController) getApplicationContext();
        editTextServerIP.setText("http://scorpio.sgroup.pk:8085");
        editTextCTID.setText("0.0");
        editTextLNG.setText("0.0");
        editTextLAT.setText("0.0");
        editTextPassword.setText("12345");
        editTextcompanycode.setText("11");
        editTextLPCODE.setText("0001");
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
            application.baseUrl = tv7.toString();
        }
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(path + fileName));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        try {
            File dir44 = new File(path + fileSetting);
            if (dir44.exists()) {
                if (br.readLine() != null) {
                    // System.out.println("No errors, and file empty");
                    application.baseUrl = tv7.toString();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        //editTextLAT.setEnabled(false);
        //editTextLNG.setEnabled(false);
        editTextPassword.setText(tv4);
        editTextLPCODE.setText(tv6);
        editTextcompanycode.setText(tv5);

        txtContent = (TextView) findViewById(R.id.txtContent);
        WifiManager wm = (WifiManager) getApplicationContext().getSystemService(WIFI_SERVICE);
        String host = Formatter.formatIpAddress(wm.getConnectionInfo().getIpAddress());
        editTextServerHost.setText(host);
        gps = new GPSTracker(Configuration.this);

        // check if GPS enabled
        gps = new GPSTracker(Configuration.this);

        // check if GPS enabled
        if (gps.canGetLocation()) {

            LAT = gps.getLatitude();
            LNG = gps.getLongitude();
            // editTextLAT.setText("" + gps.getLatitude());
            //editTextLNG.setText("" + gps.getLongitude());

        } else {

            gps.showSettingsAlert();
        }
        TelephonyManager telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        GsmCellLocation cellLocation = (GsmCellLocation) telephonyManager.getCellLocation();
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        DeviceId = telephonyManager.getDeviceId();
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
        btnRead = (Button) findViewById(R.id.btnRead);
        btnRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                txtContent.setText(FileHelper_Settings.ReadFile(Configuration.this));
            }
        });

        btnGPS = (Button) findViewById(R.id.btnGPS);
        btnGPS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String CmCode = editTextcompanycode.getText().toString();
                String Lpcode = editTextLPCODE.getText().toString();
                if (CmCode.length() == 2 && Lpcode.length() == 4) {
                    try {
                        clearTheFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (FileHelper_Settings.saveToFile(editTextLNG.getText().toString())) {
                        Toast.makeText(Configuration.this, "Saved to file", Toast.LENGTH_SHORT).show();
                    }
                    if (FileHelper_Settings.saveToFile(editTextLAT.getText().toString())) {
                        //Toast.makeText(My2.this,"Saved to file",Toast.LENGTH_SHORT).show();
                    }
                    if (FileHelper_Settings.saveToFile(editTextCTID.getText().toString())) {
                        // Toast.makeText(My2.this,"Saved to file",Toast.LENGTH_SHORT).show();
                    }
                    if (FileHelper_Settings.saveToFile(editTextPassword.getText().toString())) {
                        // Toast.makeText(My2.this,"Saved to file",Toast.LENGTH_SHORT).show();}
                    }
                    if (FileHelper_Settings.saveToFile(editTextcompanycode.getText().toString())) {
                        // Toast.makeText(My2.this,"Saved to file",Toast.LENGTH_SHORT).show();
                    }
                    if (FileHelper_Settings.saveToFile(editTextLPCODE.getText().toString())) {
                        // Toast.makeText(My2.this,"Saved to file",Toast.LENGTH_SHORT).show();}
                    }

                    if (FileHelper_Settings.saveToFile(editTextServerIP.getText().toString())) {
                    }
                    if (FileHelper_Settings.saveToFile(editTextServerHost.getText().toString())) {
                        startActivity(new Intent(Configuration.this, Registration.class));
                        overridePendingTransition(R.anim.enter, R.anim.exit);
                    } else {
                        Toast.makeText(Configuration.this, "Error save file!!!", Toast.LENGTH_SHORT).show();

                    }
                } else {
                    Toast.makeText(Configuration.this, "Sorry LP code min length is 4 char and Company Code min length is 2!!!", Toast.LENGTH_SHORT).show();

                }
            }

        });

    }

    public void readfile() {
        File file = new File(path, "SettingsELP.txt");
        StringBuilder text = new StringBuilder();
        arrayList5.clear();
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null) {
                String lineWords[] = line.split(" ");
                for (String singleWord : lineWords) {
                    arrayList5.add(singleWord);
                }
            }
            br.close();
        } catch (IOException e) {
        }

    }

    protected void onStart() {
        super.onStart();
        if (mLocationManager == null) {
            mLocationManager.startLocationFetching();
        }
    }

    protected void onStop() {
        super.onStop();
        //mLocationManager.abortLocationFetching();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //mLocationManager.pauseLocationFetching();
    }

    @Override
    public void locationFetched(Location mLocal, Location oldLocation, String time, String locationProvider) {
        editTextLAT.setText("" + mLocal.getLatitude());
        editTextLNG.setText("" + mLocal.getLongitude());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button1:
                //do somthing
                break;


        }
    }
    @Override
    public void onBackPressed() {
        startActivity(new Intent(Configuration.this, Registration.class));
        finish();
        this.overridePendingTransition(R.anim.enter, R.anim.exit);
        //super.onBackPressed();

    }
}
