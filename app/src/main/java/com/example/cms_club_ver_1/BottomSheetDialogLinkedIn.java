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

public class BottomSheetDialogLinkedIn extends BottomSheetDialogFragment
{
    public EditText ed_linkedIn;
    public AppCompatButton save;
    public AppCompatButton cancel;
    public static String member_LinkedIn = "Empty";


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.bottom_sheet_dialog_linkedin, container, false);
        ed_linkedIn = v.findViewById(R.id.ed_linkedIn);
        save = v.findViewById(R.id.btn_save_linkedIn);
        cancel = v.findViewById(R.id.btn_cancel_linkedIn);


        save.setOnClickListener(view -> {
            member_LinkedIn = ed_linkedIn.getText().toString();
            EditActivity.txt_LinkedIn_URL.setText(member_LinkedIn);
            dismiss();
        });

        cancel.setOnClickListener(view -> dismiss());

        return v;
    }
}
