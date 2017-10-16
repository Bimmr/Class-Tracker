package com.bimmr.classtracker.activites.startup;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.bimmr.classtracker.Manager;
import com.bimmr.classtracker.Preferences;
import com.bimmr.classtracker.R;
import com.bimmr.classtracker.Util;

/**
 * Created by Randy on 2017-09-19.
 */
public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        Manager.init(this);

        super.onPostCreate(savedInstanceState);

        Util.hideActionBar(this);

        Button login = (Button) findViewById(R.id.start_login);
        Button register = (Button) findViewById(R.id.start_register);

        login.setOnClickListener(click -> {
            startActivity(new Intent(this, LoginActivity.class));
        });
        register.setOnClickListener(click -> {
            startActivity(new Intent(this, RegisterActivity.class));
        });

        String email, pass;
        if ((email = Preferences.get("email")) != null && (pass = Preferences.get("password")) != null) {
            startActivity(new Intent(this, LoginActivity.class));
        }
    }
}
