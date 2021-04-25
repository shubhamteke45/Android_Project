package com.example.android_project;

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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.UUID;

public class AddNewItemProducer extends AppCompatActivity {

    Button uploadBtn;
    ImageView productImage;
    EditText productName, productPrice, productQuantity;

    TextView textView;

    private static final int GALLERY_REQUEST_CODE = 123;
    Uri mImageUri;


    StorageReference mStorageRef;
    DatabaseReference mDatabaseRef;

    String nameOfproduct, priceOfproduct, quantityOfproduct;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_item_producer);


        //Extract id of button
        uploadBtn = findViewById(R.id.uploadBtn);

        textView = findViewById(R.id.textView7);

        //Extract id of product image
        productImage = findViewById(R.id.productImageview);

        //Extract id's of edittext
        productName = findViewById(R.id.nameOfproduct);
        productPrice = findViewById(R.id.priceOfproduct);
        productQuantity = findViewById(R.id.quantityOfproduct);


        mStorageRef = FirebaseStorage.getInstance().getReference("uploads");
        mDatabaseRef = FirebaseDatabase.getInstance().getReference("uploads");


        productImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Pick an image"), GALLERY_REQUEST_CODE);
            }
        });


        uploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                nameOfproduct = productName.getText().toString().trim();
                priceOfproduct = productPrice.getText().toString().trim();
                quantityOfproduct = productQuantity.getText().toString().trim();

                if(nameOfproduct.isEmpty()){
                    productName.setError("Please enter product name");
                    return;
                }
                if(priceOfproduct.isEmpty()){
                    productPrice.setError("Please enter product price");
                    return;
                }
                if(quantityOfproduct.isEmpty()){
                    productQuantity.setError("Please enter product quantity");
                    return;
                }


                uploadImage();


            }
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY_REQUEST_CODE && resultCode == RESULT_OK && data != null) {

            mImageUri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), mImageUri);
                productImage.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();

            }
        }
    }


    private void uploadImage() {

        if(mImageUri != null)
        {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            StorageReference ref = mStorageRef.child( UUID.randomUUID().toString());

            ref.putFile(mImageUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                            ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    Upload upload = new Upload(productName.getText().toString().trim(), uri.toString(),
                                            productPrice.getText().toString().trim(), productQuantity.getText().toString().trim());

                                    String uploadId = mDatabaseRef.push().getKey();
                                    mDatabaseRef.child(uploadId).setValue(upload);

                                    progressDialog.dismiss();
                                    Toast.makeText(getApplicationContext(),"Upload Successful", Toast.LENGTH_LONG).show();
                                    startActivity(new Intent(getApplicationContext(), ProducerAddMenuItem.class));
                                    finish();
                                }
                            });

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(AddNewItemProducer.this, "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            Toast.makeText(AddNewItemProducer.this, "Uploading please Wait ", Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }


}