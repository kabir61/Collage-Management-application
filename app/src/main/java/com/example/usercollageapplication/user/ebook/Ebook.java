package com.example.usercollageapplication.user.ebook;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.usercollageapplication.R;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class Ebook extends AppCompatActivity {
    private RecyclerView ebookRecycler;
    private DatabaseReference reference;
    private List<ModelData> list;
    private EbookAdapter adapter;
    private ShimmerFrameLayout shimmerFrameLayout;
    private LinearLayout shimmerLinearLayout;
    private MaterialToolbar toolbarEbook;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ebook);
        init();

        setSupportActionBar(toolbarEbook);
        getSupportActionBar().setTitle("Ebook");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getData();
    }

    private void getData() {

        list = new ArrayList<>();
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                list.clear();
                for (DataSnapshot snapshot1: snapshot.getChildren()){
                 ModelData data1 = snapshot1.getValue(ModelData.class);
                    list.add(data1);

                }
                adapter = new EbookAdapter(list,Ebook.this);
                ebookRecycler.setLayoutManager(new LinearLayoutManager(Ebook.this));
                ebookRecycler.setAdapter(adapter);
                shimmerFrameLayout.stopShimmer();
                shimmerLinearLayout.setVisibility(View.GONE);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Ebook.this, error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onPause() {
        shimmerFrameLayout.stopShimmer();
        super.onPause();
    }

    @Override
    protected void onResume() {
        shimmerFrameLayout.startShimmer();
        super.onResume();
    }

    private void init() {
        toolbarEbook=findViewById(R.id.toolbarEbook);
        ebookRecycler=findViewById(R.id.ebookRecycler);
        reference= FirebaseDatabase.getInstance().getReference().child("pdf");
        shimmerFrameLayout=findViewById(R.id.shimmer_view_container);
        shimmerLinearLayout=findViewById(R.id.shimmerLinearLayout);

    }

}