package com.example.usercollageapplication.admin.uploadPdf;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.OpenableColumns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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

import java.io.File;
import java.util.HashMap;

public class UploadPDF extends AppCompatActivity {
    private CardView AddPDF;
    private String pdfName, title;
    private TextView pdfTextView;
    private EditText pdfTitle;
    private Button UploadPdfBtn;
    private final int GET_FROM_GALLERY = 3;
    private Uri pdfData;
    private DatabaseReference databaseReference;
    private StorageReference storageReference;
    private ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_p_d_f);

        insert();

        AddPDF.setOnClickListener(v -> openGallery());
        UploadPdfBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title = pdfTitle.getText().toString();
                if (title.isEmpty()){
                    pdfTitle.setError("Empty");
                    pdfTitle.requestFocus();
                }else if (pdfData == null){
                    Toast.makeText(UploadPDF.this, "Please Upload Pdf", Toast.LENGTH_SHORT).show();
                }else {
                    upLoadPdf();
                }
            }
        });

    }

    private void upLoadPdf() {
        pd.setTitle("Please Wait....");
        pd.setMessage("Pdf Loading....");
        pd.setCancelable(false);
        pd.show();
        StorageReference reference = storageReference.child("pdf/"+pdfName+"/"+System.currentTimeMillis()+".pdf");
        reference.putFile(pdfData)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                        while (!uriTask.isComplete());
                        Uri uri = uriTask.getResult();
                        uploadData(String.valueOf(uri));
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(UploadPDF.this, "something went wrong", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void uploadData(String downloadUrl) {
        String uniqueKey = databaseReference.child("pdf").push().getKey();

        HashMap data = new HashMap();
        data.put("name",title);
        data.put("pdfUrI",downloadUrl);

        databaseReference.child("pdf").child(uniqueKey).setValue(data).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                pd.dismiss();
                Toast.makeText(UploadPDF.this, "Pdf Uploaded Successfully", Toast.LENGTH_SHORT).show();
                pdfTitle.setText("");
                startActivity(new Intent(UploadPDF.this, Admin.class));

            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                pd.dismiss();
                Toast.makeText(UploadPDF.this, "Failed to upload Pdf", Toast.LENGTH_SHORT).show();
            }
        });


    }

    private void openGallery() {
        Intent intent = new Intent();
        intent.setType("pdf/docs/ppt");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Pdf File"),GET_FROM_GALLERY);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==GET_FROM_GALLERY && resultCode == RESULT_OK) {
            pdfData = data.getData();

            if (pdfData.toString().startsWith("content://")){
                Cursor cursor = null;
                try {
                    cursor = UploadPDF.this.getContentResolver().query(pdfData,null,null,null,null);
                    if (cursor != null && cursor.moveToFirst()){
                        pdfName = cursor.getString(cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }else if (pdfData.toString().startsWith("file://")){
                pdfName = new File(pdfData.toString()).getName();

            }

            pdfTextView.setText(pdfName);
        }

    }


    private void insert() {
        databaseReference = FirebaseDatabase.getInstance().getReference();
        storageReference = FirebaseStorage.getInstance().getReference();

        AddPDF = findViewById(R.id.AddPDF);
        pdfTitle = findViewById(R.id.pdfTitle);
        UploadPdfBtn = findViewById(R.id.UploadPdfBtn);
        pdfTextView = findViewById(R.id.pdfTextView);

        pd = new ProgressDialog(this);
    }
}