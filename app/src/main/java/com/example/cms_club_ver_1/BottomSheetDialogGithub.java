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

public class BottomSheetDialogGithub extends BottomSheetDialogFragment
{

    public EditText ed_github_url;
    public AppCompatButton save_github_url;
    public AppCompatButton cancel_github_url;
    public static String member_Github;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.bottom_sheet_dialog_github, container, false);

        ed_github_url = v.findViewById(R.id.ed_github);
        save_github_url = v.findViewById(R.id.btn_save_github);
        cancel_github_url = v.findViewById(R.id.btn_cancel_github);

        save_github_url.setOnClickListener(view -> {
            member_Github = ed_github_url.getText().toString();
            EditActivity.txt_Github_URL.setText(member_Github);
            dismiss();
        });

        cancel_github_url.setOnClickListener(view -> dismiss());

        return v;
    }
}
