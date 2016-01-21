package com.example.shivam.my_app;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationListener;
import android.location.LocationManager;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONObject;

import android.os.AsyncTask;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import pl.droidsonroids.gif.GifDrawable;
import pl.droidsonroids.gif.GifImageButton;


public class MainActivity extends AppCompatActivity {

    GPSTracker gps;
    String location="test   test";
    final Handler h = new Handler();
    final int delay = 35000;
    double latitude;
    double longitude;

    JSONArray jsonArray;

    Button b;
    Button b2;
    TextView t;

    ImageView img;

    int []imageArray={R.drawable.i1,R.drawable.i2,R.drawable.i3,R.drawable.i4,R.drawable.i5};

    EditText et;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        img= (ImageView) findViewById(R.id.imageView2);
        et = (EditText) findViewById(R.id.editText);
        b =(Button) findViewById(R.id.button);
        b2=(Button) findViewById(R.id.button2);
        t = (TextView) findViewById(R.id.Cods);
        b2.setVisibility(View.GONE);
        b.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {



                final Handler handler = new Handler();
                Runnable runnable = new Runnable() {
                    int i=0;
                    public void run() {
                        img.setImageResource(imageArray[i]);
                        i++;
                        if(i>imageArray.length-1)
                        {
                            i=0;
                        }
                        handler.postDelayed(this, 200);  //for interval...
                    }
                };
                handler.postDelayed(runnable, 2000); //for initial delay..




                location = et.getText().toString();



                b.setVisibility(View.GONE);
                b2.setVisibility(View.VISIBLE);
                et.setVisibility(View.GONE);

                gps = new GPSTracker(MainActivity.this);

                if (gps.canGetLocation()) {

                    latitude = gps.getLatitude();
                    longitude = gps.getLongitude();

                    Toast.makeText(getApplicationContext(), "Start Wandering!", Toast.LENGTH_LONG).show();
                    Log.e("Co-ordinates-", "" + latitude + "long-" + longitude + "\n");
                    t.append("Co-cordinates: Latitude:-" + latitude + " Longitude:-" + longitude + "\n");
                } else {
                    gps.showSettingsAlert();
                }


                new WriteToDatabase().execute(new ApiConnector());


                h.postDelayed(new Runnable() {
                    public void run() {
                        gps = new GPSTracker(MainActivity.this);
                        if (gps.canGetLocation()) {

                            latitude = gps.getLatitude();
                            longitude = gps.getLongitude();
                            Log.e("Co-ordinates-", "" + latitude + "long-" + longitude + "\n");
                            t.append("Co-cordinates: Latitude:-" + latitude + " Longitude:-" + longitude + "\n");

                            new WriteToDatabase().execute(new ApiConnector());

                        } else {

                            gps.showSettingsAlert();
                        }
                        h.postDelayed(this, delay);
                    }
                }, delay);
            }
        });

        b2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                System.exit(0);

            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
      //  if (id == R.id.action_settings) {
        //    return true;
        ///}

        return super.onOptionsItemSelected(item);
    }



    private class WriteToDatabase extends AsyncTask<ApiConnector, Long, JSONArray> {

        @Override
        protected void onPreExecute() {

        }


        @Override
        protected JSONArray doInBackground(ApiConnector... params) {

            // it is executed on Background thread

            return params[0].WriteCoordinates(location, latitude, longitude);
        }

        @Override
        protected void onPostExecute(JSONArray jsonArray) {

            setListAdapter(jsonArray);
}


    }
    public void setListAdapter(JSONArray jsonArray){
        this.jsonArray =jsonArray;

    }


}
