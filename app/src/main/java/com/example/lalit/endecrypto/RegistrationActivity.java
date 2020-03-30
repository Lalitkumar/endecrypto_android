package com.example.lalit.endecrypto;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class RegistrationActivity extends AppCompatActivity {

    private Button registerButton;
    private EditText userName;
    private EditText email;
    private EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        userName = findViewById(R.id.name);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        registerButton = findViewById(R.id.register);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String name = userName.getText().toString();
                final String userEmail = email.getText().toString();
                final String userPass = password.getText().toString();

                SharedPreferences loginPref = getApplicationContext().getSharedPreferences("LoginPref", 0);
                SharedPreferences.Editor loginEditor = loginPref.edit();

                loginEditor.putString("userName", name);
                loginEditor.putString("email", userEmail);
                loginEditor.putString("password", userPass);
                loginEditor.putBoolean("isLoggedIn", false);

                loginEditor.commit();
                Toast.makeText(getApplicationContext(),"Registration Completed successfully.", Toast.LENGTH_LONG).show();

                startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));

            }
        });

    }
}
