package com.example.android_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Register extends AppCompatActivity {

    public static final String TAG = "TAG";
    Button signUp;
    EditText nameRegister, addressRegister, phoneRegister, emailRegister, passwordRegister ;
    String name, address, phone, email, password, userId;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        //extract ids
        signUp = findViewById(R.id.registerBtn);

        nameRegister = findViewById(R.id.nameRegister);
        addressRegister = findViewById(R.id.addressRegister);
        phoneRegister = findViewById(R.id.phoneRegister);
        emailRegister = findViewById(R.id.EmailRegister);
        passwordRegister = findViewById(R.id.passwordRegister);

        //button action listener
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                name = nameRegister.getText().toString();
                address = addressRegister.getText().toString();
                phone = phoneRegister.getText().toString();
                email = emailRegister.getText().toString();
                password = passwordRegister.getText().toString();

                if(name.isEmpty()){
                    nameRegister.setError("Please Enter Full Name");
                    return;
                }
                if(address.isEmpty()){
                    addressRegister.setError("Please Enter Address");
                    return;
                }
                if(phone.isEmpty()){
                    phoneRegister.setError("Please Enter Phone Number");
                    return;
                }
                if(email.isEmpty()){
                    emailRegister.setError("Please Enter Email");
                    return;
                }
                if(password.isEmpty()){
                    passwordRegister.setError("Please Enter Password");
                    return;
                }
                if(password.length() < 6){
                    passwordRegister.setError("Enter password Length more than 6");
                    return;
                }

                fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(Register.this, "User Created", Toast.LENGTH_LONG).show();
                            //Fetching User Id
                            userId = fAuth.getCurrentUser().getUid();
                            //Creating new Document at firestore
                            DocumentReference documentReference = fStore.collection("Users").document(userId);
                            //Create Hash map
                            Map<String, Object> user = new HashMap<>();
                            user.put("fname", name);
                            user.put("faddress", address);
                            user.put("fphone", phone);
                            user.put("femail", email);
                            user.put("fpassword", password);
                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d(TAG, "onSuccess: User Profile is created for "+userId);
                                }
                            });

                            startActivity(new Intent(getApplicationContext(), Login.class));
                        }else{
                            Toast.makeText(getApplicationContext(), "Error!" + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });


            }
        });
    }
}