package com.example.android_project;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.Objects;

public class EditProfile extends AppCompatActivity {


    Button logoutBtn;
    TextView nameAbout, addressAbout, phoneAbout, emailAbout;
    FirebaseAuth fAuth;
    FirebaseFirestore fstore;

    String userId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        //Extract ids form drawable
        nameAbout = findViewById(R.id.titleName);
        addressAbout = findViewById(R.id.titleAddress);
        phoneAbout = findViewById(R.id.titlePhone);
        emailAbout = findViewById(R.id.titleEmail);

        //Get instances of firebase and firestore
        fAuth = FirebaseAuth.getInstance();
        fstore = FirebaseFirestore.getInstance();

        //Fetch user id
        userId = fAuth.getCurrentUser().getUid();

        DocumentReference documentReference = fstore.collection("Users").document(userId);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                nameAbout.setText(value.getString("fname"));
                addressAbout.setText(value.getString("faddress"));
                phoneAbout.setText(value.getString("fphone"));
                emailAbout.setText(value.getString("femail"));
            }
        });


        logoutBtn = findViewById(R.id.logout);
        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(),Login.class));
                Toast.makeText(getApplicationContext(),"Logout Successfully!",Toast.LENGTH_LONG).show();
                finish();
            }
        });




    }
}