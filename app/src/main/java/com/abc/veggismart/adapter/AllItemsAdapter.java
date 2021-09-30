package com.abc.veggismart.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.abc.veggismart.ItemDetailsActivity;
import com.abc.veggismart.MainActivity;
import com.abc.veggismart.R;
import com.abc.veggismart.model.AllItems;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.util.ArrayList;
import java.util.List;

public class AllItemsAdapter extends RecyclerView.Adapter<AllItemsAdapter.AllItemsViewHolder> {

    Context context;
    List<AllItems> allItemsList;

    public AllItemsAdapter(@NonNull Context context, List<AllItems> allItemsList) {
        this.context = context;
        this.allItemsList = allItemsList;
    }

    @NonNull
    @Override
    public AllItemsAdapter.AllItemsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.all_items,parent,false);
        return new AllItemsViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull AllItemsViewHolder holder, int position) {
        Glide.with(context)
                .load(allItemsList.get(position).getImageURL())
                .listener(new RequestListener() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model,
                                                Target target, boolean isFirstResource) {
                        // Log the GlideException here (locally or with a remote logging framework):
                        Log.e("TAG", "Load failed", e);

                        // You can also log the individual causes:
                        for (Throwable t : e.getRootCauses()) {
                            Log.e("TAG", "Caused by", t);
                        }
                        // Or, to log all root causes locally, you can use the built in helper method:
                        e.logRootCauses("TAG");

                        return false; // Allow calling onLoadFailed on the Target.
                    }

                    @Override
                    public boolean onResourceReady(Object resource, Object model, Target target, DataSource dataSource, boolean isFirstResource) {
                        return false;
                    }

                })
                .into(holder.itemImage);
        holder.itemName.setText(allItemsList.get(position).getName());
        holder.itemNameMarathi.setText(allItemsList.get(position).getNameMarathi());
        holder.itemQuantity.setText(String.valueOf(allItemsList.get(position).getQuantity()));
        holder.itemPrice.setText(String.valueOf("₹ "+allItemsList.get(position).getPrice()));
        holder.quantityUnit.setText(allItemsList.get(position).getQuantityUnit());
        holder.addToCart.setImageResource(R.drawable.ic_shopping_cart_black_24);
        holder.addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        holder.itemView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.getInstance().goDetail(allItemsList.get(position));
            }
        });
        holder.minimumLabel.setText("Minimum Quantity: ");
        holder.priceLabel.setText("Price: ");
    }

    @Override
    public int getItemCount() {
        return allItemsList.size();
    }


    public static class AllItemsViewHolder extends RecyclerView.ViewHolder{
        CardView itemView1;
        TextView itemName;
        TextView itemNameMarathi;
        TextView itemPrice;
        TextView itemQuantity;
        ImageView itemImage;
        ImageView addToCart;
        TextView minimumLabel;
        TextView priceLabel;
        TextView quantityUnit;

        public AllItemsViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView1=itemView.findViewById(R.id.itemView);
            itemName=itemView.findViewById(R.id.itemName);
            itemNameMarathi=itemView.findViewById(R.id.itemNameMarathi);
            itemPrice=itemView.findViewById(R.id.itemPrice);
            itemQuantity=itemView.findViewById(R.id.itemQuantity);
            itemImage=itemView.findViewById(R.id.itemImage);
            addToCart=itemView.findViewById(R.id.addToCart);
            minimumLabel=itemView.findViewById(R.id.minimumLabel);
            priceLabel=itemView.findViewById(R.id.priceLabel);
            quantityUnit=itemView.findViewById(R.id.quantityUnit);
        }
    }
}
