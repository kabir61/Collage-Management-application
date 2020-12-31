package com.example.usercollageapplication.admin.notice;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.usercollageapplication.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DeleteNotice extends AppCompatActivity {
    private RecyclerView deleteRecyclerView;
    private ProgressBar pb;
    private ArrayList<NoticeData> list;
    private NoticeAdapter adapter;

    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES) {
            setTheme(R.style.AppTheme_Dark);
        } else {
            setTheme(R.style.AppTheme_Light);
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_notice);

        deleteRecyclerView = findViewById(R.id.deleteRecyclerView);
        pb=findViewById(R.id.progressBar);

        reference = FirebaseDatabase.getInstance().getReference().child("notice");
        deleteRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        deleteRecyclerView.setHasFixedSize(true);

        getNotice();
    }

    private void getNotice() {
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                list = new ArrayList<>();
                for (DataSnapshot snapshot: datasnapshot.getChildren()){
                    NoticeData data = snapshot.getValue(NoticeData.class);
                    list.add(data);
                }
                adapter = new NoticeAdapter(list,DeleteNotice.this);
                adapter.notifyDataSetChanged();
                pb.setVisibility(View.GONE);
                deleteRecyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                pb.setVisibility(View.GONE);

                Toast.makeText(DeleteNotice.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}