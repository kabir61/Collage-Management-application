package com.example.usercollageapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.usercollageapplication.admin.Admin;
import com.example.usercollageapplication.user.UserApplication;
import com.example.usercollageapplication.user.profile.SignInActivity;

public class StartActivity extends AppCompatActivity {
    private CardView adminPanel,userPanel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);

        adminPanel=findViewById(R.id.adminPanel);
        userPanel=findViewById(R.id.userPanel);

        adminPanel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StartActivity.this,Admin.class));
            }
        });
        userPanel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StartActivity.this, UserApplication.class));
            }
        });
    }
}