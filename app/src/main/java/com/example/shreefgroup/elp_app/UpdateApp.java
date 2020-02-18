package com.example.shreefgroup.elp_app;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.FileProvider;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class UpdateApp extends Activity {

    ProgressDialog bar;
    private static String TAG = "updateFragment";
    private AppController Application;
    private String version;
   // private MyWebReceiver receiver;
    private String appURI;
    private int versionCode;
    private DownloadManager downloadManager;

   @Override
   protected void onCreate(Bundle savedInstanceState) {
       super.onCreate(savedInstanceState);
       setContentView(R.layout.activity_update);
       if (android.os.Build.VERSION.SDK_INT > 9) {
           StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
           StrictMode.setThreadPolicy(policy);
       }
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        Application = ((AppController) getApplication());

        TextView heading    = (TextView) findViewById(R.id.heading);
        Button   update_btn = (Button) findViewById(R.id.btn);
    try {
                        PackageInfo pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
                        version = pInfo.versionName;
                        versionCode = pInfo.versionCode;
                    } catch (PackageManager.NameNotFoundException e) {
                        e.printStackTrace();
                    }
        heading.setText("Currunt App Version: " + version);
        /*IntentFilter filter = new IntentFilter(MyWebReceiver.PROCESS_RESPONSE);
        filter.addCategory(Intent.CATEGORY_DEFAULT);
        receiver = new MyWebReceiver();
        registerReceiver(receiver, filter);

        //Broadcast receiver for the download manager
        filter = new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE);
        registerReceiver(downloadReceiver, filter);

        //check of internet is available before making a web service request
        if(isNetworkAvailable(getActivity())){
            Intent msgIntent = new Intent(getActivity(), MyWebService.class);
            msgIntent.putExtra(MyWebService.REQUEST_STRING, "http://scorpio.sgroup.pk:8085/Apk/app-debug.apk");
            getActivity().startService(msgIntent);
        }
*/
        update_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new DownloadNewVersion().execute();

            }
        });
    }
/*

    @SuppressLint("StaticFieldLeak")
    class DownloadNewVersion extends AsyncTask<String,Integer,Boolean> {



        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            bar = new ProgressDialog(UpdateApp.this);
            bar.setCancelable(false);
            bar.setMessage("Downloading...12");
            bar.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
            bar.setIndeterminate(true);
            bar.setCanceledOnTouchOutside(false);
            bar.show();

        }

        protected void onProgressUpdate(Integer... progress) {
            super.onProgressUpdate(progress);

            bar.setIndeterminate(false);
            bar.setMax(100);
            bar.setProgress(progress[0]);
            String msg = "";
            if(progress[0]>99){

                msg="Finishing... ";

            }else {

                msg="Downloading... "+progress[0]+"%";
            }
            bar.setMessage(msg);

        }
        @Override
        protected void onPostExecute(Boolean result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);

            bar.dismiss();

            if(result){

            Toast.makeText(getApplicationContext(),"Update Done",
                    Toast.LENGTH_SHORT).show();

            }else{

            Toast.makeText(getApplicationContext(),"Error: Try Again",
                    Toast.LENGTH_SHORT).show();

            }

        }


        @Override
        protected Boolean doInBackground(String... arg0) {
            Boolean flag = false;

            try {


                //URL url = new URL("http://scorpio.sgroup.pk:8085/event_monitoring/Apk/app-debug.apk");
                URL url = new URL("http://scorpio.sgroup.pk:8085/Apk/ELP/app-debug.apk");


                HttpURLConnection c = (HttpURLConnection) url.openConnection();
                c.setRequestMethod("GET");
                c.setDoOutput(true);
                c.connect();


                String PATH = Environment.getExternalStorageDirectory()+"/Download/";
                File file = new File(PATH);
                file.mkdirs();

                File outputFile = new File(file,"app-debug.apk");

                if(outputFile.exists()){
                    outputFile.delete();
                }


                InputStream is = c.getInputStream();

                int total_size = 1431692;//size of apk

                byte[] buffer = new byte[1024];
                int len1 = 0;
                int per = 0;
                int downloaded=0;

                FileOutputStream fos = new FileOutputStream(outputFile);

                while ((len1 = is.read(buffer)) != -1) {
                    fos.write(buffer, 0, len1);
                    downloaded +=len1;
                    per = (int) (downloaded * 100 / total_size);
                    publishProgress(per);
                }
                fos.close();
                is.close();

                OpenNewVersion(PATH);

                flag = true;
            } catch (Exception e) {
                Log.e(TAG, "Update Error: " + e.getMessage());
                flag = false;
            }
            return flag;


        }

    }
*/



    @SuppressLint("StaticFieldLeak")
    class DownloadNewVersion extends AsyncTask<String, Integer, Boolean> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            bar = new ProgressDialog(UpdateApp.this);
            bar.setCancelable(false);

            bar.setMessage("Downloading...");

            bar.setIndeterminate(true);
            bar.setCanceledOnTouchOutside(false);
            bar.show();

        }

        protected void onProgressUpdate(Integer... progress) {
            super.onProgressUpdate(progress);

            bar.setIndeterminate(false);
            bar.setMax(100);
            bar.setProgress(progress[0]);
            String msg = "";
            if (progress[0] > 99) {

                msg = "Finishing... ";

            } else {

                msg = "Downloading... " + progress[0] + "%";
            }
            bar.setMessage(msg);

        }

        @Override
        protected void onPostExecute(Boolean result) {
            // TODO Auto-generated method stub
            super.onPostExecute(result);

            bar.dismiss();

            if (result) {

                Toast.makeText(getApplicationContext(), "Update Done",
                        Toast.LENGTH_SHORT).show();

            } else {

                Toast.makeText(getApplicationContext(), "Error: Try AgainN",
                        Toast.LENGTH_SHORT).show();

            }

        }

        @Override
        protected Boolean doInBackground(String... arg0) {
            Boolean flag = false;

            try {
                URL url = new URL("http://scorpio.sgroup.pk:8085/Apk/ELP/app-debug.apk");
                HttpURLConnection c = (HttpURLConnection) url.openConnection();
                c.setRequestMethod("GET");
                c.setDoOutput(true);
                c.connect();


                String PATH = Environment.getExternalStorageDirectory() + "/Download/";
                File file = new File(PATH);
                file.mkdirs();

                File outputFile = new File(file, "app-debug.apk");

                if (outputFile.exists()) {
                    outputFile.delete();
                }

                FileOutputStream fos = new FileOutputStream(outputFile);
                InputStream is = c.getInputStream();

                int total_size = 1431692;//size of apk

                byte[] buffer = new byte[1024];
                int len1 = 0;
                int per = 0;
                int downloaded = 0;
                while ((len1 = is.read(buffer)) != -1) {
                    fos.write(buffer, 0, len1);
                    downloaded += len1;
                    per = (int) (downloaded * 100 / total_size);
                    publishProgress(per);
                }
                fos.close();
                is.close();

                OpenNewVersion(PATH);

                flag = true;
            } catch (Exception e) {
                Log.d("apk_update", "Update Error: " + e.getMessage());
                flag = false;
            }
            return flag;

        }




        void OpenNewVersion(String location) {

            Intent intent = new Intent(Intent.ACTION_VIEW);
            Uri uri = FileProvider.getUriForFile(UpdateApp.this, BuildConfig.APPLICATION_ID + ".provider",
                    new File(location + "app-debug.apk"));

            intent.setDataAndType(uri,
                    "application/vnd.android.package-archive");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);

        }



    }


/*
    void OpenNewVersion(String location) {

        Intent intent = new Intent(Intent.ACTION_VIEW);
        Uri uri = FileProvider.getUriForFile(UpdateApp.this, BuildConfig.APPLICATION_ID + ".provider",
                new File(location + "app-debug.apk"));

        intent.setDataAndType(uri,
                "application/vnd.android.package-archive");

      *//*  intent.setDataAndType(Uri.fromFile(new File(location + "app-debug.apk")),
                "application/vnd.android.package-archive");*//*
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);

    }*/

    @Override
    public void onDestroy() {
        //unregister your receivers
       // this.unregisterReceiver(receiver);
        //this.unregisterReceiver(downloadReceiver);
        super.onDestroy();
    }

    //check for internet connection
    private boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < info.length; i++) {
                    Toast.makeText(getApplicationContext(),  String.valueOf(i), Toast.LENGTH_SHORT).show();

                    //Log.v(LOG_TAG,String.valueOf(i));
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        Toast.makeText(getApplicationContext(),  "connected!", Toast.LENGTH_SHORT).show();

                     //   Log.v(LOG_TAG, "connected!");
                        return true;
                    }
                }
            }
        }
        return false;
    }

 /*   //broadcast receiver to get notification when the web request finishes
    public class MyWebReceiver extends BroadcastReceiver{

        public static final String PROCESS_RESPONSE = "com.as400samplecode.intent.action.PROCESS_RESPONSE";

        @Override
        public void onReceive(Context context, Intent intent) {

            String reponseMessage = intent.getStringExtra(MyWebService.RESPONSE_MESSAGE);
          //  Log.v(LOG_TAG, reponseMessage);
            Toast.makeText(getContext().getApplicationContext(),  reponseMessage, Toast.LENGTH_SHORT).show();


            //parse the JSON response
            JSONObject responseObj;
            try {
                responseObj = new JSONObject(reponseMessage);
                boolean success = responseObj.getBoolean("success");
                //if the reponse was successful check further
                if(success){
                    //get the latest version from the JSON string
                    int latestVersion = responseObj.getInt("latestVersion");
                    //get the lastest application URI from the JSON string
                    appURI = responseObj.getString("appURI");
                    //check if we need to upgrade?
                    if(latestVersion > versionCode){
                        //oh yeah we do need an upgrade, let the user know send an alert message
                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        builder.setMessage("There is newer version of this application available, click OK to upgrade now?")
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    //if the user agrees to upgrade
                                    public void onClick(DialogInterface dialog, int id) {
                                        //start downloading the file using the download manager
                                        downloadManager = (DownloadManager)getActivity().getSystemService(DOWNLOAD_SERVICE);
                                        Uri Download_Uri = Uri.parse(appURI);
                                        DownloadManager.Request request = new DownloadManager.Request(Download_Uri);
                                        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI);
                                        request.setAllowedOverRoaming(false);
                                        request.setTitle("My Andorid App Download");
                                        request.setDestinationInExternalFilesDir(getContext(),Environment.DIRECTORY_DOWNLOADS,"MyAndroidApp.apk");
                                        downloadReference = downloadManager.enqueue(request);
                                    }
                                })
                                .setNegativeButton("Remind Later", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        // User cancelled the dialog
                                    }
                                });
                        //show the alert message
                        builder.create().show();
                    }

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

    }

    private long downloadReference;
    //broadcast receiver to get notification about ongoing downloads
    private BroadcastReceiver downloadReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {

            //check if the broadcast message is for our Enqueued download
            long referenceId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
            if(downloadReference == referenceId){
                Toast.makeText(getContext().getApplicationContext(), "Downloading of the new app version complete.", Toast.LENGTH_SHORT).show();
               // Log.v(LOG_TAG, "Downloading of the new app version complete");
                //start the installation of the latest version
                Intent installIntent = new Intent(Intent.ACTION_VIEW);
                installIntent.setDataAndType(downloadManager.getUriForDownloadedFile(downloadReference),
                        "application/vnd.android.package-archive");
                installIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(installIntent);

            }
        }
    };*/
 @Override
 public void onBackPressed() {
     startActivity(new Intent(UpdateApp.this, Registration.class));
     overridePendingTransition(R.anim.enter, R.anim.exit);

 }
}

