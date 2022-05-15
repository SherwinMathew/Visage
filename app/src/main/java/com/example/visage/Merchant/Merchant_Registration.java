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
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
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

    AutoCompleteTextView businessType;
    ArrayAdapter<String> adapterItems;
    TextInputEditText businessOwner,businessName,contactNumber,address,merMessage;
    EditText working_hours;
    MaterialButton submit;
    FirebaseFirestore firestore;
    FirebaseAuth auth;
    CheckBox checkbox_home_service;
    String yes_or_no;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchant_registration);

        businessType = findViewById(R.id.business_type);
        adapterItems = new ArrayAdapter<String>(this,R.layout.dropdown_list,types);
        businessType.setAdapter(adapterItems);

        working_hours= findViewById(R.id.et_merchant_registration_working_hours);
        checkbox_home_service = findViewById(R.id.checkbox_home_service);
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

        checkbox_home_service.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(compoundButton.isChecked())
                {
                    yes_or_no = "yes";
                }
                else
                {
                    yes_or_no = "no";
                }
            }
        });

    
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String s_ownername = businessOwner.getText().toString().trim();
                String s_businessname = businessName.getText().toString().trim();
                String s_number = contactNumber.getText().toString().trim();
                String s_businesstype = businessType.getText().toString().trim();
                String s_working_hours = working_hours.getText().toString().trim();
                String s_message = merMessage.getText().toString().trim();
                String s_address = address.getText().toString().trim();

                if(s_ownername.isEmpty()){
                    businessOwner.setError("This fields is required");
                    businessOwner.requestFocus();
                    return;
                }

                if(s_businessname.isEmpty()){
                    businessName.setError("This fields is required");
                    businessName.requestFocus();
                    return;
                }

                if(!Patterns.PHONE.matcher(s_number).matches()){
                    contactNumber.setError("This fields is required");
                    contactNumber.requestFocus();
                    return;
                }

                if(s_businesstype.isEmpty()){
                    businessType.setError("This fields is required");
                    businessType.requestFocus();
                    return;
                }

                if(s_working_hours.isEmpty()){
                    working_hours.setError("This fields is required");
                    working_hours.requestFocus();
                    return;
                }

                if(s_message.isEmpty()){
                    merMessage.setError("This fields is required");
                    merMessage.requestFocus();
                    return;
                }

                MerchantsInfo obj = new MerchantsInfo(s_ownername,s_businessname,s_number,s_address,s_businesstype,s_working_hours,s_message,yes_or_no);
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
                                                            Map<String,Object> data1 = new HashMap<>();
                                                            data1.put("booking_count",0);

                                                            Toast.makeText(Merchant_Registration.this, "Merchant registered successfully", Toast.LENGTH_SHORT).show();

                                                            firestore.collection("MERCHANT").document(user_email)
                                                                    .collection("BOOKINGS").document("ANALYTICS")
                                                                    .set(data1);

                                                            startActivity(new Intent(Merchant_Registration.this,BottomNavigationMerchant.class));
                                                            Merchant_Registration.this.finish();
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