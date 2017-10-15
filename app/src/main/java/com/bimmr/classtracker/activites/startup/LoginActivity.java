package com.bimmr.classtracker.activites.startup;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bimmr.classtracker.Manager;
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

        if (Manager.getData().getCurrentEmail() != null && Manager.getData().getCurrentPassword() != null) {

            if (tryLogin(Manager.getData().getCurrentEmail(), Manager.getData().getCurrentPassword())) {
                Toast.makeText(this, "You've been logged back in", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Unable to login with last used account", Toast.LENGTH_SHORT).show();
                Manager.getData().setCurrentEmail(null);
                Manager.getData().setCurrentPassword(null);
            }
        }
    }

    public boolean tryLogin(String email, String password) {
        boolean loggedIn = false;

        SharedPreferences settings = getSharedPreferences("ClassTracker", 0);
        SharedPreferences.Editor editor = settings.edit();

        //Check if user can login
        if (Manager.getData().isValid(email, password)) {
            startActivity(new Intent(this, MainActivity.class));

            //Save login info
            editor.putString("email", email);
            editor.putString("password", password);
            editor.apply();

            Manager.getData().setCurrentEmail(email);
            Manager.getData().setCurrentPassword(password);

            loggedIn = true;
        } else {

            //Remove login info
            editor.remove("email");
            editor.remove("password");
            editor.apply();

            Manager.getData().setCurrentEmail(null);
            Manager.getData().setCurrentPassword(null);

            Toast.makeText(instance, "Invalid login information", Toast.LENGTH_SHORT).show();
        }
        return loggedIn;
    }
}
