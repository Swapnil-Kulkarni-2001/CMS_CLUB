package com.example.cms_club_ver_1;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ActivityChangeDescription extends AppCompatActivity
{

    public AppCompatButton btn_update_club_description;
    public EditText ed_club_description;
    public AppCompatButton btn_back;
    public DatabaseReference databaseReference;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_description);

        btn_update_club_description = findViewById(R.id.btn_update_club_description);
        ed_club_description = findViewById(R.id.ed_change_club_description);
        btn_back = findViewById(R.id.btn_change_club_description_back);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("SKCLUB").child(fragment_more.club_id).child("information");
        databaseReference.child("club_description").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
             ed_club_description.setText(snapshot.getValue(String.class));
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        btn_update_club_description.setOnClickListener(view -> {

            String description = ed_club_description.getText().toString();

            if(TextUtils.isEmpty(description) || description.equals(""))
            {
                ed_club_description.setError("Valid description is required !");
                return;
            }

            databaseReference.child("club_description").setValue(description).addOnSuccessListener(unused -> {
                Toast.makeText(ActivityChangeDescription.this, "Description updated !", Toast.LENGTH_SHORT).show();
                finish();
            }).addOnFailureListener(e -> Toast.makeText(ActivityChangeDescription.this, "Something went wrong !", Toast.LENGTH_SHORT).show());


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
