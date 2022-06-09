package com.example.cms_club_ver_1;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;

import java.util.ArrayList;

public class fragment_home extends Fragment {
    public ArrayList<SlideModel> arrayList;
    public ImageSlider imageSlider;
    public ImageSlider imageSlider2;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_home, container, false);

        imageSlider = view.findViewById(R.id.image_slider);

        imageSlider2 = view.findViewById(R.id.image_slider2);

        arrayList = new ArrayList<>();

        arrayList.add(new SlideModel(R.drawable.img_example,"My Photo",null));
        arrayList.add(new SlideModel(R.drawable.img_1,null));
        arrayList.add(new SlideModel(R.drawable.img_2,null));
        arrayList.add(new SlideModel(R.drawable.img_3,null));
        arrayList.add(new SlideModel(R.drawable.img_4,null));
        imageSlider.setImageList(arrayList, ScaleTypes.FIT);
        imageSlider2.setImageList(arrayList,ScaleTypes.FIT);
        return view;
    }
}