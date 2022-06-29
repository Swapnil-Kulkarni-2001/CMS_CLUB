package com.example.cms_club_ver_1;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.bumptech.glide.Glide;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class ActivityChangeLogo extends AppCompatActivity
{
    public AppCompatButton btn_update_club_logo;
    public ImageButton btn_choose_logo;
    public ImageView img_club_logo;
    public AppCompatButton btn_back;

    public Uri uri;

    public DatabaseReference databaseReference;
    public StorageReference storageReference;

    public ProgressBar progressBar;

    @RequiresApi(api = Build.VERSION_CODES.N)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_logo);

        btn_choose_logo = findViewById(R.id.btn_choose_logo);
        btn_update_club_logo = findViewById(R.id.btn_update_club_logo);
        img_club_logo = findViewById(R.id.img_change_club_logo);
        progressBar = findViewById(R.id.progressBar);
        btn_back = findViewById(R.id.btn_change_club_logo_back);

        progressBar.setMax(100);

        Glide.with(img_club_logo.getContext()).load(fragment_home.club_logo_url).into(img_club_logo);

        databaseReference = FirebaseDatabase.getInstance().getReference().child("SKCLUB").child(fragment_more.club_id).child("information");
        btn_choose_logo.setOnClickListener(view -> ImagePicker.with(ActivityChangeLogo.this)
                .crop()	    			//Crop image(Optional), Check Customization for more option
                .compress(1024)			//Final image size will be less than 1 MB(Optional)
                .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                .start());

        btn_update_club_logo.setOnClickListener(view -> {
            if (uri == null)
            {
                return;
            }

            progressBar.setVisibility(View.VISIBLE);
            storageReference = FirebaseStorage.getInstance().getReferenceFromUrl(fragment_home.club_logo_url);
            storageReference.delete().addOnSuccessListener(unused -> {
                progressBar.setProgress(20,true);
                storageReference = FirebaseStorage.getInstance().getReference().child(fragment_more.club_id).child("information");
                storageReference = storageReference.child(System.currentTimeMillis()+"."+getFileExtension(uri));
                storageReference.putFile(uri).addOnSuccessListener(taskSnapshot -> {
                    progressBar.setProgress(50,true);
                    taskSnapshot.getStorage().getDownloadUrl().addOnSuccessListener(uri ->{
                        progressBar.setProgress(70,true);
                        databaseReference.child("club_logo").setValue(String.valueOf(uri)).addOnSuccessListener(unused1 -> {
                            progressBar.setProgress(100,true);
                            Toast.makeText(ActivityChangeLogo.this, "Logo updated !", Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.INVISIBLE);
                            finish();
                        });
                    }).addOnFailureListener(e -> Toast.makeText(ActivityChangeLogo.this, "Something went wrong !", Toast.LENGTH_SHORT).show());
                }).addOnFailureListener(e -> Toast.makeText(ActivityChangeLogo.this, "Try again later !", Toast.LENGTH_SHORT).show());
            }).addOnFailureListener(e -> Toast.makeText(ActivityChangeLogo.this, "Try again later !", Toast.LENGTH_SHORT).show());
        });



        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data != null) {
            uri = data.getData();
            img_club_logo.setImageURI(uri);
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

    @Override
    public void finish()
    {
        super.finish();
        overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
    }

}
