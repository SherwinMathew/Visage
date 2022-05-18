package com.example.visage.Merchant;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.visage.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;


public class FragmentViewMyServices extends Fragment {

    FirebaseFirestore firestore;
    FirebaseAuth auth;
    Spinner spinner_category;
    ArrayList<String> cat_list;
    RecyclerView recyclerView;
    FirestoreRecyclerAdapter<Services,MyViewHolder> adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_view_my_services, container, false);

        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        spinner_category = view.findViewById(R.id.view_my_service_spinner_cat);
        recyclerView = view.findViewById(R.id.recycler_view_my_services);

        cat_list = new ArrayList<>();
        cat_list.add("Body Care");
        cat_list.add("Facial");
        cat_list.add("Hair Care");
        cat_list.add("Hair Colour");
        cat_list.add("Hair Cut");
        cat_list.add("Makeover");

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item,cat_list);
        spinner_category.setAdapter(arrayAdapter);

        spinner_category.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                Query query = firestore.collection("MERCHANT").document(auth.getCurrentUser().getEmail())
                        .collection(spinner_category.getSelectedItem().toString());

                FirestoreRecyclerOptions<Services> options = new FirestoreRecyclerOptions.Builder<Services>()
                        .setQuery(query,Services.class)
                        .build();


                adapter = new FirestoreRecyclerAdapter<Services,MyViewHolder>(options) {

                    @NonNull
                    @Override
                    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_layout_my_services,parent,false);
                        return new MyViewHolder(v);
                    }

                    @Override
                    protected void onBindViewHolder(@NonNull MyViewHolder holder, int position, @NonNull Services model) {
                        holder.tv_service_name.setText(model.getService_name());
                        holder.tv_description.setText(model.getDescription());
                        holder.tv_price.setText(model.getPrice());
                    }
                };

                adapter.startListening();
                recyclerView.setAdapter(adapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });



        return view;
    }

    class MyViewHolder extends RecyclerView.ViewHolder
    {

        TextView tv_service_name,tv_description,tv_price;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_service_name = itemView.findViewById(R.id.custom_myservice_service_name);
            tv_description = itemView.findViewById(R.id.custom_myservice_description);
            tv_price = itemView.findViewById(R.id.custom_myservice_price);

        }
    }

}