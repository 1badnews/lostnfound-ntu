package com.example.lostandfound;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText registerEmail,registerPassword,registerPassword2;
    private Button registerLoginButton,registerButton;

    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        registerLoginButton = findViewById(R.id.registerLoginButton);
        registerLoginButton.setOnClickListener(this);

        registerButton = findViewById(R.id.registerButton);
        registerButton.setOnClickListener(this);

        registerEmail=findViewById(R.id.registerEmail);
        registerPassword=findViewById(R.id.registerPassword);
        registerPassword2=findViewById(R.id.registerPassword2);


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

        if (email.isEmpty()){

        }
    }
}