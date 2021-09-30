package com.abc.veggismart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SearchView;

import com.abc.veggismart.adapter.AllItemsAdapter;
import com.abc.veggismart.model.AllItems;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView itemsRecycler;
    AllItemsAdapter allItemsAdapter;
    List<AllItems> allItemsList;
    List<AllItems> currentItems;
    List<AllItems> toBeDisplayed;
    ImageView profile;
    ImageView cart;
    ImageView orders;
    EditText searchItem;

    DatabaseReference databaseReference;
    private static MainActivity instance;
    public static AllItems clickedObject;
    public static ArrayList<AllItems> orderedItems=new ArrayList<>();
    public static ArrayList<AllItems> cartItems=new ArrayList<>();
    public static ArrayList<AllItems> orderList=new ArrayList<>();
    public static MainActivity getInstance() {
        return instance;
    }
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(FirebaseAuth.getInstance().getCurrentUser()==null) {
            Intent loginFirst = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(loginFirst);
        }
        instance=this;
        profile=(ImageView)findViewById(R.id.profile);
        cart=(ImageView)findViewById(R.id.cart);
        orders=(ImageView)findViewById(R.id.orderImageHome);
        searchItem=(EditText)findViewById(R.id.searchItem);

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toProfile=new Intent(MainActivity.this, ProfileActivity.class);
                startActivity(toProfile);
            }
        });

        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toCart=new Intent(MainActivity.this,MyCartActivity.class);
                startActivity(toCart);
            }
        });

        orders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toOrder=new Intent(MainActivity.this,MyOrdersActivity.class);
                startActivity(toOrder);
            }
        });

        itemsRecycler=findViewById(R.id.itemsRecycler);

        allItemsList=new ArrayList<>();
        currentItems=new ArrayList<>();
        toBeDisplayed=new ArrayList<>();

        clearAllItems();
        databaseReference=FirebaseDatabase.getInstance().getReference();
        getDataFromFirebase();
        setItemsRecycler((ArrayList<AllItems>)allItemsList);
        searchItem.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                if(s.toString().isEmpty())
                {
                    toBeDisplayed.clear();
                    toBeDisplayed.addAll(allItemsList);
                    setItemsRecycler((ArrayList<AllItems>)toBeDisplayed);
                }
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                toBeDisplayed.clear();
            }

            @Override
            public void afterTextChanged(Editable s) {
                toBeDisplayed.clear();
                if(s.toString().isEmpty())
                {
                    toBeDisplayed.addAll(allItemsList);
                }
                else
                {
                    for(AllItems x: allItemsList)
                    {
                        if(x.getName().toLowerCase().contains(s.toString().toLowerCase()))
                        {
                            toBeDisplayed.add(x);
                        }
                    }
                }
                setItemsRecycler((ArrayList<AllItems>)toBeDisplayed);
            }
        });

    }


    private void setItemsRecycler(ArrayList<AllItems> allItemsList) {
        RecyclerView.LayoutManager layoutManager= new LinearLayoutManager(this);
        itemsRecycler.setLayoutManager(layoutManager);
        allItemsAdapter=new AllItemsAdapter(this,allItemsList);
        itemsRecycler.setAdapter(allItemsAdapter);
        itemsRecycler.setHasFixedSize(true);
        allItemsAdapter.notifyDataSetChanged();
    }


    private void getDataFromFirebase()
    {
        Query query=databaseReference.child("items");
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                clearAllItems();
                for(DataSnapshot snapshot1:snapshot.getChildren())
                {
                    AllItems allItems=new AllItems();
                    allItems.setId(Integer.parseInt(snapshot1.child("id").getValue().toString()));
                    allItems.setImageURL(snapshot1.child("imageURL").getValue().toString());
                    allItems.setName(snapshot1.child("name").getValue().toString());
                    allItems.setNameMarathi(snapshot1.child("nameMarathi").getValue().toString());
                    allItems.setPrice(Double.parseDouble(snapshot1.child("price").getValue().toString()));
                    allItems.setQuantity(Double.parseDouble(snapshot1.child("quantity").getValue().toString()));
                    allItems.setQuantityUnit(snapshot1.child("quantityUnit").getValue().toString());
                    allItemsList.add(allItems);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    private void clearAllItems()
    {
        if(allItemsList!=null)
        {
            allItemsList.clear();
        }
        if(allItemsAdapter!=null)
        {
            allItemsAdapter.notifyDataSetChanged();
        }
    }

    public void goDetail(AllItems ob)
    {
        Intent toDetail=new Intent(MainActivity.this,ItemDetailsActivity.class);
        startActivity(toDetail);
        clickedObject=ob;
    }

}
