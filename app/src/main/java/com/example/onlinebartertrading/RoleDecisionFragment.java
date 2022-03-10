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
import androidx.fragment.app.Fragment;

import java.security.NoSuchAlgorithmException;

/**
 * This is the Role Decision Fragment
 * that is included as part of AuthActivity.
 */
public class RoleDecisionFragment extends Fragment implements View.OnClickListener {

    public DBHandler DB_HANDLER;

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
        DB_HANDLER = new DBHandler();
        DB_HANDLER.retrieveUsers();

        Button providerButton = requireView().findViewById(R.id.ProviderButton);
        Button recieverButton = requireView().findViewById(R.id.RecieverButton);

        providerButton.setOnClickListener(this);
        recieverButton.setOnClickListener(this);
    }

    /**
     * Sets status message
     * @param message to set
     */
    protected void setStatusMessage(String message) {
        TextView statusLabel = requireView().findViewById(R.id.roleStatus);
        statusLabel.setText(message.trim());
    }

    @Override
    public void onClick(View view) {
    }
}
