package com.example.onlinebartertrading;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.onlinebartertrading.configs.FirebaseConstants;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ChatActivity extends AppCompatActivity {


    List<ChatList> chatLists;

    ImageButton backButton;
    ImageButton sendButton;

    TextView fullName;
    EditText messageField;

    ChatAdapter chatAdapter;

    FirebaseUser firebaseUser;
    DatabaseReference databaseReference;

    RecyclerView recyclerView;

    Intent intent;
    Toolbar toolBar;

    @Override
    protected void onCreate(Bundle savedInnstanceState) {
        super.onCreate(savedInnstanceState);
        setContentView(R.layout.activity_chat);
        
        toolBar = findViewById(R.id.toolBar);
        setSupportActionBar(toolBar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolBar.setNavigationOnClickListener((view -> {
            finish();
        }));

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        backButton = findViewById(R.id.backButton);
        sendButton = findViewById(R.id.sendButton);
        fullName = findViewById(R.id.fullName);
        messageField = findViewById(R.id.messageField);

        intent = getIntent();
        String userID = intent.getStringExtra("userid");
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                View message = messageField;

                if (!message.equals("")) {
                    textMessage(firebaseUser.getUid(), userID, message);
                } else {
                    Toast.makeText(ChatActivity.this, "This is an empty message", Toast.LENGTH_LONG).show();
                }
                messageField.setText("");
            }
        });

        databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl(FirebaseConstants.FIREBASE_URL);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                readMessages(firebaseUser.getUid(), userID);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

    }

    private void textMessage(String sender, String receiver, View message) {

        databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl(FirebaseConstants.FIREBASE_URL);

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("sender", sender);
        hashMap.put("receiver", receiver);
        hashMap.put("message", message);

        databaseReference.child("Chats").push().setValue(hashMap);

    }

    private void readMessages(String id, String userId) {
        chatLists = new ArrayList<>();

        databaseReference = FirebaseDatabase.getInstance().getReference("Chats");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                chatLists.clear();

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    ChatList chat = dataSnapshot.getValue(ChatList.class);
                    boolean result = chat.getReceiver().equals(id) && chat.getSender().equals(userId) || chat.getReceiver().equals(userId) && chat.getSender().equals(id);

                    if (result) {
                        chatLists.add(chat);
                    }

                    chatAdapter = new ChatAdapter(chatLists, ChatActivity.this);
                    recyclerView.setAdapter(chatAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void setSupportActionBar(Toolbar supportActionBar) {
        this.toolBar = supportActionBar;
    }
}