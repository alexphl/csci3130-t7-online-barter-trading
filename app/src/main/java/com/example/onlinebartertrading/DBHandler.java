package com.example.onlinebartertrading;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.UUID;

public class DBHandler {

    public String hashString(String password) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-512");
        byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
        BigInteger bigInteger = new BigInteger(1, hash);

        return bigInteger.toString(16);
    }

    private void addUserData() {
        String uuid = UUID.randomUUID().toString();
        DatabaseReference dbRef = FirebaseDatabase.getInstance(FirebaseConstants.FIREBASE_URL).getReference().child(FirebaseConstants.USERS_COLLECTION).child(uuid);
        HashMap<String, String> userData = new HashMap<>();
        userData.put("first_name", "FIRSTNAME_HERE");
        userData.put("last_name", "LASTNAME_HERE");
        userData.put("email", "EMAIL_HERE");
        dbRef.push().setValue(userData);
    }

}
