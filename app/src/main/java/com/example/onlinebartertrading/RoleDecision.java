package com.example.onlinebartertrading;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import java.security.NoSuchAlgorithmException;

/**
 * This is the Role Decision Fragment
 * that is included as part of AuthActivity.
 */
public class RoleDecision extends AppCompatActivity implements View.OnClickListener {

    /**
     *  Preliminary setup
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_role);
        Button providerButton = findViewById(R.id.ProviderButton);
        Button receiverButton = findViewById(R.id.RecieverButton);
        providerButton.setOnClickListener(this);
        receiverButton.setOnClickListener(this);
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
     * Switches to post once provider role picked successfully
     * @param
     */
    protected void switch2PostsWindow() {
        Intent switchPostActivity = new Intent(this, MakePostActivity.class);
        startActivity(switchPostActivity);
    }

    /**
     * Switches to post once receiver role picked successfully
     * @param
     */
    protected void switch2ListingsWindow() {
        Intent switchListActivity = new Intent(this, ShowDetailsActivity.class);
        startActivity(switchListActivity);
    }

    @Override
    public void onClick(View view) {
        int viewId = view.getId();

        if (viewId == R.id.RecieverButton) {
            switch2PostsWindow();
        }
        else if (viewId == R.id.ProviderButton) {
            switch2ListingsWindow();
        }
    }
}
