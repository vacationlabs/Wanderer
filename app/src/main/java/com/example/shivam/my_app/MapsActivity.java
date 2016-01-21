package com.example.shivam.my_app;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.
    String location;
    JSONArray jsonArray;

    double valuelat,valuelon;

    double m1,m2;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        Intent mIntent = getIntent();
        location = mIntent.getStringExtra("location");
        new GetCoordinates().execute(new ApiConnector());

    }

    @Override
    protected void onResume() {
        super.onResume();
     //   setUpMapIfNeeded();
    }

    /**
     * Sets up the map if it is possible to do so (i.e., the Google Play services APK is correctly
     * installed) and the map has not already been instantiated.. This will ensure that we only ever
     * call {@link #setUpMap()} once when {@link #mMap} is not null.
     * <p/>
     * If it isn't installed {@link SupportMapFragment} (and
     * {@link com.google.android.gms.maps.MapView MapView}) will show a prompt for the user to
     * install/update the Google Play services APK on their device.
     * <p/>
     * A user can return to this FragmentActivity after following the prompt and correctly
     * installing/updating/enabling the Google Play services. Since the FragmentActivity may not
     * have been completely destroyed during this process (it is likely that it would only be
     * stopped or paused), {@link #onCreate(Bundle)} may not be called again so we should call this
     * method in {@link #onResume()} to guarantee that it will be called.
     */
    private void setUpMapIfNeeded() {
        // Do a null check to confirm that we have not already instantiated the map.
        if (mMap == null) {
            // Try to obtain the map from the SupportMapFragment.
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map))
                    .getMap();
            // Check if we were successful in obtaining the map.
            if (mMap != null) {
                setUpMap();
                addLines();


            }
        }
    }


    /**
     * This is where we can add markers or lines, add listeners or move the camera. In this case, we
     * just add a marker near Africa.
     * <p/>
     * This should only be called once and when we are sure that {@link #mMap} is not null.
     */
    private void setUpMap() {

        mMap.addMarker(new MarkerOptions().position(new LatLng(51.5, -0.1)).title("Marker"));
    }

    private void addLines() {
        mMap.addPolyline((new PolylineOptions())
                .add(new LatLng(51.5, -0.1), new LatLng(40.7, -74.0)).width(5).color(Color.BLUE)
                .geodesic(true));
        // move camera to zoom on map
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(51.5, -0.1),
                13));
    }


    private class GetCoordinates extends AsyncTask<ApiConnector, Long, JSONArray> {

        @Override
        protected void onPreExecute() {
            Log.e("Here","Here");

        }


        @Override
        protected JSONArray doInBackground(ApiConnector... params) {
            return params[0].GETCoordinates(location);
        }

        @Override
        protected void onPostExecute(JSONArray jsonArray) {
            setListAdapter(jsonArray);

        }

    }

        public void setListAdapter(JSONArray jsonArray){
           this.jsonArray =jsonArray;

            ArrayList<LatLng> coordList = new ArrayList<LatLng>();
            for(int i=0;i<jsonArray.length();i++){

                try {

                    JSONObject obj = jsonArray.getJSONObject(i);
                    String lat = obj.getString("lat");
                    String lon = obj.getString("lon");

                   valuelat = Double.parseDouble(lat);
                    valuelon = Double.parseDouble(lon);

                    if(i==0){

                        m1=valuelat;
                        m2=valuelon;

                    }

                    coordList.add(new LatLng(valuelat, valuelon));


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

            Log.e("coordinates",""+coordList);

            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map)).getMap();
            mMap.addMarker(new MarkerOptions().position(new LatLng(m1, m2)).title("Initial"));
            mMap.addMarker(new MarkerOptions().position(new LatLng(valuelat, valuelon)).title("Final"));
            mMap.addPolyline((new PolylineOptions()).addAll(coordList).width(5).color(Color.BLUE).geodesic(true));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(m1, m2),
                    13));

      }


}
