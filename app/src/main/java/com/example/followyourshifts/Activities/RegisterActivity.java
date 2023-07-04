package com.example.followyourshifts.Activities;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.followyourshifts.Logic.DataManager;
import com.example.followyourshifts.R;
import com.example.followyourshifts.Utilities.SignalGenerator;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;

import java.util.HashMap;
import java.util.Map;


public class RegisterActivity extends AppCompatActivity {
    private EditText edit_text_email, edit_text_password;
    private Button btnRegister;
    ProgressBar progressBar;
    TextView move_to_login;


//    @Override
//    public void onStart() {
//        super.onStart();
//        // Check if user is signed in (non-null) and update UI accordingly.
//        FirebaseUser currentUser = mAuth.getCurrentUser();
//        if(currentUser != null){
//            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
//            startActivity(intent);
//            finish();
//        }
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        DataManager.auth = FirebaseAuth.getInstance();
        edit_text_email = findViewById(R.id.edit_text_email);;
        edit_text_password = findViewById(R.id.edit_text_password);
        btnRegister = findViewById(R.id.btn_register);
        progressBar = findViewById(R.id.progress_bar);
        move_to_login = findViewById(R.id.move_to_login);

        move_to_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                // Handle register button click
                String email = edit_text_email.getText().toString();
                String password = edit_text_password.getText().toString();
                // Perform registration process here

                // Dummy code to demonstrate registration success
                if (TextUtils.isEmpty(email)) {
                    SignalGenerator.getInstance().toast("Enter Email", Toast.LENGTH_SHORT);
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    SignalGenerator.getInstance().toast("Enter Password", Toast.LENGTH_SHORT);
                    return;
                }
                DataManager.auth.createUserWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressBar.setVisibility(View.GONE);
                                if (task.isSuccessful()) {
                                    FirebaseUser user = DataManager.auth.getCurrentUser();
                                    if (user != null) {
                                        String uid = user.getUid();
                                        createUserDataInFirestore(uid);
                                    }
                                    SignalGenerator.getInstance().toast("User created.", Toast.LENGTH_SHORT);
                                    Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    SignalGenerator.getInstance().toast("Authentication failed.", Toast.LENGTH_SHORT);
                                }
                            }
                        });
            }
        });
    }
    private void createUserDataInFirestore(String uid) {
        // Create a user document in Firestore with the UID as the document ID
        DocumentReference userDocRef = DataManager.db.collection("Users").document(uid);
        Map<String, Object> userData = new HashMap<>();
        userData.put("email", edit_text_email.getText().toString());
        // Add other user data as needed
        userDocRef.set(userData)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                       SignalGenerator.getInstance().toast("user create",1000);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        SignalGenerator.getInstance().toast("user dont create",1000);
                    }
                });
    }

}
