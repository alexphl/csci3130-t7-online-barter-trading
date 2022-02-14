package com.example.onlinebartertrading;

import android.view.View;
import android.widget.TextView;

/**
 * NameSet, used exclusively in Editor class
 */
public class NameSet {
    TextView name, detail, value;

    NameSet(View view) {
        name = view.findViewById(R.id.textView1);
        value = view.findViewById(R.id.textView3);
        detail = view.findViewById(R.id.textview2);
    }


}