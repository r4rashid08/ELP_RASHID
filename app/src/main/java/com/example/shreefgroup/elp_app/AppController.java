package com.example.shreefgroup.elp_app;

/**
 * Created by ashfaq on 11/14/2016.
 */

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;
import android.text.TextUtils;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;

import java.io.File;
import java.util.ArrayList;


public class AppController extends Application {
    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);

    }

    public static final String TAG = AppController.class.getSimpleName();
    public static final String KEY_IME = "IMEI";
    public static final String KEY_REG_NUM = "REG_NO";
    public static final String KEY_CompanyCode = "COMPANY_CODE";
    public static final String KEY_LPCODE = "LP_CODE";
    public static final String KEY_KPI_CELLID = "CELL_ID";
    public static final String KEY_VALUE = "VALUE";
    public static final String KEY_TRANTIME = "TRAN_TIME";
    public static final String KEY_TRAN_DATE = "TRAN_DATE";
    public static final String KEY_LAT = "LAT_VAL";
    public static final String KEY_LNG = "LONG_VAL";
    public static final String KEY_file = "FILENAME";
    public static final String KEY_file2 = "FILENAME2";
    public static final String KEY_file3 = "FILENAME3";
    public static final String KEY_Reg = "REGISTRATION";
    public static final String KEY_simNo = "MOBILE";
    public static final String IMAGE_DIRECTORY_NAME = "Images";
    public static String baseUrl = "";
    public static final String strURLUpload_Arrival = "/cpl/andriod_conn/upload_arrival.php";
    public static final String strUrlServer_Arrival = "/cpl/andriod_conn/upload.php";
    public static final String strURLUpload = "/cpl/andriod_conn/upload.php";
    public static final String strUrlServer = "/cpl/andriod_conn/upload.php";
    public static final String URL4 = "/cpl/andriod_conn/GET_REGNO.PHP";
    public static final String IMEI = "/MobileApps/eEms/IMEI_NO.PHP";
    public static final String URL2 = "/cpl/andriod_conn/ELP_CONFIG2.PHP";
    public static final String URL3 = "/cpl/andriod_conn/elp_arrival.PHP";
    public static final String FILE_UPLOAD_URL = "/cpl/andriod_conn/uploadFile.php";
    public static final String DATA_URL = "/cpl/andriod_conn/retrievedata.php";
    public static final String Verify = "/cpl/andriod_conn/VerifyReg_No.php";
    public static final String DELETE_LIST_ITEM = "/cpl/andriod_conn/DELETE_REG.php";
    public static final String DELETE_All_LIST_ITEM = "/cpl/andriod_conn/DELETE_ALLREG.php";
    private static AppController mInstance;
    public String tv1 = "";
    public String tv2 = "";
    public String tv3 = "";
    public String tv4 = "";
    public String tv5 = "";
    public String tv6 = "";
    private RequestQueue mRequestQueue;
    private ImageLoader mImageLoader;
    public static synchronized AppController getInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }
    public ImageLoader getImageLoader() {
        getRequestQueue();
        if (mImageLoader == null) {
            mImageLoader = new ImageLoader(this.mRequestQueue,
                    new LruBitmapCache());
        }
        return this.mImageLoader;
    }

    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        }

        return mRequestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req, String tag) {
        req.setTag(TextUtils.isEmpty(tag) ? TAG : tag);
        getRequestQueue().add(req);
    }

    public <T> void addToRequestQueue(Request<T> req) {
        req.setTag(TAG);
        getRequestQueue().add(req);
    }

    public void cancelPendingRequests(Object tag) {
        if (mRequestQueue != null) {
            mRequestQueue.cancelAll(tag);
        }
    }

    public void clearApplicationData() {
        File cache = getCacheDir();
        File appDir = new File(cache.getParent());
        if (appDir.exists()) {
            String[] children = appDir.list();
            for (String s : children) {
                if (!s.equals("lib")) {
                    deleteDir(new File(appDir, s));
                    Log.i("TAG", "File /data/data/APP_PACKAGE/" + s + " DELETED");
                }
            }
        }
    }

    public static boolean deleteDir(File dir) {
        if (dir != null && dir.isDirectory()) {
            String[] children = dir.list();
            for (int i = 0; i < children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }

        return dir.delete();
    }
}