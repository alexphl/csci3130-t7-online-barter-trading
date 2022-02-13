package com.example.onlinebartertrading;

import android.provider.ContactsContract;

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
import java.util.concurrent.CountDownLatch;

/**
 * This is our database worker class
 * It is a bootstrap for all database functions
 */
public class DBHandler {

    private final DatabaseReference dbRef;
    private final HashMap<String, String> users;

    /**
     * Initializes a reference to our main database.
     */
    public DBHandler() {
        dbRef = FirebaseDatabase
                .getInstance(FirebaseConstants.FIREBASE_URL)
                .getReference();
        users = new HashMap<>();
    }

    /**
     * Support dependency injection for DatabaseReference
     * Useful for mock database testing
     */
    public DBHandler(DatabaseReference dbRef) {
        this.dbRef = dbRef;
        users = new HashMap<>();
    }

    /**
     * Hashes the input password with SHA-512 and returns the hash
     * @param password to hash
     * @return password hash
     */
    public static String hashString(String password) throws NoSuchAlgorithmException {
        MessageDigest digest = MessageDigest.getInstance("SHA-512");
        byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
        BigInteger bigInteger = new BigInteger(1, hash);

        return bigInteger.toString(16);
    }

    /**
     * Retrieves users from database and adds them to local hashmap users
     */
    public void retrieveUsers() {
        DatabaseReference usersRef = dbRef.child(FirebaseConstants.USERS_COLLECTION);

        usersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                users.clear();
                
                for(DataSnapshot children: snapshot.getChildren()) {
                    String email = children.child("email").getValue(String.class);
                    String passwordHash = children.child("pwordHash").getValue(String.class);
                    users.put(email, passwordHash);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                System.out.println("DATABASE ERROR: " + error.getMessage());
            }
        });
    }

    /**
     * Bootstrap to add new user data to the database
     * Checks if user exists and hashes the password
     * @return true if successful
     *
     * @param fName first name submitted by the user
     * @param lName last name
     * @param email email
     * @param pword password
     */
    public boolean registerUser(String fName, String lName, String email, String pword) {
        // Check if user exists
        retrieveUsers(); // Calling in case no call outside class
        if (userExists(email)) return false;

        // Hash password
        String pwordHash="";
        try {
            pwordHash = hashString(pword);
        } catch (Exception NoSuchAlgorithmException) {
            return false;
        }

        return addUserData(fName, lName, email, pwordHash);
    }

    /**
     * Adds user data to the database
     * @return true if successful
     *
     * @param fName first name submitted by the user
     * @param lName last name
     * @param email email
     * @param pwordHash password hash
     */
    private boolean addUserData(String fName, String lName, String email, String pwordHash) {
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

    /**
     * Checks if a user exists in the database
     * @param email to check for
     * @return true if user with provided email exists
     */
    public boolean userExists(String email) {
        return users.containsKey(email);
    }

    /**
     * Checks if the email of a user and the password hash match
     * @param email of the user to check
     * @param passwordHash provided by user
     * @return true if match
     */
    public boolean passwordMatches(String email, String passwordHash) {
        if (!userExists(email)) {
            return false;
        }
        return users.get(email).equals(passwordHash);
    }

}
