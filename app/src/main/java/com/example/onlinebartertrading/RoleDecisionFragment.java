package com.example.onlinebartertrading;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import java.security.NoSuchAlgorithmException;

/**
 * This is the Role Decision Fragment
 * that is included as part of AuthActivity.
 */
public class RoleDecisionFragment extends AppCompatActivity {

    /**
     * Obligatory Fragment inflater.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_role, container, false);
    }

    /**
     * This event is triggered soon after onCreateView().
     * View setup occurs here.
     */
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        Button providerButton = findViewById(R.id.ProviderButton);
        Button receiverButton = findViewById(R.id.RecieverButton);

        /**
         * "Provider" button processor.
         *  This button will do the following function:
         *      Switches to the Posts Activity if Provider Role selected
         */
        providerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch2PostsWindow();
            }
        });

        /**
         * "Receiver" button processor.
         *  This button will do the following function:
         *      Switches to the Listings Activity if Receiver Role selected
         */
        receiverButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch2ListingsWindow();
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
}
