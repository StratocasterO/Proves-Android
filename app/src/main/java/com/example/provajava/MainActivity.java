package com.example.provajava;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity {
    TextView userText, logout, email, phone;
    ImageView verification;
    boolean emailVerified;
    FirebaseAuth mAuth;
    FirebaseFirestore db;
    FirebaseUser user;
    String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setTitle("Profile");

        // inputs
        userText = findViewById(R.id.main);
        logout = findViewById(R.id.logout);
        email = findViewById(R.id.profileEmail);
        phone = findViewById(R.id.profilePhone);
        verification = findViewById(R.id.verified);

        // firebase
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        Log.d("user", "User: " + user.getUid());

        if (user != null) {
            userID = user.getUid();
            emailVerified = user.isEmailVerified();

            email.setText(user.getEmail());
            userText.setText("Hello " + user.getDisplayName() + ",\nthis is your profile!");
            userText.setTextAlignment(View.TEXT_ALIGNMENT_CENTER); // recenter the text

            // check for mail verification
            if (emailVerified) {
                verification.setVisibility(View.VISIBLE);
            } else {
                verification.setVisibility(View.INVISIBLE);
            }

            // if we have stored info into database
//            db.collection("users").document(userID).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
//                @Override
//                public void onSuccess(DocumentSnapshot documentSnapshot) {
//                    Log.d("database",documentSnapshot.toString());
//
//                    // sets user data in profile
//                    email.setText(documentSnapshot.getString("email"));
//                    phone.setText(documentSnapshot.getString("phone"));
//                    userText.setText("Hello " + documentSnapshot.getString("user") + ",\nthis is your profile!");
//                    userText.setTextAlignment(View.TEXT_ALIGNMENT_CENTER); // recenter the text
//
//                    // check for mail verification
//                    if (emailVerified) {
//                        verification.setVisibility(View.VISIBLE);
//                    } else {
//                        verification.setVisibility(View.INVISIBLE);
//                    }
//                }
//            });
        }

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                startActivity(new Intent(getApplicationContext(), Login.class));
                finish();
            }
        });
    }
}