package com.example.testfragment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.android.volley.request.SimpleMultiPartRequest;
import com.android.volley.toolbox.Volley;
import com.example.testfragment.model.Challenge;
import com.example.testfragment.model.Client;
import com.example.testfragment.model.Comments;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ChallengeDetailler extends AppCompatActivity {
    TextView textViewTitre;
    TextView textViewadress;
    TextView textViewdatestart;
    TextView textViewdateend;
    ImageView imageView;
    ListView listViewCommentire;
    EditText editText;
    ImageView commenter;
    Challenge challenge;
    Comments comments;
    List<Comments> list;
    AdapaterListCommentaire adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge_detailler);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        challenge = (Challenge) bundle.getSerializable("challenge");
        Client client = (Client) bundle.getSerializable("client");
        challenge.setrClient(client);

        textViewTitre = findViewById(R.id.txtv_titre_challenge);
        textViewadress = findViewById(R.id.txtv_address);
        textViewdatestart = findViewById(R.id.textv_date_start);
        textViewdateend = findViewById(R.id.textv_date_end);
        imageView = findViewById(R.id.img_challenge_detaille);
        listViewCommentire = findViewById(R.id.ListvewText_commentaire);
        editText = findViewById(R.id.edt_commentaire);
        commenter = findViewById(R.id.btn_comment);
        if (challenge.getrClient() != null)
            textViewTitre.setText(challenge.getrClient().getUserName());
        commenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String content = editText.getText().toString();
                comments = new Comments(content, null, 0);
                SharedPreferences sharedPref = getSharedPreferences("aziz", Context.MODE_PRIVATE);
                int id = sharedPref.getInt("id", -1);
                comments.addClient(new Client(id));


                challenge.setrClient(new Client(id));
                creeChallenge();

            }
        });

        if (challenge.getrPhoto().iterator().hasNext()) {
            Picasso.with(this).load("http://192.168.137.1:80/aziz/" + challenge.getrPhoto().iterator().next()).into(imageView);
        }
        //  textViewTitre.setText(challenge.getId());
        textViewadress.setText(challenge.getAddress().toString());
        textViewdatestart.setText(challenge.getStartingDate());
        textViewdateend.setText(challenge.getEndingDate());
        list = new ArrayList<>();

        if (!challenge.getrComments().isEmpty())
            list.addAll(challenge.getrComments());

        adapter = new AdapaterListCommentaire(this, R.layout.item_commentaire, list);
        listViewCommentire.setAdapter(adapter);
        if (challenge.getrPhoto().size() > 0) {
            String url = "http://192.168.137.1:80/photo/" + challenge.getrPhoto().iterator().next().getPath();
            Picasso.with(this).load(url).into(imageView);
        }

    }


    public void creeChallenge() {
        DateFormat df = new SimpleDateFormat("dd/MM/yy");
        Calendar calobj = Calendar.getInstance();
        RequestQueue mRequestQueue = Volley.newRequestQueue(getApplicationContext());
        SimpleMultiPartRequest smr = new SimpleMultiPartRequest(Request.Method.POST, "http://192.168.137.1:3000/comment/",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Response", response);
                        list.add(comments);
                        adapter.notifyDataSetChanged();
                        Toast.makeText(getApplicationContext(), "ok", Toast.LENGTH_LONG).show();
                        mRequestQueue.getCache().clear();

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG).show();
                mRequestQueue.getCache().clear();
            }

        });


        //smr.addStringParam("param string", " data text");
        smr.addStringParam("challenge", challenge.tojson());
        smr.addStringParam("comments", comments.tojson());
        mRequestQueue.add(smr);

    }

}
