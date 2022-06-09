package com.example.cms_club_ver_1;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AssistantBoardAdapter extends RecyclerView.Adapter<AssistantBoardAdapter.Holder>
{
    ArrayList<AssistantBoardPOJO> arrayList;

    public AssistantBoardAdapter(ArrayList<AssistantBoardPOJO> arrayList) {
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.assistant_board_row,parent,false);
        return new AssistantBoardAdapter.Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, int position) {
        holder.profile.setImageResource(arrayList.get(position).getImg_profile());
        holder.textName.setText(arrayList.get(position).getName());
        holder.textPost.setText(arrayList.get(position).getPost());
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
            profile = itemView.findViewById(R.id.assis_profile);
            textName = itemView.findViewById(R.id.txt_assis_name);
            textPost = itemView.findViewById(R.id.txt_post);
        }

    }

    public void filterList(ArrayList<AssistantBoardPOJO> filterllist) {
        // below line is to add our filtered
        // list in our course array list.
        arrayList = filterllist;
        // below line is to notify our adapter
        // as change in recycler view data.
        notifyDataSetChanged();
    }
}
