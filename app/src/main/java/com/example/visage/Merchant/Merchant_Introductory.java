package com.example.visage.Merchant;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.visage.R;

public class Merchant_Introductory extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchant_introductory);

        Intent i = new Intent(Merchant_Introductory.this,Merchant_Registration.class);
        startActivity(i);



    }
}