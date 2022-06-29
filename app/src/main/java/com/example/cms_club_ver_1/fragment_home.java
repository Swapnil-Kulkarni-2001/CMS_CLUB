package com.example.cms_club_ver_1;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Objects;

public class fragment_home extends Fragment {
    public RecyclerView rv_upcoming_events;
    public RecyclerView rv_upcoming_club_services;
    public ArrayList<EventPOJO> arrayList;

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

        arrayList = new ArrayList<>();

        arrayList.add(new EventPOJO("Event1","12/12/1200","Generating random paragraphs can be an excellent way for writers to get their creative flow going at the beginning of the day. The writer has no idea what topic the random paragraph will be about when it appears. This forces the writer to use creativity to complete one of three common writing challenges. The writer can use the paragraph as the first one of a short story and build upon it. A second option is to use the random paragraph somewhere in a short story they create. The third option is to have the random paragraph be the ending paragraph in a short story. No matter which of these challenges is undertaken, the writer is forced to use creativity to incorporate the paragraph into their writing.",EventActivity.EVENTPOSTERURI));
        arrayList.add(new EventPOJO("Event2","03/02/1200","Generating random paragraphs can be an excellent way for writers to get their creative flow going at the beginning of the day. The writer has no idea what topic the random paragraph will be about when it appears. This forces the writer to use creativity to complete one of three common writing challenges. The writer can use the paragraph as the first one of a short story and build upon it. A second option is to use the random paragraph somewhere in a short story they create. The third option is to have the random paragraph be the ending paragraph in a short story. No matter which of these challenges is undertaken, the writer is forced to use creativity to incorporate the paragraph into their writing.",EventActivity.EVENTPOSTERURI));
        arrayList.add(new EventPOJO("Event3","02/11/1200","Sunday",EventActivity.EVENTPOSTERURI));
        arrayList.add(new EventPOJO("Event4","04/09/1200","Sunday",EventActivity.EVENTPOSTERURI));
        arrayList.add(new EventPOJO("Event5","05/10/1200","Sunday",EventActivity.EVENTPOSTERURI));

        rv_upcoming_events.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        rv_upcoming_events.setAdapter(new EventAdapter(arrayList, eventPOJO -> Toast.makeText(getContext(),"Clicked "+eventPOJO.getEvent_name(),Toast.LENGTH_SHORT).show()));

        rv_upcoming_club_services.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        rv_upcoming_club_services.setAdapter(new EventAdapter(arrayList, eventPOJO -> Toast.makeText(getContext(),"Clicked "+eventPOJO.getEvent_name(),Toast.LENGTH_SHORT).show()));


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
}