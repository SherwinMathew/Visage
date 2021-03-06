package com.example.visage.Customer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.visage.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class BookingActivity extends AppCompatActivity {

    Spinner spinner;
    ArrayList<String> arrayList = new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;
    FirebaseFirestore firestore;
    Button book;
    TextView tv_business_name,tv_address,tv_working_hours,tv_contact,tv_service_name_and_price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);

        book = findViewById(R.id.btn_book);
        tv_business_name = findViewById(R.id.tv_businessname);
        tv_address = findViewById(R.id.tv_address);
        tv_working_hours = findViewById(R.id.tv_working_hours);
        tv_contact = findViewById(R.id.tv_contact);
        tv_service_name_and_price = findViewById(R.id.service_name_and_price);
        spinner = findViewById(R.id.choosetimeslot);

        Bundle bundle = getIntent().getExtras();
        String s_address = bundle.getString("ADDRESS") ;
        String s_phone = bundle.getString("CONTACT");
        String s_service = bundle.getString("SERVICE NAME");
        String s_price = bundle.getString("PRICE");
        String s_merchant_email = bundle.getString("MERCHANT EMAIL");
        String s_name = bundle.getString("NAME");
        String s_customer_email = bundle.getString("CUSTOMER EMAIL");

        firestore = FirebaseFirestore.getInstance();

        arrayList.add("Choose favourable time slot");
        arrayList.add("09:00 am to 01:00 pm");
        arrayList.add("01:00 pm to 05:00 pm");
        arrayList.add("05:00 pm to 09:00 pm");
        spinner.setSelection(0);
        arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, arrayList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);


        firestore.collection("MERCHANT").document(s_merchant_email) //fetching merchant details from database
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful())
                        {
                            DocumentSnapshot snapshot = task.getResult();
                            tv_business_name.setText(snapshot.getString("businessName"));
                            tv_working_hours.setText(snapshot.getString("working_hours"));
                            tv_address.setText(snapshot.getString("merchantAddress"));
                        }
                        else
                        {
                            Toast.makeText(BookingActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(BookingActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

        tv_contact.setText(s_phone);
        s_price = s_price.replace("@","");
        tv_service_name_and_price.setText(s_service+" @ Rs."+s_price);

        book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(spinner.getSelectedItem().toString().equals("Choose favourable time slot"))
                {
                    Toast.makeText(BookingActivity.this, "Pick a valid time slot and proceed", Toast.LENGTH_SHORT).show();
                    return;
                }

                Booking obj = new Booking(s_address,s_phone,s_service,spinner.getSelectedItem().toString(),s_name,s_customer_email);

                firestore.collection("MERCHANT").document(s_merchant_email)
                        .collection("BOOKINGS").document(s_phone)
                        .set(obj)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if(task.isSuccessful())
                                {
                                    firestore.collection("MERCHANT").document(s_merchant_email)
                                            .collection("BOOKINGS").document("ANALYTICS")//fetching analytics
                                            .get()
                                            .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                @Override
                                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                    if(task.isSuccessful())
                                                    {
                                                        DocumentSnapshot snapshot = task.getResult();
                                                        long val = snapshot.getLong("booking_count");
                                                        long val2 = snapshot.getLong("accepted_count");
                                                        val = val+1;

                                                        //Toast.makeText(BookingActivity.this,String.valueOf(val), Toast.LENGTH_SHORT).show();

                                                        Map<String,Object> data = new HashMap<>();
                                                        data.put("booking_count",val);
                                                        data.put("accepted_count",val2);
                                                        data.put("name","analytics");

                                                        firestore.collection("MERCHANT").document(s_merchant_email)
                                                                .collection("BOOKINGS").document("ANALYTICS")
                                                                .set(data)
                                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                    @Override
                                                                    public void onComplete(@NonNull Task<Void> task) {
                                                                        if(task.isSuccessful())
                                                                        {
                                                                            Toast.makeText(BookingActivity.this, "Booking has been made successfully", Toast.LENGTH_SHORT).show();
                                                                        }
                                                                        else
                                                                        {
                                                                            Toast.makeText(BookingActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                                                        }
                                                                    }
                                                                })
                                                                .addOnFailureListener(new OnFailureListener() {
                                                                    @Override
                                                                    public void onFailure(@NonNull Exception e) {
                                                                        Toast.makeText(BookingActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                                                    }
                                                                });


                                                    }
                                                    else
                                                    {
                                                        Toast.makeText(BookingActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                                    }
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    Toast.makeText(BookingActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                                }
                                            });

                                }
                                else
                                {
                                    Toast.makeText(BookingActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(BookingActivity.this,e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });


            }
        });


    }
}