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
        return inflater.inflate(R.layout.login_form_fragment, container, false);
    }



    @Override
    public void onClick(View view) {

    }
}
