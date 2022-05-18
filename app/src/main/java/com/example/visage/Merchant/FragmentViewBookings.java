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
import android.widget.Toast;

import com.example.visage.Customer.Booking;
import com.example.visage.Customer.BookingActivity;
import com.example.visage.R;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.HashMap;
import java.util.Map;


public class FragmentViewBookings extends Fragment {

    RecyclerView recyclerView;
    FirebaseFirestore firestore;
    FirestoreRecyclerAdapter adapter;
    FirebaseAuth auth;
    String customer_email;

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
                    holder.tv_customer_email.setText(model.getEmail());

                    if(holder.tv_name.getText().equals("analytics"))
                    {
                        holder.tv_name.setText("View your incoming requests above");
                        holder.tv_date.setText("");
                        holder.decline.setVisibility(View.INVISIBLE);
                        holder.accept.setVisibility(View.INVISIBLE);
                    }




                holder.decline.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                           firestore.collection("MERCHANT").document(auth.getCurrentUser().getEmail())
                                   .collection("BOOKINGS").document(model.getContact_number())
                                   .delete()
                                   .addOnCompleteListener(new OnCompleteListener<Void>() {
                                       @Override
                                       public void onComplete(@NonNull Task<Void> task) {
                                           if(task.isSuccessful())
                                           {
                                               Map<Object,String>status = new HashMap<>();
                                               status.put("booking_response","declined");

                                               firestore.collection("USERS").document(holder.tv_customer_email.getText().toString())
                                                       .collection("BOOKING RESPONSE").document(auth.getCurrentUser().getEmail())
                                                       .set(status)
                                                       .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                           @Override
                                                           public void onComplete(@NonNull Task<Void> task) {
                                                               if(task.isSuccessful())
                                                               {

                                                                   Toast.makeText(getContext(),"Booking declined successfully !", Toast.LENGTH_SHORT).show();
                                                               }
                                                               else
                                                               {
                                                                   Toast.makeText(getContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                                               }
                                                           }
                                                       })
                                                       .addOnFailureListener(new OnFailureListener() {
                                                           @Override
                                                           public void onFailure(@NonNull Exception e) {
                                                               Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                                                           }
                                                       });
                                             //  Toast.makeText(getContext(),"Booking request declined successfully", Toast.LENGTH_SHORT).show();
                                           }
                                           else
                                           {
                                               Toast.makeText(getContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                           }
                                       }
                                   })
                                   .addOnFailureListener(new OnFailureListener() {
                                       @Override
                                       public void onFailure(@NonNull Exception e) {
                                           Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                                       }
                                   });
                        }
                    });

                    holder.accept.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            Map<Object,String>status = new HashMap<>();
                            status.put("booking_response","accepted");

                            firestore.collection("USERS").document(holder.tv_customer_email.getText().toString())
                                    .collection("BOOKING RESPONSE").document(auth.getCurrentUser().getEmail())
                                    .set(status)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            if(task.isSuccessful())
                                            {

                                                firestore.collection("MERCHANT").document(auth.getCurrentUser().getEmail())
                                                        .collection("BOOKINGS").document("ANALYTICS")
                                                        .get()
                                                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                                            @Override
                                                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                                                if(task.isSuccessful())
                                                                {
                                                                    DocumentSnapshot snapshot = task.getResult();
                                                                    long val = snapshot.getLong("booking_count");
                                                                    long val2 = snapshot.getLong("accepted_count");
                                                                    val = val+1;
                                                                    val2 = val2+1;
                                                                    //Toast.makeText(BookingActivity.this,String.valueOf(val), Toast.LENGTH_SHORT).show();

                                                                    Map<String,Object> data = new HashMap<>();
                                                                    data.put("booking_count",val);
                                                                    data.put("accepted_count",val2);
                                                                    data.put("name","analytics");

                                                                    firestore.collection("MERCHANT").document(auth.getCurrentUser().getEmail())
                                                                            .collection("BOOKINGS").document("ANALYTICS")
                                                                            .set(data)
                                                                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                                                @Override
                                                                                public void onComplete(@NonNull Task<Void> task) {
                                                                                    if(task.isSuccessful())
                                                                                    {
                                                                                        Toast.makeText(getContext(), "Booking has been accepted successfully", Toast.LENGTH_SHORT).show();
                                                                                    }
                                                                                    else
                                                                                    {
                                                                                        Toast.makeText(getContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                                                                    }
                                                                                }
                                                                            })
                                                                            .addOnFailureListener(new OnFailureListener() {
                                                                                @Override
                                                                                public void onFailure(@NonNull Exception e) {
                                                                                    Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                                                                                }
                                                                            });
                                                                }
                                                                else
                                                                {
                                                                    Toast.makeText(getContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                                                }
                                                            }
                                                        })
                                                        .addOnFailureListener(new OnFailureListener() {
                                                            @Override
                                                            public void onFailure(@NonNull Exception e) {
                                                                Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                                                            }
                                                        });




                                            }
                                            else
                                            {
                                                Toast.makeText(getContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    })
                                    .addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    });
                        }
                    });


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
    TextView tv_phone,tv_name,tv_service_name,tv_address,tv_time,tv_date,tv_customer_email;
    Button accept,decline;

    public MyViewHolder(@NonNull View itemView) {
        super(itemView);

        cardView = itemView.findViewById(R.id.custom_card);
        tv_phone = itemView.findViewById(R.id.custom_tv_phone);
        tv_address = itemView.findViewById(R.id.custom_tv_address);
        tv_service_name = itemView.findViewById(R.id.tv_custom_service_name);
        tv_time = itemView.findViewById(R.id.custom_tv_time);
        tv_name = itemView.findViewById(R.id.custom_tv_name);
        tv_date = itemView.findViewById(R.id.custom_tv_date);
        cardView = itemView.findViewById(R.id.custom_card);
        accept = itemView.findViewById(R.id.btn_accept);
        decline = itemView.findViewById(R.id.btn_decline);
        tv_customer_email = itemView.findViewById(R.id.custom_tv_customer_email);


    }

}