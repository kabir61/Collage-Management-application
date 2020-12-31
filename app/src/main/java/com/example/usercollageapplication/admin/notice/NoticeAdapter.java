package com.example.usercollageapplication.admin.notice;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.usercollageapplication.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class NoticeAdapter extends RecyclerView.Adapter<NoticeAdapter.AdminNoticeAdapter> {

    private ArrayList<NoticeData> list;
    private Context context;

    public NoticeAdapter(ArrayList<NoticeData> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public AdminNoticeAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(context).inflate(R.layout.delete_notice_faculty,parent,false);

        return new AdminNoticeAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AdminNoticeAdapter holder, int position) {
        NoticeData noticeData=list.get(position);
        holder.noticeTitle.setText(noticeData.getTitle());
        try {
            if (noticeData.getImage() != null)
                Picasso.with(context).load(noticeData.getImage()).into(holder.noticeImage);
        } catch (Exception e) {
            e.printStackTrace();
        }

        holder.noticeDeleteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Are you sure want to delete this notice");
                builder.setCancelable(true);
                builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("notice");
                        reference.child(noticeData.getKey()).removeValue()
                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        Toast.makeText(context, "Deleted", Toast.LENGTH_SHORT).show();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();

                            }
                        });
                        notifyItemRemoved(position);
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });
                AlertDialog dialog = null;
                try {
                    dialog = builder.create();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (dialog != null){
                    dialog.show();
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class AdminNoticeAdapter extends RecyclerView.ViewHolder {

        private Button noticeDeleteBtn;
        private TextView noticeTitle;
        private ImageView noticeImage;
        public AdminNoticeAdapter(@NonNull View itemView) {
            super(itemView);

            noticeDeleteBtn=itemView.findViewById(R.id.noticeDeleteBtn);
            noticeImage=itemView.findViewById(R.id.noticeImage);
            noticeTitle=itemView.findViewById(R.id.noticeTitle);

        }
    }
}