package com.abc.veggismart.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.abc.veggismart.R;
import com.abc.veggismart.model.AllItems;

import java.util.ArrayList;

public class OrderItemsAdapter extends RecyclerView.Adapter<OrderItemsAdapter.ViewHolder> {
    Context context;
    ArrayList<AllItems> orderItemsList;

    public OrderItemsAdapter(Context context, ArrayList<AllItems> orderItemsList) {
        this.context = context;
        this.orderItemsList = orderItemsList;
    }

    @NonNull
    @Override
    public OrderItemsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.confirmed_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderItemsAdapter.ViewHolder holder, int position) {
        holder.name.setText(orderItemsList.get(position).getNameMarathi());
        holder.price.setText(String.valueOf(orderItemsList.get(position).getPrice()));
        holder.quantity.setText(orderItemsList.get(position).getQuantity()+orderItemsList.get(position).getQuantityUnit());
    }

    @Override
    public int getItemCount() {
        return orderItemsList.size();
    }
    public static class ViewHolder extends RecyclerView.ViewHolder{

        TextView name;
        TextView price;
        TextView quantity;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.itemNameLabelOrder);
            price=itemView.findViewById(R.id.itemPriceOrder);
            quantity=itemView.findViewById(R.id.quantityLabelOrder);
        }
    }
}
