package com.bimmr.classtracker.activites.startup;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;

import com.bimmr.classtracker.Manager;
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

        //Check if there was a previously logged in account, and if so try and login again.
        SharedPreferences settings = getSharedPreferences("ClassTracker", 0);

        if (settings.contains("email") && settings.contains("password")) {
            Manager.getData().setCurrentEmail(settings.getString("email", null));
            Manager.getData().setCurrentPassword(settings.getString("password", null));

            startActivity(new Intent(this, LoginActivity.class));
        }
    }
}
