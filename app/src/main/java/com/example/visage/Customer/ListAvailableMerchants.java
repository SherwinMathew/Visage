package com.example.visage.Customer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.visage.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class ListAvailableMerchants extends AppCompatActivity {

    ListView listview;
    String s_category,s_service;
    TextView tv;
    FirebaseFirestore firestore;
    ArrayList<String> fetched_list = new ArrayList<>();
    String s_phone,s_address,s_price,s_merchant_email,s_name;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_available_merchants);

        firestore = FirebaseFirestore.getInstance();
        auth =FirebaseAuth.getInstance();

        listview = findViewById(R.id.list_view_list_merchants);
        tv=findViewById(R.id.textView19);

        Bundle bundle = getIntent().getExtras();
        s_category = bundle.getString("CAT");
        s_service = bundle.getString("SUB");

        tv.setText("Select a merchant for "+s_category+" - "+s_service);

        firestore.collection("USERS").document(auth.getCurrentUser().getEmail())
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful())
                        {
                            DocumentSnapshot snapshot = task.getResult();
                            s_phone = snapshot.getString("mobilenumber");
                            s_address = snapshot.getString("address");
                            s_name = snapshot.getString("name");
                            //Toast.makeText(ListAvailableMerchants.this,s_address, Toast.LENGTH_SHORT).show();
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

        firestore.collection("SERVICES").document(s_category) //list the service provider, who provides particular services
                .collection(s_category).document(s_service)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful())
                        {
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

        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                String separated[] = fetched_list.get(i).split(" ");
                s_merchant_email = separated[0];
                s_price = separated[1];

                Intent i2 = new Intent(ListAvailableMerchants.this, BookingActivity.class);
                i2.putExtra("CUSTOMER EMAIL",auth.getCurrentUser().getEmail());
                i2.putExtra("MERCHANT EMAIL",s_merchant_email);
                i2.putExtra("NAME",s_name);
                i2.putExtra("ADDRESS",s_address);
                i2.putExtra("CONTACT",s_phone);
                i2.putExtra("SERVICE NAME",s_service);
                i2.putExtra("PRICE",s_price);
                startActivity(i2);

               // Toast.makeText(ListAvailableMerchants.this,separated[0], Toast.LENGTH_SHORT).show();
            }
        });

    }
}


