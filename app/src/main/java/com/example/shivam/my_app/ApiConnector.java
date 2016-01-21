package com.example.shivam.my_app;

import android.util.Log;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by Shivam on 16-01-2016.
 */
public class ApiConnector {

    String loc;
    String url_main="https://23eb22b0.ngrok.io/";

    public JSONArray WriteCoordinates(String location,Double latitude, Double longitude) {




        try {
            loc= URLEncoder.encode(location, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        String url = url_main+"/wan_get.php?place="+"'"+loc+"'"+"&lat="+latitude+"&lon="+longitude;

        HttpEntity httpEntity2 = null;
        HttpClient httpClient = new DefaultHttpClient();

        try {

            HttpGet httpGet = new HttpGet(url);


            HttpResponse httpResponse = httpClient.execute(httpGet);

            httpEntity2 = httpResponse.getEntity();


        } catch (ClientProtocolException e) {
            // Signals error in http protocol
            e.printStackTrace();
            //Log Errors Here
        } catch (IOException e) {
            e.printStackTrace();
        }


        // Convert HttpEntity into JSON Array
        JSONArray jsonArray4 = null;

        if (httpEntity2 != null) {
            try {
                String entityResponse = EntityUtils.toString(httpEntity2);

                Log.e("Entity Response  : ", entityResponse);


                jsonArray4 = new JSONArray(entityResponse);


            } catch (JSONException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return jsonArray4;

    }



    public JSONArray GetAllPlacesApi() {

        String url = url_main+"/wan_menu.php";

        HttpEntity httpEntity2 = null;
        HttpClient httpClient = new DefaultHttpClient();

        try {
            HttpGet httpGet = new HttpGet(url);
            HttpResponse httpResponse = httpClient.execute(httpGet);
            httpEntity2 = httpResponse.getEntity();


        } catch (ClientProtocolException e) {
            // Signals error in http protocol
            e.printStackTrace();
            //Log Errors Here
        } catch (IOException e) {
            e.printStackTrace();
        }


        // Convert HttpEntity into JSON Array
        JSONArray jsonArray1 = null;

        if (httpEntity2 != null) {
            try {
                String entityResponse = EntityUtils.toString(httpEntity2);

                Log.e("Entity Response  : ", entityResponse);


                jsonArray1 = new JSONArray(entityResponse);


            } catch (JSONException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return jsonArray1;

    }


    public JSONArray GETCoordinates(String location) {


        try {
            loc= URLEncoder.encode(location, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        Log.e("LocationAt-API",loc);


        String url = url_main+"/wan_latlon.php?place="+"'"+loc+"'";

        HttpEntity httpEntity2 = null;
        HttpClient httpClient = new DefaultHttpClient();

        try {
            HttpGet httpGet = new HttpGet(url);
            HttpResponse httpResponse = httpClient.execute(httpGet);
            httpEntity2 = httpResponse.getEntity();


        } catch (ClientProtocolException e) {
            // Signals error in http protocol
            e.printStackTrace();
            //Log Errors Here
        } catch (IOException e) {
            e.printStackTrace();
        }

        JSONArray jsonArray5 = null;

        if (httpEntity2 != null) {
            try {
                String entityResponse = EntityUtils.toString(httpEntity2);
                Log.e("Entity Response  : ", entityResponse);
                jsonArray5 = new JSONArray(entityResponse);

            } catch (JSONException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return jsonArray5;

    }

}
