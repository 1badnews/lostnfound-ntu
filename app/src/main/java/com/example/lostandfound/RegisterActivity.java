package com.example.lostandfound;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
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
import com.google.firebase.database.FirebaseDatabase;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText registerEmail,registerPassword,registerPassword2,registerStudentID;
    private Button registerLoginButton,registerButton;

    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        registerLoginButton = findViewById(R.id.registerLoginButton);
        registerLoginButton.setOnClickListener(this);
        mAuth = FirebaseAuth.getInstance();

        registerButton = findViewById(R.id.registerButton);
        registerButton.setOnClickListener(this);

        registerEmail=findViewById(R.id.registerEmail);
        registerPassword=findViewById(R.id.registerPassword);
        registerPassword2=findViewById(R.id.registerPassword2);
        registerStudentID=findViewById(R.id.registerStudentID);


    }

    @Override
    public void onClick(View v) {
        switch(v.getId())
        {
            case R.id.registerLoginButton:
                startActivity(new Intent(this, MainActivity.class));
                break;
            case R.id.registerButton:
                register();
        }
    }
    private void register()
    {
        String email=registerEmail.getText().toString().trim();
        String password=registerPassword.getText().toString().trim();
        String password2=registerPassword2.getText().toString().trim();
        String studentID=registerStudentID.getText().toString().trim();

        if (studentID.isEmpty()){
            registerStudentID.setError("Please enter your student ID");
            registerStudentID.requestFocus();
            return;
        }

        if (email.isEmpty()){

            registerEmail.setError("Email is empty!");
            registerEmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            registerEmail.setError("Please type in a valid Email!");
            return;
        }
        if (password.isEmpty()){
            registerPassword.setError("Password is empty!");
            registerPassword.requestFocus();
            return;
        }

        if (password2.isEmpty()){
            registerPassword2.setError("Confirm Password is empty!");
            registerPassword2.requestFocus();
            return;

        }
        if (password.length() < 5){
            registerPassword.setError("Please enter a password with more than 5 characters.");
            return;
        }
        if (!registerPassword.getText().toString().equals(registerPassword2.getText().toString()))
        {
            registerPassword2.setError("Passwords do not match!");
            registerPassword.setError("Passwords do not match!");
            registerPassword.requestFocus();
            return;

        }

        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()){
                        Account user = new Account(studentID,email);

                            FirebaseDatabase.getInstance().getReference("Accounts").child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(user).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful())
                                    {
                                        Toast.makeText(RegisterActivity.this,"Account registered successfully!", Toast.LENGTH_SHORT).show();

                                    }
                                    else{
                                        Toast.makeText(RegisterActivity.this,"Account failed to register!", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        } else{
                            Toast.makeText(RegisterActivity.this,"Account failed to register!", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
        startActivity(new Intent(this, MainActivity.class));
    }
}