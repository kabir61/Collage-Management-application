package com.example.usercollageapplication.user.ui.faculty;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.usercollageapplication.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class FacultyFragment extends Fragment {

    private RecyclerView tctDepartment,cstDepartment,dntDepartment;
    private LinearLayout tctNoData,cstNoData,dntNoData;
    private List<TeacherData> list1,list2,list3;
    private DatabaseReference reference,dbReference;
    private TeacherAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View view = inflater.inflate(R.layout.fragment_faculty, container, false);
        tctDepartment =view.findViewById(R.id.tctDepartment);
        cstDepartment =view.findViewById(R.id.cstDepartment);
        dntDepartment =view.findViewById(R.id.dntDepartment);
        tctNoData =view.findViewById(R.id.tctNoData);
        cstNoData =view.findViewById(R.id.cstNoData);
        dntNoData =view.findViewById(R.id.dntNoData);
        reference = FirebaseDatabase.getInstance().getReference().child("teacher");

        tctDepartment();
        cstDepartment();
        dntDepartment();

    return view;
    }

    private void tctDepartment() {
        dbReference = reference.child("Telecommunication Technology");
        dbReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list1 = new ArrayList<>();
                if (!snapshot.exists()){
                    tctNoData.setVisibility(View.VISIBLE);
                    tctDepartment.setVisibility(View.GONE);
                }else {
                    tctNoData.setVisibility(View.GONE);
                    tctDepartment.setVisibility(View.VISIBLE);

                    for (DataSnapshot snapShot: snapshot.getChildren()){
                        TeacherData data = snapShot.getValue(TeacherData.class);
                        list1.add(data);
                    }
                    tctDepartment.setHasFixedSize(true);
                    tctDepartment.setLayoutManager(new LinearLayoutManager(getContext()));
                    adapter = new TeacherAdapter(list1,getContext());
                    tctDepartment.setAdapter(adapter);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void cstDepartment() {
        dbReference = reference.child("Computer Science Technology");
        dbReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                list2 = new ArrayList<>();
                if (!datasnapshot.exists()){
                    cstNoData.setVisibility(View.VISIBLE);
                    cstDepartment.setVisibility(View.GONE);
                }else {
                    cstNoData.setVisibility(View.GONE);
                    cstDepartment.setVisibility(View.VISIBLE);
                    for (DataSnapshot snapshot: datasnapshot.getChildren()){
                        TeacherData data = snapshot.getValue(TeacherData.class);
                        list2.add(data);
                    }
                    cstDepartment.setHasFixedSize(true);
                    cstDepartment.setLayoutManager(new LinearLayoutManager(getContext()));
                    adapter = new TeacherAdapter(list2,getContext());
                    cstDepartment.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void dntDepartment() {
        dbReference = reference.child("Data Telecommunication Technology");
        dbReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                list3 = new ArrayList<>();
                if (!datasnapshot.exists()){
                    dntNoData.setVisibility(View.VISIBLE);
                    dntDepartment.setVisibility(View.GONE);
                }else {
                    dntNoData.setVisibility(View.GONE);
                    dntDepartment.setVisibility(View.VISIBLE);
                    for (DataSnapshot snapshot: datasnapshot.getChildren()){
                        TeacherData data = snapshot.getValue(TeacherData.class);
                        list3.add(data);
                    }
                    dntDepartment.setHasFixedSize(true);
                    dntDepartment.setLayoutManager(new LinearLayoutManager(getContext()));
                    adapter = new TeacherAdapter(list3,getContext());
                    dntDepartment.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
}
}