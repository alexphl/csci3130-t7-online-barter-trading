package com.example.onlinebartertrading;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.fragment.app.Fragment;

public class SignupFormFragment extends Fragment {

    EditText user_name, user_email, user_pword;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        user_email = requireView().findViewById(R.id.emailField);
        user_pword = requireView().findViewById(R.id.passwordField);
        //user_name = requireView().findViewById(R.id.nameField);

        //attach an event handler to the retrieve button
        Button retrieveButton = requireView().findViewById(R.id.signupSubmitButton);
        retrieveButton.setOnClickListener((View.OnClickListener) this);

        return inflater.inflate(R.layout.signup_form_fragment, container, false);
    }

    public void onClick(View view) {

    }
}
