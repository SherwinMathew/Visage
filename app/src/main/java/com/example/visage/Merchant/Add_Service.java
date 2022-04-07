package com.example.visage.Merchant;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;

import com.example.visage.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.firestore.FirebaseFirestore;

public class Add_Service extends AppCompatActivity {

    TextInputEditText serviceCategory,serviceName,serviceRate,serviceInfo;
    Button addService;
    FirebaseFirestore firestore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_service);

        firestore = FirebaseFirestore.getInstance();

        serviceCategory = findViewById(R.id.service_category);
        serviceName = findViewById(R.id.service_name);
        serviceRate = findViewById(R.id.service_rate);
        serviceInfo = findViewById(R.id.service_info);
        addService = findViewById(R.id.addser_btn);

        addService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s_category = serviceCategory.getText().toString().trim();
                String s_name = serviceCategory.getText().toString().trim();
                String s_rate = serviceCategory.getText().toString().trim();
                String s_category = serviceCategory.getText().toString().trim();


            }
        });

    }
}
