package com.bimmr.classtracker.activites.startup;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bimmr.classtracker.Manager;
import com.bimmr.classtracker.R;
import com.bimmr.classtracker.User;
import com.bimmr.classtracker.Util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by Randy on 2017-09-19.
 */
public class RegisterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Util.hideActionBar(this);

        Button create = (Button) findViewById(R.id.register_create);
        Button back = (Button) findViewById(R.id.register_back);
        EditText birthday = (EditText) findViewById(R.id.register_birthdate);

        create.setOnClickListener(click -> {
            EditText emailText = (EditText) findViewById(R.id.register_email);
            EditText passwordText = (EditText) findViewById(R.id.register_password);
            EditText nameText = (EditText) findViewById(R.id.register_name);

            String email = emailText.getText().toString();
            String password = passwordText.getText().toString();
            String name = nameText.getText().toString();
            String date = birthday.getText().toString();


            String invalidMessage = "Invalid Information"; // 19 chars long

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
            if(date.length() == 0)
                invalidMessage += "\n- Select your birthday";

            if (Manager.getData().isEmail(email))
                Toast.makeText(this, "Email is already in use", Toast.LENGTH_LONG).show();
            else if (invalidMessage.length() > 19) {
                Toast.makeText(this, invalidMessage, Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Successfully registered new account", Toast.LENGTH_LONG).show();
                Manager.getData().addUser(new User(name, email, password, birthday.getText().toString()));
                startActivity(new Intent(this, LoginActivity.class));
            }
        });

        back.setOnClickListener(click -> {
            startActivity(new Intent(this, StartActivity.class));
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
