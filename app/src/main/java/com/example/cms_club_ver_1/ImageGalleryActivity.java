package com.example.cms_club_ver_1;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.RecyclerView;

import com.github.dhaval2404.imagepicker.ImagePicker;

import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class ImageGalleryActivity extends AppCompatActivity
{

    public ArrayList<ImagePOJO> arrayList;
    public RecyclerView recyclerView;
    public ImageAdapter adapter;
    public AppCompatButton btn_add_img;
    public Intent intent;
    public String called_from;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.image_gallery);

        intent = getIntent();
        arrayList = new ArrayList<>();
        recyclerView = findViewById(R.id.rv_img);
        btn_add_img = findViewById(R.id.btn_img_add);

        called_from = intent.getStringExtra("event_name");


        arrayList.add(new ImagePOJO("https://images.unsplash.com/photo-1624555130581-1d9cca783bc0?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1171&q=80"));
        arrayList.add(new ImagePOJO("https://images.unsplash.com/photo-1587691592099-24045742c181?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1173&q=80"));
        arrayList.add(new ImagePOJO("https://images.unsplash.com/photo-1617854818583-09e7f077a156?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=1170&q=80"));
        arrayList.add(new ImagePOJO("https://images.unsplash.com/photo-1591154669695-5f2a8d20c089?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=687&q=80"));

        adapter = new ImageAdapter(arrayList, getApplicationContext(), new OnImageClickListener() {
            @Override
            public void onLongClicked(ImagePOJO pojo) {

                SweetAlertDialog dialog = new SweetAlertDialog(ImageGalleryActivity.this,SweetAlertDialog.WARNING_TYPE);
                dialog.setTitleText("Confirmation");
                dialog.setContentText("Are you sure want to delete this image");
                dialog.show();
                dialog.setConfirmClickListener(sweetAlertDialog -> {

                    //remove image from firebase
                    //use pojo.getImgURL() to get the url to delete from firebase
                    dialog.dismiss();
                    Toast.makeText(getApplicationContext(),pojo.getImgURL(),Toast.LENGTH_SHORT).show();
                    arrayList.remove(pojo);
                    recyclerView.setAdapter(adapter);

                });
            }
        });

        //recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setLayoutManager(new MyLinearLayoutManager(getApplicationContext()));

        recyclerView.setAdapter(adapter);
        //adapter.filterList(arrayList);

        btn_add_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImagePicker.with(ImageGalleryActivity.this)
                        .crop()	    			//Crop image(Optional), Check Customization for more option
                        .compress(1024)			//Final image size will be less than 1 MB(Optional)
                        .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                        .start();
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Uri uri = data.getData();
        // store image in firebase

    }
}
