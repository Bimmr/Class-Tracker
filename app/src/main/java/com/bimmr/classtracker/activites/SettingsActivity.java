package com.bimmr.classtracker.activites;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bimmr.classtracker.Manager;
import com.bimmr.classtracker.Preferences;
import com.bimmr.classtracker.R;
import com.bimmr.classtracker.activites.startup.StartActivity;

public class SettingsActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((TextView)findViewById(R.id.settings_currentEmail)).setText(Preferences.get("email"));

        ((Button) findViewById(R.id.settings_logout)).setOnClickListener(click -> {
            new AlertDialog.Builder(this)
                    .setTitle("Are you sure?")
                    .setMessage("Do you really want to log out?")
                    .setPositiveButton(android.R.string.yes, (dialog, whichButton) -> {
                        Preferences.remove("email","password");
                        startActivity(new Intent(getBaseContext(), StartActivity.class));
                    })
                    .setNegativeButton(android.R.string.no, null).show();
        });

        ((TextView) findViewById(R.id.settings_changePassTitle)).setOnClickListener(click -> {
            ((TextView) findViewById(R.id.settings_changePass)).callOnClick();
        });
        ((TextView) findViewById(R.id.settings_changePass)).setOnClickListener(click -> {
            Toast.makeText(Manager.getContext(), "Change pass", Toast.LENGTH_SHORT).show();
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
