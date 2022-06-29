package com.example.cms_club_ver_1;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatButton;
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
    public void onBindViewHolder(@NonNull Holder holder, @SuppressLint("RecyclerView") int position) {
        holder.profile.setImageResource(arrayList.get(position).getImg_profile());
        holder.textName.setText(arrayList.get(position).getName());
        holder.textPost.setText(arrayList.get(position).getPost());
        holder.textPhone_no.setText(arrayList.get(position).getPhone_no());
        holder.btn_LinkedIn.setOnClickListener(view -> GoToURL(arrayList.get(position).getLinkedin_url()));

        holder.btn_Github.setOnClickListener(view -> GoToURL(arrayList.get(position).getGithub_url()));

        holder.btn_Instagram.setOnClickListener(view -> GoToURL(arrayList.get(position).getInstagram_url()));


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
        TextView textPhone_no;
        AppCompatButton btn_LinkedIn;
        AppCompatButton btn_Github;
        AppCompatButton btn_Instagram;

        public Holder(@NonNull View itemView) {
            super(itemView);
            profile = itemView.findViewById(R.id.profile);
            textName = itemView.findViewById(R.id.txt_name);
            textPost = itemView.findViewById(R.id.txt_role);
            textPhone_no = itemView.findViewById(R.id.txt_phone_no);
            btn_LinkedIn = itemView.findViewById(R.id.btn_linkedIn);
            btn_Github = itemView.findViewById(R.id.btn_github);
            btn_Instagram = itemView.findViewById(R.id.btn_instagram);
        }

        public void bind(final MainBoardPOJO item, final OnMainBoardRowClickListener listener) {
            itemView.setOnClickListener(v -> listener.onItemClick(item));
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    public void filterList(ArrayList<MainBoardPOJO> filterllist) {
        // below line is to add our filtered
        // list in our course array list.
        arrayList = filterllist;
        // below line is to notify our adapter
        // as change in recycler view data.
        notifyDataSetChanged();
    }

    void GoToURL(String url){
        Uri uri = Uri.parse(url);
        Intent intent= new Intent(Intent.ACTION_VIEW,uri);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        MainActivity.MainActivity_context.startActivity(intent);
    }
}
