package com.example.cms_club_ver_1;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;

public class ActivityForgetPassword extends AppCompatActivity
{
    public EditText ed_email;
    public AppCompatButton btn_send_request;
    public String email;
    public TextView txt_message;


    public FirebaseAuth auth;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        ed_email = findViewById(R.id.ed_forget_email);
        btn_send_request = findViewById(R.id.btn_send_request);
        txt_message = findViewById(R.id.txt_message);

        btn_send_request.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = ed_email.getText().toString();

                if (TextUtils.isEmpty(email))
                {
                    ed_email.setError("Valid email required");
                    return;
                }

                auth = FirebaseAuth.getInstance();
                auth.sendPasswordResetEmail(email).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        txt_message.setVisibility(View.VISIBLE);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ActivityForgetPassword.this, "Something went wrong !", Toast.LENGTH_SHORT).show();
                    }
                });

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
