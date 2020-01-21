package com.example.testfragment;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.testfragment.model.Challenge;
import com.example.testfragment.model.Challengeee;
import com.example.testfragment.model.Client;
import com.example.testfragment.model.Comment;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class ChallengeDetailler extends AppCompatActivity {
    TextView textViewTitre;
    TextView textViewadress;
    TextView textViewdatestart;
    TextView textViewdateend;
    ImageView imageView;
    ListView listViewCommentire;
    EditText editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_challenge_detailler);
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        Challenge challenge = (Challenge) bundle.getSerializable("challenge");

        textViewTitre=findViewById(R.id.txtv_titre_challenge);
        textViewadress=findViewById(R.id.txtv_address);
        textViewdatestart=findViewById(R.id.textv_date_start);
        textViewdateend=findViewById(R.id.textv_date_end);
        imageView=findViewById(R.id.img_challenge_detaille);
        listViewCommentire=findViewById(R.id.ListvewText_commentaire);
        editText=findViewById(R.id.edt_commentaire);
        if(challenge.getrPhoto().iterator().hasNext()) {
            Picasso.with(this).load("http://192.168.137.1:80/aziz/"+challenge.getrPhoto().iterator().next()).into(imageView);
        }

      //  textViewTitre.setText(challenge.getId());
        textViewadress.setText(challenge.getAddress().toString());
        textViewdatestart.setText(challenge.getStartingDate());
        textViewdateend.setText(challenge.getEndingDate());
        List<Comment> list=new ArrayList<>();
        Comment comment=new Comment("aziz",null,1);
        Client client=new Client(1,"","","boussehal","",null,"","");
        comment.addClient(client);
        list.add(comment);
        list.add(comment);
        list.add(comment);
        list.add(comment);
        AdapaterListCommentaire adapter=new AdapaterListCommentaire(this,R.layout.item_commentaire,list);
        listViewCommentire.setAdapter(adapter);





    }
}
