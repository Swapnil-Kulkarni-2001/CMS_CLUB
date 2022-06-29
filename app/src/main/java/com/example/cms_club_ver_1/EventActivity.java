package com.example.cms_club_ver_1;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.material.datepicker.MaterialDatePicker;

import java.util.Objects;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class EventActivity extends AppCompatActivity {

    public TextView textEventName;
    public TextView textEventDate;
    public TextView textEventFileUri;
    public TextView textEventDescription;

    public EditText ed_event;

    public AppCompatButton btn_event_edit;
    public AppCompatButton btn_event_edit_save;
    public AppCompatButton btn_event_all_save;
    public AppCompatButton btn_event_poster;
    public AppCompatButton btn_event_date;
    public AppCompatButton btn_event_description;

    public AppCompatButton back;

    public EditText ed_event_description;
    public AppCompatButton btn_event_description_save;


    public ImageView img_event_poster;

    public String EVENTNAME;
    public String EVENTDATE;
    public String EVENTDESCRIPTION;
    public static Uri EVENTPOSTERURI;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_organize);

        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(getResources().getColor(R.color.orange));
            changeStatusBarContrastStyle(window,true);
        }

        textEventName = findViewById(R.id.txt_event_name);
        ed_event = findViewById(R.id.ed_event_name);
        btn_event_edit = findViewById(R.id.btn_event_name_edit);
        btn_event_edit_save = findViewById(R.id.btn_event_name_edit_save);

        btn_event_all_save = findViewById(R.id.btn_event_all_save);
        btn_event_date = findViewById(R.id.btn_event_date);
        btn_event_poster = findViewById(R.id.btn_event_file);
        btn_event_description = findViewById(R.id.btn_event_description);

        back = findViewById(R.id.btn_event_cs_back);

        textEventDate = findViewById(R.id.txt_event_date);
        textEventFileUri = findViewById(R.id.txt_event_file_uri);
        textEventDescription = findViewById(R.id.txt_event_description);

        img_event_poster = findViewById(R.id.img_event_poster);

        btn_event_edit.setOnClickListener(view -> {
            textEventName.setVisibility(View.GONE);
            ed_event.setVisibility(View.VISIBLE);
            btn_event_edit.setVisibility(View.GONE);
            btn_event_edit_save.setVisibility(View.VISIBLE);
        });

        btn_event_edit_save.setOnClickListener(view -> {
            EVENTNAME = ed_event.getText().toString();
            textEventName.setText(EVENTNAME);
            ed_event.setVisibility(View.GONE);
            btn_event_edit_save.setVisibility(View.GONE);
            textEventName.setVisibility(View.VISIBLE);
            btn_event_edit.setVisibility(View.VISIBLE);

        });

        btn_event_poster.setOnClickListener(view -> ImagePicker.with(EventActivity.this)
                .crop()	    			//Crop image(Optional), Check Customization for more option
                .compress(1024)			//Final image size will be less than 1 MB(Optional)
                .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                .start());


        MaterialDatePicker.Builder materialDateBuilder = MaterialDatePicker.Builder.datePicker();
        materialDateBuilder.setTitleText("SELECT A DATE");
        final MaterialDatePicker materialDatePicker = materialDateBuilder.build();

        btn_event_date.setOnClickListener(view -> materialDatePicker.show(getSupportFragmentManager(), "MATERIAL_DATE_PICKER"));

        materialDatePicker.addOnPositiveButtonClickListener(
                selection -> {
                    EVENTDATE = materialDatePicker.getHeaderText();
                    textEventDate.setText(EVENTDATE);
                });


        btn_event_description.setOnClickListener(view -> {
            final AlertDialog.Builder alert = new AlertDialog.Builder(EventActivity.this);
            View view2 = getLayoutInflater().inflate(R.layout.multiline_edittext_dialog_box,null);
            alert.setView(view2);
            final AlertDialog alertDialog = alert.create();

            ed_event_description = view2.findViewById(R.id.ed_event_description);
            btn_event_description_save = view2.findViewById(R.id.btn_event_description_save);
            alertDialog.setCanceledOnTouchOutside(false);
            alertDialog.show();
            btn_event_description_save.setOnClickListener(view1 -> {
                EVENTDESCRIPTION = ed_event_description.getText().toString();
                if(EVENTDESCRIPTION.length() > 20 )
                {
                    Toast.makeText(getApplicationContext(),"Clicked",Toast.LENGTH_SHORT).show();
                    textEventDescription.setText(EVENTDESCRIPTION.substring(0,20)+"....");
                    alertDialog.dismiss();
                    return;
                }

                new SweetAlertDialog(
                        EventActivity.this, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Error")
                        .setContentText("Description must be greater than 10")
                        .show();
            });
        });

        btn_event_all_save.setOnClickListener(view -> {
            //firebase code to store event
//                use variable EVENTNAME;
//                use variable EVENTDATE;
//                use variable EVENTPOSTERURI;
//                use variable EVENTDESCRIPTION;
            //store above data in firebase

        });


        back.setOnClickListener(view -> finish());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        EVENTPOSTERURI= Objects.requireNonNull(data).getData();

        if(EVENTPOSTERURI == null)
        {
            return;
        }
        textEventFileUri.setTextSize(10f);
        textEventFileUri.setText(EVENTPOSTERURI.getPath());
        img_event_poster.setVisibility(View.VISIBLE);
        img_event_poster.setImageURI(EVENTPOSTERURI);

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
