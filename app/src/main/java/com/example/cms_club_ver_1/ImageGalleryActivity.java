package com.example.cms_club_ver_1;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class ImageGalleryActivity extends AppCompatActivity
{

    public ArrayList<ImagePOJO> arrayList;
    public RecyclerView recyclerView;
    public ImageAdapter adapter;
    public AppCompatButton btn_add_img;
    public Intent intent;
    public EventPOJO event_data;

    public DatabaseReference databaseReference;
    public StorageReference storageReference;

    public Uri poster_uri = null;

    public int error_check = 0;

    public ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_gallery);

        intent = getIntent();
        arrayList = new ArrayList<>();
        recyclerView = findViewById(R.id.rv_img);
        btn_add_img = findViewById(R.id.btn_img_add);
        progressBar = findViewById(R.id.progressBar2);
        progressBar.setMax(100);

        event_data = (EventPOJO) intent.getSerializableExtra("event_data");


        databaseReference = FirebaseDatabase.getInstance().getReference().child("SKCLUB").child(fragment_event.club_id).child("events").child(event_data.getCms_id()).child("gallery");
        storageReference = FirebaseStorage.getInstance().getReference().child(fragment_event.club_id).child("events");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren())
                {
                    ImagePOJO imagePOJO = new ImagePOJO(dataSnapshot.getValue(String.class),String.valueOf(dataSnapshot.getKey()));
                    arrayList.add(imagePOJO);

                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getApplicationContext(), "Failed to retrieve data ! ", Toast.LENGTH_SHORT).show();
            }
        });



        adapter = new ImageAdapter(arrayList, pojo -> {

            SweetAlertDialog dialog = new SweetAlertDialog(ImageGalleryActivity.this,SweetAlertDialog.WARNING_TYPE);
            dialog.setTitleText("Confirmation");
            dialog.setContentText("Are you sure want to delete this image");
            dialog.show();
            dialog.setConfirmClickListener(sweetAlertDialog -> {
                Toast.makeText(getApplicationContext(),pojo.getKey()+"", Toast.LENGTH_SHORT).show();

                storageReference = FirebaseStorage.getInstance().getReferenceFromUrl(pojo.getImgURL());
                storageReference.delete().addOnSuccessListener(unused -> {
                    databaseReference.child(pojo.getKey()).removeValue();
                    Toast.makeText(getApplicationContext(), "Image deleted successfully !", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }).addOnFailureListener(e -> {
                    Toast.makeText(getApplicationContext(), "Failed to delete image !", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                });
            });
        });

        recyclerView.setLayoutManager(new MyLinearLayoutManager(getApplicationContext()));

        recyclerView.setAdapter(adapter);

        btn_add_img.setOnClickListener(view -> {
            ImagePicker.with(ImageGalleryActivity.this)
                    .crop()	    			//Crop image(Optional), Check Customization for more option
                    .compress(1024)			//Final image size will be less than 1 MB(Optional)
                    .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                    .start();
        });
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(data.getData() != null)
        {
            progressBar.setVisibility(View.VISIBLE);
            poster_uri = data.getData();
            storageReference = storageReference.child(System.currentTimeMillis()+"."+getFileExtension(poster_uri));
            storageReference.putFile(poster_uri).addOnSuccessListener(taskSnapshot -> {
                progressBar.setProgress(50,true);
                storageReference.getDownloadUrl().addOnSuccessListener(uri -> {
                    progressBar.setProgress(80,true);
                    String key = databaseReference.push().getKey();
                    assert key != null;
                    databaseReference.child(key).setValue(String.valueOf(uri)).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            progressBar.setProgress(100,true);
                            Toast.makeText(getApplicationContext(), "Image uploaded successfully ! ", Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    });
                }).addOnFailureListener(e -> error_check = 1);

            }).addOnFailureListener(e -> error_check = 1);

            if(error_check == 1)
            {
                Toast.makeText(getApplicationContext(), "Failed to upload data ! ", Toast.LENGTH_SHORT).show();
            }
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
