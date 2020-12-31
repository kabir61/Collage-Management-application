package com.example.usercollageapplication.admin.faculty;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.usercollageapplication.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;

public class UpdateTeacher extends AppCompatActivity {

    private EditText updateTeacherName,updateTeacherEmail,updateTeacherMobile,updateTeacherPost;
    private ImageView updateTeacherImage;
    private Button updateTeacherBtn,deleteTeacherBtn;
    private String name,email,post,mobile,image;
    private final int GET_FROM_GALLERY = 3;
    private Bitmap bitmap;
    private Uri uri;
    private StorageReference storageReference;
    private DatabaseReference reference;
    private  String downloadUrl,uniqueKey,category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_teacher);

        name = getIntent().getStringExtra("name");
        email = getIntent().getStringExtra("email");
        post = getIntent().getStringExtra("post");
        mobile =getIntent().getStringExtra("mobile");
        image = getIntent().getStringExtra("image");

        uniqueKey = getIntent().getStringExtra("key");
        category = getIntent().getStringExtra("category");


        initialize();

        reference = FirebaseDatabase.getInstance().getReference().child("teacher");
        storageReference = FirebaseStorage.getInstance().getReference();
        try {
            Picasso.with(UpdateTeacher.this).load(image).into(updateTeacherImage);
        } catch (Exception e) {
            e.printStackTrace();
        }

        updateTeacherName.setText(name);
        updateTeacherEmail.setText(email);
        updateTeacherPost.setText(post);
        updateTeacherMobile.setText(mobile);


        updateTeacherImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

        updateTeacherBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkValidation();
            }
        });

        deleteTeacherBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteData();
            }
        });

    }

    private void checkValidation() {
        name = updateTeacherName.getText().toString();
        email = updateTeacherEmail.getText().toString();
        post = updateTeacherPost.getText().toString();
        mobile=updateTeacherMobile.getText().toString();

        if (name.isEmpty()){
            updateTeacherName.setError("Empty");
            updateTeacherName.requestFocus();
        }else  if (email.isEmpty()){
            updateTeacherEmail.setError("Empty");
            updateTeacherEmail.requestFocus();
        }else  if (mobile.isEmpty()){
            updateTeacherMobile.setError("Empty");
            updateTeacherMobile.requestFocus();
        }else  if (post.isEmpty()){
            updateTeacherPost.setError("Empty");
            updateTeacherPost.requestFocus();
        }else if (bitmap==null){
            uploadData(image);
        }else {
            uploadImage();
        }
    }

    private void uploadData(String s) {

        HashMap<String, Object> hm = new HashMap<>();

        hm.put("name",name);
        hm.put("email",email);
        hm.put("post",post);
        hm.put("mobile",mobile);
        hm.put("image",s);

        reference.child(category).child(uniqueKey).updateChildren(hm)
                .addOnSuccessListener(new OnSuccessListener() {
                    @Override
                    public void onSuccess(Object o) {
                        Toast.makeText(UpdateTeacher.this, "Teacher Data Updated", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(UpdateTeacher.this,UpdateFaculty.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Toast.makeText(UpdateTeacher.this, "Something went wrong", Toast.LENGTH_SHORT).show();

            }
        });

    }


    private void  uploadImage(){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,50,baos);
        byte [] finalimg= baos.toByteArray();
        final StorageReference filepath;
        filepath = storageReference.child("teacher").child(finalimg+"jpg");
        final UploadTask uploadTask = filepath.putBytes(finalimg);
        uploadTask.addOnCompleteListener(UpdateTeacher.this, new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if (task.isSuccessful()){
                    uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            filepath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    downloadUrl = String.valueOf(uri);
                                    uploadData(downloadUrl);
                                }
                            });
                        }
                    });
                }else {
                    Toast.makeText(UpdateTeacher.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void deleteData() {

        reference.child(category).child(uniqueKey).removeValue()
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Toast.makeText(UpdateTeacher.this, "Teacher Data Deleted", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(UpdateTeacher.this,UpdateFaculty.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(UpdateTeacher.this,e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void openGallery() {
        Intent pickImage = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(Intent.createChooser(pickImage, "Select Image"), GET_FROM_GALLERY);
    }

    @Override
    protected void onActivityResult ( int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GET_FROM_GALLERY && resultCode == RESULT_OK) {
            assert data != null;
            uri = data.getData();
        }
        try {
            bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
        } catch (IOException e) {
            e.printStackTrace();
        }
        updateTeacherImage.setImageBitmap(bitmap);

    }


    private void initialize() {

        updateTeacherName = findViewById(R.id.updateTeacherName);
        updateTeacherPost = findViewById(R.id.updateTeacherPost);
        updateTeacherMobile=findViewById(R.id.updateTeacherMobile);
        updateTeacherEmail = findViewById(R.id.updateTeacherEmail);
        updateTeacherImage = findViewById(R.id.updateTeacherImage);
        updateTeacherBtn = findViewById(R.id.updateTeacherBtn);
        deleteTeacherBtn = findViewById(R.id.deleteTeacherBtn);
    }
}