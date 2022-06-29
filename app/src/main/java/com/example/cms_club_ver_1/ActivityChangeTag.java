package com.example.cms_club_ver_1;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ActivityChangeTag extends AppCompatActivity
{
    public AppCompatButton btn_update_tag;
    public EditText ed_change_tag;
    public AppCompatButton btn_back;
    public DatabaseReference databaseReference;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_tag);



        databaseReference = FirebaseDatabase.getInstance().getReference().child("SKCLUB").child(fragment_more.club_id).child("information");

        ed_change_tag = findViewById(R.id.ed_new_club_tag);
        btn_update_tag = findViewById(R.id.btn_update_club_tag);
        btn_back = findViewById(R.id.btn_change_club_tag_back);

        btn_update_tag.setOnClickListener(view -> {

            String tag = ed_change_tag.getText().toString();

            if(TextUtils.isEmpty(tag))
            {
                ed_change_tag.setError("Valid tag is required !");
                return;
            }

            databaseReference.child("club_tag").setValue(tag).addOnSuccessListener(unused -> {
                Toast.makeText(ActivityChangeTag.this, "Tag updated !", Toast.LENGTH_SHORT).show();
                finish();
            }).addOnFailureListener(e -> Toast.makeText(ActivityChangeTag.this, "Something went wrong !", Toast.LENGTH_SHORT).show());

        });

        btn_back.setOnClickListener(view -> finish());
    }


    @Override
    public void finish()
    {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }
}
