package com.bimmr.classtracker.activites;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.bimmr.classtracker.Preferences;
import com.bimmr.classtracker.R;
import com.bimmr.classtracker.activites.startup.StartActivity;

public class SettingsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((TextView)findViewById(R.id.settings_currentEmail)).setText(Preferences.get("email"));

        ((Button) findViewById(R.id.settings_logout)).setOnClickListener(click -> {
            Preferences.remove("email","password");

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
