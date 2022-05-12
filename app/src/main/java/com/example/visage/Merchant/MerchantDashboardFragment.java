package com.example.visage.Merchant;

import android.graphics.Color;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.visage.R;


public class MerchantDashboardFragment extends Fragment {

    CardView add_service;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view =  inflater.inflate(R.layout.fragment_merchant_dashboard, container, false);

        add_service = view.findViewById(R.id.dashboard_add_service);

        add_service.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final FragmentTransaction ft = getFragmentManager().beginTransaction();
                ft.replace(R.id.frame_layout2, new FragmentAddService(), "");
                ft.commit();

            }
        });


        return view;
    }


}