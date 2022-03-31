package com.example.onlinebartertrading;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.example.onlinebartertrading.configs.FirebaseConstants;
import com.example.onlinebartertrading.entities.Preferences;
import com.example.onlinebartertrading.entities.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.UUID;

public class ProfileActivity extends BaseActivity {

    User user;
    DatabaseReference userRef;
    ArrayList<String> titles;
    ArrayList<String> values;
    ArrayList<String> status;

    // Useful to display
    int numPosts = 0;
    int totalValue = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        user = (User) getIntent().getSerializableExtra("user");
        setEmail();
        loadHistory();
    }

    protected void setEmail() {
        TextView emailLabel = findViewById(R.id.username);
        emailLabel.setText(user.getEmail().trim());
    }

    protected void initializeUserDBRef() {
        DatabaseReference dbRef = FirebaseDatabase
                .getInstance(FirebaseConstants.FIREBASE_URL)
                .getReference();

        String uuid = UUID.nameUUIDFromBytes(user.getEmail().getBytes()).toString();
        userRef = dbRef.child(FirebaseConstants.USERS_COLLECTION).child(uuid);
    }

    /**
     * Populates ArrayLists with users history to be able to display correctly
     */
    protected void loadHistory() {

        initializeUserDBRef();

        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChild("history_provider")) {
                    DataSnapshot history = snapshot.child("history_provider");

                    for (DataSnapshot snap: history.getChildren()) {
                        String title = snap.child("post_title").getValue(String.class);
                        String value = snap.child("post_value").getValue(String.class);
                        String stat = snap.child("status").getValue(String.class);

                        titles.add(title);
                        values.add(value);
                        status.add(stat);

                        totalValue += Integer.valueOf(value);
                        numPosts++;


                    }
                    TextView totalValue = findViewById(R.id.totalValue);
                    totalValue.setText("$"+totalValue);

                    TextView totalPosts = findViewById(R.id.totalPosts);
                    totalPosts.setText(numPosts+" posts");
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("DATABASE ERROR: " + error.getMessage());
            }
        });
    }
}