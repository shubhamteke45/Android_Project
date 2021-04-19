package com.example.android_project;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

public class ProducerModernDashboard extends AppCompatActivity {

    CardView viewMenu, addNewProduct, pendingOrders, placedOrders;

    FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_producer_modern_dashboard);

        viewMenu = findViewById(R.id.viewMenu);
        addNewProduct = findViewById(R.id.addNewProduct);
        pendingOrders = findViewById(R.id.pendingOrders);
        placedOrders = findViewById(R.id.placedOrders);

        viewMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ProducerModernDashboard.class));
            }
        });

        addNewProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ProducerModernDashboard.class));
            }
        });

        pendingOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ProducerModernDashboard.class));
            }
        });

        placedOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ProducerModernDashboard.class));
            }
        });
    }
}