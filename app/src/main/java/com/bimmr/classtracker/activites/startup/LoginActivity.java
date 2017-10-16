package com.bimmr.classtracker.activites.startup;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Pair;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bimmr.classtracker.Manager;
import com.bimmr.classtracker.Preferences;
import com.bimmr.classtracker.R;
import com.bimmr.classtracker.Util;
import com.bimmr.classtracker.activites.MainActivity;

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


        Button login = (Button) findViewById(R.id.login_login);
        Button back = (Button) findViewById(R.id.login_back);

        login.setOnClickListener(click -> {
            EditText emailText = (EditText) findViewById(R.id.login_email);
            EditText passwordText = (EditText) findViewById(R.id.login_password);

            String email = emailText.getText().toString();
            String password = passwordText.getText().toString();
            tryLogin(email, password);
        });

        back.setOnClickListener(click -> {
            startActivity(new Intent(this, StartActivity.class));
        });

        String email, pass;
        if ((email = Preferences.get("email")) != null && (pass = Preferences.get("password")) != null) {

            if (tryLogin(email, pass)) {
                Toast.makeText(this, "You've been logged back in", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Unable to login with last used account", Toast.LENGTH_SHORT).show();
                Preferences.remove("email", "password");
            }
        }
    }

    public boolean tryLogin(String email, String password) {
        boolean loggedIn = false;

        //Check if user can login
        if (Manager.getData().isValid(email, password)) {
            startActivity(new Intent(this, MainActivity.class));

            //Save login info
            Preferences.set(new Pair<>("email", email), new Pair<>("password", password));
            loggedIn = true;
        } else {
            Preferences.remove("email", "password");
            Toast.makeText(instance, "Invalid login information", Toast.LENGTH_SHORT).show();
        }
        return loggedIn;
    }
}
