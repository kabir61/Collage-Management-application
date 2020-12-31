package com.example.usercollageapplication.user.ui.home;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.example.usercollageapplication.R;
import com.example.usercollageapplication.databinding.FragmentHomeBinding;
import com.example.usercollageapplication.user.developer.Developer;
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType;
import com.smarteist.autoimageslider.SliderAnimations;

public class HomeFragment extends Fragment{
    private FragmentHomeBinding binding;
    private SliderAdapter adapter;

    int [] images = {R.drawable.slider_one,R.drawable.fci,
            R.drawable.fci_header,R.drawable.slider_four,R.drawable.slider_five};



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding=FragmentHomeBinding.inflate(getLayoutInflater());

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //initialize adapter
        adapter = new SliderAdapter(binding,images);
        //set adapter
        binding.imageSlider.setSliderAdapter(adapter);
        //set indicator animation
        binding.imageSlider.setIndicatorAnimation(IndicatorAnimationType.WORM);
        //set transformation animation
        binding.imageSlider.setSliderTransformAnimation(SliderAnimations.DEPTHTRANSFORMATION);
        //set auto cycle
        binding.imageSlider.startAutoCycle();


    }


}