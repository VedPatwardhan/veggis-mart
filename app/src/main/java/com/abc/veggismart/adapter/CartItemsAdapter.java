package com.abc.veggismart.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.abc.veggismart.MainActivity;
import com.abc.veggismart.MyCartActivity;
import com.abc.veggismart.MyOrdersActivity;
import com.abc.veggismart.OrderActivity;
import com.abc.veggismart.R;
import com.abc.veggismart.model.AllItems;

import java.util.List;

public class CartItemsAdapter extends RecyclerView.Adapter<CartItemsAdapter.CartItemsViewHolder>{
    Context context;
    List<AllItems> cartItemsList;

    public CartItemsAdapter(@NonNull Context context, List<AllItems> cartItemsList) {
        this.context = context;
        this.cartItemsList = cartItemsList;
    }

    @NonNull
    @Override
    public CartItemsAdapter.CartItemsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_item,parent,false);
        return new CartItemsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartItemsAdapter.CartItemsViewHolder holder, int position) {
        holder.nameLabel.setText("Item Name: ");
        holder.nameMarathiLabel.setText("मराठीतील नाव: ");
        holder.quantityLabel.setText("Quantity: ");
        holder.priceLabel.setText("Total Price: ");
        holder.removeFromCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyCartActivity.getInstance().progressBar.setVisibility(View.VISIBLE);
                MainActivity.cartItems.remove(cartItemsList.get(position));
                MyCartActivity.getInstance().cartItemsList=MainActivity.cartItems;
                MyCartActivity.getInstance().setCartRecycler(MyCartActivity.getInstance().cartItemsList);
                MyCartActivity.getInstance().progressBar.setVisibility(View.GONE);
            }
        });
        holder.orderNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.orderList.add(cartItemsList.get(position));
                MainActivity.orderedItems.clear();
                MainActivity.orderedItems.add(cartItemsList.get(position));
                Intent toOrder=new Intent(MyCartActivity.getInstance(), OrderActivity.class);
                MyCartActivity.getInstance().startActivity(toOrder);
            }
        });
        holder.itemName.setText(cartItemsList.get(position).getName());
        holder.itemNameMarathi.setText(cartItemsList.get(position).getNameMarathi());
        holder.itemQuantity.setText(String.valueOf(cartItemsList.get(position).getQuantity()));
        holder.quantityUnit.setText(cartItemsList.get(position).getQuantityUnit());
        holder.itemPrice.setText(String.valueOf(cartItemsList.get(position).getPrice()));
    }

    @Override
    public int getItemCount() {
        return cartItemsList.size();
    }

    public static class CartItemsViewHolder extends RecyclerView.ViewHolder{
        CardView itemView1;
        TextView itemName;
        TextView itemNameMarathi;
        TextView itemPrice;
        TextView itemQuantity;
        TextView nameLabel;
        TextView nameMarathiLabel;
        TextView quantityLabel;
        TextView priceLabel;
        TextView quantityUnit;
        Button removeFromCart;
        Button orderNow;

        public CartItemsViewHolder(@NonNull View itemView) {
            super(itemView);
            itemView1=itemView.findViewById(R.id.itemView);
            itemName=itemView.findViewById(R.id.itemNameCart);
            itemNameMarathi=itemView.findViewById(R.id.itemNameMarathiCart);
            itemPrice=itemView.findViewById(R.id.itemPriceCart);
            itemQuantity=itemView.findViewById(R.id.quantityCart);
            quantityUnit=itemView.findViewById(R.id.quantityUnitCart);
            nameLabel=itemView.findViewById(R.id.nameLabelCart);
            nameMarathiLabel=itemView.findViewById(R.id.nameMarathiLabelCart);
            quantityLabel=itemView.findViewById(R.id.quantityLabelCart);
            priceLabel=itemView.findViewById(R.id.priceLabelCart);
            removeFromCart=itemView.findViewById(R.id.removeFromCart);
            orderNow=itemView.findViewById(R.id.orderNowCart);
        }
    }
}
