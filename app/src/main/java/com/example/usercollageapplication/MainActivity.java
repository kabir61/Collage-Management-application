package com.example.usercollageapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.usercollageapplication.admin.Admin;
import com.example.usercollageapplication.user.UserApplication;

public class MainActivity extends AppCompatActivity {

    private EditText mobileNumberET, passwordET;
    private Button loginBtn;
    private Spinner spinner;
    private long backPressed;
    private Toast backToast;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    private void init() {
        mobileNumberET = findViewById(R.id.mobileNumberET);
        passwordET = findViewById(R.id.passwordET);
        loginBtn = findViewById(R.id.loginBtn);
        spinner = findViewById(R.id.spinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.category, R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String item = spinner.getSelectedItem().toString();
                if (mobileNumberET.getText().toString().equals("Admin") && passwordET.getText().toString().equals("123456") && item.equals("Admin Panel")) {
                    startActivity(new Intent(MainActivity.this, Admin.class));

                } else if (mobileNumberET.getText().toString().equals("User") && passwordET.getText().toString().equals("123456789") && item.equals("User Panel")) {
                    startActivity(new Intent(MainActivity.this, UserApplication.class));
                } else {
                    Toast.makeText(MainActivity.this, "Error", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}