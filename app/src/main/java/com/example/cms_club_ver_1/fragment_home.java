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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class fragment_home extends Fragment {
    public RecyclerView rv_upcoming_events;
    public RecyclerView rv_upcoming_club_services;
    public ArrayList<EventPOJO> arrayList;
    public ArrayList<CSPOJO> arrayList2;
    public String club_id;

    public TextView txt_club_name;
    public TextView txt_club_tag;
    public ImageView img_club_logo;

    public EventAdapter adapter;
    public CSAdapter adapter2;

    public static String club_logo_url;

    public ClubInfoPOJO clubInfoPOJO;

    public DatabaseReference databaseReference;
    public DatabaseReference databaseReference2;

    public StorageReference storageReference;
    public StorageReference storageReference2;
    public int error_check;


    @SuppressLint("ObsoleteSdkInt")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        if (Build.VERSION.SDK_INT >= 21) {
            @SuppressLint("UseRequireInsteadOfGet") Window window = Objects.requireNonNull(getActivity()).getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(getResources().getColor(R.color.orange));
            changeStatusBarContrastStyle(window,true);
        }


        rv_upcoming_events = view.findViewById(R.id.rv_upcoming_events);
        rv_upcoming_club_services = view.findViewById(R.id.rv_upcoming_club_services);

        txt_club_name = view.findViewById(R.id.txt_club_name);
        txt_club_tag = view.findViewById(R.id.txt_club_tag);
        img_club_logo = view.findViewById(R.id.img_club_logo);

        Intent intent = requireActivity().getIntent();
        club_id = intent.getStringExtra("club_id");
        fragment_event.club_id=this.club_id;
        fragment_service.club_id=this.club_id;

        databaseReference = FirebaseDatabase.getInstance().getReference().child("SKCLUB").child(club_id).child("information");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                clubInfoPOJO = snapshot.getValue(ClubInfoPOJO.class);
                assert clubInfoPOJO != null;
                txt_club_name.setText(clubInfoPOJO.getClub_name());
                txt_club_tag.setText(clubInfoPOJO.getClub_tag());
                club_logo_url = clubInfoPOJO.getClub_logo();
                Glide.with(img_club_logo.getContext()).load(clubInfoPOJO.getClub_logo()).into(img_club_logo);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(MainActivity.MainActivity_context, "Something went wrong !", Toast.LENGTH_SHORT).show();
            }
        });


        arrayList = new ArrayList<>();
        arrayList2 = new ArrayList<>();

        databaseReference = FirebaseDatabase.getInstance().getReference().child("SKCLUB").child(club_id).child("events");
        databaseReference2 = FirebaseDatabase.getInstance().getReference().child("SKCLUB").child(club_id).child("club_services");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren())
                {
                    EventPOJO ch = dataSnapshot.getValue(EventPOJO.class);
                    assert ch != null;
                    Date date1  = new Date(ch.getEvent_date());
                    Date date2 = new Date(getDateTime());
                    if(!date1.before(date2))
                    {
                        arrayList.add(dataSnapshot.getValue(EventPOJO.class));
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        rv_upcoming_events.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        adapter = new EventAdapter(arrayList, eventPOJO -> {
            Toast.makeText(getContext(), eventPOJO.getEvent_name(), Toast.LENGTH_SHORT).show();
            Intent intent1 = new Intent(getContext(), ImageGalleryActivity.class);
            intent1.putExtra("event_data",eventPOJO);
            startActivity(intent1);
        }, eventPOJO -> {
            //

            SweetAlertDialog dialog = new SweetAlertDialog(requireActivity(),SweetAlertDialog.WARNING_TYPE);
            dialog.setTitleText("Confirmation");
            dialog.setContentText("Are you sure want to delete this event");
            dialog.setConfirmClickListener(sweetAlertDialog -> {
                databaseReference.child(eventPOJO.getCms_id()).child("gallery").get().addOnCompleteListener(task -> {
                    if (task.isSuccessful())
                    {
                        if (task.getResult().getValue() != null)
                        {
                            @SuppressWarnings (value="unchecked")
                            Map<String, Object> map = (Map<String, Object>) task.getResult().getValue();
                            for (Map.Entry<String,Object> entry : map.entrySet())
                            {
                                storageReference = FirebaseStorage.getInstance().getReferenceFromUrl(String.valueOf(entry.getValue()));
                                storageReference.delete().addOnSuccessListener(unused -> databaseReference.child(eventPOJO.getCms_id()).child("gallery").child(entry.getKey()).removeValue().addOnSuccessListener(unused1 -> error_check = 0).addOnFailureListener(e -> error_check = 1));
                            }
                        }
                    }
                });

                databaseReference.child(eventPOJO.getCms_id()).child("event_poster").get().addOnCompleteListener(task -> {
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

                databaseReference.child(eventPOJO.getCms_id()).removeValue().addOnSuccessListener(unused -> {
                    error_check = 0;
                    Toast.makeText(MainActivity.MainActivity_context, "Event deleted successfully ! ", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }).addOnFailureListener(e -> error_check = 1);

                if(error_check != 0)
                {
                    Toast.makeText(MainActivity.MainActivity_context,"Failed to delete event ! ", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }

            });

            dialog.show();

            //
        });


        rv_upcoming_events.setAdapter(adapter);


        //upcoming club service
        rv_upcoming_club_services.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));

        databaseReference2.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayList2.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren())
                {
                    CSPOJO ch = dataSnapshot.getValue(CSPOJO.class);
                    assert ch != null;
                    Date date1  = new Date(ch.getCs_date());
                    Date date2 = new Date(getDateTime());
                    if(!date1.before(date2))
                    {
                        arrayList2.add(dataSnapshot.getValue(CSPOJO.class));
                    }
                }
                adapter2.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        adapter2 = new CSAdapter(arrayList2, new OnCSListener() {
            @Override
            public void onItemClicked(CSPOJO cspojo) {
                Toast.makeText(getContext(), cspojo.getCs_topic(), Toast.LENGTH_SHORT).show();
                Intent intent1 = new Intent(getContext(), ActivityCSFile.class);
                intent1.putExtra("cs_data",cspojo);
                startActivity(intent1);
            }

            @Override
            public void onLongClicked(CSPOJO cspojo) {
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

        //

        rv_upcoming_club_services.setAdapter(adapter2);



        return view;
    }


    @SuppressLint("InlinedApi")
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


    private String getDateTime() {

        DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy", Locale.getDefault());

        Date date = new Date();

        return dateFormat.format(date);
    }

}