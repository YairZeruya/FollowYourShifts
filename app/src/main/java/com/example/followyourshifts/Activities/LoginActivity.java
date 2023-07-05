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

import com.example.followyourshifts.Activities.MainActivity;
import com.example.followyourshifts.Activities.RegisterActivity;
import com.example.followyourshifts.Logic.DataManager;
import com.example.followyourshifts.R;
import com.example.followyourshifts.Utilities.SignalGenerator;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    private EditText edit_text_email, edit_text_password;
    private Button btnLogin;
    ProgressBar progressBar;
    TextView tv_register;

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
        setContentView(R.layout.activity_login);
        edit_text_email = findViewById(R.id.edit_text_email);
        btnLogin = findViewById(R.id.btn_login);
        edit_text_password = findViewById(R.id.edit_text_password);
        progressBar = findViewById(R.id.progress_bar);
        tv_register = findViewById(R.id.tv_register);

        tv_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressBar.setVisibility(View.VISIBLE);
                String email = edit_text_email.getText().toString();
                String[] parts = email.split("@");
                String emailUserName = parts[0];
                String password = edit_text_password.getText().toString();

                if (TextUtils.isEmpty(email)) {
                    SignalGenerator.getInstance().toast("Enter Email", Toast.LENGTH_SHORT);
                    return;
                }
                if(TextUtils.isEmpty(password)){
                    SignalGenerator.getInstance().toast("Enter Password", Toast.LENGTH_SHORT);
                    return;
                }

                DataManager.auth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                progressBar.setVisibility(View.GONE);
                                if (task.isSuccessful()) {
                                    String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
                                    SignalGenerator.getInstance().toast("Welcome " + emailUserName, Toast.LENGTH_SHORT);
                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                    intent.putExtra("username", emailUserName);
                                    intent.putExtra("userId", userId);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    SignalGenerator.getInstance().toast("Login failed.",
                                            Toast.LENGTH_SHORT);

                                }
                            }
                        });
            }
        });
    }
}
