package com.example.visage.Customer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.visage.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.okhttp.internal.DiskLruCache;

import java.util.ArrayList;

public class ListAvailableMerchants extends AppCompatActivity {

    ListView listview;
    String s_category,s_service;
    TextView tv;
    FirebaseFirestore firestore;
    String fetched_array;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_available_merchants);

        firestore = FirebaseFirestore.getInstance();

        listview = findViewById(R.id.list_view_list_merchants);
        tv=findViewById(R.id.textView19);

        Bundle bundle = getIntent().getExtras();
        s_category = bundle.getString("CAT");
        s_service = bundle.getString("SUB");

//        s_category = "Body Care";
//        s_service = "Ayurvedic Body Massage";

        tv.setText("Select a merchant for "+s_category+" - "+s_service);

        firestore.collection("SERVICES").document(s_category)
                .collection(s_category).document(s_service)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful())
                        {
                            ArrayList<String> fetched_list = new ArrayList<>();

                            DocumentSnapshot obj = task.getResult();
                            FData obj2 = obj.toObject(FData.class);

                            if(obj.contains("available_merchants"))
                            {
                                fetched_list = obj2.getAvailable_merchants();

                                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(ListAvailableMerchants.this, android.R.layout.simple_list_item_1,fetched_list);
                                listview.setAdapter(arrayAdapter);
                            }
                            else
                            {
                                Toast.makeText(ListAvailableMerchants.this, "No registered merchants provide this service", Toast.LENGTH_SHORT).show();
                            }

                            //Toast.makeText(ListAvailableMerchants.this, obj2.getAvailable_merchants().toString(), Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            Toast.makeText(ListAvailableMerchants.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(ListAvailableMerchants.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

    }
}