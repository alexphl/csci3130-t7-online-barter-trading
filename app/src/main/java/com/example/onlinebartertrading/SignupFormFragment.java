package com.example.onlinebartertrading;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

public class SignupFormFragment extends Fragment implements View.OnClickListener {

    EditText fnameField, lnameField, emailField, pwordField, pwordRepField;
    TextView statusLabel;
    DBHandler dbHandler;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.signup_form_fragment, container, false);
    }

    // This event is triggered soon after onCreateView().
    // Any view setup should occur here.  E.g., view lookups and attaching view listeners.
    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        fnameField = requireView().findViewById(R.id.fnameField);
        lnameField = requireView().findViewById(R.id.lnameField);
        emailField = requireView().findViewById(R.id.emailField);
        pwordField = requireView().findViewById(R.id.passwordField);
        pwordRepField = requireView().findViewById(R.id.passwordConfirmField);

        statusLabel = requireView().findViewById(R.id.statusLabel);

        dbHandler = new DBHandler();

        //attach an event handler to the button
        Button formSubmitButton = requireView().findViewById(R.id.signupSubmitButton);
        formSubmitButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {

        String fName = fnameField.getText().toString().trim();
        String lName = lnameField.getText().toString().trim();
        if(isEmptyName(fName, lName)) {
            setStatusMessage("Name cannot be empty"); return;
        }

        String email = emailField.getText().toString().trim();

        if (email.isEmpty()) {
            setStatusMessage("Email cannot be empty"); return;
        }

        if (!isValidEmailAddress(email)) {
            setStatusMessage("Invalid email"); return;
        }

        String pword = pwordField.getText().toString().trim();
        String pwordRep = pwordRepField.getText().toString().trim();

        if(pword.isEmpty()) {
            setStatusMessage("Password cannot be empty"); return;
        }
        if (!pword.equals(pwordRep)) {
            setStatusMessage("Passwords don't match"); return;
        }

        dbHandler.registerUser(fName, lName, email, pword);
    }

    protected void setStatusMessage(String message) {
        statusLabel.setText(message.trim());
    }

    protected boolean isValidEmailAddress(String emailAddress) {
        return emailAddress.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$");
    }

    protected boolean isEmptyName(String fName, String lName) {
        return fName.isEmpty() || lName.isEmpty();
    }
}
