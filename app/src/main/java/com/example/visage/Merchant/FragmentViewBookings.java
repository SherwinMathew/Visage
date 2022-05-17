package com.example.visage.Merchant;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.visage.Customer.Booking;
import com.example.visage.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;


public class FragmentViewBookings extends Fragment {

    RecyclerView recyclerView;
    FirebaseFirestore firestore;
    FirestoreRecyclerAdapter adapter;
    FirebaseAuth auth;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_view_bookings, container, false);

        recyclerView = v.findViewById(R.id.recycler_view_view_bookings);

        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();

        Query query = firestore.collection("MERCHANT").document(auth.getCurrentUser().getEmail()).collection("BOOKINGS");

        FirestoreRecyclerOptions<Booking> options = new FirestoreRecyclerOptions.Builder<Booking>()
                .setQuery(query,Booking.class)
                .build();

        adapter = new FirestoreRecyclerAdapter<Booking,MyViewHolder>(options) {

            @NonNull
            @Override
            public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_layout_view_request,parent,false);
                return new MyViewHolder(v);
            }

            @Override
            protected void onBindViewHolder(@NonNull MyViewHolder holder, int position, @NonNull Booking model) {

                holder.tv_phone.setText(model.getContact_number());
                holder.tv_address.setText(model.getAddress());
                holder.tv_time.setText(model.getConvenient_time());
                holder.tv_service_name.setText(model.getService_name());
                holder.tv_name.setText(model.getName());

            }
        };

        adapter.startListening();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(adapter);


        return v;
    }
}

class MyViewHolder extends RecyclerView.ViewHolder
{

    CardView cardView;
    TextView tv_phone,tv_name,tv_service_name,tv_address,tv_time,tv_date;
    Button accept,decline;

    public MyViewHolder(@NonNull View itemView) {
        super(itemView);

        tv_phone = itemView.findViewById(R.id.custom_tv_phone);
        tv_address = itemView.findViewById(R.id.custom_tv_address);
        tv_service_name = itemView.findViewById(R.id.tv_custom_service_name);
        tv_time = itemView.findViewById(R.id.custom_tv_time);
        tv_name = itemView.findViewById(R.id.custom_tv_name);
        tv_date = itemView.findViewById(R.id.custom_tv_date);
        cardView = itemView.findViewById(R.id.custom_card);
        accept = itemView.findViewById(R.id.btn_accept);
        decline = itemView.findViewById(R.id.btn_decline);



    }


}