package com.example.onlinebartertrading;

import androidx.annotation.NonNull;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.widget.ListView;
import android.widget.TextView;

import com.example.onlinebartertrading.configs.FirebaseConstants;
import com.example.onlinebartertrading.entities.User;
import com.example.onlinebartertrading.lib.HistoryAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;

public class ProfileActivity extends BaseActivity {

    ListView listGoods;

    User user;
    DatabaseReference userRef;
    ArrayList<String> titles = new ArrayList<>();
    ArrayList<String> values = new ArrayList<>();
    ArrayList<String> status = new ArrayList<>();

    // Useful to display
    private int numPosts = 0;
    private int totalValue = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        listGoods = findViewById(R.id.listView);

        user = (User) getIntent().getSerializableExtra("user");
        setEmail();
        loadHistory();
    }

    protected void setEmail() {
        TextView emailLabel = findViewById(R.id.username);
        emailLabel.setText(user.getEmail().trim());
    }

    protected void setValue() {
        TextView valueView = findViewById(R.id.valueBox);
        String value = "$" + totalValue;
        valueView.setText(value.trim());
    }

    protected void setNumPosts() {
        TextView numPostsView = findViewById(R.id.postBox);
        String formattedPosts = numPosts + " posts";
        numPostsView.setText(formattedPosts.trim());
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

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (user.getIsProvider() && snapshot.hasChild("history_provider")) {
                    DataSnapshot history = snapshot.child("history_provider");

                    saveToLists(history);
                }
                else if (!user.getIsProvider() && snapshot.hasChild("history_receiver")) {
                    DataSnapshot history = snapshot.child("history_receiver");

                    saveToLists(history);
                }

                setValue();
                setNumPosts();

                HistoryAdapter historyListAdapter = new HistoryAdapter(ProfileActivity.this,titles,values,status);
                listGoods.setAdapter(historyListAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("DATABASE ERROR: " + error.getMessage());
            }
        });
    }

    /**
     * Saves data from database to ArrayLists to be displayed in UI
     * @param history snapshot to take data from
     */
    protected void saveToLists(DataSnapshot history) {
        for (DataSnapshot snap: history.getChildren()) {
            String title = snap.child("post_title").getValue(String.class);
            int value = snap.child("post_value").getValue(Integer.class);
            String stat = snap.child("status").getValue(String.class);

            titles.add(title);
            values.add(Integer.toString(value));
            status.add(stat);

            totalValue += value;
            numPosts++;
        }
    }

}