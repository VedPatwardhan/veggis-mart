package com.abc.veggismart;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.abc.veggismart.adapter.AllItemsAdapter;
import com.abc.veggismart.adapter.CartItemsAdapter;
import com.abc.veggismart.model.AllItems;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class MyCartActivity extends AppCompatActivity {
    private ImageButton backButton;
    public ArrayList<AllItems> cartItemsList;
    private RecyclerView cartItemsRecycler;
    private TextView totalPrice;
    CartItemsAdapter cartItemsAdapter;
    private static MyCartActivity instance;
    public ProgressBar progressBar;
    private Button orderAll;
    public static boolean orderedAll=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_cart);
        orderAll=(Button)findViewById(R.id.orderAll);
        progressBar=(ProgressBar)findViewById(R.id.progressBar);
        progressBar.setVisibility(View.GONE);
        instance=this;
        backButton=(ImageButton)findViewById(R.id.backButtonCart);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent toHome=new Intent(MyCartActivity.this,MainActivity.class);
                startActivity(toHome);
            }
        });
        cartItemsRecycler=(RecyclerView)findViewById(R.id.cartItemsRecycler);
        cartItemsList=new ArrayList<>();
        cartItemsList=MainActivity.cartItems;
        totalPrice=(TextView)findViewById(R.id.totalPriceCart);
        double sum=0.0;
        for(AllItems x:cartItemsList)
        {
            sum+=x.getPrice();
        }
        totalPrice.setText(String.valueOf(sum));
        setCartRecycler(cartItemsList);
        orderAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.orderList.addAll(cartItemsList);
                MainActivity.orderedItems.addAll(cartItemsList);
                orderedAll=true;
                Intent toOrder=new Intent(MyCartActivity.this,OrderActivity.class);
                startActivity(toOrder);
            }
        });

    }

    public void setCartRecycler(ArrayList<AllItems> cartItemsList) {
        RecyclerView.LayoutManager layoutManager= new LinearLayoutManager(this);
        cartItemsRecycler.setLayoutManager(layoutManager);
        cartItemsAdapter=new CartItemsAdapter(this,cartItemsList);
        cartItemsRecycler.setAdapter(cartItemsAdapter);
        cartItemsRecycler.setHasFixedSize(true);
        cartItemsAdapter.notifyDataSetChanged();
    }

    public static MyCartActivity getInstance()
    {
        return instance;
    }
}