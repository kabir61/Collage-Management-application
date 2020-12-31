package com.example.usercollageapplication.user.zoomImage;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.usercollageapplication.R;
import com.github.chrisbanes.photoview.PhotoView;

public class ZoomImage extends AppCompatActivity {
    private PhotoView zoomImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zoom_image);

        zoomImageView=findViewById(R.id.zoomImageView);

        String image=getIntent().getStringExtra("zoomImage");

        Glide.with(this).load(image).into(zoomImageView);


    }
}