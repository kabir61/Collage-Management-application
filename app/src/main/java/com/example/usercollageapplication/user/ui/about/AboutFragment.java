package com.example.usercollageapplication.user.ui.about;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.usercollageapplication.R;

import java.util.ArrayList;
import java.util.List;


public class AboutFragment extends Fragment {
    private ImageView mapImage;
    private ViewPager viewPager;
    private BranceAdapter branceAdapter;
    private List<BranceModel> list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
       View view= inflater.inflate(R.layout.fragment_about, container, false);
        mapImage=view.findViewById(R.id.mapImage);


        mapImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMap();
            }
        });


       list = new ArrayList<>();
       list.add(new BranceModel(R.drawable.ic_tct,"Telecommunication Technology","Bit Bash was established to further the culture of independent game development in Chicago by hosting larger, public-facing events. Our goal is to promote games that either can't be played at home"));
       list.add(new BranceModel(R.drawable.ic_cst,"Computer Science Technology","Bit Bash was established to further the culture of independent game development in Chicago by hosting larger, public-facing events. Our goal is to promote games that either can't be played at home"));
       list.add(new BranceModel(R.drawable.ic_dnt,"Data Telecommunication and Networking Technology","Bit Bash was established to further the culture of independent game development in Chicago by hosting larger, public-facing events. Our goal is to promote games that either can't be played at home"));

       branceAdapter=new BranceAdapter(getContext(),list);

       viewPager=view.findViewById(R.id.viewPager);
       viewPager.setAdapter(branceAdapter);

       return view;
    }

    private void openMap() {
            Uri uri = Uri.parse("geo:0, 0?q=Feni Computer Institute");
            Intent intent = new Intent(Intent.ACTION_VIEW,uri);
            intent.setPackage("com.google.android.apps.maps");
            startActivity(intent);

    }
}