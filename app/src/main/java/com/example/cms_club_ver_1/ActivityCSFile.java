package com.example.cms_club_ver_1;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class ActivityCSFile extends AppCompatActivity
{

    public RecyclerView recyclerView;
    public ImageButton btn_add_file;
    public ProgressBar progressBar;
    public CSFileRowAdapter adapter;

    public CSPOJO cs_data;

    @SuppressLint("StaticFieldLeak")
    public static Context ActivityCSFileContext;
    public ArrayList<CSFilePOJO> arrayList;

    public long file_size;
    public DatabaseReference databaseReference;
    public StorageReference storageReference;

    public Object systemService;

    @SuppressLint("ObsoleteSdkInt")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cs_file);

        ActivityCSFileContext = getApplicationContext();
        systemService = getSystemService(DOWNLOAD_SERVICE);

        if (Build.VERSION.SDK_INT >= 21) {
            @SuppressLint("UseRequireInsteadOfGet") Window window = this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(getResources().getColor(R.color.white));
            changeStatusBarContrastStyle(window,false);
        }


        arrayList = new ArrayList<>();

        btn_add_file = findViewById(R.id.btn_add_file);
        recyclerView = findViewById(R.id.rv_cs_files);
        progressBar = findViewById(R.id.progressBar3);
        progressBar.setMax(100);

        Intent intent = getIntent();
        cs_data = (CSPOJO) intent.getSerializableExtra("cs_data");


        databaseReference = FirebaseDatabase.getInstance().getReference().child("SKCLUB").child(fragment_service.club_id).child("club_services").child(cs_data.getCms_id()).child("cs_files");

        storageReference = FirebaseStorage.getInstance().getReference().child(fragment_service.club_id).child("club_services");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                arrayList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren())
                {
                    CSFilePOJO file_data = new CSFilePOJO();
                    file_data.setFile_url(String.valueOf(dataSnapshot.getValue()));
                    file_data.setKey(String.valueOf(dataSnapshot.getKey()));
                    storageReference = FirebaseStorage.getInstance().getReferenceFromUrl(String.valueOf(dataSnapshot.getValue()));
                    String str = storageReference.getName();
                    String separator ="#";
                    int sepPos = str.indexOf(separator);
                    String file_name = str.substring(sepPos+separator.length());
                    file_data.setFile_name(file_name);
                    storageReference.getMetadata().addOnSuccessListener(storageMetadata -> file_data.setFile_size(file_size+"Bytes"));
                    arrayList.add(file_data);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        adapter = new CSFileRowAdapter(arrayList, systemService, csFilePOJO -> {
            Toast.makeText(getApplicationContext(), "Long clicked", Toast.LENGTH_SHORT).show();

            SweetAlertDialog dialog = new SweetAlertDialog(ActivityCSFile.this,SweetAlertDialog.WARNING_TYPE);
            dialog.setTitleText("Confirmation");
            dialog.setContentText("Are you sure want to delete this image");
            dialog.show();
            dialog.setConfirmClickListener(sweetAlertDialog -> {

                storageReference = FirebaseStorage.getInstance().getReferenceFromUrl(csFilePOJO.getFile_url());
                //
                storageReference.delete().addOnSuccessListener(unused -> databaseReference.child(csFilePOJO.getKey()).removeValue().addOnSuccessListener(unused1 -> {
                    Toast.makeText(getApplicationContext(), "File deleted successfully ! ", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                }).addOnFailureListener(e -> Toast.makeText(getApplicationContext(), "Something went wrong !", Toast.LENGTH_SHORT).show()));
            });

        });

        recyclerView.setAdapter(adapter);



        btn_add_file.setOnClickListener(this::openFileDialog);

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



    ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult()
            , new ActivityResultCallback<ActivityResult>() {
                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        if (data == null) {
                            return;
                        }
                        Uri uri = data.getData();
                        //File file = new File(uri.getPath());
                        progressBar.setVisibility(View.VISIBLE);
                        String file_name = queryName(getContentResolver(),uri);
                        Toast.makeText(ActivityCSFile.this, uri.getPath(), Toast.LENGTH_SHORT).show();
                        storageReference = storageReference.child(System.currentTimeMillis() + "#" + file_name);
                        storageReference.putFile(uri).addOnSuccessListener(taskSnapshot -> {
                            progressBar.setProgress(50,true);
                            taskSnapshot.getStorage().getDownloadUrl().addOnSuccessListener(uri1 -> {
                                progressBar.setProgress(70,true);
                                String key = databaseReference.push().getKey();
                                assert key != null;
                                databaseReference.child(key).setValue(String.valueOf(uri1)).addOnSuccessListener(unused -> {
                                    progressBar.setProgress(100,true);
                                    progressBar.setVisibility(View.GONE);
                                    Toast.makeText(ActivityCSFile.this, "File uploaded successfully !", Toast.LENGTH_SHORT).show();
                                }).addOnFailureListener(e -> Toast.makeText(ActivityCSFile.this, "Failed !", Toast.LENGTH_SHORT).show());
                            }).addOnFailureListener(e -> Toast.makeText(ActivityCSFile.this, "Failed !", Toast.LENGTH_SHORT).show());
                        }).addOnFailureListener(e -> Toast.makeText(ActivityCSFile.this, "Failed !", Toast.LENGTH_SHORT).show());
                    }
                }
            });


    public void openFileDialog(View view)
    {
        Intent data = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        data.setType("*/*");
        data = Intent.createChooser(data,"Choose a file");
        activityResultLauncher.launch(data);
    }



    private String queryName(ContentResolver resolver, Uri uri) {
        Cursor returnCursor =
                resolver.query(uri, null, null, null, null);
        assert returnCursor != null;
        int nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
        returnCursor.moveToFirst();
        String name = returnCursor.getString(nameIndex);
        returnCursor.close();
        return name;
    }

}
