package com.example.lalit.endecrypto;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.lalit.endecrypto.util.AESUtils;

public class MainActivity extends AppCompatActivity {

    private EditText textToAction;
    private Button encryptButton;
    private Button decryptButton;
    private EditText actionedText;
    private ClipboardManager clipboardManager;
    private ClipData clipData;
    private TextView textView;
    private TextView welcomeText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        textToAction = findViewById(R.id.textToAction);
        actionedText = findViewById(R.id.textActioned);
        encryptButton = findViewById(R.id.encrypt);
        decryptButton = findViewById(R.id.decrypt);
        textView = findViewById(R.id.keyVal);
        welcomeText = findViewById(R.id.welcomeText);
        clipboardManager = (ClipboardManager)getSystemService(Context.CLIPBOARD_SERVICE);
        SharedPreferences loginPref = getApplicationContext().getSharedPreferences("LoginPref", 0);
        welcomeText.setText("Welcome "+loginPref.getString("userName", null));


        encryptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateLogin();
                final String textToEncryptDecrypt = textToAction.getText().toString();
                try {
                    String result = AESUtils.encrypt(textToEncryptDecrypt, textView.getText().toString().getBytes());
                    actionedText.setText(result);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        });


        decryptButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateLogin();
                final String textToEncryptDecrypt = textToAction.getText().toString();
                try {
                    String result = AESUtils.decrypt(textToEncryptDecrypt, textView.getText().toString().getBytes());
                    actionedText.setText(result);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        actionedText.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                String txtcopy = actionedText.getText().toString();
                clipData = ClipData.newPlainText("text",txtcopy);
                clipboardManager.setPrimaryClip(clipData);
                Toast.makeText(getApplicationContext(),"Data Copied to Clipboard", Toast.LENGTH_SHORT).show();
                return true;
            }


        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu); //your file name
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId())
        {
            case R.id.logout:
                SharedPreferences loginPref = getApplicationContext().getSharedPreferences("LoginPref", 0);
                SharedPreferences.Editor loginEditor = loginPref.edit();
                loginEditor.putBoolean("isLoggedIn", false);
                loginEditor.commit();
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                break;
            case R.id.registration:
                startActivity(new Intent(MainActivity.this, RegistrationActivity.class));
                break;
            case R.id.about:
                startActivity(new Intent(MainActivity.this, AboutActivity.class));
                break;
        }
        return true;
    }

    public void validateLogin(){
        SharedPreferences loginPref = getSharedPreferences("LoginPref", 0);
        if(!loginPref.getBoolean("isLoggedIn", false)){
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
        }
    }
}
