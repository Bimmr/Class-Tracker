package com.bimmr.classtracker.activites;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.PopupMenu;
import android.widget.Toast;

import com.bimmr.classtracker.Data;
import com.bimmr.classtracker.R;
import com.bimmr.classtracker.Util;

public class HomeActivity extends AppCompatActivity {

    private AppCompatActivity instance;

    private Thread confirmLeave;
    private Boolean warnedLeave = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        this.instance = this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Util.hideActionBar(this);

        Button logout = (Button) findViewById(R.id.logout);

        logout.setOnClickListener(click -> {
            SharedPreferences settings = getSharedPreferences("ClassTracker", 0);
            SharedPreferences.Editor editor = settings.edit();
            editor.remove("email");
            editor.remove("password");
            editor.apply();

            Data.setCurrentEmail(null);
            Data.setCurrentPassword(null);

            Util.switchActivity(this, StartActivity.class);
        });

        this.confirmLeave = new Thread() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3250);
                    warnedLeave = false;
                } catch (Exception e) {
                }
            }
        };
    }

    @Override
    public void onBackPressed() {
        if (!warnedLeave) {
            Toast.makeText(this, "Press back again to exit", Toast.LENGTH_LONG).show();
            warnedLeave = true;
            confirmLeave.start();
        } else {
            Intent homeIntent = new Intent(Intent.ACTION_MAIN);
            homeIntent.addCategory(Intent.CATEGORY_HOME);
            homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(homeIntent);
        }
    }

    @Override
    public void onDestroy() {
        System.out.println("Destroyed");
        confirmLeave.interrupt();
        super.onDestroy();
    }
}
