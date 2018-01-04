package com.bimmr.classtracker.activites;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.bimmr.classtracker.Manager;
import com.bimmr.classtracker.Preferences;
import com.bimmr.classtracker.R;
import com.bimmr.classtracker.activites.startup.StartActivity;
import com.bimmr.classtracker.tasks.DownloadTask;

public class SettingsActivity extends BaseActivity {

    public static SettingsActivity settingsActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        settingsActivity = this;
        super.onCreate(savedInstanceState);

        ((TextView) findViewById(R.id.settings_currentEmail)).setText(Preferences.get("email"));

        ((Button) findViewById(R.id.settings_logout)).setOnClickListener(click -> {
            new AlertDialog.Builder(this)
                    .setTitle("Are you sure?")
                    .setMessage("Do you really want to log out?")
                    .setPositiveButton(android.R.string.yes, (dialog, whichButton) -> {
                        Preferences.remove("email", "password");
                        startActivity(new Intent(getBaseContext(), StartActivity.class));
                    })
                    .setNegativeButton(android.R.string.no, null).show();
        });

        ((TextView) findViewById(R.id.settings_changePassTitle)).setOnClickListener(click -> {
            ((TextView) findViewById(R.id.settings_changePass)).callOnClick();
        });
        ((TextView) findViewById(R.id.settings_changePass)).setOnClickListener(click -> {

            EditText pass = new EditText(this);

            new AlertDialog.Builder(this)
                    .setTitle("Change your password")
                    .setMessage("Enter your new password")
                    .setView(pass)
                    .setPositiveButton("Set", (dialog, whichButton) -> {
                        String p = pass.getText().toString();
                        String email = Preferences.get("email");

                        Preferences.set(new Pair<>("email", email.toLowerCase()), new Pair<>("password", p));
                        Manager.getSqlLiteManager().update("User", new String[]{"Password", "Email"}, new String[]{p, email});
                    })
                    .setNegativeButton("Cancel", (dialog, whichButton) -> {

                    })
                    .show();

        });
        ((Button) findViewById(R.id.settings_contactme)).setOnClickListener(click -> {
            startActivity(new Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:Bimmr6696@gmail.com")));
        });
        ((Button) findViewById(R.id.settings_viewOnAppStore)).setOnClickListener(click -> {
            final String appPackageName = getPackageName();
            try {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
            } catch (android.content.ActivityNotFoundException anfe) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
            }
        });
        ((Button) findViewById(R.id.settings_viewPDF)).setOnClickListener(click -> {
            new DownloadTask().execute();
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
