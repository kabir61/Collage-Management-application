package com.example.usercollageapplication.ui.about;

import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.usercollageapplication.R;

import java.util.List;

public class BranceAdapter extends PagerAdapter {
    private Context context;
    private List<BranceModel> list;

    public BranceAdapter(Context context, List<BranceModel> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view.equals(object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        View view = LayoutInflater.from(context).inflate(R.layout.brance_item_layout,container,false);

        ImageView branceImage;
        TextView branceTitle,branceDescription;
        branceImage=view.findViewById(R.id.branceImage);
        branceTitle=view.findViewById(R.id.branceTitle);
        branceDescription=view.findViewById(R.id.branceDescription);

        branceImage.setImageResource(list.get(position).getImg());

        branceTitle.setText(list.get(position).getTitle());
        branceDescription.setText(list.get(position).getDescribe());

        container.addView(view,0);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View)object);
    }
}
