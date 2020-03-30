package com.example.lalit.endecrypto;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    private Button loginButton;
    private EditText loginName;
    private EditText loginPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginButton = findViewById(R.id.login);
        loginName = findViewById(R.id.loginName);
        loginPassword = findViewById(R.id.loginPass);

        SharedPreferences loginPref = getSharedPreferences("LoginPref", 0);
        if(loginPref.getString("userName",null) == null){
            Toast.makeText(getApplicationContext(),"Please complete registration process first", Toast.LENGTH_LONG).show();
            startActivity(new Intent(LoginActivity.this, RegistrationActivity.class));
        }


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String userName = loginName.getText().toString();
                final String userPass = loginPassword.getText().toString();

                if(validateUser(userName, userPass)){
                    SharedPreferences loginPref = getSharedPreferences("LoginPref", 0);
                    SharedPreferences.Editor loginEditor = loginPref.edit();
                    loginEditor.putBoolean("isLoggedIn", true);
                    loginEditor.commit();
                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
                }
            }
        });

    }

    boolean validateUser(final String userName, final String userPass){
        SharedPreferences loginPref = getSharedPreferences("LoginPref", 0);
        if(userName.length()==0 || userName == null){
            Toast.makeText(getApplicationContext(),"User Name can not be blank", Toast.LENGTH_LONG).show();
            return false;
        }else if(userPass.length() == 0 || userPass == null){
            Toast.makeText(getApplicationContext(),"Password can not be blank", Toast.LENGTH_LONG).show();
            return false;
        }else if(!userName.equals(loginPref.getString("email",null))){
            Toast.makeText(getApplicationContext(),"Incorrect userName", Toast.LENGTH_LONG).show();
            return false;
        }else if(!userPass.equals(loginPref.getString("password",null))){
            Toast.makeText(getApplicationContext(),"Incorrect Password", Toast.LENGTH_LONG).show();
            return false;
        }

        return true;
    }

}
