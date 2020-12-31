package com.example.usercollageapplication.admin.faculty;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.usercollageapplication.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UpdateFaculty extends AppCompatActivity {

    FloatingActionButton fab;
    private RecyclerView tctDepartment,cstDepartment,dntDepartment;
    private LinearLayout tctNoData,cstNoData,dntNoData;
    private List<TeacherData> list1,list2,list3;
    private DatabaseReference reference,dbReference;
    private TeacherAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_faculty);

        initialize();
        reference = FirebaseDatabase.getInstance().getReference().child("teacher");


        tctDepartment();
        cstDepartment();
        dntDepartment();


        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UpdateFaculty.this,AddTeacher.class));
            }
        });

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
                    tctDepartment.setLayoutManager(new LinearLayoutManager(UpdateFaculty.this));
                    adapter = new TeacherAdapter(list1,UpdateFaculty.this,"Telecommunication Technology");
                    tctDepartment.setAdapter(adapter);
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UpdateFaculty.this, error.getMessage(), Toast.LENGTH_SHORT).show();
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
                    cstDepartment.setLayoutManager(new LinearLayoutManager(UpdateFaculty.this));
                    adapter = new TeacherAdapter(list2,UpdateFaculty.this,"Computer Science Technology");
                    cstDepartment.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
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
                    dntDepartment.setLayoutManager(new LinearLayoutManager(UpdateFaculty.this));
                    adapter = new TeacherAdapter(list3,UpdateFaculty.this,"Data Telecommunication Technology");
                    dntDepartment.setAdapter(adapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void initialize() {
        fab = findViewById(R.id.fab);
        tctDepartment =  findViewById(R.id.tctDepartment);
        cstDepartment =  findViewById(R.id.cstDepartment);
        dntDepartment =  findViewById(R.id.dntDepartment);

        tctNoData =  findViewById(R.id.tctNoData);
        cstNoData =  findViewById(R.id.cstNoData);
        dntNoData =  findViewById(R.id.dntNoData);
        reference = FirebaseDatabase.getInstance().getReference().child("teacher");





    }
}