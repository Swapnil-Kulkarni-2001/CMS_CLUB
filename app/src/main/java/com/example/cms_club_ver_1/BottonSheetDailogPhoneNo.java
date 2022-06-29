package com.example.cms_club_ver_1;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class BottonSheetDailogPhoneNo extends BottomSheetDialogFragment {

    public EditText ed_phone;
    public AppCompatButton save_phone;
    public AppCompatButton cancel;

    public static String member_phone_no;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.bottom_sheet_dialog_phoneno, container, false);
        ed_phone = v.findViewById(R.id.ed_phone_no);
        save_phone = v.findViewById(R.id.btn_save_phone);
        cancel = v.findViewById(R.id.btn_cancel_phone);

        save_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                member_phone_no = ed_phone.getText().toString();
                EditActivity.txt_phone_no.setText(member_phone_no);
                dismiss();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });

        return v;
    }
}
