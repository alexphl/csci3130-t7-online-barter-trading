package com.example.onlinebartertrading;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.UUID;

public class DBUserPreference {

    private final DatabaseReference dbRef;
    private final DatabaseReference userRef;

    public DBUserPreference(String email) {
        dbRef = FirebaseDatabase.getInstance(FirebaseConstants.FIREBASE_URL).getReference();

        String uuid = UUID.nameUUIDFromBytes(email.getBytes()).toString();
        userRef = dbRef.child(FirebaseConstants.USERS_COLLECTION).child(uuid);
    }
}
