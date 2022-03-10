package com.example.lostandfound;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView register;
    private FirebaseAuth mAuth;
    private Button login;
    private EditText emailET,passwordET;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        register = (TextView) findViewById(R.id.loginRegisterButton);
        register.setOnClickListener(this);
        login = (Button) findViewById(R.id.loginButton);
        login.setOnClickListener(this);
        emailET = (EditText) findViewById(R.id.loginEmail);
        passwordET = (EditText) findViewById(R.id.loginPassword);
        mAuth = FirebaseAuth.getInstance();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.loginRegisterButton:
                startActivity(new Intent(this, RegisterActivity.class));
                break;

            case R.id.loginButton:
                Login();
                break;
        }
    }
    private void Login(){
        String email = emailET.getText().toString().trim();
        String password = passwordET.getText().toString().trim();

        if (password.isEmpty()){
            passwordET.setError("Please insert Password!");
            passwordET.requestFocus();
            return;
        }
        if (email.isEmpty()){
            emailET.setError("Please insert Email!");
            emailET.requestFocus();
            return;
        }

        mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()){
                    startActivity(new Intent(MainActivity.this, PrimaryActivity.class));
                }else{
                    Toast.makeText(MainActivity.this, "Credentials are invalid!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}