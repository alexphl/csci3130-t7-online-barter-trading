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

import com.example.onlinebartertrading.entities.User;
import com.example.onlinebartertrading.lib.DBHandler;

import java.security.NoSuchAlgorithmException;

/**
 * This is the Login form Fragment
 * that is included as part of AuthActivity.
 */
public class LoginFormFragment extends Fragment implements View.OnClickListener {

    public DBHandler DB_HANDLER;
    private User user;

    /**
     * Obligatory Fragment inflater.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.login_form_fragment, container, false);
    }

    /**
     * This event is triggered soon after onCreateView().
     * View setup occurs here.
     */
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        DB_HANDLER = new DBHandler();
        DB_HANDLER.retrieveUsers();

        Button loginButton = requireView().findViewById(R.id.loginButton);
        loginButton.setOnClickListener(this);
    }

    /**
     * Email field getter
     */
    protected String getEmailAddress() {
        EditText emailBox = requireView().findViewById(R.id.emailFieldL);
        return emailBox.getText().toString().trim();
    }

    /**
     * Password field getter
     */
    protected String getPassword() {
        EditText passwordBox = requireView().findViewById(R.id.passwordFieldL);
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

    /**
     * @return true if param string is empty
     */
    protected boolean isEmptyEmail(String email) {
        return email.isEmpty();
    }

    /**
     * @return true if param string is empty
     */
    protected boolean isEmptyPassword(String password) {
        return password.isEmpty();
    }

    /**
     * Switches to post once login successful
     */
    protected void switch2RoleWindow() {
        Intent switchActivity = new Intent(getActivity(), RoleDecisionActivity.class);
        switchActivity.putExtra("user", user);
        startActivity(switchActivity);
    }

    /**
     * "Login" button processor.
     *  This will do the following:
     *      Validate user input for email and password fields
     *      Check if the user exists
     *      Switch to the Posts Activity
     */
    @Override
    public void onClick(View view) {
        String emailAddress = getEmailAddress();
        String passwordHash = getPasswordHash(getPassword());

        String errorMessage = "";

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
            user = new User(emailAddress);
            FCMService.setUser(user);
            setStatusMessage(errorMessage);
            switch2RoleWindow();
        }


        setStatusMessage(errorMessage);
    }
}