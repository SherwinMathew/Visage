package com.example.visage.Customer;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.android.car.ui.toolbar.TextViewListener;
import com.example.visage.R;

import java.util.List;

public class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.ServiceHolder> {

    List<ServiceItem> data;
    int selectedItem = 0;

    public ServiceAdapter(List<ServiceItem> data){ this.data = data;}
    @NonNull
    @Override
    public ServiceHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.service_holder, parent, false);

        return new ServiceHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ServiceHolder holder, int position) {

        holder.price.setText(String.format("₹%d",data.get(position).getPrice()));
        holder.image.setImageResource(data.get(position).getImage());
        holder.title.setText(data.get(position).getName());
        holder.ratingBar.setRating(data.get(position).getRating());

        if (selectedItem == position){
            holder.cardView.animate().scaleX(1.1f);
            holder.cardView.animate().scaleY(1.1f);
            holder.title.setTextColor(Color.WHITE);
            holder.price.setTextColor(Color.WHITE);
//            holder.ratingBar.setRating(Color.WHITE);
            holder.llBackground.setBackgroundResource(R.color.colorPrimary);
        }else {
            holder.cardView.animate().scaleX(1f);
            holder.cardView.animate().scaleY(1f);
            holder.title.setTextColor(Color.BLACK);
            holder.price.setTextColor(Color.BLACK);
//            holder.ratingBar.setRating(Color.BLACK);
            holder.llBackground.setBackgroundResource(R.color.white);
        }

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class ServiceHolder extends RecyclerView.ViewHolder {
        RatingBar ratingBar;
        ImageView image;
        TextView title;
        TextView price;
        LinearLayout llBackground;
        CardView cardView;

        public ServiceHolder(@NonNull View holder) {
            super(holder);
            ratingBar = holder.findViewById(R.id.rating);
            title = holder.findViewById(R.id.food_title);
            image = holder.findViewById(R.id.food_img);
            price = holder.findViewById(R.id.txt_price);
            cardView = holder.findViewById(R.id.service_background);
            llBackground = holder.findViewById(R.id.ll_background);

            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    selectedItem = getAdapterPosition();
                    notifyDataSetChanged();
                }
            });

        }
    }
}
