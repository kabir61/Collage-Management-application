package com.example.usercollageapplication.admin.faculty;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.usercollageapplication.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class TeacherAdapter extends RecyclerView.Adapter<TeacherAdapter.AdminTeacherAdapter>{
    public TeacherAdapter(List<TeacherData> list, Context context, String category) {
        this.list = list;
        this.context = context;
        this.category = category;
    }

    private List<TeacherData> list;
    private Context context;
    private String category;
    @NonNull
    @Override
    public AdminTeacherAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(context).inflate(R.layout.teacher_item_faculty,parent,false);

        return new AdminTeacherAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminTeacherAdapter holder, int position) {

        TeacherData teacherData=list.get(position);
        holder.adminNameTV.setText(teacherData.getName());
        holder.adminPostTV.setText(teacherData.getPost());
        holder.adminEmailTV.setText(teacherData.getEmail());
        holder.adminMobileTV.setText(teacherData.getMobile());

        try {
            Picasso.with(context).load(teacherData.getImage()).into(holder.adminUpdateImage);
        } catch (Exception e) {
            e.printStackTrace();
        }

        holder.adminUpdateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, UpdateTeacher.class);
                intent.putExtra("name",teacherData.getName());
                intent.putExtra("email",teacherData.getEmail());
                intent.putExtra("post",teacherData.getPost());
                intent.putExtra("mobile",teacherData.getMobile());
                intent.putExtra("image",teacherData.getImage());
                intent.putExtra("key",teacherData.getKey());
                intent.putExtra("category",category);
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class AdminTeacherAdapter extends RecyclerView.ViewHolder {
        private TextView adminNameTV,adminPostTV,adminMobileTV,adminEmailTV;
        private Button adminUpdateBtn;
        private  ImageView adminUpdateImage;
        public AdminTeacherAdapter(@NonNull View itemView) {
            super(itemView);
            adminNameTV=itemView.findViewById(R.id.adminNameTV);
            adminPostTV=itemView.findViewById(R.id.adminPostTV);
            adminMobileTV=itemView.findViewById(R.id.adminMobileTV);
            adminEmailTV=itemView.findViewById(R.id.adminEmailTV);
            adminUpdateBtn=itemView.findViewById(R.id.adminUpdateBtn);
            adminUpdateImage=itemView.findViewById(R.id.adminUpdateImage);

        }
    }
}