package com.example.firebasedemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class login extends AppCompatActivity {
    private EditText emaillog,passwordlog;
    private Button loginpbtn;

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emaillog =findViewById(R.id.emaillog);
        passwordlog =findViewById(R.id.passwordlog);
        loginpbtn =findViewById(R.id.loginpbtn);

        auth=FirebaseAuth.getInstance();

        loginpbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String txt_email = emaillog.getText().toString();
                String txt_password = passwordlog.getText().toString();
                loginUser(txt_email , txt_password);
            }
        });
    }

    private void loginUser(String email, String password) {
        auth.signInWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                Toast.makeText(login.this,"login successfull",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(login.this,MainActivity.class));
                finish();
            }
        });
    }
}