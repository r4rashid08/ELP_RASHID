package com.example.shreefgroup.elp_app;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;

//import static com.google.android.gms.analytics.internal.zzy.v;

public class CheckRegistrations extends Activity implements View.OnClickListener {
    AppController application;
    String item;
    ProgressDialog loading = null;
    ArrayList<String> arraylist = new ArrayList<String>();
   ArrayList<String> arrayList5 = new ArrayList<String>();
    private ProgressDialog pDialog;
    private Object jsonResponse = "";
    private String middle3;
    private ListView list;
    private ArrayAdapter adapter;
    private String reg;
    private String tv, tv2, tv3, tv4, tv5, tv6, tv7, tv8;
    private Button done;
    private View view;
    private static InputStream is = null;
    private static JSONObject jObj = null;
    private static String json = "";
    final static String path = Environment.getExternalStorageDirectory() + "/ELP" + "/";
    public static final String TAG = Configuration.class.getSimpleName();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkveichle);
        list = (ListView) findViewById(R.id.spinner1);
        application = (AppController) getApplicationContext();
        done = (Button)findViewById(R.id.button1);
        done.setVisibility(View.VISIBLE);
        readfileELPSatting();
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

        try {
            CheckVehicalRegistration(tv6);

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        HashSet<String> hashSet = new HashSet<String>();
        hashSet.addAll(arraylist);
        arraylist.clear();
        arraylist.addAll(hashSet);
        Collections.reverse(arraylist);
        adapter = new ArrayAdapter<String>(this, R.layout.list_item2, R.id.UnitName, arraylist);
        list.setAdapter(adapter);
        Collections.reverseOrder();
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @TargetApi(Build.VERSION_CODES.M)
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                item = parent.getItemAtPosition(position).toString();
                reg = item.substring(item.lastIndexOf("-") + 1).trim();
                dilouge();


            }
        });

    }
    public void readfileELPSatting() {
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
    private void dilouge() {
        new AlertDialog.Builder(this)
                .setTitle("Delete entry")
                .setMessage("Are you sure you want to delete this entry?؟")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        deleteData(reg);
                        finish();
                        startActivity(getIntent());
                    }


                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing

                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();

    }

    private void deleteData(String item) {
        loading = new ProgressDialog(this);
        loading.setCancelable(true);
        loading.setMessage("Please wait ...");
        loading.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        loading.show();
        String url = application.baseUrl + application.DELETE_LIST_ITEM + "?REGNO=" + item.toString();
        final JsonArrayRequest req = new JsonArrayRequest(url,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        Toast.makeText(CheckRegistrations.this, "Record Delete Successfully", Toast.LENGTH_LONG).show();
                        loading.cancel();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d("volly", "Error: " + error.getMessage());

            }
        });

        AppController.getInstance().addToRequestQueue(req);

    }

    private void deleteAllData(String lpcode) {
        loading = new ProgressDialog(this);
        loading.setCancelable(true);
        loading.setMessage("Please wait...");
        loading.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        loading.show();
        String url = application.baseUrl + application.DELETE_All_LIST_ITEM + "?LPCODE=" + lpcode.toString().trim();
        StringRequest strReq = new StringRequest(
                url, new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                Log.d(TAG, response.toString());
                arraylist.clear();
                adapter = new ArrayAdapter<String>(CheckRegistrations.this, R.layout.list_item2, R.id.UnitName, arraylist);
                list.setAdapter(adapter);
                loading.cancel();
                Toast.makeText(CheckRegistrations.this, "Deleted Successfully", Toast.LENGTH_LONG).show();


            }
        }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(TAG, "Error: " + error.getMessage());
            }
        });

        AppController.getInstance().addToRequestQueue(strReq);
    }

    public void CheckVehicalRegistration(String tv5) throws JSONException, IOException {
        loading = new ProgressDialog(this);
        loading.setCancelable(true);
        loading.setMessage("Loading Pleas Wait...");
        loading.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        loading.show();
        String url = application.baseUrl + application.URL4 + "?LPCODE=" + tv5.toString();
        JSONObject object = new JSONObject(String.valueOf((getJSONUrl(url))));
        JSONArray Jarray = object.getJSONArray("Result");
        try {
            for (int i = 0; i < Jarray.length(); i++) {
                JSONObject jsonObject = Jarray.getJSONObject(i);
                String REGNO = jsonObject.getString("REGNO");
                jsonResponse = REGNO;
                arrayList11(REGNO);

            }


        } catch (JSONException e) {
            e.printStackTrace();

        }
        loading.cancel();

    }

    public JSONObject getJSONUrl(String url1) throws IOException {
        URL url = new URL(url1);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        is = (InputStream) conn.getContent();
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    is, "iso-8859-1"), 8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            is.close();
            json = sb.toString();
        } catch (Exception e) {
            Log.e("Buffer Error", "Error converting result " + e.toString());
        }

        // try parse the string to a JSON object
        try {
            jObj = new JSONObject(json);
        } catch (JSONException e) {
            Log.e("JSON Parser", "Error parsing data " + e.toString());
        }

        // return JSON String
        return jObj;

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
    }

    public void arrayList11(String regno) {
        arraylist.add(regno);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.button1:
                alert_delete();
                break;


        }
    }
    public void alert_delete(){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setCancelable(false);

        // Setting Dialog Title
        alertDialog.setTitle("Confirm Delete");

        // Setting Dialog Message
        alertDialog.setMessage("Are you sure you want to delete all the registration؟");

        // Setting Icon to Dialog
      //  alertDialog.setIcon(R.drawable.delete);

        // Setting Positive "Yes" Button
        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog,int which) {
                deleteAllData(tv6);

            }
        });

        // Setting Negative "NO" Button
        alertDialog.setNegativeButton("NO", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // Write your code here to invoke NO event
                Toast.makeText(getApplicationContext(), "You clicked on NO", Toast.LENGTH_SHORT).show();
                dialog.cancel();
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }
    @Override
    public void onBackPressed() {
        startActivity(new Intent(CheckRegistrations.this, Registration.class));
        finish();
        this.overridePendingTransition(R.anim.left_right, R.anim.right_left);
        //super.onBackPressed();

    }
}

