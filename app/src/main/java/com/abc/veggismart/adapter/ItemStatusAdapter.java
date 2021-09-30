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
import com.abc.veggismart.model.ItemStatus;

import java.util.ArrayList;

public class ItemStatusAdapter extends RecyclerView.Adapter<ItemStatusAdapter.ViewHolder>{

    Context context;
    ArrayList<ItemStatus> itemStatusList;

    public ItemStatusAdapter(Context context, ArrayList<ItemStatus> itemStatusList) {
        this.context = context;
        this.itemStatusList = itemStatusList;
    }

    @NonNull
    @Override
    public ItemStatusAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.order_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemStatusAdapter.ViewHolder holder, int position) {
        holder.itemName.setText(itemStatusList.get(position).getName().trim());
        holder.itemNameMarathi.setText(itemStatusList.get(position).getNameMarathi().trim());
        holder.itemQuantity.setText(itemStatusList.get(position).getQuantity()+" "+itemStatusList.get(position).getQuantityUnit());
        holder.itemPrice.setText("₹ "+itemStatusList.get(position).getPrice());
        if(itemStatusList.get(position).getChecked()==0)
            holder.status.setText("No reply yet");
        else if(itemStatusList.get(position).getChecked()==1)
            holder.status.setText("Accepted");
        else if(itemStatusList.get(position).getChecked()==2)
            holder.status.setText("Rejected");
    }

    @Override
    public int getItemCount() {
        return itemStatusList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        TextView itemName;
        TextView itemNameMarathi;
        TextView itemQuantity;
        TextView itemPrice;
        TextView status;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            itemName=itemView.findViewById(R.id.itemNameOrderStatus);
            itemNameMarathi=itemView.findViewById(R.id.itemNameMarathiOrderStatus);
            itemQuantity=itemView.findViewById(R.id.itemQuantityOrderStatus);
            itemPrice=itemView.findViewById(R.id.itemPriceOrderStatus);
            status=itemView.findViewById(R.id.feedbackOrderStatus);

        }
    }
}
