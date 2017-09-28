package com.bimmr.classtracker.activites;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;

import com.bimmr.classtracker.Data;
import com.bimmr.classtracker.R;
import com.bimmr.classtracker.Util;

import java.util.Map;

/**
 * Created by Randy on 2017-09-19.
 */
public class StartActivity extends AppCompatActivity {

    private AppCompatActivity instance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.instance = this;
        setContentView(R.layout.activity_start);

        Data.init();

        super.onPostCreate(savedInstanceState);

        Util.hideActionBar(this);

        Button login = (Button) findViewById(R.id.login);
        Button register = (Button) findViewById(R.id.register);

        login.setOnClickListener(click -> {
            Util.switchActivity(instance, LoginActivity.class);
        });
        register.setOnClickListener(click -> {
            Util.switchActivity(instance, RegisterActivity.class);
        });

        SharedPreferences settings = getSharedPreferences("ClassTracker", 0);
        for(Map.Entry<String, ?> e : settings.getAll().entrySet()){
            Log.d("info", e.getKey() + " "+ e.getValue());
        }

        if (settings.contains("email") && settings.contains("password")) {
            Data.setCurrentEmail(settings.getString("email", null));
            Data.setCurrentPassword(settings.getString("password", null));

            Util.switchActivity(this, LoginActivity.class);
        }
    }
}
