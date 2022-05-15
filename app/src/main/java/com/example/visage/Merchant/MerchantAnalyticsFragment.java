package com.example.visage.Merchant;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.visage.R;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;


public class MerchantAnalyticsFragment extends Fragment {

    PieChart pieChart;
    TextView tv_total,tv_active,tv_cancelled;
    int total,active,cancelled;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_merchant_analytics, container, false);

        tv_total = view.findViewById(R.id.tot_book);
        tv_active = view.findViewById(R.id.actv_book);
        tv_cancelled = view.findViewById(R.id.cancel_book);
        pieChart = view.findViewById(R.id.piechart);

        tv_total.setText(Integer.toString(30));
        tv_active.setText(Integer.toString(20));
        tv_cancelled.setText(Integer.toString(50));

        total = Integer.parseInt(tv_total.getText().toString());
        active = Integer.parseInt(tv_active.getText().toString());
        cancelled = Integer.parseInt(tv_cancelled.getText().toString());


        pieChart.addPieSlice(new PieModel("1",total, Color.parseColor("#FFA726")));
        pieChart.addPieSlice(new PieModel("2",active, Color.parseColor("#66BB6A")));
        pieChart.addPieSlice(new PieModel("3",cancelled, Color.parseColor("#EF5350")));

        pieChart.startAnimation();


        return view;

    }
}