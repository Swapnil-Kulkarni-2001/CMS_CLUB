package com.example.cms_club_ver_1;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.MimeTypeMap;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Objects;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class ClubServiceActivity extends AppCompatActivity
{

    public TextView textCSName;
    public TextView textCSDate;
    public TextView textCSFileUri;
    public TextView textCSDescription;

    public EditText ed_cs;

    public AppCompatButton btn_cs_edit;
    public AppCompatButton btn_cs_edit_save;
    public AppCompatButton btn_cs_all_save;
    public AppCompatButton btn_cs_poster;
    public AppCompatButton btn_cs_date;
    public AppCompatButton btn_cs_description;

    public AppCompatButton back;

    public EditText ed_cs_description;
    public AppCompatButton btn_cs_description_save;


    public ImageView img_cs_poster;

    public int error_check;

    public String CSNAME;
    public String CSDATE;
    public String CSDESCRIPTION;
    public static Uri CSPOSTERURI;

    public DatabaseReference databaseReference;
    public StorageReference storageReference;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_club_service_organize);

        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(getResources().getColor(R.color.orange));
            changeStatusBarContrastStyle(window,true);
        }


        textCSName = findViewById(R.id.txt_club_service_name);
        ed_cs = findViewById(R.id.ed_club_service_name);
        btn_cs_edit = findViewById(R.id.btn_club_service_name_edit);
        btn_cs_edit_save = findViewById(R.id.btn_club_service_name_edit_save);

        btn_cs_all_save = findViewById(R.id.btn_organize);
        btn_cs_date = findViewById(R.id.btn_club_service_date);
        btn_cs_poster = findViewById(R.id.btn_club_service_file);
        btn_cs_description = findViewById(R.id.btn_club_service_description);

        back = findViewById(R.id.btn_club_service_back);

        textCSDate = findViewById(R.id.txt_club_service_date);
        textCSFileUri = findViewById(R.id.txt_club_service_file_uri);
        textCSDescription = findViewById(R.id.txt_club_service_description);

        img_cs_poster = findViewById(R.id.img_club_service_poster);


        databaseReference = FirebaseDatabase.getInstance().getReference().child("SKCLUB").child(fragment_service.club_id).child("club_services");
        storageReference = FirebaseStorage.getInstance().getReference().child(fragment_service.club_id).child("club_services");


        btn_cs_edit.setOnClickListener(view -> {
            textCSName.setVisibility(View.GONE);
            ed_cs.setVisibility(View.VISIBLE);
            btn_cs_edit.setVisibility(View.GONE);
            btn_cs_edit_save.setVisibility(View.VISIBLE);
        });

        btn_cs_edit_save.setOnClickListener(view -> {
            CSNAME = ed_cs.getText().toString();
            textCSName.setText(CSNAME);
            ed_cs.setVisibility(View.GONE);
            btn_cs_edit_save.setVisibility(View.GONE);
            textCSName.setVisibility(View.VISIBLE);
            btn_cs_edit.setVisibility(View.VISIBLE);

        });


        btn_cs_poster.setOnClickListener(view -> ImagePicker.with(ClubServiceActivity.this)
                .crop()	    			//Crop image(Optional), Check Customization for more option
                .compress(1024)			//Final image size will be less than 1 MB(Optional)
                .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                .start());


        MaterialDatePicker.Builder materialDateBuilder = MaterialDatePicker.Builder.datePicker();
        materialDateBuilder.setTitleText("SELECT A DATE");
        final MaterialDatePicker materialDatePicker = materialDateBuilder.build();

        btn_cs_date.setOnClickListener(view -> materialDatePicker.show(getSupportFragmentManager(), "MATERIAL_DATE_PICKER"));

        materialDatePicker.addOnPositiveButtonClickListener(
                selection -> {
                    CSDATE = materialDatePicker.getHeaderText();
                    textCSDate.setText(CSDATE);
                });



        btn_cs_description.setOnClickListener(view -> {
            final AlertDialog.Builder alert = new AlertDialog.Builder(ClubServiceActivity.this);
            View view2 = getLayoutInflater().inflate(R.layout.multiline_edittext_dialog_box,null);
            alert.setView(view2);
            final AlertDialog alertDialog = alert.create();

            ed_cs_description = view2.findViewById(R.id.ed_event_description);
            btn_cs_description_save = view2.findViewById(R.id.btn_event_description_save);
            alertDialog.setCanceledOnTouchOutside(false);
            alertDialog.show();
            btn_cs_description_save.setOnClickListener(view1 -> {
                CSDESCRIPTION = ed_cs_description.getText().toString();
                if(CSDESCRIPTION.length() > 20 )
                {
                    Toast.makeText(getApplicationContext(),"Clicked",Toast.LENGTH_SHORT).show();
                    textCSDescription.setText(CSDESCRIPTION.substring(0,20)+"....");
                    alertDialog.dismiss();
                    return;
                }

                new SweetAlertDialog(
                        ClubServiceActivity.this, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Error")
                        .setContentText("Description must be greater than 20")
                        .show();
            });
        });


        btn_cs_all_save.setOnClickListener(view -> {
            //firebase code to store CS
            //store above data in firebase

            int field_check = 0;
            if(TextUtils.isEmpty(CSNAME))
            {
                field_check = 1;
            }
            if(TextUtils.isEmpty(CSDATE))
            {
                field_check = 1;
            }
            if(TextUtils.isEmpty(CSDESCRIPTION))
            {
                field_check = 1;
            }
            if(TextUtils.isEmpty(String.valueOf(CSPOSTERURI)))
            {
                field_check = 1;
            }

            if(field_check == 1)
            {
                Toast.makeText(getApplicationContext(), "Failed to create club service !", Toast.LENGTH_SHORT).show();
                return;
            }

            storageReference = storageReference.child(System.currentTimeMillis()+"."+getFileExtension(CSPOSTERURI));
            storageReference.putFile(CSPOSTERURI).addOnSuccessListener(taskSnapshot -> storageReference.getDownloadUrl().addOnSuccessListener(uri -> {
                String key = databaseReference.push().getKey();
                CSPOJO data = new CSPOJO();
                data.setCms_id(key);
                data.setCs_topic(CSNAME);
                data.setCs_date(CSDATE);
                data.setCs_description(CSDESCRIPTION);
                data.setCs_poster(String.valueOf(uri));
                assert key != null;
                databaseReference.child(key).setValue(data).addOnSuccessListener(unused -> error_check = 0);
            }));

            if (error_check == 0)
            {
                Toast.makeText(getApplicationContext(), "Club service created successfully ! ", Toast.LENGTH_SHORT).show();
                finish();
            }


        });

        back.setOnClickListener(view -> finish());

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        CSPOSTERURI= Objects.requireNonNull(data).getData();

        if (CSPOSTERURI == null)
        {
            return;
        }
        textCSFileUri.setTextSize(10f);
        textCSFileUri.setText(CSPOSTERURI.getPath());
        img_cs_poster.setVisibility(View.VISIBLE);
        img_cs_poster.setImageURI(CSPOSTERURI);

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


    private String getFileExtension(Uri uri)
    {
        if(uri!=null)
        {
            ContentResolver contentResolver = getContentResolver();
            MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
            return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(uri));
        }
        return "error";
    }
}
