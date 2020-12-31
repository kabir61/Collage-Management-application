package com.example.usercollageapplication.user.developer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.example.usercollageapplication.R;
import com.google.android.material.appbar.MaterialToolbar;

public class Developer extends AppCompatActivity {
    private MaterialToolbar toolbarDeveloper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_developer);
        toolbarDeveloper=findViewById(R.id.toolbarDeveloper);

        setSupportActionBar(toolbarDeveloper);
        getSupportActionBar().setTitle("Developer");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}