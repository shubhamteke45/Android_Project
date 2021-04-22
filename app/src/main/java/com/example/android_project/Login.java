package com.example.android_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class Login extends AppCompatActivity {


    Button loginBtn;
    EditText name_login, password_login;
    TextView register_link, forget_link;

    String name, password;

    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Extract id's
        loginBtn = findViewById(R.id.login_btn);

        name_login = findViewById(R.id.name_login);
        password_login = findViewById(R.id.password_login);

        register_link = findViewById(R.id.register_link);
        forget_link = findViewById(R.id.forgetPasswordLogin);

        fAuth = FirebaseAuth.getInstance();



        loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                name = name_login.getText().toString();
                password = password_login.getText().toString();

                if(name.isEmpty()){
                    name_login.setError("Please check Email");
                    return;
                }
                if(password.isEmpty()){
                    password_login.setError("Please check your password");
                    return;
                }

                if(name.equals("producer") && password.equals("producer")){
                    startActivity(new Intent(getApplicationContext(), ProducerModernDashboard.class));
                }

                fAuth.signInWithEmailAndPassword(name, password).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                    @Override
                    public void onSuccess(AuthResult authResult) {
                        startActivity(new Intent(getApplicationContext(), ConsumerModernDashboard.class));
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Login.this, "Invalid Credentials", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

        register_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Register.class));
            }
        });

        forget_link.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
    
    @Override
    protected void onStart() {
        super.onStart();
        // TO check whether user is logged in or not
        if(FirebaseAuth.getInstance().getCurrentUser()!=null){
            DocumentReference documentReference = FirebaseFirestore.getInstance().collection("Users").document(FirebaseAuth.getInstance().getCurrentUser().getUid());
            documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    startActivity(new Intent(getApplicationContext(), ConsumerModernDashboard.class));
                }
            });

        }

    }

}