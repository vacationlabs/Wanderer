package com.example.shivam.my_app;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SelectPlace extends AppCompatActivity {

    private ListView GetAllCustomerListView;
    private JSONArray jsonArray;
    Context context;
    String location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_place);

        context=this;

        this.GetAllCustomerListView =(ListView) this.findViewById(R.id.listView);
        new GetAllPlaces().execute(new ApiConnector());

        GetAllCustomerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                location = ((TextView) view.findViewById(R.id.name)).getText().toString();

                Intent activityChangeIntent = new Intent(SelectPlace.this, MapsActivity.class);

                activityChangeIntent.putExtra("location", location);

                SelectPlace.this.startActivity(activityChangeIntent);

                Log.e("Location:-",""+location);



            }
        });
    }

    private class GetAllPlaces extends AsyncTask<ApiConnector,Long,JSONArray>
    {


        @Override
        protected void onPreExecute() {
          //  progressDialog  = ProgressDialog.show(MainActivity.this, "Please wait...", "Loading List...");

        }


        @Override
        protected JSONArray doInBackground(ApiConnector... params) {
            return params[0].GetAllPlacesApi();
        }

        @Override
        protected void onPostExecute(JSONArray jsonArray) {
            setListAdapter(jsonArray);

        }
    }

    public void setListAdapter(JSONArray jsonArray){
        this.jsonArray =jsonArray;
        this.GetAllCustomerListView.setAdapter(new GetAllList(jsonArray, this, context));

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_select_place, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
    }
}
