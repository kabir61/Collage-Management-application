package com.example.usercollageapplication.user.ui.website;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.usercollageapplication.R;

public class Website extends AppCompatActivity {
    private WebView website;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_website);
        website=findViewById(R.id.website);
        website.setWebViewClient(new WebViewClient());
        website.loadUrl("https://www.fci.gov.bd/");

        WebSettings webSettings=website.getSettings();
        webSettings.setJavaScriptEnabled(true);
    }

    @Override
    public void onBackPressed() {
        if (website.canGoBack()){
            website.goBack();
        }else {
            super.onBackPressed();
        }
    }
}