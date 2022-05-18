package com.example.visage.Customer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.visage.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.scottyab.showhidepasswordedittext.ShowHidePasswordEditText;

public class Login_Activity extends AppCompatActivity implements View.OnClickListener {

    EditText lgEmail;
    ShowHidePasswordEditText lgPass;
    TextView sign_up_here;
    ImageView continue_btn;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);


        lgEmail = findViewById(R.id.et_log_email);
        lgPass = findViewById(R.id.et_log_pass);
        sign_up_here = findViewById(R.id.new_sign_up);
        continue_btn = findViewById(R.id.login_continue);

        continue_btn.setVisibility(View.VISIBLE);

        mAuth = FirebaseAuth.getInstance();

        sign_up_here.setOnClickListener(this);

        continue_btn.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()){
            case R.id.new_sign_up:
                startActivity(new Intent(this, com.example.visage.Customer.Registration_Activity.class));
                break;
            case R.id.login_continue:
                loginUser();
        }

    }

    private void loginUser() {

        continue_btn.setVisibility(View.INVISIBLE);

        String email = lgEmail.getText().toString().trim();
        String password = lgPass.getText().toString().trim();

        if(TextUtils.isEmpty(email)){
            lgEmail.setError("Email is required");
            lgEmail.requestFocus();
            continue_btn.setVisibility(View.VISIBLE);
        }else if(TextUtils.isEmpty(password)) {
            lgPass.setError("Password is required");
            lgPass.requestFocus();
            continue_btn.setVisibility(View.VISIBLE);
        }else{

            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        Toast.makeText(Login_Activity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Login_Activity.this, com.example.visage.Customer.BottomNavigationCustomer.class));
                    }else {
                        Toast.makeText(Login_Activity.this, "Login Error", Toast.LENGTH_SHORT).show();
                        continue_btn.setVisibility(View.VISIBLE);
                    }
                }
            });
        }
    }
}