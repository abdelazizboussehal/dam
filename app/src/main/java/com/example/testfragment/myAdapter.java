package com.example.testfragment;


import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class myAdapter extends ArrayAdapter<String> {
        Activity activity;
        int itemResourceId;
        List<String> items;

    public myAdapter(Activity activity, int itemResourceId, List<String> items) {
        super(activity, itemResourceId, items);
        this.activity = activity;
        this.itemResourceId = itemResourceId;
        this.items = items;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        String item = items.get(position);
        // Check if an existing view is being reused, otherwise inflate the view
        View layout=convertView;
        if(convertView==null){
            LayoutInflater inflater = activity.getLayoutInflater();
            layout=inflater.inflate(itemResourceId,parent,false);
        }

        TextView tvName = (TextView) layout.findViewById(R.id.tvdate);

        // Populate the data into the template view using the data object
        tvName.setText(item);
        // Return the completed view to render on screen
        return layout;
    }
}
