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

public class BottomSheetDialogLinkedIn extends BottomSheetDialogFragment
{
    public EditText ed_linkedIn;
    public AppCompatButton save;
    public AppCompatButton cancel;
    public int check;
    public static String member_LinkedIn;
    public ClubMemberPOJO current_member_data;

    public BottomSheetDialogLinkedIn(int check)
    {
        this.check = check;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.bottom_sheet_dialog_linkedin, container, false);
        ed_linkedIn = v.findViewById(R.id.ed_linkedIn);
        save = v.findViewById(R.id.btn_save_linkedIn);
        cancel = v.findViewById(R.id.btn_cancel_linkedIn);

        Intent intent = getActivity().getIntent();
        current_member_data = (ClubMemberPOJO) intent.getSerializableExtra("current_member_data");
        save.setOnClickListener(view -> {
            member_LinkedIn = ed_linkedIn.getText().toString();
            switch (check)
            {
                case 1 :
                    pushToFirebase("mentor",member_LinkedIn);
                    break;
                case 2 :
                    pushToFirebase("main",member_LinkedIn);
                    break;
                case 3 :
                    pushToFirebase("assistant",member_LinkedIn);
                    break;
            }
            dismiss();
        });

        cancel.setOnClickListener(view -> dismiss());

        return v;
    }

    void pushToFirebase(String member_type,String data)
    {
        if (TextUtils.isEmpty(data) || (!URLUtil.isValidUrl(data)))
        {
            ed_linkedIn.setError("Empty field not allowed");
            return;
        }

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("SKCLUB").child(EditActivity.club_id).child("members").child(member_type).child(current_member_data.getCms_id());

        databaseReference.child("linkedin_account").setValue(data).addOnSuccessListener(unused -> {
            EditActivity.txt_LinkedIn_URL.setText(data);
            Toast.makeText(EditActivity.EditActivityContext, "Linkedin url Updated Successfully!", Toast.LENGTH_SHORT).show();
        }).addOnFailureListener(e -> Toast.makeText(EditActivity.EditActivityContext, "Something went wrong!!", Toast.LENGTH_SHORT).show());

    }


}
