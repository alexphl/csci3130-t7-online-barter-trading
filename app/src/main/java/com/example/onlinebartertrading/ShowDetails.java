package com.example.onlinebartertrading;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * This class represents the details activity
 * which appears when a user enters a product post
 **/
public class ShowDetails extends AppCompatActivity {

    ListView listGoods;
    ArrayList<String> name = new ArrayList<>();
    ArrayList<String> detail = new ArrayList<>();
    ArrayList<String> value = new ArrayList<>();
    DatabaseReference reference;

    /**
     * View setup and value listeners
     **/
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //listview layout
        setContentView(R.layout.activity_listmain);
        listGoods = findViewById(R.id.listView);
        reference = FirebaseDatabase.getInstance().getReference().child("posts");
        reference.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
            //get data from firebase

                for(DataSnapshot snapshot : datasnapshot.getChildren()) {
                    String tit = snapshot.child("title").getValue().toString();
                    String de = snapshot.child("desc").getValue().toString();
                    String val = snapshot.child("value").getValue().toString();

                    name.add(tit);
                    detail.add(de);
                    value.add(val);
                }

                //Send to adapter and make data in each layout
                Editor editor = new Editor(ShowDetails.this, name, detail, value);
                listGoods.setAdapter(editor);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // TODO: handle this exception
            }
        });

    }
}
