package com.example.testfragment;


import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.testfragment.model.Challenge;
import com.example.testfragment.model.Challengeee;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterListChallenges extends ArrayAdapter<Challenge> {
        Activity activity;
        int itemResourceId;
        List<Challenge> items;

    public AdapterListChallenges(Activity activity, int itemResourceId, List<Challenge> items) {
        super(activity, itemResourceId, items);
        this.activity = activity;
        this.itemResourceId = itemResourceId;
        this.items = items;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Challenge item = items.get(position);
        // Check if an existing view is being reused, otherwise inflate the view
        View layout=convertView;
        if(convertView==null){
            LayoutInflater inflater = activity.getLayoutInflater();
            layout=inflater.inflate(itemResourceId,parent,false);
        }
        ImageView img=layout.findViewById(R.id.img_chlng);
        TextView tvdates = layout.findViewById(R.id.tvdatestart);
        TextView tvdatee = layout.findViewById(R.id.tvdateEnd);

        // Populate the data into the template view using the data object
        Drawable drawable  = getContext().getResources().getDrawable(R.drawable.lieu1);
        img.setImageDrawable(drawable);
        tvdates.setText(item.toString());
        tvdatee.setText(item.toString());
        // Return the completed view to render on screen
        return layout;
    }
}
