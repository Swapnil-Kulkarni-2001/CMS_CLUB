package com.example.cms_club_ver_1;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

public class fragment_more extends Fragment {



    public AppCompatButton btn_change_logo;
    public AppCompatButton btn_change_name;
    public AppCompatButton btn_change_tag;
    public AppCompatButton btn_change_club_description;
    public AppCompatButton btn_change_password;
    public AppCompatButton btn_about;
    public static String club_id;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_more, container, false);

        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getActivity().getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(getResources().getColor(R.color.light_orange));
            changeStatusBarContrastStyle(window,false);
        }

        Intent intent = getActivity().getIntent();
        club_id = intent.getStringExtra("club_id");


        btn_change_name = view.findViewById(R.id.btn_change_name);
        btn_change_tag = view.findViewById(R.id.btn_change_tag);
        btn_change_logo = view.findViewById(R.id.btn_change_logo);
        btn_change_club_description = view.findViewById(R.id.btn_change_club_description);
        btn_change_password = view.findViewById(R.id.btn_change_password);
        btn_about = view.findViewById(R.id.btn_about);


        btn_change_name.setOnClickListener(view1 -> {
            startActivity(new Intent(getContext(),ActivityChangeName.class));
            getActivity().overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
        });


        btn_change_tag.setOnClickListener(view16 -> {
            startActivity(new Intent(getContext(),ActivityChangeTag.class));
            getActivity().overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
        });

        btn_change_club_description.setOnClickListener(view12 -> {
            startActivity(new Intent(getContext(),ActivityChangeDescription.class));
            getActivity().overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
        });


        btn_change_password.setOnClickListener(view13 -> {
            startActivity(new Intent(getContext(),ActivityChangePassword.class));
            getActivity().overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
        });


        btn_change_logo.setOnClickListener(view14 -> {
            startActivity(new Intent(getContext(),ActivityChangeLogo.class));
            getActivity().overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
        });


        btn_about.setOnClickListener(view15 -> {

        });

        return view;
    }


    @SuppressLint("InlinedApi")
    public static void changeStatusBarContrastStyle(Window window, Boolean lightIcons) {
        View decorView = window.getDecorView();
        if (lightIcons) {
            // Draw light icons on a dark background color
            decorView.setSystemUiVisibility(decorView.getSystemUiVisibility() & ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        } else {
            // Draw dark icons on a light background color
            decorView.setSystemUiVisibility(decorView.getSystemUiVisibility() | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
    }

}