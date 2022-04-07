package com.example.visage.Customer;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.visage.R;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link HomeFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomeFragment extends Fragment {

    TextView switchToSearch;
    RecyclerView recyclerCategories;
    RecyclerView recyclerItems;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    //Button map;


    public HomeFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomeFragment.
     */
    // TODO: Rename and change types and number of parameters

    public static HomeFragment newInstance(String param1, String param2) {
        HomeFragment fragment = new HomeFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_home, container, false);

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        //String menu = getArguments().getString("Menu");
//        Button map = (Button) view.findViewById(R.id.map_test);
//        map.setOnClickListener(view1 -> {
//            Intent i = new Intent(getContext(),Map_Activity.class);
//            startActivity(i);
//        });

        switchToSearch = view.findViewById(R.id.to_search_bar);
        recyclerCategories = view.findViewById(R.id.recycler_categories);
        recyclerItems = view.findViewById(R.id.recycler_services);

        setCategories();
        setServiceItem(0);

        switchToSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(),SearchView_Activity.class));
            }
        });

        return view;

    }

    private void setCategories() {
        List<ServiceCategory> data = new ArrayList<>();

        ServiceCategory serviceCategory = new ServiceCategory("Hair",R.drawable.hair_treatment);
        ServiceCategory serviceCategory2 = new ServiceCategory("Skin",R.drawable.hair_treatment);
        ServiceCategory serviceCategory3 = new ServiceCategory("Manicure",R.drawable.hair_treatment);
        ServiceCategory serviceCategory4 = new ServiceCategory("Spa",R.drawable.hair_treatment);

        data.add(serviceCategory);
        data.add(serviceCategory2);
        data.add(serviceCategory3);
        data.add(serviceCategory4);

        ServiceCategoryAdapter serviceCategoryAdapter = new ServiceCategoryAdapter(data, getActivity(), new ServiceCategoryAdapter.OnCategoryClick() {
            @Override
            public void onClick(int pos) {
                setServiceItem(pos);
            }
        });

        recyclerCategories.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.HORIZONTAL,false));
        recyclerCategories.setAdapter(serviceCategoryAdapter);
        serviceCategoryAdapter.notifyDataSetChanged();
    }

    private void setServiceItem(int pos) {
        List<ServiceItem> serviceItems = new ArrayList<>();
        switch (pos){
            case 0:
                ServiceItem serviceItem = new ServiceItem("Hair coloring",4.5f,1500,R.drawable.hair_coloring);
                ServiceItem serviceItem2 = new ServiceItem("Hair coloring",5f,1000,R.drawable.hair_coloring);

                serviceItems.add(serviceItem);
                serviceItems.add(serviceItem2);

                break;
            case 1:
                ServiceItem serviceItem5 = new ServiceItem("Hair coloring",4.5f,2000,R.drawable.hair_coloring);
                ServiceItem serviceItem6 = new ServiceItem("Hair coloring",5f,2500,R.drawable.hair_coloring);

                serviceItems.add(serviceItem5);
                serviceItems.add(serviceItem6);

                break;
            case 2:
                ServiceItem serviceItem9 = new ServiceItem("Hair coloring",4.5f,900,R.drawable.hair_coloring);
                ServiceItem serviceItem10 = new ServiceItem("Hair coloring",5f,700,R.drawable.hair_coloring);

                serviceItems.add(serviceItem9);
                serviceItems.add(serviceItem10);

                break;

        }

        ServiceAdapter serviceAdapter = new ServiceAdapter(serviceItems);
        recyclerItems.setLayoutManager(new LinearLayoutManager(getActivity(),RecyclerView.VERTICAL,false));

        recyclerItems.setAdapter(serviceAdapter);

    }


}