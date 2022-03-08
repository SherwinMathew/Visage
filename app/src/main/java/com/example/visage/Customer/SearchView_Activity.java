package com.example.visage.Customer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

import com.example.visage.Merchant.MerchantsInfo;
import com.example.visage.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.util.ArrayList;
import java.util.List;

public class SearchView_Activity extends AppCompatActivity {

    SearchView searchView;
    Button searchBtn;
    String searchString = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_view);

        searchView = findViewById(R.id.search_view);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                startSeaching();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchString = newText;
                return false;
            }
        });

    }

    private static final String TAG = "333";
    FirebaseDatabase firebaseDatabase;
    void startSeaching(){

        if(searchString == null) return;

        Log.d(TAG, "startSeaching: Searching ..." + searchString);
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        Query query = ref.child("MerchantsInfo");
        query.get()
                .addOnCompleteListener(new OnCompleteListener<DataSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DataSnapshot> task) {

                        if(task.isSuccessful()){
                            if(task.getResult().exists())
                            {
                                List<MerchantsInfo> lists = new ArrayList<>();
                                for (DataSnapshot data : task.getResult().getChildren()){
                                    MerchantsInfo info=  data.getValue(MerchantsInfo.class);
                                    if(info.getAvailServices().equals(searchString)) lists.add(info);
                                }
                                updateRecyclerview(lists);
                            } else
                            Log.d(TAG, "onComplete: No Data exist");

                        }else {
                            Toast.makeText(SearchView_Activity.this, "Failed to get.", Toast.LENGTH_SHORT).show();
                            Toast.makeText(SearchView_Activity.this, task.getException().toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    void updateRecyclerview(List<MerchantsInfo> lists){
        for(MerchantsInfo info : lists){
            Log.d(TAG, "updateRecyclerview: Data => " + info.getBusinessName());
        }
    }
}