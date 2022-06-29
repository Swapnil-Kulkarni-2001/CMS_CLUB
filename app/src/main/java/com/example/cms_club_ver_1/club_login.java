package com.example.cms_club_ver_1;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class club_login extends Fragment {

    EditText ed_id;
    EditText ed_pass;
    AppCompatButton btn_login;
    AppCompatButton btn_forget;

    FirebaseAuth firebaseAuth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.activity_club_login, container, false);

        ed_id = view.findViewById(R.id.ed_id);
        ed_pass = view.findViewById(R.id.ed_pass);
        btn_login = view.findViewById(R.id.btn_login);
        btn_forget = view.findViewById(R.id.btn_forget);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String email = ed_id.getText().toString();
                String password = ed_pass.getText().toString();

                String club_id = clubID(email);
                if (club_id.equals("error"))
                {
                    Toast.makeText(getContext(),"Invalid email id!",Toast.LENGTH_SHORT).show();
                    return;
                }
                firebaseAuth = FirebaseAuth.getInstance();

                firebaseAuth.signInWithEmailAndPassword(email,password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        Toast.makeText(getContext(),"Login successful!",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getActivity(),MainActivity.class);
                        intent.putExtra("club_id",club_id);
                        startActivity(intent);

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(),"Wrong user!",Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        btn_forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getContext(),ActivityForgetPassword.class));
                getActivity().overridePendingTransition(R.anim.slide_in_right,R.anim.slide_out_left);
            }
        });


        return view;
    }


    private String clubID(String email)
    {
        Pattern pattern = Pattern.compile("^(.+?)@");
        Matcher matcher = pattern.matcher(email);
        String club_id;
        if(matcher.find())
        {
            club_id = matcher.group(1);
            return club_id;
        }
        return "error";
    }
}