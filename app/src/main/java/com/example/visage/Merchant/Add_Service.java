package com.example.visage.Merchant;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.visage.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

public class Add_Service extends AppCompatActivity {

    TextInputEditText serviceCategory,serviceName,serviceRate,serviceInfo;
    Button addService;
    FirebaseFirestore firestore;
    FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_service);

        serviceCategory = findViewById(R.id.service_category);
        serviceName = findViewById(R.id.service_name);
        serviceRate = findViewById(R.id.service_rate);
        serviceInfo = findViewById(R.id.service_info);
        addService = findViewById(R.id.addser_btn);

        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        addService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s_category = serviceCategory.getText().toString().trim();
                String s_name = serviceName.getText().toString().trim();
                String s_rate = serviceRate.getText().toString().trim();
                String s_info = serviceInfo.getText().toString().trim();

                String user_email = auth.getCurrentUser().getEmail();

                Services obj = new Services(s_name,s_rate,s_info,s_category);

                firestore.collection("MERCHANT").document(user_email)
                        .collection("SERVICES").document(s_category)
                        .set(obj)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful())
                                {
                                    Toast.makeText(Add_Service.this, "Data added successfully", Toast.LENGTH_SHORT).show();
                                }
                                else
                                {
                                    Toast.makeText(Add_Service.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(Add_Service.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });


            }
        });


    }
}
