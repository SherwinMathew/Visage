package com.example.visage.Customer;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.visage.Merchant.Services;
import com.example.visage.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {

    TextView switchToSearch;
    RecyclerView recyclerCategories;
    RecyclerView recyclerItems;
    FirestoreRecyclerAdapter adapter;
    FirebaseFirestore firestore;
    FirebaseAuth auth;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);

        switchToSearch = view.findViewById(R.id.to_search_bar);
        recyclerCategories = view.findViewById(R.id.recycler_categories);
        //recyclerItems = view.findViewById(R.id.recycler_services);

        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        switchToSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getActivity(),SearchView_Activity.class));
            }
        });

        setCategories();

//        String user_email = auth.getCurrentUser().getEmail();
//
//        Query query =  firestore.collection("MERCHANT").document(user_email)
//                .collection("SERVICES");
//
//        FirestoreRecyclerOptions<Services>options= new FirestoreRecyclerOptions.Builder<Services>()
//                .setQuery(query,Services.class)
//                .build();
//
//        adapter = new FirestoreRecyclerAdapter<Services,MyViewHolder>(options) {
//
//            @NonNull
//            @Override
//            public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//
//                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.category_holder,parent,false);
//                return new MyViewHolder(v);
//            }
//
//            @Override
//            protected void onBindViewHolder(@NonNull MyViewHolder holder, int position, @NonNull Services model) {
//
//                holder.title.setText("content to be changed");
//
//            }
//        };
//
//        adapter.startListening();
//        recyclerItems.setHasFixedSize(true);
//        recyclerItems.setLayoutManager(new LinearLayoutManager(getContext(),recyclerItems.HORIZONTAL,false));
//        recyclerItems.setAdapter(adapter);

        return view;

    }

    private void setCategories() {

        List<ServiceCategory> data = new ArrayList<>();

        ServiceCategory serviceCategory = new ServiceCategory("Body care",R.drawable.ic_baseline_miscellaneous_services_24);
        ServiceCategory serviceCategory2 = new ServiceCategory("Facial",R.drawable.ic_baseline_miscellaneous_services_24);
        ServiceCategory serviceCategory3 = new ServiceCategory("Hair Care",R.drawable.ic_baseline_miscellaneous_services_24);
        ServiceCategory serviceCategory4 = new ServiceCategory("Hair Colour",R.drawable.ic_baseline_miscellaneous_services_24);
        ServiceCategory serviceCategory5 = new ServiceCategory("Hair Cut",R.drawable.ic_baseline_miscellaneous_services_24);
        ServiceCategory serviceCategory6 = new ServiceCategory("Makeover",R.drawable.ic_baseline_miscellaneous_services_24);

        data.add(serviceCategory);
        data.add(serviceCategory2);
        data.add(serviceCategory3);
        data.add(serviceCategory4);
        data.add(serviceCategory5);
        data.add(serviceCategory6);



        ServiceCategoryAdapter serviceCategoryAdapter = new ServiceCategoryAdapter(data, getActivity(), new ServiceCategoryAdapter.OnCategoryClick() {
            @Override
            public void onClick(int pos) {
                switch(pos)
                {
                    case 0:
                        Intent i = new Intent(getContext(),ListSubServices.class);
                        i.putExtra("CAT","Body Care");
                        startActivity(i);
                        break;
                    case 1:
                        Intent i2 = new Intent(getContext(),ListSubServices.class);
                        i2.putExtra("CAT","Facial");
                        startActivity(i2);
                        break;
                    case 2:
                        Intent i3 = new Intent(getContext(),ListSubServices.class);
                        i3.putExtra("CAT","Hair Care");
                        startActivity(i3);
                        break;
                    case 3:
                        Intent i4 = new Intent(getContext(),ListSubServices.class);
                        i4.putExtra("CAT","Hair Colour");
                        startActivity(i4);
                        break;
                    case 4:
                        Intent i5 = new Intent(getContext(),ListSubServices.class);
                        i5.putExtra("CAT","Hair Cut");
                        startActivity(i5);
                        break;
                    case 5:
                        Intent i6 = new Intent(getContext(),ListSubServices.class);
                        i6.putExtra("CAT","Makeover");
                        startActivity(i6);
                        break;
                }

            }
        });

        recyclerCategories.setLayoutManager(new GridLayoutManager(getActivity(),2));
        recyclerCategories.setAdapter(serviceCategoryAdapter);
        serviceCategoryAdapter.notifyDataSetChanged();

    }


}

//class MyViewHolder extends RecyclerView.ViewHolder
//{
//
//    TextView title;
//
//    public MyViewHolder(@NonNull View itemView) {
//        super(itemView);
//
//        title = itemView.findViewById(R.id.txt_title);
//
//    }
//}