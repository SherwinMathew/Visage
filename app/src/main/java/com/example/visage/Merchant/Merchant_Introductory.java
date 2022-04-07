package com.example.visage.Merchant;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.visage.Customer.Users;
import com.example.visage.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class Merchant_Introductory extends AppCompatActivity {

    FirebaseFirestore firestore;
    FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchant_introductory);

        firestore = FirebaseFirestore.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        firestore.collection("USERS").document(firebaseUser.getEmail())
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful())
                        {
                            DocumentSnapshot snapshot = task.getResult();
                            Users obj = snapshot.toObject(Users.class);

                            String s_u_type = obj.getUser_type();

                            if(s_u_type.equals("user"))
                            {
                                Intent i = new Intent(Merchant_Introductory.this,Merchant_Registration.class);
                                startActivity(i);
                            }
                            else
                            {
                                Intent i = new Intent(Merchant_Introductory.this,BottomNavigationMerchant.class);
                                startActivity(i);
                            }

                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Merchant_Introductory.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });


    }
}