package com.example.cms_club_ver_1;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import ru.embersoft.expandabletextview.ExpandableTextView;

public class CSAdapter extends RecyclerView.Adapter<CSAdapter.Holder>
{

    public ArrayList<CSPOJO> arrayList;
    public OnCSListener listener;


    public CSAdapter(ArrayList<CSPOJO> arrayList, OnCSListener listener) {
        this.arrayList = arrayList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.event_row_new,parent,false);
        return new CSAdapter.Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, @SuppressLint("RecyclerView") int position) {


        holder.textCSTopic.setText(arrayList.get(position).getCs_topic());
        holder.textDate.setText(arrayList.get(position).getCs_date());
        holder.expandableDescription.setText(arrayList.get(position).getCs_description());
        holder.expandableDescription.setOnStateChangeListener(new ExpandableTextView.OnStateChangeListener() {
            @Override
            public void onStateChange(boolean isShrink) {
                CSPOJO item = arrayList.get(position);
                item.setShrink(isShrink);
                arrayList.set(position,item);
            }
        });
        holder.expandableDescription.setText(arrayList.get(position).getCs_description());
        holder.expandableDescription.resetState(arrayList.get(position).isShrink);
        if (arrayList.get(position).getCs_poster() == null)
        {
            holder.poster.setVisibility(View.GONE);
        }
        else
        {
            Glide.with(holder.poster.getContext()).load(arrayList.get(position).getCs_poster()).into(holder.poster);
        }

        holder.bind(arrayList.get(position),listener);

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    class Holder extends RecyclerView.ViewHolder
    {
        TextView textCSTopic;
        TextView textDate;
        ImageView poster;
        ExpandableTextView expandableDescription;
        public Holder(@NonNull View itemView) {
            super(itemView);
            textCSTopic = itemView.findViewById(R.id.eventName);
            textDate = itemView.findViewById(R.id.date);
            poster = itemView.findViewById(R.id.poster);
            expandableDescription = itemView.findViewById(R.id.expandable_description);
        }

        public void bind(final CSPOJO item, final OnCSListener listener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onItemClicked(item);
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    listener.onLongClicked(item);
                    return true;
                }
            });
        }

    }
}
