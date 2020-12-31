package com.example.usercollageapplication.admin.faculty;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.usercollageapplication.R;
import com.example.usercollageapplication.admin.Admin;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class AddTeacher extends AppCompatActivity {

    private ImageView AddTeacherImage;
    private EditText AddTeacherName,AddTeacherEmail,AddTeachermobile,AddTeacherPost;
    private Spinner AddTeacherCategory;
    private Button UploadTeacherBtn;
    private String category;
    private final int GET_FROM_GALLERY = 3;
    private Bitmap bitmap=null;
    private Uri uri;
    private String name,email,mobile,post,downloadUrl="";
    private DatabaseReference reference,dbReference;
    private StorageReference storageReference;
    private ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_teacher);


        initialize();

        pd = new ProgressDialog(this);

        reference = FirebaseDatabase.getInstance().getReference().child("teacher");
        storageReference = FirebaseStorage.getInstance().getReference();


        UploadTeacherBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkValidation();
            }
        });


        String[] items = new String[]{"Select Category","Telecommunication Technology","Computer Science Technology","Data Telecommunication Technology","Others Events"};
        AddTeacherCategory.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item,items));

        AddTeacherCategory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                category = AddTeacherCategory.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        AddTeacherImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

    }

    private void checkValidation() {
        name = AddTeacherName.getText().toString();
        email = AddTeacherEmail.getText().toString();
        post = AddTeacherPost.getText().toString();
        mobile= AddTeachermobile.getText().toString();


        if (name.isEmpty()){
            AddTeacherName.setError("Empty");
            AddTeacherName.requestFocus();
        }else  if (email.isEmpty()){
            AddTeacherEmail.setError("Empty");
            AddTeacherEmail.requestFocus();
        }else  if (mobile.isEmpty()){
            AddTeachermobile.setError("Empty");
            AddTeachermobile.requestFocus();
        }else  if (post.isEmpty()){
            AddTeacherPost.setError("Empty");
            AddTeacherPost.requestFocus();
        }else if (category.equals("Select Category")){
            Toast.makeText(this, "Please Provide Teacher Category", Toast.LENGTH_SHORT).show();
        }else if (bitmap==null){
            pd.setMessage("Uploading....");
            pd.setCancelable(false);
            pd.show();
            uploadData();
        }else {
            pd.setMessage("Uploading....");
            pd.setCancelable(false);
            pd.show();
            uploadImage();
        }
    }

    private void uploadData() {
        dbReference = reference.child(category);
        final String uniqueKey = dbReference.push().getKey();

        TeacherData teacherData = new TeacherData(name,email,post,mobile,downloadUrl,uniqueKey);

        dbReference.child(uniqueKey).setValue(teacherData).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                pd.dismiss();
                Toast.makeText(AddTeacher.this, "Teacher Added", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(AddTeacher.this, Admin.class));
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(AddTeacher.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void uploadImage() {
        pd.setMessage("Uploading....");
        pd.setCancelable(false);
        pd.show();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,50,baos);
        byte [] finalimg= baos.toByteArray();
        final StorageReference filepath;
        filepath = storageReference.child("teacher").child(finalimg+"jpg");
        final UploadTask uploadTask = filepath.putBytes(finalimg);
        uploadTask.addOnCompleteListener(AddTeacher.this, new OnCompleteListener<UploadTask.TaskSnapshot>() {
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
                                    uploadData();
                                }
                            });
                        }
                    });
                }else {
                    pd.dismiss();
                    Toast.makeText(AddTeacher.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void openGallery() {
        Intent pickImage = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(Intent.createChooser(pickImage,"Select Image"),GET_FROM_GALLERY);

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==GET_FROM_GALLERY && resultCode == RESULT_OK) {
            assert data != null;
            uri = data.getData();
        }
        try {
            bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
        } catch (IOException e) {
            e.printStackTrace();
        }
        AddTeacherImage.setImageBitmap(bitmap);

    }


    private void initialize() {
        AddTeacherImage = findViewById(R.id.AddTeacherImage);
        AddTeacherName = findViewById(R.id.AddTeacherName);
        AddTeacherEmail = findViewById(R.id.AddTeacherEmail);
        AddTeachermobile = findViewById(R.id.AddTeachermobile);
        AddTeacherPost = findViewById(R.id.AddTeacherPost);
        AddTeacherCategory = findViewById(R.id.AddTeacherCategory);
        UploadTeacherBtn = findViewById(R.id.UploadTeacherBtn);


    }
}