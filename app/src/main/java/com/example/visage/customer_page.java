package com.example.visage;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;

public class customer_page extends AppCompatActivity {

    Switch switchAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_page);

        switchAccount = findViewById(R.id.merchant_switch);

        switchAccount.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(
                        compoundButton.isChecked())
                {
                    Intent i = new Intent(customer_page.this,MainActivity.class);
                    startActivity(i);
                }
            }
        });

    }
}