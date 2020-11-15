package com.example.usercollageapplication.ui.gallery;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.usercollageapplication.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class GalleryFragment extends Fragment {
    private RecyclerView convocationRecyclerView,independenceRecyclerView,othersRecyclerView;
    private GalleryAdapter galleryAdapter;

    private DatabaseReference reference,dbReference;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
       View view=inflater.inflate(R.layout.fragment_gallery, container, false);

       convocationRecyclerView=view.findViewById(R.id.convocationRecyclerView);
       independenceRecyclerView=view.findViewById(R.id.independenceRecyclerView);
       othersRecyclerView=view.findViewById(R.id.othersRecyclerView);

       reference= FirebaseDatabase.getInstance().getReference().child("gallery");

       getConvoRecycler();
       getIndependeceRecycler();

       getOtherRecycler();


       return view;
    }

    private void getIndependeceRecycler() {
        dbReference=reference.child("Independence Day");
        dbReference.addValueEventListener(new ValueEventListener() {

            final List<String> imageList = new ArrayList<>();
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    String data = dataSnapshot.getValue().toString();
                    imageList.add(data);

                }
                galleryAdapter= new GalleryAdapter(getContext(),imageList);
                independenceRecyclerView.setLayoutManager(new GridLayoutManager(getContext(),3));
                independenceRecyclerView.setAdapter(galleryAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getOtherRecycler() {
        reference.child("Others Events").addValueEventListener(new ValueEventListener() {

            final List<String> imageList = new ArrayList<>();
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    String data = dataSnapshot.getValue().toString();
                    imageList.add(data);

                }

                galleryAdapter= new GalleryAdapter(getContext(),imageList);
                othersRecyclerView.setLayoutManager(new GridLayoutManager(getContext(),3));
                othersRecyclerView.setAdapter(galleryAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    
    }

    private void getConvoRecycler() {
        dbReference=reference.child("Convocation");
        dbReference.addValueEventListener(new ValueEventListener() {

            final List<String> imageList = new ArrayList<>();
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    String data = dataSnapshot.getValue().toString();
                    imageList.add(data);

                }

                galleryAdapter= new GalleryAdapter(getContext(),imageList);
                convocationRecyclerView.setLayoutManager(new GridLayoutManager(getContext(),3));
                convocationRecyclerView.setAdapter(galleryAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}