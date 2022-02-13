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

public class LoginFormFragment extends Fragment implements View.OnClickListener {

    public static String LOGGED_USER_ID = "com.example.onlinebartertrading.userID";
    public DBHandler DB_HANDLER;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.login_form_fragment, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        DB_HANDLER = new DBHandler();
        DB_HANDLER.retrieveUsers();

        Button loginButton = requireView().findViewById(R.id.loginButton);
        loginButton.setOnClickListener(this);
    }

    // Gets password from edit field
    protected String getEmailAddress() {
        EditText emailBox = requireView().findViewById(R.id.emailField);
        return emailBox.getText().toString().trim();
    }

    // Gets password from edit field
    protected String getPassword() {
        EditText passwordBox = requireView().findViewById(R.id.passwordField);
        return passwordBox.getText().toString().trim();
    }

    /**
     * Hashes password and returns the hash
     * @param password to hash
     * @return the hashed password
     */
    protected String getPasswordHash(String password) {
        String passwordHash = "";

        try {
            passwordHash = DBHandler.hashString(password);
        }
        catch (NoSuchAlgorithmException e) {
            System.out.println("Exception occured: " + e);
        }

        return passwordHash;
    }

    /**
     * Sets status message
     * @param message to set
     */
    protected void setStatusMessage(String message) {
        TextView statusLabel = requireView().findViewById(R.id.loginStatus);
        statusLabel.setText(message.trim());
    }

    // Checks if email field is empty
    protected boolean isEmptyEmail(String email) {
        return email.isEmpty();
    }

    // Check if password field is empty
    protected boolean isEmptyPassword(String password) {
        return password.isEmpty();
    }

    /**
     * Switches to post once login successful
     * @param email to pass to next activity
     */
    protected void switch2PostsWindow(String email) {
        Intent switchActivity = new Intent(this, MakePostActivity.class);
        switchActivity.putExtra(LOGGED_USER_ID, email);
        startActivity(switchActivity);
    }

    @Override
    public void onClick(View view) {
        String emailAddress = getEmailAddress();
        String passwordHash = getPasswordHash(getPassword());

        String errorMessage;

        if (isEmptyEmail(emailAddress)) {
            errorMessage = getResources().getString(R.string.EMPTY_EMAIL).trim();
        }
        else if (isEmptyPassword(passwordHash)) {
            errorMessage = getResources().getString(R.string.EMPTY_PASSWORD).trim();
        }
        else if (!DB_HANDLER.userExists(emailAddress)) {
            errorMessage = getResources().getString(R.string.INVALID_EMAIL).trim();
        }
        else if (!DB_HANDLER.passwordMatches(emailAddress, passwordHash)) {
            errorMessage = getResources().getString(R.string.INVALID_PASSWORD).trim();
        }
        else {
            errorMessage = "";
            switch2PostsWindow(emailAddress);
        }

        setStatusMessage(errorMessage);
    }
}