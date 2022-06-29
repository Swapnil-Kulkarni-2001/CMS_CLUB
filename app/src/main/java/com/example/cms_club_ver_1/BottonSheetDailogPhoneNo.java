package com.example.cms_club_ver_1;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class BottonSheetDailogPhoneNo extends BottomSheetDialogFragment {

    public EditText ed_phone;
    public AppCompatButton save_phone;
    public AppCompatButton cancel;
    public int check;
    public static String member_phone_no;
    public ClubMemberPOJO current_member_data;


    public BottonSheetDailogPhoneNo (int check)
    {
        this.check = check;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.bottom_sheet_dialog_phoneno, container, false);
        ed_phone = v.findViewById(R.id.ed_phone_no);
        save_phone = v.findViewById(R.id.btn_save_phone);
        cancel = v.findViewById(R.id.btn_cancel_phone);

        Intent intent = getActivity().getIntent();
        current_member_data = (ClubMemberPOJO) intent.getSerializableExtra("current_member_data");
        save_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                member_phone_no = ed_phone.getText().toString();
                switch (check)
                {
                    case 1 :
                        pushToFirebase("mentor",member_phone_no);
                        break;
                    case 2 :
                        pushToFirebase("main",member_phone_no);
                        break;
                    case 3 :
                        pushToFirebase("assistant",member_phone_no);
                        break;
                }
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

    void pushToFirebase(String member_type,String data)
    {
        if (TextUtils.isEmpty(data))
        {
            ed_phone.setError("Empty field not allowed");
            return;
        }

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("SKCLUB").child(EditActivity.club_id).child("members").child(member_type).child(current_member_data.getCms_id());

        databaseReference.child("phone_no").setValue(data).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                EditActivity.txt_phone_no.setText(data);
                Toast.makeText(EditActivity.EditActivityContext, "Phone No Updated Successfully!", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(EditActivity.EditActivityContext, "Something went wrong!!", Toast.LENGTH_SHORT).show();
            }
        });

    }

}
