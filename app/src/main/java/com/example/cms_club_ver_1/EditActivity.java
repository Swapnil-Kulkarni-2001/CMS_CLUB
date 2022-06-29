package com.example.cms_club_ver_1;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.github.dhaval2404.imagepicker.ImagePicker;

import java.util.Objects;

public class EditActivity extends AppCompatActivity
{
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
    public AppCompatButton button3;
    public AppCompatButton button4;

    public AppCompatButton phoneNo;
    public AppCompatButton linkedIn;
    public AppCompatButton Github;
    public AppCompatButton Instagram;

    public AppCompatButton back;

    public AppCompatButton SAVE;
    public ImageView imageView;
    public int check;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_activity_for_all_members);

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

        SAVE = findViewById(R.id.btn_save_all);

        back = findViewById(R.id.btn_edit_member_back);


        imageView = findViewById(R.id.profile_pic);

        Intent intent = getIntent();
        check = intent.getIntExtra("CALLED_FROM",0);


        button1.setOnClickListener(view -> {
            Toast.makeText(getApplicationContext(),"Clicked",Toast.LENGTH_SHORT).show();
            BottomSheetDialog bottomSheet = new BottomSheetDialog(check);
            bottomSheet.show(getSupportFragmentManager(), "ModalBottomSheet");
        });

        button2.setOnClickListener(view -> {
            Toast.makeText(getApplicationContext(),"Clicked",Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(getApplicationContext(),"Delete member from mentor board",Toast.LENGTH_SHORT).show();
                    //Delete member from mentor board
                    break;
                case 2 :
                    Toast.makeText(getApplicationContext(),"Delete member from main board",Toast.LENGTH_SHORT).show();
                    //Delete member from main board
                    break;
                case 3 :
                    Toast.makeText(getApplicationContext(),"Delete member from Assistant board",Toast.LENGTH_SHORT).show();
                    //Delete member from Assistant board
                    break;
            }
        });


        phoneNo.setOnClickListener(view -> {
            BottonSheetDailogPhoneNo bottonSheetDailogPhoneNo = new BottonSheetDailogPhoneNo();
            bottonSheetDailogPhoneNo.show(getSupportFragmentManager(),"ModalBottomSheet");
        });

        linkedIn.setOnClickListener(view -> {
            BottomSheetDialogLinkedIn bottomSheetDialogLinkedIn = new BottomSheetDialogLinkedIn();
            bottomSheetDialogLinkedIn.show(getSupportFragmentManager(),"ModalBottomSheet");
        });


        Github.setOnClickListener(view -> {
            BottomSheetDialogGithub bottomSheetDialogGithub = new BottomSheetDialogGithub();
            bottomSheetDialogGithub.show(getSupportFragmentManager(),"ModalBottomSheet");
        });

        Instagram.setOnClickListener(view -> {
            BottomSheetDialogInstagram bottomSheetDialogInstagram = new BottomSheetDialogInstagram();
            bottomSheetDialogInstagram.show(getSupportFragmentManager(),"ModalBottomSheet");
        });


        SAVE.setOnClickListener(view -> {
            String final_name = BottomSheetDialog.member_name;
            String final_post = BottomSheetDailogForPost.member_post;
            String final_phone = BottonSheetDailogPhoneNo.member_phone_no;
            String final_linkedIn_URL = BottomSheetDialogLinkedIn.member_LinkedIn;
            String final_github_URL = BottomSheetDialogGithub.member_Github;
            String final_instagram_URL = BottomSheetDialogInstagram.member_Instagram;
            Toast.makeText(getApplicationContext(),final_name+" "+final_post+" "+final_phone+" "+final_linkedIn_URL+
                    " "+final_github_URL+" "+final_instagram_URL,Toast.LENGTH_SHORT).show();
        });


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Uri uri = Objects.requireNonNull(data).getData();
        imageView.setImageURI(uri);
        switch (check)
        {
            case 1 :
                Toast.makeText(getApplicationContext(),"save img to firebase for mentor board",Toast.LENGTH_SHORT).show();
                //save img to firebase for mentor board
                break;
            case 2 :
                Toast.makeText(getApplicationContext(),"save img to firebase for main board",Toast.LENGTH_SHORT).show();
                //save img to firebase for main board
                break;
            case 3 :
                Toast.makeText(getApplicationContext(),"save img to firebase for Assistant board",Toast.LENGTH_SHORT).show();
                //save img to firebase for Assistant board
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

}
