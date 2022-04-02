package com.example.onlinebartertrading;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.onlinebartertrading.configs.FirebaseConstants;
import com.example.onlinebartertrading.entities.User;
import com.example.onlinebartertrading.lib.LocationProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class ChatActivity extends AppCompatActivity {

    List<ChatList> chats;

    Button backButton;
    Button sendButton;

    TextView fullName;
    EditText messageField;

    ChatAdapter chatAdapter;

    User user;
    DatabaseReference databaseReference;

    RecyclerView recyclerView;

    Intent intent;
    Toolbar toolBar;
    String currFName;
    String myFName;
    String otherID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(getIntent().getSerializableExtra("user") == null) {
            user = new User("x@email.com");
        }
        else {
            user = (User) getIntent().getSerializableExtra("user");
            user.setLocationProvider(new LocationProvider(this));
        }

        setContentView(R.layout.activity_chat);

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        backButton = findViewById(R.id.backButton);
        sendButton = findViewById(R.id.sendButton);
        messageField = findViewById(R.id.messageField);

        myFName = user.getFirstName();
        otherID = getIntent().getStringExtra("users");

        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        final String fName = user.getFirstName();

        intent = getIntent();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                readMessages(user.getFirstName(), fName);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        messageField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().length() > 0) {
                    sendButton.setEnabled(true);
                }
                else {
                    sendButton.setEnabled(false);
                }
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String text = messageField.getText().toString();

                if (!text.startsWith(" ")) {
                    messageField.getText().insert(0, " ");
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = messageField.getText().toString();

                if (!message.equals("")) {
                    textMessage(myFName, fName, message);
                } else {
                    Toast.makeText(ChatActivity.this, "This is an empty message", Toast.LENGTH_LONG).show();
                }
                messageField.setText(" ");
            }
        });
    }

    private void textMessage(String senderID, String receiverID, String message) {

        databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl(FirebaseConstants.FIREBASE_URL);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put("sender", senderID);
                hashMap.put("receiver", receiverID);
                hashMap.put("message", message);

                String key = chatExists(snapshot, senderID, receiverID);

                if (key == null) {
                    key = UUID.randomUUID().toString();
                }

                databaseReference.child("Chats").child(key).push().setValue(hashMap);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void readMessages(String myName, String userName) {
        chats = new ArrayList<>();

        databaseReference = FirebaseDatabase.getInstance().getReference("Chats");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                chats.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    ChatList chat = dataSnapshot.getValue(ChatList.class);
                    boolean result = chat.getReceiver().equals(myName) && chat.getSender().equals(userName) || chat.getReceiver().equals(userName) && chat.getSender().equals(myName);

                    if (result) {
                        chats.add(chat);
                    }

                    chatAdapter = new ChatAdapter(chats, ChatActivity.this);
                    recyclerView.setAdapter(chatAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private String chatExists(DataSnapshot snapshot, String senderID, String receiverID) {

        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
            boolean receiverCond;
            boolean senderCond = dataSnapshot.child("receiver").equals(receiverID) || dataSnapshot.child("sender").equals(senderID);

            if (senderCond) {
                receiverCond = dataSnapshot.child("receiver").equals(receiverID) || dataSnapshot.child("sender").equals(senderID);

                if (receiverCond) {
                    return dataSnapshot.getKey();
                }
            }
        }
        return null;
    }

    public void setSupportActionBar(Toolbar supportActionBar) {
        this.toolBar = supportActionBar;
    }
}
