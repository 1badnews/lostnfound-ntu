package com.example.lostandfound;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView register;
    private FirebaseAuth mAuth;
    private Button login;
    private EditText emailET,passwordET;
    private TextView lostpassword;




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
        lostpassword = (TextView) findViewById(R.id.Lostpassword);

        lostpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = emailET.getText().toString().trim();
                if (email.isEmpty()){

                    emailET.setError("Email is empty!");
                    emailET.requestFocus();
                    return;
                }

                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    emailET.setError("Please type in a valid Email!");
                    return;
                }



                mAuth.sendPasswordResetEmail(email)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    Toast.makeText(MainActivity.this, "Password reset instructions have been sent to your Email!", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        });
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
                    FirebaseUser verify =FirebaseAuth.getInstance().getCurrentUser();
                    Boolean verif = verify.isEmailVerified();
                    if (verif==true)
                    startActivity(new Intent(MainActivity.this, PrimaryActivity.class));
                    else{
                        Toast.makeText(MainActivity.this, "Please verify your email!", Toast.LENGTH_SHORT).show();
                    }
                }else{
                    Toast.makeText(MainActivity.this, "Credentials are invalid!", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}