package com.example.cms_club_ver_1;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;


public class fragment_service extends Fragment {

    public AppCompatButton btn_organize_cs;

    public RecyclerView rv;
    public ArrayList<CSPOJO> arrayList;
    public static String club_id;
    public CSAdapter adapter;
    public DatabaseReference databaseReference;
    public StorageReference storageReference;
    public int error_check;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_service, container, false);

        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getActivity().getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(getResources().getColor(R.color.white));
            changeStatusBarContrastStyle(window,false);
        }

        Intent intent = getActivity().getIntent();
        club_id = intent.getStringExtra("club_id");

        btn_organize_cs = view.findViewById(R.id.btn_organize_club_service);

        rv = view.findViewById(R.id.rv_club_services);
        arrayList = new ArrayList<>();
        rv.setLayoutManager(new LinearLayoutManager(getContext()));

        databaseReference = FirebaseDatabase.getInstance().getReference().child("SKCLUB").child(club_id).child("club_services");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayList.clear();
                for(DataSnapshot dataSnapshot:snapshot.getChildren())
                {
                    arrayList.add(dataSnapshot.getValue(CSPOJO.class));
                }
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        adapter = new CSAdapter(arrayList, new OnCSListener() {
            @Override
            public void onItemClicked(CSPOJO cspojo) {
                Toast.makeText(getContext(), cspojo.getCs_topic(), Toast.LENGTH_SHORT).show();
                Intent intent1 = new Intent(getContext(), ActivityCSFile.class);
                intent1.putExtra("cs_data",cspojo);
                startActivity(intent1);
            }

            @Override
            public void onLongClicked(CSPOJO cspojo) {
                Toast.makeText(MainActivity.MainActivity_context, "long clicked", Toast.LENGTH_SHORT).show();

                //

                SweetAlertDialog dialog = new SweetAlertDialog(getActivity(),SweetAlertDialog.WARNING_TYPE);
                dialog.setTitleText("Confirmation");
                dialog.setContentText("Are you sure want to delete this club service");
                dialog.setConfirmClickListener(sweetAlertDialog -> {
                    databaseReference.child(cspojo.getCms_id()).child("cs_files").get().addOnCompleteListener(task -> {
                        if (task.isSuccessful())
                        {
                            if (task.getResult().getValue() != null)
                            {
                                Map<String, Object> map = (Map<String, Object>) task.getResult().getValue();
                                for (Map.Entry<String,Object> entry : map.entrySet())
                                {
                                    storageReference = FirebaseStorage.getInstance().getReferenceFromUrl(String.valueOf(entry.getValue()));
                                    storageReference.delete().addOnSuccessListener(unused -> databaseReference.child(cspojo.getCms_id()).child("cs_files").child(entry.getKey()).removeValue().addOnSuccessListener(unused1 -> error_check = 0).addOnFailureListener(e -> error_check = 1));
                                }
                            }
                        }
                    });

                    databaseReference.child(cspojo.getCms_id()).child("cs_poster").get().addOnCompleteListener(task -> {
                        if (task.isSuccessful())
                        {
                            if (task.getResult()!=null)
                            {
                                String s =  String.valueOf(task.getResult().getValue());
                                storageReference = FirebaseStorage.getInstance().getReferenceFromUrl(s);
                                storageReference.delete().addOnSuccessListener(unused -> error_check = 0).addOnFailureListener(e -> error_check = 1);
                            }
                        }
                    });

                    databaseReference.child(cspojo.getCms_id()).removeValue().addOnSuccessListener(unused -> {
                        error_check = 0;
                        Toast.makeText(MainActivity.MainActivity_context, "Club service deleted successfully ! ", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }).addOnFailureListener(e -> error_check = 1);

                    if(error_check != 0)
                    {
                        Toast.makeText(MainActivity.MainActivity_context,"Failed to delete club service ! ", Toast.LENGTH_SHORT).show();
                        dialog.dismiss();
                    }

                });

                dialog.show();

                //

            }
        });

        rv.setAdapter(adapter);

        btn_organize_cs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),ClubServiceActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }



    public static void changeStatusBarContrastStyle(Window window, Boolean lightIcons) {
        View decorView = window.getDecorView();
        if (lightIcons) {
            // Draw light icons on a dark background color
            decorView.setSystemUiVisibility(decorView.getSystemUiVisibility() & ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        } else {
            // Draw dark icons on a light background color
            decorView.setSystemUiVisibility(decorView.getSystemUiVisibility() | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        }
    }
}