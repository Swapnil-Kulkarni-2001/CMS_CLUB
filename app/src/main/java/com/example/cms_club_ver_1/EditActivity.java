package com.example.cms_club_ver_1;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.MimeTypeMap;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.bumptech.glide.Glide;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class EditActivity extends AppCompatActivity
{
    @SuppressLint("StaticFieldLeak")
    public static Context EditActivityContext;

    @SuppressLint("StaticFieldLeak")
    public static TextView txt_name;
    @SuppressLint("StaticFieldLeak")
    public static TextView txt_post;
    @SuppressLint("StaticFieldLeak")
    public static TextView txt_phone_no;
    @SuppressLint("StaticFieldLeak")
    public static TextView txt_LinkedIn_URL;
    @SuppressLint("StaticFieldLeak")
    public static TextView txt_Github_URL;
    @SuppressLint("StaticFieldLeak")
    public static TextView txt_Instagram_URL;



    public AppCompatButton button1;
    public AppCompatButton button2;
    public ImageButton button3;
    public AppCompatButton button4;

    public AppCompatButton phoneNo;
    public AppCompatButton linkedIn;
    public AppCompatButton Github;
    public AppCompatButton Instagram;

    public AppCompatButton back;

    public ImageView imageView;
    public int check;
    public int delete_check;

    public ClubMemberPOJO current_member_data;

    public DatabaseReference databaseReference;
    public StorageReference storageReference;

    public Uri uri;
    public static String club_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_activity_for_all_members);

        EditActivityContext = getApplicationContext();

        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(getResources().getColor(R.color.orange));
            changeStatusBarContrastStyle(window,true);
        }




        txt_name = findViewById(R.id.txt_edit_name);
        txt_post = findViewById(R.id.txt_post_name);
        txt_phone_no = findViewById(R.id.txt_phone_no_account);
        txt_LinkedIn_URL = findViewById(R.id.txt_LinkedIn_account);
        txt_Github_URL = findViewById(R.id.txt_Github_account);
        txt_Instagram_URL = findViewById(R.id.txt_Instagram_account);


        button1 = findViewById(R.id.btn_edit);
        button2 = findViewById(R.id.btn_edit_post);
        button3 = findViewById(R.id.btn_edit_profile_img);
        button4 = findViewById(R.id.btn_member_delete);

        phoneNo = findViewById(R.id.btn_edit_phone_no);
        linkedIn = findViewById(R.id.btn_edit_LinkedIn);
        Github = findViewById(R.id.btn_edit_Github);
        Instagram = findViewById(R.id.btn_edit_Instagram);


        back = findViewById(R.id.btn_edit_member_back);


        imageView = findViewById(R.id.profile_pic);

        Intent intent = getIntent();
        check = intent.getIntExtra("CALLED_FROM",0);
        club_id = intent.getStringExtra("club_id");
        current_member_data = (ClubMemberPOJO) intent.getSerializableExtra("current_member_data");

        storageReference = FirebaseStorage.getInstance().getReference().child(club_id).child("members");
        databaseReference = FirebaseDatabase.getInstance().getReference().child("SKCLUB").child(club_id).child("members");

        txt_name.setText(current_member_data.getName());
        txt_post.setText(current_member_data.getPost());
        txt_phone_no.setText(current_member_data.getPhone_no());
        txt_LinkedIn_URL.setText(current_member_data.getLinkedin_account());
        txt_Github_URL.setText(current_member_data.getGithub_account());
        txt_Instagram_URL.setText(current_member_data.getInstagram_account());
        Glide.with(getApplicationContext()).load(current_member_data.getProfile_image()).into(imageView);



        button1.setOnClickListener(view -> {
            BottomSheetDialog bottomSheet = new BottomSheetDialog(check);
            bottomSheet.show(getSupportFragmentManager(), "ModalBottomSheet");
        });

        button2.setOnClickListener(view -> {
            BottomSheetDailogForPost  bottomSheetDailogForPost = new BottomSheetDailogForPost(check);

            bottomSheetDailogForPost.show(getSupportFragmentManager(),"ModalBottomSheet");
        });

        button3.setOnClickListener(view -> ImagePicker.with(EditActivity.this)
                .crop()	    			//Crop image(Optional), Check Customization for more option
                .compress(1024)			//Final image size will be less than 1 MB(Optional)
                .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                .start());

        button4.setOnClickListener(view -> {
            switch (check)
            {
                case 1 :
                    deleteMemberFromFirebase("mentor");
                    break;
                case 2 :
                    deleteMemberFromFirebase("main");
                    break;
                case 3 :
                    deleteMemberFromFirebase("assistant");
                    break;
            }
        });


        phoneNo.setOnClickListener(view -> {
            BottonSheetDailogPhoneNo bottonSheetDailogPhoneNo = new BottonSheetDailogPhoneNo(check);
            bottonSheetDailogPhoneNo.show(getSupportFragmentManager(),"ModalBottomSheet");
        });

        linkedIn.setOnClickListener(view -> {
            BottomSheetDialogLinkedIn bottomSheetDialogLinkedIn = new BottomSheetDialogLinkedIn(check);
            bottomSheetDialogLinkedIn.show(getSupportFragmentManager(),"ModalBottomSheet");
        });


        Github.setOnClickListener(view -> {
            BottomSheetDialogGithub bottomSheetDialogGithub = new BottomSheetDialogGithub(check);
            bottomSheetDialogGithub.show(getSupportFragmentManager(),"ModalBottomSheet");
        });

        Instagram.setOnClickListener(view -> {
            BottomSheetDialogInstagram bottomSheetDialogInstagram = new BottomSheetDialogInstagram(check);
            bottomSheetDialogInstagram.show(getSupportFragmentManager(),"ModalBottomSheet");
        });


        back.setOnClickListener(view -> finish());

    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            uri = data.getData();
        }
        if (uri==null)
        {
            return;
        }
        imageView.setImageURI(uri);


        switch (check)
        {
            case 1 :
                putImageToFirebase("mentor");
                break;
            case 2 :
                putImageToFirebase("main");
                break;
            case 3 :
                putImageToFirebase("assistant");
                break;
        }

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


    void putImageToFirebase(String member_type)
    {
        storageReference = storageReference.child(member_type).child(System.currentTimeMillis()+"."+getFileExtension(uri));
        storageReference.putFile(uri).addOnSuccessListener(taskSnapshot -> storageReference.getDownloadUrl().addOnSuccessListener(uri2 -> {
            storageReference = FirebaseStorage.getInstance().getReferenceFromUrl(current_member_data.getProfile_image());
            storageReference.delete().addOnSuccessListener(unused -> databaseReference.child(member_type).child(current_member_data.getCms_id()).child("profile_image").setValue(String.valueOf(uri2)).addOnSuccessListener(unused1 -> Toast.makeText(getApplicationContext(),"Profile image updated successfully",Toast.LENGTH_SHORT).show()));

        })).addOnFailureListener(e -> Toast.makeText(getApplicationContext(), "Something went wrong!!", Toast.LENGTH_SHORT).show());
    }

    void deleteMemberFromFirebase(String member_type)
    {
        storageReference = FirebaseStorage.getInstance().getReferenceFromUrl(current_member_data.getProfile_image());
        storageReference.delete().addOnSuccessListener(unused -> databaseReference.child(member_type).child(current_member_data.getCms_id()).removeValue().addOnSuccessListener(unused1 -> {
            Toast.makeText(getApplicationContext(), "Member deleted successfully !", Toast.LENGTH_SHORT).show();
            finish();
        }).addOnFailureListener(e -> delete_check = 1)).addOnFailureListener(e -> delete_check = 1);

        if (delete_check == 1)
        {
            Toast.makeText(getApplicationContext(), "Something went wrong !", Toast.LENGTH_SHORT).show();
        }
    }

}
