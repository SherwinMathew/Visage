package com.example.visage.Merchant;

import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.visage.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;


public class MerchantAnalyticsFragment extends Fragment {

    PieChart pieChart;
    TextView tv_total,tv_active,tv_cancelled;
    long total,active,cancelled;
    FirebaseFirestore firestore;
    FirebaseAuth auth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_merchant_analytics, container, false);

        tv_total = view.findViewById(R.id.tot_book);
        tv_active = view.findViewById(R.id.actv_book);
        tv_cancelled = view.findViewById(R.id.cancel_book);
        pieChart = view.findViewById(R.id.piechart);

        //tv_active.setText(Integer.toString(20));
        tv_cancelled.setText(Integer.toString(50));

       //active = Integer.parseInt(tv_active.getText().toString());
        cancelled = Integer.parseInt(tv_cancelled.getText().toString());

        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        firestore.collection("MERCHANT").document(auth.getCurrentUser().getEmail())
                .collection("BOOKINGS").document("ANALYTICS")
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if(task.isSuccessful())
                        {
                            DocumentSnapshot snapshot = task.getResult();
                            Long val = snapshot.getLong("booking_count");
                            Long val2 = snapshot.getLong("accepted_count");
                            //Toast.makeText(getContext(),val.toString(), Toast.LENGTH_SHORT).show();

                            tv_total.setText(val.toString());
                            tv_active.setText(val2.toString());
                            total = val.intValue();
                            active = val2.intValue();
                            pieChart.addPieSlice(new PieModel("1",total, Color.parseColor("#FFA726")));
                            pieChart.addPieSlice(new PieModel("2",active, Color.parseColor("#66BB6A")));

                        }
                        else
                        {
                            Toast.makeText(getContext(),task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

       // pieChart.addPieSlice(new PieModel("3",cancelled, Color.parseColor("#EF5350")));

        pieChart.startAnimation();


        return view;

    }
}