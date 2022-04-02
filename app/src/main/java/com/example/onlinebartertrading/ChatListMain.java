package com.example.onlinebartertrading;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class ChatListMain extends AppCompatActivity {

    RecyclerView recyclerView;
    DatabaseReference database;
    ChatHistory chatHistory;
    ArrayList<ChatUser> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_history);

        recyclerView = findViewById(R.id.recycler1);
        database = FirebaseDatabase.getInstance().getReference("chat");
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        chatHistory = new ChatHistory(ChatListMain.this,list);
        recyclerView.setAdapter(chatHistory);

        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()){

                    ChatUser chatUser = dataSnapshot.getValue(ChatUser.class);
                    list.add(chatUser);
                }
                chatHistory.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}

