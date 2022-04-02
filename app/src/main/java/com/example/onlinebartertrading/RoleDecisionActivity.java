package com.example.onlinebartertrading;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import com.example.onlinebartertrading.entities.User;
import com.example.onlinebartertrading.lib.LocationProvider;

/**
 * This is the Role Decision
 * class.
 */
public class RoleDecisionActivity extends BaseActivity implements View.OnClickListener {
    User user;

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
        startActivity(switchListActivity);
    }

    @Override
    public void onClick(View view) {
        int viewId = view.getId();

        if (viewId == R.id.ReceiverButton) {
            switch2Activity(PostListActivity.class);
        }
        else if (viewId == R.id.ProviderButton) {
            switch2Activity(MakePostActivity.class);
        }
    }
}
