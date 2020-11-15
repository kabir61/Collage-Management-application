package com.example.usercollageapplication.ebook;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.usercollageapplication.R;

import java.net.URI;
import java.util.List;

public class EbookAdapter extends RecyclerView.Adapter<EbookAdapter.EbookViewHolder> {

    private List<ModelData> list;
    private Context context;

    public EbookAdapter(List<ModelData> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public EbookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.ebook_item,parent,false);
        return new EbookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EbookViewHolder holder, int position) {


        holder.pdfTitleTV.setText(list.get(position).getName());
        holder.pdfTitleTV.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,PdfViewer.class);
                intent.putExtra("pdfUrI",list.get(position).getPdfUrI());
                context.startActivity(intent);
                 }
        });
        holder.pdfDown.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(list.get(position).getPdfUrI()));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class EbookViewHolder extends RecyclerView.ViewHolder {
        private ImageView pdfDown;
        private TextView pdfTitleTV;
        public EbookViewHolder(@NonNull View itemView) {
            super(itemView);

            pdfDown=itemView.findViewById(R.id.pdfDownload);
            pdfTitleTV=itemView.findViewById(R.id.pdfTitle);
        }
    }
}
