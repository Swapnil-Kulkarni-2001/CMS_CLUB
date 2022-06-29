package com.example.cms_club_ver_1;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.URLUtil;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class BottomSheetDialogInstagram extends BottomSheetDialogFragment
{

    public EditText ed_instagram;
    public AppCompatButton save_instagram_url;
    public AppCompatButton cancel_instagram_url;
    public int check;
    public static String member_Instagram;
    public ClubMemberPOJO current_member_data;

    public BottomSheetDialogInstagram(int check)
    {
        this.check = check;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.bottom_sheet_dialog_instagram, container, false);

        ed_instagram = v.findViewById(R.id.ed_instagram);
        save_instagram_url = v.findViewById(R.id.btn_save_instagram);
        cancel_instagram_url = v.findViewById(R.id.btn_cancel_instagram);



        Intent intent = getActivity().getIntent();
        current_member_data = (ClubMemberPOJO) intent.getSerializableExtra("current_member_data");
        save_instagram_url.setOnClickListener(view -> {
            member_Instagram = ed_instagram.getText().toString();
            switch (check)
            {
                case 1 :
                    pushToFirebase("mentor",member_Instagram);
                    break;
                case 2 :
                    pushToFirebase("main",member_Instagram);
                    break;
                case 3 :
                    pushToFirebase("assistant",member_Instagram);
                    break;
            }
            dismiss();
        });


        cancel_instagram_url.setOnClickListener(view -> dismiss());

        return v;
    }

    void pushToFirebase(String member_type,String data)
    {
        if (TextUtils.isEmpty(data) || (!URLUtil.isValidUrl(data)))
        {
            ed_instagram.setError("Empty field not allowed");
            return;
        }

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("SKCLUB").child(EditActivity.club_id).child("members").child(member_type).child(current_member_data.getCms_id());

        databaseReference.child("instagram_account").setValue(data).addOnSuccessListener(unused -> {
            EditActivity.txt_Instagram_URL.setText(data);
            Toast.makeText(EditActivity.EditActivityContext, "Instagram url Updated Successfully!", Toast.LENGTH_SHORT).show();
        }).addOnFailureListener(e -> Toast.makeText(EditActivity.EditActivityContext, "Something went wrong!!", Toast.LENGTH_SHORT).show());
    }

}
