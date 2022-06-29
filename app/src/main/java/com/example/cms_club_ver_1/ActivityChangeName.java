package com.example.cms_club_ver_1;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ActivityChangeName extends AppCompatActivity
{

    public AppCompatButton btn_update_name;
    public EditText ed_change_name;
    public AppCompatButton btn_back;
    public DatabaseReference databaseReference;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_name);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("SKCLUB").child(fragment_more.club_id).child("information");

        ed_change_name = findViewById(R.id.ed_new_club_name);
        btn_update_name = findViewById(R.id.btn_update_club_name);
        btn_back = findViewById(R.id.btn_change_club_name_back);

        btn_update_name.setOnClickListener(view -> {

            String name = ed_change_name.getText().toString();

            if(TextUtils.isEmpty(name))
            {
                ed_change_name.setError("Valid name is required !");
                return;
            }

            databaseReference.child("club_name").setValue(name).addOnSuccessListener(unused -> {
                Toast.makeText(ActivityChangeName.this, "Name updated !", Toast.LENGTH_SHORT).show();
                finish();
            }).addOnFailureListener(e -> Toast.makeText(ActivityChangeName.this, "Something went wrong !", Toast.LENGTH_SHORT).show());

        });

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    @Override
    public void finish()
    {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }
}
