package com.example.visage;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

public class FragmentAddDetails extends AppCompatActivity {
    Spinner spinner = findViewById(R.id.choosetimeslot);
    ArrayList<String> arrayList = new ArrayList<>();
    ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragment_add_details);


        arrayList.add("Choose favourable time slot");
        arrayList.add("09:00 am to 01:00 pm");
        arrayList.add("01:00 pm to 05:00 pm");
        arrayList.add("05:00 pm to 09:00 pm");
        spinner.setSelection(0);
        arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, arrayList);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String timeslot = parent.getItemAtPosition(position).toString();
                Toast.makeText(parent.getContext(), "Selected: " + timeslot,
    }
}