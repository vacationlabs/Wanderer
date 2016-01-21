package com.example.shivam.my_app;

/**
 * Created by Shivam on 16-01-2016.
 */
import android.app.Activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Shivam on 04-11-2015.
 */
public class GetAllList extends BaseAdapter implements ListAdapter {

    Context context;



    private JSONArray dataArray;

    private Activity activity;

    private static LayoutInflater inflater = null;



    int[] pos =new int[1000];






    public GetAllList(JSONArray jsonArray, Activity a,Context context){
        assert a != null;
        assert jsonArray != null;

        this.context=context;
        this.dataArray = jsonArray;
        this.activity = a;

    }



    @Override
    public int getCount() {
        //return this.dataArray.length();

        return dataArray.length();

    }

    @Override
    public  JSONObject getItem(int position) {
        //return position;
        return dataArray.optJSONObject(position);
    }

    @Override
    public long getItemId(int position) {
        //return position;
        JSONObject jsonObject = getItem(position);

        return jsonObject.optLong("id");

    }








    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null)
        convertView = activity.getLayoutInflater().inflate(R.layout.menu_adapter, null);

        TextView Name;

        Name  = (TextView)convertView.findViewById(R.id.name);


        try {

            JSONObject jsonObject = getItem(position);

                Name.setText(jsonObject.getString("place"));


        }catch(JSONException e)
        {
            e.printStackTrace();
        }

        return convertView;
    }


}

