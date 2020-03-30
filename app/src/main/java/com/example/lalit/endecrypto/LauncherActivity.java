package com.example.lalit.endecrypto;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;

public class LauncherActivity extends Activity {

    private Button loginButton;
    private Button registerButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_launcher);

        loginButton = findViewById(R.id.launch_login);
        registerButton = findViewById(R.id.launch_register);

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SharedPreferences loginPref = getSharedPreferences("LoginPref", 0);
                if(loginPref.getString("userName",null) == null){
                    startActivity(new Intent(LauncherActivity.this, RegistrationActivity.class));
                }else{
                    confirmRegistrationUpdate();
                }
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(LauncherActivity.this, LoginActivity.class));
            }
        });


    }

    public void confirmRegistrationUpdate(){

        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which){
                    case DialogInterface.BUTTON_POSITIVE:
                        startActivity(new Intent(LauncherActivity.this, RegistrationActivity.class));
                        break;

                    case DialogInterface.BUTTON_NEGATIVE:
                        startActivity(new Intent(LauncherActivity.this, LoginActivity.class));
                        break;
                }
            }
        };

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Confirm");
        builder.setMessage("You are already registered. Do you want to update Registration details?")
                .setPositiveButton("Yes", dialogClickListener)
                .setNegativeButton("No", dialogClickListener).show();

    }
}
