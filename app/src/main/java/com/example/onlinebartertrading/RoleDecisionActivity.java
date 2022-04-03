package com.example.onlinebartertrading;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.example.onlinebartertrading.entities.User;
import com.example.onlinebartertrading.lib.LocationProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.UUID;

/**
 * This is the Role Decision
 * class.
 */
public class RoleDecisionActivity extends BaseActivity implements View.OnClickListener {
    User user;
    DatabaseReference reference;

    /**
     *  Preliminary setup
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_role);
        Button providerButton = findViewById(R.id.ProviderButton);
        Button receiverButton = findViewById(R.id.ReceiverButton);
        providerButton.setOnClickListener(this);
        receiverButton.setOnClickListener(this);

        user = (User) getIntent().getSerializableExtra("user");
        user.setLocationProvider(new LocationProvider(this));

        String uuid = UUID.nameUUIDFromBytes(user.getEmail().getBytes()).toString();
        reference = FirebaseDatabase.getInstance().getReference().child("users").child(uuid);
        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.hasChild("isProvider")) {
                    user.setIsProvider(snapshot.child("isProvider").getValue().toString().equals("1"));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    /**
     * Sets status message
     * @param message to set
     */
    protected void setStatusMessage(String message) {
        TextView statusLabel = findViewById(R.id.roleStatus);
        statusLabel.setText(message.trim());
    }

    /**
     * Switches to appropriate activity once user picks role
     * @param target activity to switch to
     */
    protected void switch2Activity(Class target) {
        Intent switchListActivity = new Intent(this, target);
        switchListActivity.putExtra("user", user);
        FCMService.setUser(user);
        startActivity(switchListActivity);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onClick(View view) {


        int viewId = view.getId();

        if (viewId == R.id.ReceiverButton) {
            if(user.getIsProvider() != null && user.getIsProvider()) {
                Toast.makeText(getBaseContext(), "User already registered as a provider.", Toast.LENGTH_SHORT).show();
                return;
            }
            if(user.getIsProvider() == null) {
                HashMap<String, Object> map = new HashMap<>();
                map.put("isProvider", 0);
                reference.updateChildren(map);
                user.setIsProvider(false);
            }
            switch2Activity(PostListActivity.class);
        }
        else if (viewId == R.id.ProviderButton) {
            if(user.getIsProvider() !=null && !user.getIsProvider()) {
                Toast.makeText(getBaseContext(), "User already registered as a receiver.", Toast.LENGTH_SHORT).show();
                return;
            }
            if(user.getIsProvider() == null) {
                HashMap<String, Object> map = new HashMap<>();
                map.put("isProvider", 1);
                reference.updateChildren(map);
                user.setIsProvider(true);
            }
            switch2Activity(MakePostActivity.class);
        }
    }
}
