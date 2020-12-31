package com.example.usercollageapplication.user.ui.faculty;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.usercollageapplication.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class TeacherAdapter extends RecyclerView.Adapter<TeacherAdapter.TeacherViewAdapter> {

    private List<TeacherData> list;
    private Context context;

    public TeacherAdapter(List<TeacherData> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public TeacherViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.faculty_item_layout,parent,false);

        return new TeacherViewAdapter (view);
    }

    @Override
    public void onBindViewHolder(@NonNull TeacherViewAdapter holder, int position) {
        TeacherData teacherData = list.get(position);
        holder.nameTV.setText(teacherData.getName());
        holder.emailTV.setText(teacherData.getEmail());
        holder.postTV.setText(teacherData.getPost());
        holder.mobileTV.setText(teacherData.getMobile());
        try {
            Picasso.with(context).load(teacherData.getImage()).into(holder.updateImage);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class TeacherViewAdapter extends RecyclerView.ViewHolder {

        private TextView nameTV,emailTV,postTV,mobileTV;
        private ImageView updateImage;

        public TeacherViewAdapter(@NonNull View itemView) {
            super(itemView);
           nameTV = itemView.findViewById(R.id.nameTV);
           emailTV = itemView.findViewById(R.id.emailTV);
           postTV = itemView.findViewById(R.id.postTV);
           mobileTV = itemView.findViewById(R.id.mobileTV);
           updateImage = itemView.findViewById(R.id.updateImage);
        }
    }
}
