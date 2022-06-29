package com.example.cms_club_ver_1;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class fragment_event extends Fragment {

    public AppCompatButton btn_organize_event;
    public RecyclerView rv;
    public ArrayList<EventPOJO> arrayList;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_event, container, false);

        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getActivity().getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(getResources().getColor(R.color.white));
            changeStatusBarContrastStyle(window,false);
        }

        btn_organize_event = view.findViewById(R.id.btn_organize);
        rv = view.findViewById(R.id.rv_event);
        arrayList = new ArrayList<>();

        arrayList.add(new EventPOJO("Event1","12/12/1200","Generating random paragraphs can be an excellent way for writers to get their creative flow going at the beginning of the day. The writer has no idea what topic the random paragraph will be about when it appears. This forces the writer to use creativity to complete one of three common writing challenges. The writer can use the paragraph as the first one of a short story and build upon it. A second option is to use the random paragraph somewhere in a short story they create. The third option is to have the random paragraph be the ending paragraph in a short story. No matter which of these challenges is undertaken, the writer is forced to use creativity to incorporate the paragraph into their writing.",EventActivity.EVENTPOSTERURI));
        arrayList.add(new EventPOJO("Event2","03/02/1200","Generating random paragraphs can be an excellent way for writers to get their creative flow going at the beginning of the day. The writer has no idea what topic the random paragraph will be about when it appears. This forces the writer to use creativity to complete one of three common writing challenges. The writer can use the paragraph as the first one of a short story and build upon it. A second option is to use the random paragraph somewhere in a short story they create. The third option is to have the random paragraph be the ending paragraph in a short story. No matter which of these challenges is undertaken, the writer is forced to use creativity to incorporate the paragraph into their writing.",EventActivity.EVENTPOSTERURI));
        arrayList.add(new EventPOJO("Event3","02/11/1200","Sunday",EventActivity.EVENTPOSTERURI));
        arrayList.add(new EventPOJO("Event4","04/09/1200","Sunday",EventActivity.EVENTPOSTERURI));
        arrayList.add(new EventPOJO("Event5","05/10/1200","Sunday",EventActivity.EVENTPOSTERURI));

        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        rv.setAdapter(new EventAdapter(arrayList, new OnEventClickListener() {
            @Override
            public void onItemClicked(EventPOJO eventPOJO) {
                Toast.makeText(getContext(),eventPOJO.getEvent_name(),Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getContext(),ImageGalleryActivity.class);
                intent.putExtra("event_name",eventPOJO.getEvent_name());
                startActivity(intent);
            }
        }));


        btn_organize_event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(getActivity(),EventActivity.class);
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