package com.bimmr.classtracker.activites;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.bimmr.classtracker.Manager;
import com.bimmr.classtracker.R;
import com.bimmr.classtracker.Util;
import com.bimmr.classtracker.activites.startup.StartActivity;

public class SettingsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((TextView)findViewById(R.id.settings_currentEmail)).setText(Manager.getData().getCurrentEmail());

        ((Button) findViewById(R.id.settings_logout)).setOnClickListener(click -> {
            SharedPreferences settings = getSharedPreferences("ClassTracker", 0);
            SharedPreferences.Editor editor = settings.edit();
            editor.remove("email");
            editor.remove("password");
            editor.apply();

            Manager.getData().setCurrentEmail(null);
            Manager.getData().setCurrentPassword(null);

            startActivity(new Intent(this, StartActivity.class));
        });

    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_settings;
    }

    @Override
    public int getNavigationMenuItemId() {
        return R.id.navigation_settings;
    }

}
