package com.example.firebasedemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class StartActivity extends AppCompatActivity {
    private Button register;
    private Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        register =findViewById(R.id.register);
        login =findViewById(R.id.login);

        register.setOnClickListener(view -> {
            startActivity(new Intent(StartActivity.this,register.class));
            finish();
        });

        login.setOnClickListener(view -> {
            startActivity(new Intent(StartActivity.this,login.class));
            finish();
        });
    }

//    @Override
//    protected void onStart() {
//        super.onStart();
//
//        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//
//        if (user != null){
//            startActivity(new Intent(StartActivity.this,MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK));
//
//        }
//
//
//
//
//
//    }
}