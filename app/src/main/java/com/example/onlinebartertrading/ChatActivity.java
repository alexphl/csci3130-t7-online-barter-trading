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
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.onlinebartertrading.configs.FirebaseConstants;
import com.example.onlinebartertrading.entities.User;
import com.example.onlinebartertrading.lib.DBHandler;
import com.example.onlinebartertrading.lib.LocationProvider;
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

    public DBHandler DB_HANDLER;

    List<ChatList> chats;

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
    String currFName;
    String myID;
    String otherID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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


        myID = firebaseUser.getUid();
        otherID = getIntent().getStringExtra("otherID");

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        final String fName = String.valueOf(databaseReference.child("user/first_name"));
        FirebaseAuth auth = FirebaseAuth.getInstance();

        intent = getIntent();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                String temp = user.getFirstName() + " " + user.getLastName();
                fullName.setText(temp);
                readMessages(myID, otherID);
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
                    textMessage(firebaseUser.getFirstName(), fName, message);
                } else {
                    Toast.makeText(ChatActivity.this, "This is an empty message", Toast.LENGTH_LONG).show();
                }
                messageField.setText(" ");
            }
        });

        readMessages();

    }

    private void textMessage(String sender, String receiver, String message) {

        databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl(FirebaseConstants.FIREBASE_URL);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                HashMap<String, Object> hashMap = new HashMap<>();
                hashMap.put("sender", sender);
                hashMap.put("receiver", receiver);
                hashMap.put("message", message);

                databaseReference.child("Chats").push().setValue(hashMap);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void readMessages(String id, String userId) {
        chats = new ArrayList<>();

        databaseReference = FirebaseDatabase.getInstance().getReference("Chats");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                chats.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    ChatList chat = dataSnapshot.getValue(ChatList.class);
                    boolean result = chat.getReceiver().equals(id) && chat.getSender().equals(userId) || chat.getReceiver().equals(userId) && chat.getSender().equals(id);

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

    public void setSupportActionBar(Toolbar supportActionBar) {
        this.toolBar = supportActionBar;
    }

    private void sendUserToRegisterActivity() {
        Intent intent = new Intent(ChatActivity.this, AuthActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    private void sendUserToLoginActivity() {
        Intent intent = new Intent(ChatActivity.this, AuthActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }
}
