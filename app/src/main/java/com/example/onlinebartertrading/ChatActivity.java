package com.example.onlinebartertrading;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.auth.User;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {


    private final List<ChatList> totalChatLists = new ArrayList<>();
    private String key;
    private String firstName;
    private String lastName;
    private String email;
    private View chatView;
    private String finalChatKey;
    String getUserKey = "";


    ImageView backButton;
    ImageView sendButton;

    TextView fullName;
    EditText messageField;

    User firebaseUser;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReferenceFromUrl(FirebaseConstants.FIREBASE_URL);

    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        backButton = findViewById(R.id.back);
        sendButton = findViewById(R.id.send);
        fullName = findViewById(R.id.fullName);
        messageField = findViewById(R.id.chatInput);

        final String firstName = getIntent().getStringExtra("first_name");
        final String lastName = getIntent().getStringExtra("last_name");
        final String chatKey = getIntent().getStringExtra("chat_key");
        final String userKey = getIntent().getStringExtra("user_key");

        getUserKey = ChatData.getData(ChatActivity.this);
        fullName.setText(firstName + " " + lastName);

        //userKey = ChatData.getData(ChatActivity.this);

        chatView.setLay
        if (chatKey.isEmpty()) {
            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {

                    // generates chat key, and the default key value is 1
                    finalChatKey = "1";

                    if (snapshot.hasChild("chat")) {
                        finalChatKey = String.valueOf(snapshot.child("chat").getChildrenCount() + 1);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final String getMessage = chatBox.getText().toString();

                final String currentTime = String.valueOf(System.currentTimeMillis()).substring(0, 10);

                databaseReference.child("chat").child(chatKey).child("user_1").setValue(getUserKey);
                databaseReference.child("chat").child(chatKey).child("user_2").setValue(chatKey);
                databaseReference.child("chat").child(chatKey).child("messages").child(currentTime).child("message").setValue(getMessage);
                databaseReference.child("chat").child(chatKey).child("messages").child(currentTime).child("user_key").setValue(getUserKey);


                chatBox.setText("");
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        chatView = findViewById(R.id.RecyclerView);

        chatView.setHasFixedSize(true);
        chatView.setLayoutManager(new LinearLayoutManager(this));

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                totalChatLists.clear();
                chatKey = "";

                for (DataSnapshot dataSnapshot : snapshot.child("users").getChildren()) {
                    final String getKey = dataSnapshot.getKey();

                    if (getKey.equals(key)) {
                        final String getFirstName = dataSnapshot.child("first_name").getValue(String.class);
                        final String getLastName = dataSnapshot.child("last_name").getValue(String.class);
                        final String getEmail = dataSnapshot.child("email").getValue(String.class);

                        databaseReference.child("chat").addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {

                                int getChatCounts = (int)snapshot.getChildrenCount();

                                if (getChatCounts > 0) {
                                    for (DataSnapshot dataSnapshot1 : snapshot.getChildren()){
                                        final String getKey = dataSnapshot1.getKey();
                                        chatKey = getKey;
                                        final String firstUser = dataSnapshot1.child("user_1").getValue(String.class);
                                        final String secondUser = dataSnapshot1.child("user_2").getValue(String.class);

                                        if (firstUser.equals(getKey) && secondUser.equals(key) || firstUser.equals(key) && secondUser.equals(getKey)) {
                                            for (DataSnapshot chatSnapshot : dataSnapshot1.child("messages").getChildren()){
                                                final long getMessageKey = Long.parseLong(chatSnapshot.getKey());
                                            }
                                        }
                                    }
                                }

                                /*
                                ChatList chatList = new ChatList(getFirstName, getLastName, getKey);
                                totalChatLists.add(chatList);
                                chatView.setAdapter(new ChatAdapter(totalChatLists, ChatActivity.this));
                                */
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}