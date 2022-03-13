package com.example.onlinebartertrading;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

/**
 * This is the Role Decision
 * class.
 */
public class RoleDecisionActivity extends AppCompatActivity implements View.OnClickListener {
    CharSequence text = "Successfully posted";
    int duration = Toast.LENGTH_SHORT;
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
     */
    protected void switch2PostsWindow() {
        Intent switchPostActivity = new Intent(this, MakePostActivity.class);
        switchPostActivity.putExtra("user", user);
        startActivity(switchPostActivity);
        Toast toast = Toast.makeText(this, text, duration);
        toast.show();
    }

    /**
     * Switches to post once receiver role picked successfully
     */
    protected void switch2ListingsWindow() {
        Intent switchListActivity = new Intent(this, PostListActivity.class);
        switchListActivity.putExtra("user", user);
        startActivity(switchListActivity);
    }

    @Override
    public void onClick(View view) {
        int viewId = view.getId();

        if (viewId == R.id.ReceiverButton) {
            switch2PostsWindow();
        }
        else if (viewId == R.id.ProviderButton) {
            switch2ListingsWindow();
        }
    }
}
