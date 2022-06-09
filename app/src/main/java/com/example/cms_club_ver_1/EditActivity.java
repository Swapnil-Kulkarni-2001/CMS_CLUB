package com.example.cms_club_ver_1;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.github.dhaval2404.imagepicker.ImagePicker;

public class EditActivity extends AppCompatActivity
{
    public TextView txtname;
    public TextView txtpost;
    public AppCompatButton button1;
    public AppCompatButton button2;
    public AppCompatButton button3;
    public AppCompatButton button4;
    public ImageView imageView;
    public int check;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_activity_for_all_members);
        txtname = findViewById(R.id.txt_edit_name);
        txtpost = findViewById(R.id.txt_post_name);

        button1 = findViewById(R.id.btn_edit);
        button2 = findViewById(R.id.btn_edit_post);
        button3 = findViewById(R.id.btn_edit_profile_img);
        button4 = findViewById(R.id.btn_member_delete);

        imageView = findViewById(R.id.profile_pic);

        Intent intent = getIntent();
        check = intent.getIntExtra("CALLED_FROM",0);


        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"Clicked",Toast.LENGTH_SHORT).show();
                BottomSheetDialog bottomSheet = new BottomSheetDialog(check);
                bottomSheet.show(getSupportFragmentManager(), "ModalBottomSheet");
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(),"Clicked",Toast.LENGTH_SHORT).show();
                BottomSheetDailogForPost  bottomSheetDailogForPost = new BottomSheetDailogForPost(check);

                bottomSheetDailogForPost.show(getSupportFragmentManager(),"ModalBottomSheet");
            }
        });

        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ImagePicker.with(EditActivity.this)
                        .crop()	    			//Crop image(Optional), Check Customization for more option
                        .compress(1024)			//Final image size will be less than 1 MB(Optional)
                        .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                        .start();
            }
        });

        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Uri uri = data.getData();
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
}
