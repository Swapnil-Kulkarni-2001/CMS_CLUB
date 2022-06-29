package com.example.cms_club_ver_1;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ActivityChangePassword extends AppCompatActivity
{
    public AppCompatButton btn_update_password;
    public EditText ed_current_password;
    public EditText ed_new_password;
    public AppCompatButton btn_back;
    public FirebaseUser firebaseUser;
    private String email;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        btn_update_password = findViewById(R.id.btn_update_password);
        ed_current_password = findViewById(R.id.ed_current_password);
        ed_new_password = findViewById(R.id.ed_new_password);
        btn_back = findViewById(R.id.btn_change_club_password_back);

        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        assert firebaseUser != null;
        email = firebaseUser.getEmail();


        btn_update_password.setOnClickListener(view -> {
            String current_password = ed_current_password.getText().toString();
            String new_password = ed_new_password.getText().toString();

            if (TextUtils.isEmpty(current_password))
            {
                ed_current_password.setError("Invalid password");
                return;
            }
            if (TextUtils.isEmpty(new_password))
            {
                ed_new_password.setError("Invalid password");
                return;
            }
            if (TextUtils.isEmpty(current_password) && TextUtils.isEmpty(new_password))
            {
                ed_current_password.setError("Invalid password");
                ed_new_password.setError("Invalid password");
                return;
            }

            AuthCredential credential = EmailAuthProvider.getCredential(email,current_password);
            firebaseUser.reauthenticate(credential).addOnSuccessListener(unused -> firebaseUser.updatePassword(new_password).addOnSuccessListener(unused1 -> {
                Toast.makeText(ActivityChangePassword.this, "Password updated !", Toast.LENGTH_SHORT).show();
                finish();
            }).addOnFailureListener(e -> Toast.makeText(ActivityChangePassword.this, "Failed to update password !", Toast.LENGTH_SHORT).show())).addOnFailureListener(e -> Toast.makeText(ActivityChangePassword.this, "Failed to authenticate user !", Toast.LENGTH_SHORT).show());

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
