package com.example.usercollageapplication.user;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.usercollageapplication.R;
import com.example.usercollageapplication.user.developer.Developer;
import com.example.usercollageapplication.user.ebook.Ebook;
import com.example.usercollageapplication.user.model.Users;
import com.example.usercollageapplication.user.profile.UpdateProfile;
import com.example.usercollageapplication.user.ui.home.HomeFragment;
import com.example.usercollageapplication.user.ui.website.Website;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Objects;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserApplication extends AppCompatActivity {
    // variable Initialise
    private BottomNavigationView buttonNavigation;
    private NavController navController;
    private LinearLayout share, rateUs;
    private CircleImageView updateProfile;
    private TextView profileName,profileMail;
    private CardView profileUpdate;
    FirebaseUser firebaseUser;
    DatabaseReference reference;

    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;

    private MaterialToolbar toolbar;
    private SwitchMaterial mySwitch;

    private long backPressed;
    private Toast backToast;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // initialize theme
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            setTheme(R.style.AppTheme_Dark);
        } else {
            setTheme(R.style.AppTheme_Light);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_application);
        //assign variable
        updateProfile=findViewById(R.id.updateImage);
        profileName=findViewById(R.id.profileName);
        profileMail=findViewById(R.id.profileMail);
        profileUpdate=findViewById(R.id.profileUpdate);
        share = findViewById(R.id.share);
        rateUs = findViewById(R.id.rateUs);
        toolbar = findViewById(R.id.toolbar);
        drawerLayout = findViewById(R.id.drawerLayout);
        mySwitch = findViewById(R.id.mySwitch);
        buttonNavigation = findViewById(R.id.buttonNavigation);

        //set toolbar
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Feni Computer Institute");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        reference = FirebaseDatabase.getInstance().getReference("User").child(firebaseUser.getUid());
       /* reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Users users=snapshot.getValue(Users.class);
                profileName.setText(users.getUsername());
                if (users.getImageURL().equals("default")){
                    updateProfile.setImageResource(R.mipmap.ic_launcher_round);
                }
                else {
                    Glide.with(getApplicationContext()).load(users.getImageURL()).into(updateProfile);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
       */

        //set navigation layout
        navController = Navigation.findNavController(this, R.id.fragment_layout);

        //Dark Mood enable
        mySwitch = findViewById(R.id.mySwitch);

        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            mySwitch.setChecked(true);
        }

        mySwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                }
            }
        });

        toggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.start, R.string.close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();


        profileUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserApplication.this, UpdateProfile.class));
            }
        });
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setType("text/plain");
                    intent.putExtra(Intent.EXTRA_SUBJECT, "Feni Computer Institute");
                    intent.putExtra(Intent.EXTRA_TEXT, "https://play.google.com/store/apps/details?id=" + getApplicationContext().getPackageName());
                    startActivity(Intent.createChooser(intent, "Share with"));
                } catch (Exception e) {
                    Toast.makeText(UserApplication.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });


        rateUs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://play.google.com/store/apps/details?id=" + getApplicationContext().getPackageName());
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                try {
                    startActivity(intent);
                } catch (Exception e) {
                    Toast.makeText(UserApplication.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

        NavigationUI.setupWithNavController(buttonNavigation, navController);

        if (!isConnected(UserApplication.this)) {
            builderDialog(UserApplication.this).show();
        }
    }


    // drawer activate
    public void Website(View view) {
        rederectActivity(this, Website.class);
    }

    public void develop(View view) {
        rederectActivity(this, Developer.class);
    }
    public void eBook(View view) {
        rederectActivity(this, Ebook.class);
    }

    private void rederectActivity(Activity activity, Class aClass) {
        Intent intent = new Intent(activity, aClass);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        activity.startActivity(intent);
    }


    public boolean isConnected(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context.getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo info = manager.getActiveNetworkInfo();
        if (info != null && info.isConnectedOrConnecting()) {
            android.net.NetworkInfo wifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            android.net.NetworkInfo mobile = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
            if ((mobile != null && mobile.isConnectedOrConnecting()) || (wifi != null && wifi.isConnectedOrConnecting()))
                return true;
            else return false;
        } else {
            return false;
        }

    }

    public AlertDialog.Builder builderDialog(Context context) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
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
        if (toggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else if (backPressed + 3000 > System.currentTimeMillis()) {
            super.onBackPressed();
            backToast.cancel();
            return;
        } else {
            CharSequence text;
            backToast = Toast.makeText(UserApplication.this, "Press back again to exit", Toast.LENGTH_LONG);
            backToast.show();
        }
        backPressed = System.currentTimeMillis();
    }
}