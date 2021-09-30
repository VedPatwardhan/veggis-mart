package com.abc.veggismart;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.abc.veggismart.model.AllItems;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.sql.Array;
import java.util.ArrayList;

public class ItemDetailsActivity extends AppCompatActivity {
    ImageView imageDetails;
    ImageButton backButtonDetails;
    TextView nameDetails;
    TextView nameMarathiDetails;
    TextView quantityUnitDetails;
    TextView priceDetails;
    Button orderNow;
    Button addToCart;
    Spinner quantity;
    String name;
    String nameMarathi;
    String quantitySelected;
    String quantityUnit;
    String priceSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_details);
        imageDetails=(ImageView)findViewById(R.id.itemImageDetail);
        backButtonDetails=(ImageButton)findViewById(R.id.backButtonDetails);
        nameDetails=(TextView)findViewById(R.id.itemNameDetails);
        nameMarathiDetails=(TextView)findViewById(R.id.itemNameMarathiDetails);
        quantityUnitDetails=(TextView)findViewById(R.id.quantityUnitDetails);
        priceDetails=(TextView)findViewById(R.id.priceDetails);
        orderNow=(Button)findViewById(R.id.orderNowDetails);
        addToCart=(Button)findViewById(R.id.addToCartDetails);
        quantity=(Spinner)findViewById(R.id.quantitySpinner);

        Glide.with(ItemDetailsActivity.this)
                .load(MainActivity.clickedObject.getImageURL())
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
                .into(imageDetails);
        backButtonDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toHome=new Intent(ItemDetailsActivity.this, MainActivity.class);
                startActivity(toHome);
            }
        });
        nameDetails.setText(MainActivity.clickedObject.getName());
        nameMarathiDetails.setText(MainActivity.clickedObject.getNameMarathi());
        quantityUnitDetails.setText(MainActivity.clickedObject.getQuantityUnit());
        quantityUnit=MainActivity.clickedObject.getQuantityUnit();
        name=MainActivity.clickedObject.getName();
        nameMarathi=MainActivity.clickedObject.getNameMarathi();
        ArrayList<String> quantitiesForSpinner=new ArrayList<>();
        for(int i=1;i<10;i++)
        {
            quantitiesForSpinner.add(Double.toString(MainActivity.clickedObject.getQuantity()*i));
        }
        ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line,quantitiesForSpinner);
        quantity.setAdapter(arrayAdapter);
        quantity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                priceDetails.setText(String.valueOf(Double.parseDouble(parent.getItemAtPosition(position).toString())*MainActivity.clickedObject.getPrice()));
                priceSelected=priceDetails.getText().toString();
                quantitySelected=parent.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ItemDetailsActivity.this,"Item has been added to cart", Toast.LENGTH_SHORT).show();
                AllItems cartItem=new AllItems(1,MainActivity.clickedObject.getImageURL(),name,Double.parseDouble(quantitySelected),Double.parseDouble(priceSelected),nameMarathi,quantityUnit);
                MainActivity.cartItems.add(cartItem);
            }
        });
        orderNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.orderList.add(MainActivity.clickedObject);
                MainActivity.orderedItems.clear();
                MainActivity.orderedItems.add(MainActivity.clickedObject);
                Intent toOrder=new Intent(ItemDetailsActivity.this,OrderActivity.class);
                startActivity(toOrder);
            }
        });

    }
}