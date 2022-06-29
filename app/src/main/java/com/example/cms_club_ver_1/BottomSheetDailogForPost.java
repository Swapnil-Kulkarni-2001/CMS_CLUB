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

public class BottomSheetDailogForPost extends BottomSheetDialogFragment
{
    public EditText ed_name;
    public AppCompatButton save;
    public AppCompatButton cancel;
    public int check;
    public ClubMemberPOJO current_member_data;
    public static String member_post;

    public BottomSheetDailogForPost(int check)
    {
        this.check = check;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.bottom_sheet_dailog_for_poat, container, false);
        ed_name = v.findViewById(R.id.ed_post);
        save = v.findViewById(R.id.btn_save);
        cancel = v.findViewById(R.id.btn_cancel);

        Intent intent = getActivity().getIntent();
        current_member_data = (ClubMemberPOJO) intent.getSerializableExtra("current_member_data");
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                member_post = ed_name.getText().toString();
                switch (check)
                {
                    case 1 :
                        pushToFirebase("mentor",member_post);
                        break;
                    case 2 :
                        pushToFirebase("main",member_post);
                        break;
                    case 3 :
                        pushToFirebase("assistant",member_post);
                        break;
                }

                dismiss();
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(),"Canceled",Toast.LENGTH_SHORT).show();
                dismiss();
            }
        });

        return v;
    }



    void pushToFirebase(String member_type,String data)
    {
        if (TextUtils.isEmpty(data))
        {
            ed_name.setError("Empty field not allowed");
            return;
        }

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("SKCLUB").child(EditActivity.club_id).child("members").child(member_type).child(current_member_data.getCms_id());

        databaseReference.child("post").setValue(data).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                EditActivity.txt_post.setText(data);
                Toast.makeText(EditActivity.EditActivityContext, "Post Updated Successfully!", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(EditActivity.EditActivityContext, "Something went wrong!!", Toast.LENGTH_SHORT).show();
            }
        });

    }

}
