package com.example.usercollageapplication.user.ui.notice;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.usercollageapplication.R;
import com.example.usercollageapplication.user.ui.notice.NoticeAdapter;
import com.example.usercollageapplication.user.ui.notice.NoticeData;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class NoticeFragment extends Fragment {

    private RecyclerView newsFeedRecyclerView;
    private ProgressBar pb;
    private ArrayList<NoticeData> list;
    private NoticeAdapter adapter;

    private DatabaseReference reference;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

       View view = inflater.inflate(R.layout.fragment_notice, container, false);


        newsFeedRecyclerView = view.findViewById(R.id.newsFeedRecyclerView);
        pb=view.findViewById(R.id.progressBar);

        reference = FirebaseDatabase.getInstance().getReference().child("notice");
        newsFeedRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        newsFeedRecyclerView.setHasFixedSize(true);

        getNotice();

        return view;
    }

    private void getNotice() {
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                list = new ArrayList<>();
                for (DataSnapshot snapshot : datasnapshot.getChildren()) {
                    NoticeData data = snapshot.getValue(NoticeData.class);
                    list.add(0,data);
                }
                adapter = new NoticeAdapter(list, getContext());
                adapter.notifyDataSetChanged();
                pb.setVisibility(View.GONE);
                newsFeedRecyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                pb.setVisibility(View.GONE);
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });



    }
}

