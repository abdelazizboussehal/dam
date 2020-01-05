package com.example.testfragment;


import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.testfragment.model.Challengeee;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AdapterListChallenges extends ArrayAdapter<Challengeee> {
        Activity activity;
        int itemResourceId;
        List<Challengeee> items;

    public AdapterListChallenges(Activity activity, int itemResourceId, List<Challengeee> items) {
        super(activity, itemResourceId, items);
        this.activity = activity;
        this.itemResourceId = itemResourceId;
        this.items = items;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Challengeee item = items.get(position);
        // Check if an existing view is being reused, otherwise inflate the view
        View layout=convertView;
        if(convertView==null){
            LayoutInflater inflater = activity.getLayoutInflater();
            layout=inflater.inflate(itemResourceId,parent,false);
        }
        CircleImageView img=(CircleImageView)layout.findViewById(R.id.img_chlng);
        TextView tvName = (TextView) layout.findViewById(R.id.tvdate);

        // Populate the data into the template view using the data object
        Drawable drawable  = getContext().getResources().getDrawable(item.getIdImage());
        img.setImageDrawable(drawable);
        tvName.setText(item.toString());
        // Return the completed view to render on screen
        return layout;
    }
}
