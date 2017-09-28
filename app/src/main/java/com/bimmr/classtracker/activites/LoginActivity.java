package com.bimmr.classtracker.activites;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bimmr.classtracker.Data;
import com.bimmr.classtracker.R;
import com.bimmr.classtracker.Util;

import java.util.logging.Logger;

/**
 * Created by Randy on 2017-09-19.
 */
public class LoginActivity extends AppCompatActivity {

    private AppCompatActivity instance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.instance = this;
        setContentView(R.layout.activity_login);

        Util.hideActionBar(this);


        Button login = (Button) findViewById(R.id.login);
        Button back = (Button) findViewById(R.id.back);

        login.setOnClickListener(click -> {
            EditText emailText = (EditText) findViewById(R.id.email);
            EditText passwordText = (EditText) findViewById(R.id.password);

            String email = emailText.getText().toString();
            String password = passwordText.getText().toString();
            tryLogin(email, password);
        });

        back.setOnClickListener(click -> {
            Util.switchActivity(instance, StartActivity.class);
        });

        if (Data.getCurrentEmail() != null && Data.getCurrentPassword() != null) {

            if (tryLogin(Data.getCurrentEmail(), Data.getCurrentPassword())) {
                Toast.makeText(this, "You've been logged back in", Toast.LENGTH_SHORT).show();
            } else
                Toast.makeText(this, "Unable to login with last used account", Toast.LENGTH_SHORT).show();
        }
    }

    public boolean tryLogin(String email, String password) {
        boolean loggedIn = false;

        if (Data.isValid(email, password)) {
            Util.switchActivity(instance, HomeActivity.class);

            SharedPreferences settings = getSharedPreferences("ClassTracker", 0);
            SharedPreferences.Editor editor = settings.edit();
            editor.putString("email", email);
            editor.putString("password", password);
            editor.apply();

            if (Data.getCurrentEmail() == null && Data.getCurrentPassword() == null) {
                Data.setCurrentEmail(email);
                Data.setCurrentPassword(password);
            }
            loggedIn = true;
        } else
            Toast.makeText(instance, "Invalid login information", Toast.LENGTH_SHORT).show();
        return loggedIn;
    }
}
