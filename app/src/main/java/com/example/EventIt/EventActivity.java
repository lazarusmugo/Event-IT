package com.example.EventIt;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.net.Uri;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;


public class EventActivity extends AppCompatActivity{
    private static final int PICK_IMAGE_REQUEST = 1;
    private Button mButtonChooseImage, ButtonUpload;
    private TextView mTextViewShowUploads;
    private EditText mEditTextFileName,EventDescription;
    private ImageView mImageview;
    private Uri mImageUri;
    private Button Location;
    private Spinner Category;


    Double  Latitude = MapsActivity.getLatDouble();
    Double  Longitude = MapsActivity.getLangDouble();


    Double  latitude = MapsActivity.getLatDouble();
    Double  longitude = MapsActivity.getLangDouble();


private static final int IMAGE_PICK_CODE = 1000;
    private static final int PERMISSION_CODE = 1001;


    private StorageReference mStorageRef;
    private DatabaseReference mDatabaseRef;

    private StorageTask mUploadTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {



        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);


        mButtonChooseImage = findViewById(R.id.btnChooseImage);
        mEditTextFileName = findViewById(R.id.tvFileName);
        mImageview = findViewById(R.id.imageView4);
       EventDescription = findViewById(R.id.edtEventDescription);
        ButtonUpload = findViewById(R.id.btnUpload);

        mStorageRef = FirebaseStorage.getInstance().getReference("uploads");
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("uploads");

        mButtonChooseImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                            == PackageManager.PERMISSION_DENIED) {
                        String[] permissions = {Manifest.permission.READ_EXTERNAL_STORAGE};
                        requestPermissions(permissions, PERMISSION_CODE);
                    } else {
                        //permission already granted
                        pickImageFromGallery();
                    }
                } else {
                    //system os is less then marshmallow
                    pickImageFromGallery();

                }
            }

        });

        ButtonUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mUploadTask != null && mUploadTask.isInProgress()){
Toast.makeText(EventActivity.this, "Upload in Progress", Toast.LENGTH_SHORT).show();
                }else{
               uploadFile();

            }}
        });

      Category = findViewById(R.id.spnCategory);
        String[] trial = getResources().getStringArray(R.array.Categories);
        ArrayAdapter adapter = new ArrayAdapter(this,
                android.R.layout.simple_spinner_item, trial);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        Category.setAdapter(adapter);


       Location = findViewById(R.id.btnLocation);
       Location.setOnClickListener(  new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(EventActivity.this, MapsActivity.class);
                startActivity(intent);
            }
        });


    }
//gets the extension type of an image
    private String getFileExtension(Uri uri){
        ContentResolver cR = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cR.getType(uri));
    }



private void uploadFile(){

if(mImageUri != null){
StorageReference fileReference = mStorageRef.child(System.currentTimeMillis()
+ "." +getFileExtension(mImageUri));
 mUploadTask = fileReference.putFile(mImageUri)
        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> downloadUri=fileReference.getDownloadUrl();
                Task<Uri> pic=fileReference.getDownloadUrl();
                Toast.makeText(EventActivity.this, "upload successful",
                        Toast.LENGTH_LONG).show();

                Uploads upload = new Uploads(mEditTextFileName.getText().toString().trim(),
                        pic.toString(),Category.toString(),
                        EventDescription.getText().toString(),Latitude.doubleValue(),Longitude.doubleValue() );

                String uploadId = mDatabaseRef.push().getKey();
                mDatabaseRef.child(uploadId).setValue(upload);

            }
        })
        .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

                Toast.makeText(EventActivity.this, "Failed",
                        Toast.LENGTH_SHORT).show();

            }
        });
}else{
    Toast.makeText(this, "No file selected",
            Toast.LENGTH_SHORT).show();
}
}

    private void pickImageFromGallery() {
        //intent to pick image
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, IMAGE_PICK_CODE);
    }

    //handle result of runtime permission
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSION_CODE: {
                if (grantResults.length > 0 && grantResults[0] ==
                        PackageManager.PERMISSION_GRANTED) {
                    //permission was granted
                    pickImageFromGallery();
                } else {
                    //permission was denied
                    Toast.makeText(this, "Permission denied...!", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    //handle result of picked image
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == IMAGE_PICK_CODE) {
            //set image to image view
            mImageUri=data.getData();
            mImageview.setImageURI(mImageUri);
                    }
    }

}