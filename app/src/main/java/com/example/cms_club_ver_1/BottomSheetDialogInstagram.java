package com.example.cms_club_ver_1;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class BottomSheetDialogInstagram extends BottomSheetDialogFragment
{

    public EditText ed_instagram;
    public AppCompatButton save_instagram_url;
    public AppCompatButton cancel_instagram_url;


    public static String member_Instagram = "Empty";


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.bottom_sheet_dialog_instagram, container, false);

        ed_instagram = v.findViewById(R.id.ed_instagram);
        save_instagram_url = v.findViewById(R.id.btn_save_instagram);
        cancel_instagram_url = v.findViewById(R.id.btn_cancel_instagram);


        save_instagram_url.setOnClickListener(view -> {
            member_Instagram = ed_instagram.getText().toString();
            EditActivity.txt_Instagram_URL.setText(member_Instagram);
            dismiss();
        });


        cancel_instagram_url.setOnClickListener(view -> dismiss());

        return v;
    }
}
