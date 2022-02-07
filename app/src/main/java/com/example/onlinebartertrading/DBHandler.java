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

    private static final DatabaseReference dbRef = FirebaseDatabase
                .getInstance(FirebaseConstants.FIREBASE_URL)
                .getReference();

    public static String hashString(String password) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-512");
        byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
        BigInteger bigInteger = new BigInteger(1, hash);

        return bigInteger.toString(16);
    }

    // Returns true if successful
    public static boolean registerUser(String fName, String lName, String email, String pword) {
        // Check if user exists
        if (userExists(UUID.nameUUIDFromBytes(email.getBytes()).toString())) return false;

        // Hash password
        String pwordHash="";
        try {
            pwordHash = hashString(pword);
        } catch (Exception NoSuchAlgorithmException) {
            return false;
        }

        return addUserData(fName, lName, email, pwordHash);
    }

    // TODO
    public static boolean userExists(String uuid) {
        return false;
    }

    private static boolean addUserData(String fName, String lName, String email, String pwordHash) {
        String uuid = UUID.nameUUIDFromBytes(email.getBytes()).toString();
        DatabaseReference userRef = dbRef.child(FirebaseConstants.USERS_COLLECTION).child(uuid);
        HashMap<String, String> userData = new HashMap<>();
        userData.put("first_name", fName);
        userData.put("last_name", lName);
        userData.put("email", email);
        userData.put("pwordHash", pwordHash);
        userRef.setValue(userData);
        return true;
    }

    // TODO
    // This don't work yet
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

    //Todo
    public static String getUserName(String uuid) {
        return null;
    }

}
