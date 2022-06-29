package com.example.cms_club_ver_1;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class fragment_main_board extends Fragment {

    public AppCompatButton button;
    public EditText ed_search;
    public RecyclerView recyclerView;
    public ArrayList<MainBoardPOJO> arrayList;
    public MainBoardAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_main_board, container, false);
        ed_search = view.findViewById(R.id.ed_search);
        recyclerView = view.findViewById(R.id.rv);
        button = view.findViewById(R.id.btn_main_board_add);
        arrayList = new ArrayList<>();

        //Add main member board information from firebase to arrayList

        arrayList.add(new MainBoardPOJO(R.drawable.ic_launcher_background,"Swapnil Kulkarni","Web Developer","9403342835","https://www.instagram.com/swapnil_kulkarni_2001/","https://github.com/Swapnil-Kulkarni-2001","https://www.instagram.com/swapnil_kulkarni_2001/"));
        arrayList.add(new MainBoardPOJO(R.drawable.ic_launcher_background,"Harshal Gawande","Treasurer","9403342835","https://www.instagram.com/swapnil_kulkarni_2001/","https://github.com/Swapnil-Kulkarni-2001","https://www.instagram.com/swapnil_kulkarni_2001/"));
        arrayList.add(new MainBoardPOJO(R.drawable.ic_launcher_background,"Swapnil Kulkarni","Web Developer","9403342835","https://www.instagram.com/swapnil_kulkarni_2001/","https://github.com/Swapnil-Kulkarni-2001","https://www.instagram.com/swapnil_kulkarni_2001/"));
        arrayList.add(new MainBoardPOJO(R.drawable.ic_launcher_background,"Swapnil Kulkarni","Web Developer","9403342835","https://www.instagram.com/swapnil_kulkarni_2001/","https://github.com/Swapnil-Kulkarni-2001","https://www.instagram.com/swapnil_kulkarni_2001/"));
        arrayList.add(new MainBoardPOJO(R.drawable.ic_launcher_background,"Swapnil Kulkarni","Web Developer","9403342835","https://www.instagram.com/swapnil_kulkarni_2001/","https://github.com/Swapnil-Kulkarni-2001","https://www.instagram.com/swapnil_kulkarni_2001/"));
        arrayList.add(new MainBoardPOJO(R.drawable.ic_launcher_background,"Swapnil Kulkarni","Web Developer","9403342835","https://www.instagram.com/swapnil_kulkarni_2001/","https://github.com/Swapnil-Kulkarni-2001","https://www.instagram.com/swapnil_kulkarni_2001/"));
        arrayList.add(new MainBoardPOJO(R.drawable.ic_launcher_background,"Swapnil Kulkarni","Web Developer","9403342835","https://www.instagram.com/swapnil_kulkarni_2001/","https://github.com/Swapnil-Kulkarni-2001","https://www.instagram.com/swapnil_kulkarni_2001/"));

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter = new MainBoardAdapter(arrayList, new OnMainBoardRowClickListener() {
            @Override
            public void onItemClick(MainBoardPOJO mainBoardPOJO) {
                Toast.makeText(getContext(),mainBoardPOJO.getName(),Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getContext(),EditActivity.class);
                intent.putExtra("CALLED_FROM",2);
                startActivity(intent);
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

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(),MainBoardAddPage.class);
                intent.putExtra("CALLED_FROM",2);
                startActivity(intent);
            }
        });

        return view;
    }

    private void filter(String text) {
        ArrayList<MainBoardPOJO> filteredlist = new ArrayList<>();
        for (MainBoardPOJO item : arrayList) {
            if (item.getName().toLowerCase().contains(text.toLowerCase())) {
                filteredlist.add(item);
            }
        }
        if (filteredlist.isEmpty()) {
            Toast.makeText(getContext(), "No Data Found..", Toast.LENGTH_SHORT).show();
        } else {

            adapter.filterList(filteredlist);
        }
    }
}