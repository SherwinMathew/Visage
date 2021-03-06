package com.example.visage.Customer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.visage.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.scottyab.showhidepasswordedittext.ShowHidePasswordEditText;


public class Registration_Activity extends AppCompatActivity implements View.OnClickListener {

    EditText regName,regEmail,regMbn;
    ShowHidePasswordEditText regPass;
    Button regSignup;
    TextView regLogin,regAdd;
    FirebaseAuth mAuth;
    FirebaseFirestore firestore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_registration);

        regName = findViewById(R.id.reg_name);
        regEmail = findViewById(R.id.reg_email);
        regMbn = findViewById(R.id.reg_mbn);
        regPass = findViewById(R.id.reg_pass);
        regSignup = findViewById(R.id.reg_signup);
        regLogin = findViewById(R.id.reg_login);
        regAdd = findViewById(R.id.reg_address);
        mAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        regLogin.setOnClickListener(this);
        regSignup.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.reg_login:
                startActivity(new Intent(this, Login_Activity.class));
                break;
            case R.id.reg_signup:
                registerUser();
        }
    }

    private void registerUser() {
        String name = regName.getText().toString().trim();
        String email = regEmail.getText().toString().trim();
        String mobilenumber = regMbn.getText().toString().trim();
        String password = regPass.getText().toString().trim();
        String address = regAdd.getText().toString().trim();

        if (TextUtils.isEmpty(name)) {
            regName.setError("Name is required");
            regName.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(email)) {
            regEmail.setError("Email is required");
            regEmail.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            regEmail.setError("Please provide valid email");
            regEmail.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(mobilenumber)) {
            regMbn.setError("Mobile Number is required");
            regMbn.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(password)) {
            regPass.setError("Password is required");
            regPass.requestFocus();
            return;
        }
        if (password.length() < 6) {
            regPass.setError("Minimum password length should be 6 characters");
            regPass.requestFocus();
            return;
        }
        if (TextUtils.isEmpty(address)){
            regAdd.setError("Address is required");
            regAdd.requestFocus();
            return;
        }

        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful())
                {
                    Users user = new Users(name, email, mobilenumber,"user",address);

                    firestore.collection("USERS").document(email)
                            .set(user)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful())
                                    {
                                        Toast.makeText(Registration_Activity.this, "User registered successfully", Toast.LENGTH_SHORT).show();
                                    }
                                    else
                                    {
                                        Toast.makeText(Registration_Activity.this,task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(Registration_Activity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });

                }
                else {
                    Toast.makeText(Registration_Activity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
