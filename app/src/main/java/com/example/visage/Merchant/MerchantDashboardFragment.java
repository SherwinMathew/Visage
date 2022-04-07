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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MerchantDashboardFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MerchantDashboardFragment extends Fragment {

    PieChart pieChart;
    TextView tv_total,tv_active,tv_cancelled;
    int total,active,cancelled;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MerchantDashboardFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MerchantDashboardFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MerchantDashboardFragment newInstance(String param1, String param2) {
        MerchantDashboardFragment fragment = new MerchantDashboardFragment();
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

        View view =  inflater.inflate(R.layout.fragment_merchant_dashboard, container, false);

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