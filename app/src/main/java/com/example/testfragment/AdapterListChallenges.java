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
import com.squareup.picasso.Picasso;

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
    public View getView(int position, View convertView, ViewGroup parent)throws NullPointerException {
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
        TextView user = layout.findViewById(R.id.txtv_user);
        if(!item.getrPhoto().isEmpty())
            Picasso.with(getContext()).load("http://192.168.137.1:80/photo/"+item.getrPhoto().iterator().next().getPath()).into(img);
        // Populate the data into the template view using the data object
        user.setText(item.getrClient().getUserName());
        Drawable drawable  = getContext().getResources().getDrawable(R.drawable.lieu1);
        img.setImageDrawable(drawable);
        if(item.getCreatedDate()!=null)
        tvdates.setText(item.getStartingDate().toString());
        if(item.getEndingDate()!=null)
        tvdatee.setText(item.getEndingDate().toString());
        // Return the completed view to render on screen
        return layout;
    }
}
