package com.bimmr.classtracker.activites;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.bimmr.classtracker.Data;
import com.bimmr.classtracker.R;
import com.bimmr.classtracker.User;
import com.bimmr.classtracker.Util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Pattern;

/**
 * Created by Randy on 2017-09-19.
 */
public class RegisterActivity extends AppCompatActivity {
    private AppCompatActivity instance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.instance = this;
        setContentView(R.layout.activity_register);

        Util.hideActionBar(this);

        Button create = (Button) findViewById(R.id.create);
        Button back = (Button) findViewById(R.id.back);
        EditText birthday = (EditText) findViewById(R.id.birthdate);

        create.setOnClickListener(click -> {
            EditText emailText = (EditText) findViewById(R.id.email);
            EditText passwordText = (EditText) findViewById(R.id.password);
            EditText nameText = (EditText) findViewById(R.id.name);

            String email = emailText.getText().toString();
            String password = passwordText.getText().toString();
            String name = nameText.getText().toString();


            String invalidMessage = "Invalid Information";//19

            //Email Checks
            if (!email.matches(".*@.*\\..*"))
                invalidMessage += "\n- Enter a valid email address";

            //Password Checks
            if (password.length() < 6)
                invalidMessage += "\n- Password must be at least 6 characters";
            if (!password.matches(".*\\d+.*"))
                invalidMessage += "\n- Password must contain at least one number";

            if(name.length() < 2)
                invalidMessage += "\n- Enter a name";
            if(name.length() < 2)
                invalidMessage += "\n- Select your birthday";



            if (Data.isEmail(email))
                Toast.makeText(instance, "Email is already in use", Toast.LENGTH_LONG).show();
            else if (invalidMessage.length() > 19) {
                Toast.makeText(instance, invalidMessage, Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(instance, "Successfully registered new account", Toast.LENGTH_LONG).show();
                Data.addUser(new User(name, email, password, null));
                Util.switchActivity(this, LoginActivity.class);
            }
        });

        back.setOnClickListener(click -> {
            Util.switchActivity(instance, StartActivity.class);
        });

        Calendar calendar = Calendar.getInstance();

        DatePickerDialog.OnDateSetListener date = (view, year, monthOfYear, dayOfMonth) -> {
            calendar.set(Calendar.YEAR, year);
            calendar.set(Calendar.MONTH, monthOfYear);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            String myFormat = "EEEE, MMMM, dd, yyyy";
            SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

            birthday.setText(sdf.format(calendar.getTime()));
        };


        birthday.setOnClickListener(click -> {
            new DatePickerDialog(this, date, calendar
                    .get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)).show();
        });

    }

}
