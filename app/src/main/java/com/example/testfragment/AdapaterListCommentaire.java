package com.example.testfragment;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.testfragment.model.Comments;

import java.util.List;

public class AdapaterListCommentaire extends ArrayAdapter<Comments> {
    Activity activity;
    int itemResourceId;
    List<Comments> items;

    public AdapaterListCommentaire(Activity activity, int itemResourceId, List<Comments> items) {
        super(activity, itemResourceId, items);
        this.activity = activity;
        this.itemResourceId = itemResourceId;
        this.items = items;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        Comments item = items.get(position);
        // Check if an existing view is being reused, otherwise inflate the view
        View layout=convertView;
        if(convertView==null){
            LayoutInflater inflater = activity.getLayoutInflater();
            layout=inflater.inflate(itemResourceId,parent,false);
        }
            TextView textView_nom_user = layout.findViewById(R.id.txtv_nom_client);
            TextView textView_cntenu = layout.findViewById(R.id.contenue_commentaire);
            TextView textView_date = layout.findViewById(R.id.textv_date);
        try {
            textView_nom_user.setText(item.getrClient().getUserName());
            textView_cntenu.setText(item.getContent());
            if (item.getCreationDate() != null)
                textView_date.setText(item.getCreationDate().toString());
        }
        catch (NullPointerException e){
            Toast.makeText(getContext(),e.getMessage(),Toast.LENGTH_LONG).show();
        }
        // Return the completed view to render on screen
        return layout;
    }
}
