package com.example.cms_club_ver_1;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.github.dhaval2404.imagepicker.ImagePicker;


public class MainBoardAddPage extends AppCompatActivity
{
    public AppCompatButton btn_add_image;
    public AppCompatButton btn_save;
    public ImageView profile;
    public EditText ed_new_member_name;
    public EditText ed_new_member_post;
    int check;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_board_add_page);
        btn_add_image = findViewById(R.id.btn_add_image);
        profile = findViewById(R.id.profile_pic);
        btn_save = findViewById(R.id.btn_save);
        ed_new_member_name = findViewById(R.id.ed_new_member_name);
        ed_new_member_post = findViewById(R.id.ed_new_member_psot);

        Intent intent = getIntent();
        check = intent.getIntExtra("CALLED_FROM",0);

        btn_add_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImagePicker.with(MainBoardAddPage.this)
                        .crop()	    			//Crop image(Optional), Check Customization for more option
                        .compress(1024)			//Final image size will be less than 1 MB(Optional)
                        .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                        .start();
            }
        });

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //firebase code to add member details to database
                Toast.makeText(getApplicationContext(),"Clicked",Toast.LENGTH_SHORT).show();
                switch (check)
                {
                    case 1 :
                        Toast.makeText(getApplicationContext(),"save data to firebase for mentor board",Toast.LENGTH_SHORT).show();
                        //save data to firebase for mentor board
                        break;
                    case 2 :
                        Toast.makeText(getApplicationContext(),"data img to firebase for main board",Toast.LENGTH_SHORT).show();
                        //save data to firebase for main board
                        break;
                    case 3 :
                        Toast.makeText(getApplicationContext(),"data img to firebase for Assistant board",Toast.LENGTH_SHORT).show();
                        //save data to firebase for Assistant board
                        break;
                }
            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Uri uri = data.getData();
        profile.setImageURI(uri);
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
}
