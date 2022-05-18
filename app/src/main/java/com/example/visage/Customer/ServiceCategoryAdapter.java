package com.example.visage.Customer;

import android.content.Context;
import android.graphics.PorterDuff;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.visage.R;
import com.google.android.material.card.MaterialCardView;

import java.util.List;

public class ServiceCategoryAdapter extends RecyclerView.Adapter<ServiceCategoryAdapter.CategoryHolder> {

    List<ServiceCategory> data;
    Context context;
    int selectedItem = 0;

    OnCategoryClick onCategoryClick;

    public interface OnCategoryClick {
        void onClick(int pos);
    }

    public ServiceCategoryAdapter(List<ServiceCategory> data, Context context, OnCategoryClick onCategoryClick){
        this.data = data;
        this.context = context;
        this.onCategoryClick = onCategoryClick;
    }

    @NonNull
    @Override
    public ServiceCategoryAdapter.CategoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from((Context) context);
        View view = layoutInflater.inflate(R.layout.category_holder,parent,false);
        return new CategoryHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.P)
    @Override
    public void onBindViewHolder(@NonNull CategoryHolder holder, int position) {

        holder.image.setImageResource(data.get(position).getImage());
        holder.title.setText(data.get(position).getName());
        if (position == selectedItem){
            // Make card selected
            holder.cardView.setOutlineSpotShadowColor(context.getColor(R.color.colorPrimary));
            holder.cardView.setOutlineAmbientShadowColor(context.getColor(R.color.colorPrimary));
            holder.cardView.setStrokeWidth(2);
            holder.title.setTextColor(context.getColor(R.color.colorPrimary));
            //holder.image.setColorFilter(ContextCompat.getColor((Context) context,R.color.colorPrimary), PorterDuff.Mode.SRC_IN);
        }else {
            // Make card inactive
            holder.cardView.setOutlineSpotShadowColor(context.getColor(R.color.colorSecondary));
            holder.cardView.setOutlineAmbientShadowColor(context.getColor(R.color.colorSecondary));
            holder.title.setTextColor(context.getColor(R.color.colorSecondary));
            //holder.image.setColorFilter(ContextCompat.getColor((Context) context,R.color.colorSecondary), PorterDuff.Mode.SRC_IN);
            holder.cardView.setStrokeWidth(0);
        }

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class CategoryHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView image;
        MaterialCardView cardView;

        public CategoryHolder(@NonNull View holder) {
            super(holder);
            title = holder.findViewById(R.id.txt_title);
            image = holder.findViewById(R.id.img);
            cardView = holder.findViewById(R.id.category_cardview);

            cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    selectedItem = getAdapterPosition();
                    //reset items, so that color changes when we click on card
                    if (onCategoryClick != null){
                        onCategoryClick.onClick(getAdapterPosition());
                    }
                    notifyDataSetChanged();
                }
            });
        }
    }
}