package com.example.visage.Merchant;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.example.visage.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Merchant_Registration extends AppCompatActivity {


    String [] types = {"Salon","Spa","Parlor","Individual"};
    String [] service = {"Hair Styling","Hair Cutting","Hair Coloring","Hair Form","Hair Treatment","Manicure","Bridal","Spa Treatments","Steam with Shower"};

    AutoCompleteTextView businessType,availServices;
    ArrayAdapter<String> adapterItems;

    TextInputEditText businessOwner,businessName,contactNumber,address,merMessage;
    MaterialButton submit;

    FirebaseFirestore firestore;

    FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchant_registration);

        businessType = findViewById(R.id.business_type);
        adapterItems = new ArrayAdapter<String>(this,R.layout.dropdown_list,types);
        businessType.setAdapter(adapterItems);

        availServices = findViewById(R.id.avail_service);
        adapterItems = new ArrayAdapter<String>(this,R.layout.dropdown_list,service);
        availServices.setAdapter(adapterItems);

        businessOwner = findViewById(R.id.business_owner);
        businessName = findViewById(R.id.business_name);
        contactNumber = findViewById(R.id.contact_number);
        address = findViewById(R.id.address);
        merMessage = findViewById(R.id.mer_message);
        submit = findViewById(R.id.submit_btn);

        auth = FirebaseAuth.getInstance();

        firestore = FirebaseFirestore.getInstance();

        businessType.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String item = parent.getItemAtPosition(position).toString();
            }
        });

        availServices.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String service = parent.getItemAtPosition(position).toString();
            }
        });

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s_ownername = businessOwner.getText().toString().trim();
                String s_businessname = businessName.getText().toString().trim();
                String s_number = contactNumber.getText().toString().trim();
                String s_businesstype = businessType.getText().toString().trim();
                String s_availservices = availServices.getText().toString().trim();
                String s_message = merMessage.getText().toString().trim();
                String s_address = address.getText().toString().trim();

                if(s_ownername.isEmpty()){
                    businessOwner.setError("This files is required");
                    businessOwner.requestFocus();
                    return;
                }

                if(s_businessname.isEmpty()){
                    businessName.setError("This files is required");
                    businessName.requestFocus();
                    return;
                }

                if(!Patterns.PHONE.matcher(s_number).matches()){
                    contactNumber.setError("This files is required");
                    contactNumber.requestFocus();
                    return;
                }

                if(s_businesstype.isEmpty()){
                    businessType.setError("This files is required");
                    businessType.requestFocus();
                    return;
                }

                if(s_availservices.isEmpty()){
                    availServices.setError("This files is required");
                    availServices.requestFocus();
                    return;
                }

                if(s_message.isEmpty()){
                    merMessage.setError("This files is required");
                    merMessage.requestFocus();
                    return;
                }

                MerchantsInfo obj = new MerchantsInfo(s_ownername,s_businessname,s_number,s_address,s_businesstype,s_availservices,s_message);
                String user_email = auth.getCurrentUser().getEmail();

                if(user_email!=null)
                {
                    firestore.collection("MERCHANT").document(user_email)
                            .set(obj)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful())
                                    {
                                        Map<String,Object>data = new HashMap<>();
                                        data.put("user_type","merchant");

                                        firestore.collection("USERS").document(user_email)
                                                .update(data)
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if(task.isSuccessful())
                                                        {
                                                            Toast.makeText(Merchant_Registration.this, "Merchant registered successfully", Toast.LENGTH_SHORT).show();
                                                            startActivity(new Intent(Merchant_Registration.this,BottomNavigationMerchant.class));
                                                        }
                                                        else
                                                        {
                                                            Toast.makeText(Merchant_Registration.this,task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        Toast.makeText(Merchant_Registration.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                                    }
                                                });
                                    }
                                    else
                                    {
                                        Toast.makeText(Merchant_Registration.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(Merchant_Registration.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                }
                else
                {
                    Toast.makeText(Merchant_Registration.this, "null email", Toast.LENGTH_SHORT).show();
                }



            }


        });

    }
}