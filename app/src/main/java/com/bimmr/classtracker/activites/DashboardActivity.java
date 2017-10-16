package com.bimmr.classtracker.activites;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.bimmr.classtracker.R;

public class DashboardActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((Button)findViewById(R.id.dashboard_classes)).setOnClickListener(click ->{
            Toast.makeText(this, "TODO: Open Class managment page", Toast.LENGTH_SHORT).show();
        });
        ((Button)findViewById(R.id.dashboard_schedule)).setOnClickListener(click ->{
            Toast.makeText(this, "TODO: Open Schedule managment page", Toast.LENGTH_SHORT).show();
        });
        ((Button)findViewById(R.id.dashboard_assignment)).setOnClickListener(click ->{
            Toast.makeText(this, "TODO: Open Assignment managment page", Toast.LENGTH_SHORT).show();
        });
        ((Button)findViewById(R.id.dashboard_tests)).setOnClickListener(click ->{
            Toast.makeText(this, "TODO: Open Test managment page", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_dashboard;
    }

    @Override
    public int getNavigationMenuItemId() {
        return R.id.navigation_dashboard;
    }
}
