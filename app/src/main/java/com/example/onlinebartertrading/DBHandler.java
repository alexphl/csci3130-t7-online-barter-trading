package com.example.onlinebartertrading;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.UUID;

public class DBHandler {

    private static DatabaseReference dbRef;

    protected static void initializeDatabase() {
        dbRef = FirebaseDatabase
                .getInstance(FirebaseConstants.FIREBASE_URL)
                .getReference();
    }

    public static String hashString(String password) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-512");
        byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
        BigInteger bigInteger = new BigInteger(1, hash);

        return bigInteger.toString(16);
    }

    public static boolean registerUser() {
        return false;
    }

    public static boolean userExists(String uuid) {
        return false;
    }

    private void addUserData(String fName, String lName, String email) {
        String uuid = UUID.nameUUIDFromBytes(email.getBytes()).toString();
        DatabaseReference userRef = dbRef.child(FirebaseConstants.USERS_COLLECTION).child(uuid);
        HashMap<String, String> userData = new HashMap<>();
        userData.put("first_name", "FIRSTNAME_HERE");
        userData.put("last_name", "LASTNAME_HERE");
        userData.put("email", "EMAIL_HERE");
        userRef.push().setValue(userData);
    }

    // TODO
    public static String getUserEmail(String uuid) {
        String extractedEmail = "";
        DatabaseReference emailRef = dbRef
                .child(FirebaseConstants.USERS_COLLECTION)
                .child(uuid)
                .child("email");

        emailRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //extractedEmail = snapshot.getValue(String.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        return extractedEmail;
    }

    public static String getUserName(String uuid) {
        return null;
    }

}
