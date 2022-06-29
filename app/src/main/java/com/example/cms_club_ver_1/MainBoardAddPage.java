package com.example.cms_club_ver_1;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.github.dhaval2404.imagepicker.ImagePicker;

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

    public Uri uri;

    int check;

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

        btn_add_image.setOnClickListener(view -> ImagePicker.with(MainBoardAddPage.this)
                .crop()	    			//Crop image(Optional), Check Customization for more option
                .compress(1024)			//Final image size will be less than 1 MB(Optional)
                .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                .start());

        btn_save.setOnClickListener(view -> {

            String name = ed_new_member_name.getText().toString();
            String post = ed_new_member_post.getText().toString();
            String phone_no = ed_new_member_phone_no.getText().toString();
            String linkedin_url = ed_new_member_linkedin_url.getText().toString();
            String github_url = ed_new_member_github_url.getText().toString();
            String instagram_url = ed_new_member_instagram_url.getText().toString();

            //firebase code to add member details to database
            Toast.makeText(getApplicationContext(),"Clicked",Toast.LENGTH_SHORT).show();
            switch (check)
            {
                case 1 :
                    Toast.makeText(getApplicationContext(),"Mentor Board "+name+" "+post+" "+phone_no+" "+linkedin_url+" "+github_url+" "+instagram_url+"",Toast.LENGTH_SHORT).show();
                    //save data to firebase for mentor board
                    break;
                case 2 :
                    Toast.makeText(getApplicationContext(),"Main Board "+name+" "+post+" "+phone_no+" "+linkedin_url+" "+github_url+" "+instagram_url+"",Toast.LENGTH_SHORT).show();
                    //save data to firebase for main board
                    break;
                case 3 :
                    Toast.makeText(getApplicationContext(),"Assistant Board "+name+" "+post+" "+phone_no+" "+linkedin_url+" "+github_url+" "+instagram_url+"",Toast.LENGTH_SHORT).show();
                    //save data to firebase for Assistant board
                    break;
            }
        });

        btn_back.setOnClickListener(view -> finish());

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        uri = Objects.requireNonNull(data).getData();
        profile.setImageURI(uri);
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
