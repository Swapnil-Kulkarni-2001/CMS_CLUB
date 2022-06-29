package com.example.cms_club_ver_1;

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
import android.webkit.URLUtil;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Objects;


public class MainBoardAddPage extends AppCompatActivity
{
    public AppCompatButton btn_back;
    public ImageButton btn_add_image;
    public AppCompatButton btn_save;
    public ImageView profile;
    public EditText ed_new_member_name;
    public EditText ed_new_member_post;
    public EditText ed_new_member_phone_no;
    public EditText ed_new_member_linkedin_url;
    public EditText ed_new_member_github_url;
    public EditText ed_new_member_instagram_url;
    public DatabaseReference databaseReference;
    public StorageReference storageReference;
    public String club_id;
    public Uri uri = null;

    String name;
    String post;
    String phone_no;
    String linkedin_url;
    String github_url;
    String instagram_url;

    int check;
    int error_check = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_board_add_page);

        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(getResources().getColor(R.color.orange));
            changeStatusBarContrastStyle(window,true);
        }

        btn_add_image = findViewById(R.id.btn_add_image);
        profile = findViewById(R.id.profile_pic);
        btn_save = findViewById(R.id.btn_save);
        btn_back = findViewById(R.id.btn_member_add_back);

        ed_new_member_name = findViewById(R.id.ed_new_member_name);
        ed_new_member_post = findViewById(R.id.ed_new_member_post);
        ed_new_member_phone_no = findViewById(R.id.ed_new_phone_no_add);
        ed_new_member_linkedin_url = findViewById(R.id.ed_new_linkedin_url);
        ed_new_member_github_url = findViewById(R.id.ed_new_github_url);
        ed_new_member_instagram_url = findViewById(R.id.ed_new_instagram_url);

        Intent intent = getIntent();
        check = intent.getIntExtra("CALLED_FROM",0);
        club_id = intent.getStringExtra("club_id");

        databaseReference = FirebaseDatabase.getInstance().getReference().child("SKCLUB").child(club_id).child("members");
        storageReference = FirebaseStorage.getInstance().getReference().child(club_id).child("members");


        btn_add_image.setOnClickListener(view -> ImagePicker.with(MainBoardAddPage.this)
                .crop()	    			//Crop image(Optional), Check Customization for more option
                .compress(1024)			//Final image size will be less than 1 MB(Optional)
                .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                .start());

        btn_save.setOnClickListener(view -> {

            int field_check = 0;
            name = ed_new_member_name.getText().toString();
            post = ed_new_member_post.getText().toString();
            phone_no = ed_new_member_phone_no.getText().toString();
            linkedin_url = ed_new_member_linkedin_url.getText().toString();
            github_url = ed_new_member_github_url.getText().toString();
            instagram_url = ed_new_member_instagram_url.getText().toString();

            if(TextUtils.isEmpty(name))
            {
                ed_new_member_name.setError("Empty field not allowed !");
                field_check = 1;
            }
            if(TextUtils.isEmpty(post))
            {
                ed_new_member_post.setError("Empty field not allowed !");
                field_check = 1;
            }
            if(TextUtils.isEmpty(phone_no))
            {
                ed_new_member_phone_no.setError("Empty field not allowed !");
                field_check = 1;
            }
            if(TextUtils.isEmpty(linkedin_url) || (!URLUtil.isValidUrl(linkedin_url)))
            {
                ed_new_member_linkedin_url.setError("Empty field not allowed !");
                field_check = 1;
            }
            if(TextUtils.isEmpty(github_url) || (!URLUtil.isValidUrl(github_url)))
            {
                ed_new_member_github_url.setError("Empty field not allowed !");
                field_check = 1;
            }
            if(TextUtils.isEmpty(instagram_url) || (!URLUtil.isValidUrl(instagram_url)))
            {
                ed_new_member_instagram_url.setError("Empty field not allowed !");
                field_check = 1;
            }
            if(uri == null)
            {
                field_check = 1;
                Toast.makeText(getApplicationContext(), "Profile image not selected", Toast.LENGTH_SHORT).show();
            }
            if (field_check == 1)
            {
                return;
            }


            //firebase code to add member details to database
            Toast.makeText(getApplicationContext(),"Clicked",Toast.LENGTH_SHORT).show();
            switch (check)
            {
                case 1 :
                    pushDataToFirebase("mentor");
                    break;
                case 2 :
                    pushDataToFirebase("main");
                    break;
                case 3 :
                    pushDataToFirebase("assistant");
                    break;
            }
        });

        btn_back.setOnClickListener(view -> finish());

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data != null) {
            uri = data.getData();
            profile.setImageURI(uri);
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

    public void pushDataToFirebase(String member_type)
    {

        storageReference = storageReference.child(member_type).child(System.currentTimeMillis()+"."+getFileExtension(uri));
        storageReference.putFile(uri).addOnSuccessListener(taskSnapshot -> storageReference.getDownloadUrl().addOnSuccessListener(uri -> {
            String cms_id = databaseReference.child(member_type).push().getKey();
            ClubMemberPOJO data = new ClubMemberPOJO();
            data.setCms_id(cms_id);
            data.setProfile_image(uri.toString());
            data.setName(name);
            data.setPost(post);
            data.setPhone_no(phone_no);
            data.setLinkedin_account(linkedin_url);
            data.setGithub_account(github_url);
            data.setInstagram_account(instagram_url);
            databaseReference.child(member_type).child(Objects.requireNonNull(cms_id)).setValue(data).addOnSuccessListener(unused -> {
                Toast.makeText(getApplicationContext(), "Member added successfully !", Toast.LENGTH_SHORT).show();
                finish();
            }).addOnFailureListener(e -> error_check = 1);
        }).addOnFailureListener(e -> error_check = 1)).addOnFailureListener(e -> error_check = 1);

        if (error_check == 1)
        {
            Toast.makeText(getApplicationContext(), "Something went wrong !", Toast.LENGTH_SHORT).show();
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
