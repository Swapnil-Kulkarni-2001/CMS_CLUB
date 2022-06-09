package com.example.cms_club_ver_1;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainBoardAdapter extends RecyclerView.Adapter<MainBoardAdapter.Holder>
{

    ArrayList<MainBoardPOJO> arrayList;
    OnMainBoardRowClickListener listener;

    public MainBoardAdapter(ArrayList<MainBoardPOJO> arrayList, OnMainBoardRowClickListener listener)
    {
        this.arrayList = arrayList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.main_board_row,parent,false);
        return new Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.profile.setImageResource(arrayList.get(position).getImg_profile());
        holder.textName.setText(arrayList.get(position).getName());
        holder.textPost.setText(arrayList.get(position).getPost());
        holder.bind(arrayList.get(position),listener);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    static class Holder extends RecyclerView.ViewHolder
    {
        ImageView profile;
        TextView textName;
        TextView textPost;

        public Holder(@NonNull View itemView) {
            super(itemView);
            profile = itemView.findViewById(R.id.profile);
            textName = itemView.findViewById(R.id.txt_name);
            textPost = itemView.findViewById(R.id.txt_role);
        }

        public void bind(final MainBoardPOJO item, final OnMainBoardRowClickListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onItemClick(item);
                }
            });
        }
    }

    public void filterList(ArrayList<MainBoardPOJO> filterllist) {
        // below line is to add our filtered
        // list in our course array list.
        arrayList = filterllist;
        // below line is to notify our adapter
        // as change in recycler view data.
        notifyDataSetChanged();
    }
}
