package com.example.usercollageapplication.admin.notice;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class UploadNotice extends AppCompatActivity {
    private  CardView AddImage;
    private EditText noticeTitle;
    private Button UploadNoticeBtn;
    private ImageView noticeImageView;
    private final int GET_FROM_GALLERY = 1;
    private Bitmap bitmap;
    private  Uri noticeData;
    DatabaseReference reference;
    StorageReference storageReference;
    private ProgressDialog pd;
    String downloadUrl = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_notice);

        initialize();
        reference = FirebaseDatabase.getInstance().getReference().child("notice");
        storageReference = FirebaseStorage.getInstance().getReference().child("notice");

        AddImage.setOnClickListener(v -> openGallery());
        UploadNoticeBtn.setOnClickListener(v -> {
            if (noticeTitle.getText().toString().isEmpty()){
                noticeTitle.setError("Empty");
                noticeTitle.requestFocus();
            } else if (bitmap==null){
                uploadData();
            }else
                uploadImage();
        });
    }

    private void initialize() {

        AddImage = findViewById(R.id.AddImage);
        noticeImageView = findViewById(R.id.noticeImageView);
        noticeTitle = findViewById(R.id.noticeTitle);
        UploadNoticeBtn = findViewById(R.id.UploadNoticeBtn);
        pd = new ProgressDialog(this);
    }

    private void uploadImage() {
        pd.setMessage("Uploading....");
        pd.setCancelable(false);
        pd.show();

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,50,baos);
        byte [] finalImg= baos.toByteArray();
        final StorageReference filepath;
        filepath = storageReference.child(finalImg+"jpg");
        final UploadTask uploadTask = filepath.putBytes(finalImg);
        uploadTask.addOnCompleteListener(UploadNotice.this, new OnCompleteListener<UploadTask.TaskSnapshot>() {
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
                    Toast.makeText(UploadNotice.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void uploadData() {
        final String uniqueKey = reference.push().getKey();
        String title = noticeTitle.getText().toString();

        Calendar callForDate=Calendar.getInstance();
        SimpleDateFormat currentDate=new SimpleDateFormat("dd-MM-yy");
        String date=currentDate.format(callForDate.getTime());

        Calendar callForTime=Calendar.getInstance();
        SimpleDateFormat currentTime=new SimpleDateFormat("hh-mm a");
        String time=currentTime.format(callForTime.getTime());

        NoticeData noticeData = new NoticeData(title,downloadUrl,date,time,uniqueKey);
        reference.child(uniqueKey).setValue(noticeData).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isComplete()){
                    pd.dismiss();
                    Toast.makeText(UploadNotice.this, "Notice Added", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(UploadNotice.this, Admin.class));
                }
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(UploadNotice.this, "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void openGallery() {
        Intent pickImage = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickImage,GET_FROM_GALLERY);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==GET_FROM_GALLERY && resultCode == RESULT_OK) {
            assert data != null;
            noticeData = data.getData();
        }
        try {
            bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),noticeData);
        } catch (IOException e) {
            e.printStackTrace();
        }
        noticeImageView.setImageBitmap(bitmap);

    }
}