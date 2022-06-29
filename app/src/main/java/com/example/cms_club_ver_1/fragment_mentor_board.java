package com.example.cms_club_ver_1;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
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

import java.util.ArrayList;
import java.util.ListIterator;


public class fragment_mentor_board extends Fragment {
    public EditText ed_search;
    public RecyclerView recyclerView;
    public ArrayList<ClubMemberPOJO> arrayList;
    public AppCompatButton btn_add_mentor;

    public ListIterator<ClubMemberPOJO> iter;
    public MainBoardAdapter adapter;
    public DatabaseReference databaseReference;
    public String club_id;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_mentor_board, container, false);
        ed_search = view.findViewById(R.id.ed_search);
        recyclerView = view.findViewById(R.id.rv);
        btn_add_mentor = view.findViewById(R.id.btn_mentor_add);

        arrayList = new ArrayList<>();
        iter = arrayList.listIterator();
        Intent intent1 = getActivity().getIntent();
        club_id = intent1.getStringExtra("club_id");
        Toast.makeText(getContext(),club_id,Toast.LENGTH_SHORT).show();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("SKCLUB").child(club_id).child("members").child("mentor").addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayList.clear();
                for(DataSnapshot dataSnapshot:snapshot.getChildren())
                {
                    ClubMemberPOJO clubMemberPOJO =dataSnapshot.getValue(ClubMemberPOJO.class);
                    arrayList.add(clubMemberPOJO);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(),"Something went wrong!!!",Toast.LENGTH_SHORT).show();
            }
        });


        adapter = new MainBoardAdapter(arrayList, new OnMainBoardRowClickListener() {
            @Override
            public void onItemClick(ClubMemberPOJO clubMemberPOJO) {
                Toast.makeText(getContext(),clubMemberPOJO.getName(),Toast.LENGTH_SHORT).show();
                Intent intent2 = new Intent(getContext(),EditActivity.class);
                intent2.putExtra("CALLED_FROM",1);
                intent2.putExtra("current_member_data",clubMemberPOJO);
                intent2.putExtra("club_id",club_id);
                startActivity(intent2);
            }

        });
        recyclerView.setAdapter(adapter);

        ed_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                filter(charSequence.toString());

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        btn_add_mentor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),MainBoardAddPage.class);
                intent.putExtra("CALLED_FROM",1);
                intent.putExtra("club_id",club_id);
                startActivity(intent);
            }
        });

        return view;
    }

    private void filter(String text) {
        ArrayList<ClubMemberPOJO> filteredlist = new ArrayList<>();
        for (ClubMemberPOJO item : arrayList) {
            if (item.getName().toLowerCase().contains(text.toLowerCase())) {
                filteredlist.add(item);
            }
        }
        if (!filteredlist.isEmpty()) {
            adapter.filterList(filteredlist);
        }
    }
}