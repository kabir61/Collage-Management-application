package com.example.usercollageapplication.admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.usercollageapplication.R;
import com.example.usercollageapplication.admin.faculty.UpdateFaculty;
import com.example.usercollageapplication.admin.notice.DeleteNotice;
import com.example.usercollageapplication.admin.notice.UploadNotice;
import com.example.usercollageapplication.admin.uploadImage.UploadImage;
import com.example.usercollageapplication.admin.uploadPdf.UploadPDF;

public class Admin extends AppCompatActivity implements View.OnClickListener {
    private CardView addNotice, AddGallery, AddEbook,AddFaculty,DeleteNotice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        assign();

        addNotice.setOnClickListener(this);
        AddGallery.setOnClickListener(this);
        AddEbook.setOnClickListener(this);
        AddFaculty.setOnClickListener(this);
        DeleteNotice.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;
        switch (v.getId()){
            case (R.id.AddNotice):
                intent= new Intent (Admin.this, UploadNotice.class);
                startActivity(intent);
                break;
            case (R.id.AddGallery):
                intent= new Intent (Admin.this, UploadImage.class);
                startActivity(intent);
                break;
            case (R.id.AddEbook):
                intent= new Intent (Admin.this, UploadPDF.class);
                startActivity(intent);
                break;
            case (R.id.AddFaculty):
                intent= new Intent (Admin.this, UpdateFaculty.class);
                startActivity(intent);
                break;
            case (R.id.DeleteNotice):
                intent= new Intent (Admin.this,DeleteNotice.class);
                startActivity(intent);
                break;
        }
    }



    private void assign() {
        addNotice = findViewById(R.id.AddNotice);
        AddGallery = findViewById(R.id.AddGallery);
        AddEbook= findViewById(R.id.AddEbook);
        AddFaculty= findViewById(R.id.AddFaculty);
        DeleteNotice=findViewById(R.id.DeleteNotice);

    }
}