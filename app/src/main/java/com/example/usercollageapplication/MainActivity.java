package com.example.usercollageapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.usercollageapplication.ebook.Ebook;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;

import java.util.Objects;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private BottomNavigationView buttonNavigation;
    private NavController navController;

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;

    private long backPressed;
    private Toast backToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Feni Computer Institute");
        buttonNavigation =findViewById(R.id.buttonNavigation);
        navController = Navigation.findNavController(this,R.id.fragment_layout);
        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navigationView);


        toggle  = new ActionBarDrawerToggle(this,drawerLayout,R.string.start,R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        navigationView.setNavigationItemSelectedListener(this);


        NavigationUI.setupWithNavController(buttonNavigation,navController);

        if (!isConnected(MainActivity.this)){
            builderDialog(MainActivity.this).show();
        }
    }

    public boolean isConnected(Context context){
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();
        if (info != null && info.isConnectedOrConnecting()){
            android.net.NetworkInfo wifi =  manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            android.net.NetworkInfo mobile =  manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            if ((mobile != null && mobile.isConnectedOrConnecting()) || (wifi != null && wifi.isConnectedOrConnecting()))
                return true;
            else  return false;
        }else {
            return false;
        }

    }

    public AlertDialog.Builder builderDialog(Context context){
        final AlertDialog.Builder builder= new AlertDialog.Builder(context);
        builder.setTitle("No Internet Connection");
        builder.setMessage("Dear user you are not connected to internet");
        builder.setCancelable(false);
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
               builder.setCancelable(true);
            }
        });
        return builder;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (toggle.onOptionsItemSelected(item)){
            return true;
        }
        return true;
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.navigation_video:
                Toast.makeText(this, "Click Video", Toast.LENGTH_LONG).show();
                break;
            case R.id.navigation_Ebook:
                startActivity(new Intent(MainActivity.this, Ebook.class));
                break;
            case R.id.navigation_theme:
                Toast.makeText(this, "Click Theme", Toast.LENGTH_LONG).show();
                break;
            case R.id.navigation_website:
                Toast.makeText(this, "Click Website", Toast.LENGTH_LONG).show();
                break;
            case R.id.navigation_share:
                try {
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setType("text/plain");
                    intent.putExtra(Intent.EXTRA_SUBJECT,"Feni Computer Institute");
                    intent.putExtra(Intent.EXTRA_TEXT,"https://play.google.com/store/apps/details?id="+getApplicationContext().getPackageName());
                    startActivity(Intent.createChooser(intent,"Share with"));
                } catch (Exception e) {
                    Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.navigation_rateUs:
                Uri uri = Uri.parse("https://play.google.com/store/apps/details?id="+getApplicationContext().getPackageName());
                Intent intent = new Intent(Intent.ACTION_VIEW,uri);
                try {
                    startActivity(intent);
                } catch (Exception e) {
                    Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.navigation_developer:
                Toast.makeText(this, "Click Developer", Toast.LENGTH_LONG).show();
                break;
        }
        return true;
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else if (backPressed+3000> System.currentTimeMillis()){
            super.onBackPressed();
            backToast.cancel();
            return;
        } else{
            CharSequence text;
            backToast = Toast.makeText(MainActivity.this,"Press back again to exit",Toast.LENGTH_LONG);
            backToast.show();
        }
        backPressed = System.currentTimeMillis();
    }
}