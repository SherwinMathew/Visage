package com.example.visage.Merchant;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.visage.Customer.BottomNavigationCustomer;
import com.example.visage.Customer.Introductory_Activity;
import com.example.visage.Customer.Users;
import com.example.visage.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;


public class MerchantProfileFragment extends Fragment {

    Button logout;
    TextView tv_name,tv_email,tv_phone;
    FirebaseAuth auth;
    ProgressBar progressBar;
    Switch switchAccount;
    FirebaseFirestore firestore;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_account, container, false);


        logout = view.findViewById(R.id.logout_settings);
        tv_email = view.findViewById(R.id.prof_email);
        tv_name = view.findViewById(R.id.prof_name);
        tv_phone = view.findViewById(R.id.prof_mobile);
        progressBar = view.findViewById(R.id.progressBar_profile);
        switchAccount = view.findViewById(R.id.merchant_switch);

        // progressBar.setVisibility(View.VISIBLE);

        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        firestore.collection("USERS").document(auth.getCurrentUser().getEmail())
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful())
                        {
                            DocumentSnapshot snapshot = task.getResult();

                            Users obj = snapshot.toObject(Users.class);

                            String email = obj.getEmail();
                            String mobile = obj.getMobilenumber();
                            String name = obj.getName();

                            if(email.isEmpty() || mobile.isEmpty() || name.isEmpty())
                            {
                                Toast.makeText(getContext(), "Data null", Toast.LENGTH_SHORT).show();
                            }
                            else
                            {
                                tv_name.setText(name);
                                tv_email.setText(email);
                                tv_phone.setText(mobile);
                                progressBar.setVisibility(View.GONE);
                            }
                        }
                        else
                        {
                            Toast.makeText(getContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });


        logout.setOnClickListener(view1 -> {
            auth.signOut();
            Toast.makeText(getContext(), "Logged out", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getActivity(), com.example.visage.Customer.Login_Activity.class));
        });


        switchAccount.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(compoundButton.isChecked()) {
                    startActivity(new Intent(getActivity(), BottomNavigationCustomer.class));
                }
            }
        });





        return view;
    }
}