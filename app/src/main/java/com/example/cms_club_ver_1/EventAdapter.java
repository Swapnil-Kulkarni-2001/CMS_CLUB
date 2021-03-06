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
import ru.embersoft.expandabletextview.ExpandableTextView.OnStateChangeListener;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.Holder>
{
    public ArrayList<EventPOJO> arrayList;
    public OnEventClickListener single_click_listener;
    public OnEventLongClickListener longClickListener;


    public EventAdapter(ArrayList<EventPOJO> arrayList, OnEventClickListener single_click_listener, OnEventLongClickListener longClickListener) {
        this.arrayList = arrayList;
        this.single_click_listener = single_click_listener;
        this.longClickListener = longClickListener;
    }

    @NonNull
    @Override
    public Holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.event_row_new,parent,false);
        return new EventAdapter.Holder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Holder holder, @SuppressLint("RecyclerView") int position) {
        holder.textEventName.setText(arrayList.get(position).getEvent_name());
        holder.textDate.setText(arrayList.get(position).getEvent_date());
        holder.expandableDescription.setText(arrayList.get(position).getEvent_description());
        holder.expandableDescription.setOnStateChangeListener(new OnStateChangeListener() {
            @Override
            public void onStateChange(boolean isShrink) {
                EventPOJO item = arrayList.get(position);
                item.setShrink(isShrink);
                arrayList.set(position,item);
            }
        });
        holder.expandableDescription.setText(arrayList.get(position).getEvent_description());
        holder.expandableDescription.resetState(arrayList.get(position).isShrink);
        if (arrayList.get(position).getEvent_poster() == null)
        {
            holder.poster.setVisibility(View.GONE);
        }
        else
        {
            Glide.with(holder.poster.getContext()).load(arrayList.get(position).getEvent_poster()).into(holder.poster);
        }

        holder.bind(arrayList.get(position),single_click_listener,longClickListener);

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    static class Holder extends RecyclerView.ViewHolder
    {
        TextView textEventName;
        TextView textDate;
        ImageView poster;
        ExpandableTextView expandableDescription;

        public Holder(@NonNull View itemView) {
            super(itemView);
            textEventName = itemView.findViewById(R.id.eventName);
            textDate = itemView.findViewById(R.id.date);
            poster = itemView.findViewById(R.id.poster);
            expandableDescription = itemView.findViewById(R.id.expandable_description);
        }

        public void bind(final EventPOJO item, final OnEventClickListener listener, final OnEventLongClickListener longClickListener) {
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override public void onClick(View v) {
                    listener.onItemClicked(item);
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    longClickListener.onLongClicked(item);
                    return true;
                }
            });
        }
    }

}
