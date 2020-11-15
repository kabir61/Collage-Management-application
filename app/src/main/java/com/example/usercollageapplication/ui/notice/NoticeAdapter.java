package com.example.usercollageapplication.ui.notice;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.usercollageapplication.R;
import com.example.usercollageapplication.zoomImage.ZoomImage;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;

public class NoticeAdapter extends RecyclerView.Adapter<NoticeAdapter.NoticeViewAdapter> {


    private ArrayList<NoticeData> list;
    private Context context;
    private int lastPosition = -1;

    public NoticeAdapter(ArrayList<NoticeData> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public NoticeViewAdapter onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.newsfeed_item, parent, false);
        return new NoticeViewAdapter(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoticeViewAdapter holder, int position) {

        NoticeData currentItem = list.get(position);
        holder.noticeTitle.setText(currentItem.getTitle());
        holder.date.setText(currentItem.getDate());
        holder.time.setText(currentItem.getTime());

        try {
            if (currentItem.getImage() != null)
                Picasso.with(context).load(currentItem.getImage()).into(holder.noticeImage);
        } catch (Exception e) {
            e.printStackTrace();
        }
        holder.noticeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ZoomImage.class);
                intent.putExtra("zoomImage",currentItem.getImage());
                context.startActivity(intent);
            }
        });
        setAnimation(holder.noticeImage,position);
    }
    public void setAnimation(View viewToAnimate, int position){
            if (position> lastPosition){
                ScaleAnimation animation=new ScaleAnimation(0.0f,1.0f,0.0f,1.0f,
                        Animation.RELATIVE_TO_SELF,0.5f,
                        Animation.RELATIVE_TO_SELF,0.5f);
                animation.setDuration(1500);
                viewToAnimate.startAnimation(animation);
                lastPosition = position;
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class NoticeViewAdapter extends RecyclerView.ViewHolder {
        private TextView noticeTitle, date, time;
        private ImageView noticeImage;

        public NoticeViewAdapter(@NonNull View itemView) {
            super(itemView);

            noticeTitle = itemView.findViewById(R.id.noticeTitle);
            noticeImage = itemView.findViewById(R.id.noticeImage);
            date = itemView.findViewById(R.id.date);
            time = itemView.findViewById(R.id.time);
        }
    }
}
