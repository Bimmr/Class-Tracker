package com.bimmr.classtracker.activites;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.bimmr.classtracker.R;
import com.bimmr.classtracker.objects.ClassManager;

public class DashboardActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((Button)findViewById(R.id.dashboard_classes)).setOnClickListener(click ->{
            startActivity(new Intent(this, ClassManageActivity.class));
        });
        ((Button)findViewById(R.id.dashboard_schedule)).setOnClickListener(click ->{
            startActivity(new Intent(this, ScheduleManageActivity.class));
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
