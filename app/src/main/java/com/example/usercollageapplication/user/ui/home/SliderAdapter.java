package com.example.usercollageapplication.user.ui.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.usercollageapplication.R;
import com.example.usercollageapplication.databinding.FragmentHomeBinding;
import com.smarteist.autoimageslider.SliderViewAdapter;

public class SliderAdapter extends SliderViewAdapter<SliderAdapter.SliderAdapterVH> {

    // initialize DataBinding
    private FragmentHomeBinding binding;
        //Initialize variable
        int [] images;

    public SliderAdapter(FragmentHomeBinding binding, int[] images) {
        this.binding = binding;
        this.images = images;
    }

    @Override
public SliderAdapterVH onCreateViewHolder(ViewGroup parent) {
        // initialize view
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.slider_item_view, parent,false);
        // return view
        return new SliderAdapterVH(view);
        }

@Override
public void onBindViewHolder(SliderAdapterVH viewHolder, final int position) {
        //set image on image view
        viewHolder.sliderIV.setImageResource(images[position]);
        }

@Override
public int getCount() {
        // return image length
        return images.length;
        }

class SliderAdapterVH extends SliderViewAdapter.ViewHolder {
    // initialize variable
    ImageView sliderIV;

    public SliderAdapterVH(View itemView) {
        super(itemView);
        // assign variable
        sliderIV = itemView.findViewById(R.id.sliderIV);

    }
}

}
