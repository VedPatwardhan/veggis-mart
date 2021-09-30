package com.abc.veggismart;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.abc.veggismart.adapter.ItemStatusAdapter;
import com.abc.veggismart.adapter.OrderItemsAdapter;
import com.abc.veggismart.model.AllItems;
import com.abc.veggismart.model.ItemStatus;

import java.util.ArrayList;

public class MyOrdersActivity extends AppCompatActivity {


    private ArrayList<ItemStatus> orders;
    private ImageButton backButtonOrderStatus;
    private RecyclerView orderStatusRecycler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_orders);

        backButtonOrderStatus=(ImageButton)findViewById(R.id.backButtonMyOrder);
        backButtonOrderStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toHome=new Intent(MyOrdersActivity.this,MainActivity.class);
                startActivity(toHome);
            }
        });
        orders=new ArrayList<>();
        for(AllItems x:MainActivity.orderList)
        {
            ItemStatus y=new ItemStatus(x.getName(),x.getQuantity(),x.getPrice(),x.getNameMarathi(),x.getQuantityUnit(),0);
            orders.add(y);
        }
        orderStatusRecycler=(RecyclerView)findViewById(R.id.orderStatusRecycler);
        setMyOrdersRecycler(orders);

    }

    private void setMyOrdersRecycler(ArrayList<ItemStatus> orders) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        orderStatusRecycler.setLayoutManager(layoutManager);
        ItemStatusAdapter itemStatusAdapter = new ItemStatusAdapter(this, orders);
        orderStatusRecycler.setAdapter(itemStatusAdapter);
        orderStatusRecycler.setHasFixedSize(true);
        itemStatusAdapter.notifyDataSetChanged();
    }
}