package com.example.visage.Customer;

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

import com.example.visage.Merchant.Merchant_Introductory;
import com.example.visage.R;

import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AccountFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AccountFragment extends Fragment {

    Button logout;
    TextView tv_name,tv_email,tv_phone;
    FirebaseAuth auth;
    ProgressBar progressBar;


    Switch switchAccount;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AccountFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AccountFragment.
     */
    // TODO: Rename and change types and number of parameters

    public static AccountFragment newInstance(String param1, String param2) {
        AccountFragment fragment = new AccountFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


    }

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

        progressBar.setVisibility(View.VISIBLE);

        try{

            auth = FirebaseAuth.getInstance();

            FirebaseDatabase database;
            DatabaseReference databaseReference;

            database = FirebaseDatabase.getInstance();
            databaseReference = database.getReference("Users");


            if(auth.getCurrentUser() != null){
                databaseReference.child(auth.getCurrentUser().getUid())
                        .addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {

                                Users obj = snapshot.getValue(Users.class);

                                String email = obj.getEmail();
                                String mobile = obj.getMobilenumber();
                                String name = obj.getName();

                                if(email.isEmpty() || mobile.isEmpty() || name.isEmpty())
                                {
                                    Toast.makeText(getContext(), "Data null", Toast.LENGTH_SHORT).show();
                                }
                                else
                                {
                                    tv_name.setText(obj.getName());
                                    tv_email.setText(obj.getEmail());
                                    tv_phone.setText(obj.getMobilenumber());
                                    progressBar.setVisibility(View.GONE);
                                }

                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {
                                Toast.makeText(getContext(), error.toString(), Toast.LENGTH_SHORT).show();
                            }
                        });

            } else Toast.makeText(getActivity(), "Account null", Toast.LENGTH_SHORT).show();

        } catch (Error error){
            Toast.makeText(getActivity(), "Error " + error, Toast.LENGTH_SHORT).show();
        }

        logout.setOnClickListener(view1 -> {
            auth.signOut();
            Toast.makeText(getContext(), "Logged out", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getActivity(), com.example.visage.Customer.Login_Activity.class));
        });

        switchAccount = view.findViewById(R.id.merchant_switch);

        switchAccount.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(compoundButton.isChecked())
                        startActivity(new Intent(getActivity(), Merchant_Introductory.class));
            }
        });


        return view;

    }


}