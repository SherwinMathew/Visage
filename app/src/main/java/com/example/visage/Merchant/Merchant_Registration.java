package com.example.visage.Merchant;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import com.example.visage.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Merchant_Registration extends AppCompatActivity {


    String [] types = {"Salon","Spa","Parlor","Individual"};
    String [] service = {"Hair Styling","Hair Cutting","Hair Coloring","Hair Form","Hair Treatment","Manicure","Bridal","Spa Treatments","Steam with Shower"};

    AutoCompleteTextView businessType,availServices;
    ArrayAdapter<String> adapterItems;

    TextInputEditText businessOwner,businessName,contactNumber,address,merMessage;
    MaterialButton submit;

    FirebaseDatabase firebaseDatabase;
    DatabaseReference databaseReference;

    MerchantsInfo merchantsInfo;


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
//        businessType = findViewById(R.id.business_type);
//        availServices = findViewById(R.id.avail_service);
        merMessage = findViewById(R.id.mer_message);
        submit = findViewById(R.id.submit_btn);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("MerchantsInfo");

        merchantsInfo = new MerchantsInfo();

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
                String ownername = businessOwner.getText().toString().trim();
                String businessname = businessName.getText().toString().trim();
                String number = contactNumber.getText().toString().trim();
                String businesstype = businessType.getText().toString().trim();
                String availservices = availServices.getText().toString().trim();
                String message = merMessage.getText().toString().trim();

                if(TextUtils.isEmpty(ownername)){
                    businessOwner.setError("This files is required");
                    businessOwner.requestFocus();
                }

                if(TextUtils.isEmpty(businessname)){
                    businessName.setError("This files is required");
                    businessName.requestFocus();
                }

                if(!Patterns.PHONE.matcher(number).matches()){
                    contactNumber.setError("This files is required");
                    contactNumber.requestFocus();
                }

                if(TextUtils.isEmpty(businesstype)){
                    businessType.setError("This files is required");
                    businessType.requestFocus();
                }

                if(TextUtils.isEmpty(availservices)){
                    availServices.setError("This files is required");
                    availServices.requestFocus();
                }

                if(TextUtils.isEmpty(message)){
                    merMessage.setError("This files is required");
                    merMessage.requestFocus();
                }else{
                    merchantDetails(ownername,businessname,number,businesstype,availservices,message);
                }
            }

            private void merchantDetails(String ownername, String businessname, String number, String businesstype,
                                         String availservices, String message) {
                merchantsInfo.setOwnerName(ownername);
                merchantsInfo.setBusinessName(businessname);
                merchantsInfo.setContactNumber(number);
                merchantsInfo.setBusinessType(businesstype);
                merchantsInfo.setAvailServices(availservices);
                merchantsInfo.setMessage(message);

                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        databaseReference.setValue(merchantsInfo);
                        Toast.makeText(Merchant_Registration.this, "Data added", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(Merchant_Registration.this, "Failed to add data", Toast.LENGTH_SHORT).show();
                    }
                });
            }

        });

    }
}