package com.example.testfragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.testfragment.model.Address;
import com.example.testfragment.model.Challenge;
import com.example.testfragment.model.Challengeee;

import java.util.ArrayList;
import java.util.List;

public class FragementListMesChallenges extends Fragment {
    ListView listView;
    List<Challenge> noms;
    int position=-1;
    AdapterListChallenges myada;
    public FragementListMesChallenges(){
        super();
    }
    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        getActivity().setTitle("Mes challenegs");
        View view= inflater.inflate(R.layout.fragement_list_challenges, container, false);
        noms=new ArrayList<>();
        noms.add(new Challenge(1,"12/12/2020","2/1/2020","12/12/2020",new Address(12,1,1,"cnep"
                ,"constantine","21210","algeria")));
        noms.add(new Challenge(1,"12/12/2020","2/1/2020","12/12/2020",new Address(12,1,1,"cnep"
                ,"constantine","21210","algeria")));
        noms.add(new Challenge(1,"12/12/2020","2/1/2020","12/12/2020",new Address(12,1,1,"cnep"
                ,"constantine","21210","algeria")));
        listView=(ListView)view.findViewById(R.id.ListvewText);
        myada=new AdapterListChallenges(getActivity(),R.layout.item_challenge,noms);
        listView.setAdapter(myada);
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getActivity(),"suprimer",Toast.LENGTH_LONG).show();
                position=i;
                AlertDialog alertDialog=creedialog();
                alertDialog.show();
                return false;
            }
        });

        return view;
    }

    public AlertDialog creedialog(){
        AlertDialog myQuittingDialogBox = new AlertDialog.Builder(getActivity())
                // set message, title, and icon

                .setTitle("Delete")
                .setMessage("Do you want to Delete "+(position++)+" item")
                .setIcon(R.drawable.challenges_black_24dp)

                .setPositiveButton("Delete", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        position--;
                        noms.remove(position);
                        myada.notifyDataSetChanged();
                        dialog.dismiss();
                    }

                })
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();

                    }
                })
                .create();
        return myQuittingDialogBox;
    }
}
