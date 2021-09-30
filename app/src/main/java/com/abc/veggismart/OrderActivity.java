package com.abc.veggismart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.icu.text.CaseMap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.abc.veggismart.adapter.AllItemsAdapter;
import com.abc.veggismart.adapter.OrderItemsAdapter;
import com.abc.veggismart.model.AllItems;
import com.abc.veggismart.model.ItemStatus;
import com.abc.veggismart.model.User;
import com.abc.veggismart.sendNotificationPack.APIService;
import com.abc.veggismart.sendNotificationPack.Client;
import com.abc.veggismart.sendNotificationPack.Data;
import com.abc.veggismart.sendNotificationPack.MyResponse;
import com.abc.veggismart.sendNotificationPack.NotificationSender;
import com.abc.veggismart.sendNotificationPack.Token;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class OrderActivity extends AppCompatActivity {
    private Button confirmOrder;
    private TextView totalAmount;
    private RecyclerView orderRecycler;
    private ImageButton backButtonOrder;
    private FirebaseAuth firebaseAuth;
    private APIService apiService;
    public static OrderActivity instance;
    private DatabaseReference databaseReference;
    public static User currentUser = new User();
    public static boolean choice = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        instance = this;
        confirmOrder = (Button) findViewById(R.id.confirmOrder);
        totalAmount = (TextView) findViewById(R.id.totalAmountOrder);
        orderRecycler = (RecyclerView) findViewById(R.id.orderRecycler);
        backButtonOrder = (ImageButton) findViewById(R.id.backButtonOrder);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        double sum = 0.0;
        for (AllItems x : MainActivity.orderedItems) {
            sum += x.getPrice();
        }
        totalAmount.setText(String.valueOf(sum));
        apiService = Client.getClient("https://fcm.googleapis.com/").create(APIService.class);
        firebaseAuth = FirebaseAuth.getInstance();
        Query query = FirebaseDatabase.getInstance().getReference().child("Users").child(firebaseAuth.getUid().toString().trim());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = new User();
                user.setName(snapshot.child("name").getValue().toString().trim());
                user.setEmail(snapshot.child("email").getValue().toString().trim());
                user.setAddress(snapshot.child("address").getValue().toString().trim());
                user.setID(snapshot.child("id").getValue().toString().trim());
                user.setPhoneNumber(snapshot.child("phoneNumber").getValue().toString().trim());
                currentUser = user;
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        confirmOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Query query = databaseReference.child("AdminTokens");
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        for (DataSnapshot snapshot1 : snapshot.getChildren()) {
                            String token = snapshot1.child("token").getValue().toString().trim();
                            sendNotifications(token, "New Order Received", currentUser.getName() + " has placed an order");
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                Toast.makeText(OrderActivity.this, "Your order has been placed successfully", Toast.LENGTH_SHORT).show();
                Intent toCart = new Intent(OrderActivity.this, MyCartActivity.class);
                startActivity(toCart);

                updateToken();

                if(MyCartActivity.orderedAll)
                    MainActivity.cartItems.clear();
                else
                {
                    MainActivity.cartItems.removeAll(MainActivity.orderedItems);
                }
                FirebaseDatabase.getInstance().getReference("Orders").child(FirebaseAuth.getInstance().getUid()).child("User").setValue(currentUser);
                int i=0;
                for(AllItems x: MainActivity.orderList)
                {
                    i++;
                    ItemStatus y=new ItemStatus(x.getName(),x.getQuantity(),x.getPrice(),x.getNameMarathi(),x.getQuantityUnit(),0);
                    FirebaseDatabase.getInstance().getReference("Orders").child(FirebaseAuth.getInstance().getUid()).child("OrderList").setValue(currentUser);
                }
            }
        });
        backButtonOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toCart = new Intent(OrderActivity.this, MyOrdersActivity.class);
                startActivity(toCart);
            }
        });
        setOrderItemsRecycler(MainActivity.orderedItems);
    }

    private void setOrderItemsRecycler(ArrayList<AllItems> orderedItems) {
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        orderRecycler.setLayoutManager(layoutManager);
        OrderItemsAdapter orderItemsAdapter = new OrderItemsAdapter(this, orderedItems);
        orderRecycler.setAdapter(orderItemsAdapter);
        orderRecycler.setHasFixedSize(true);
        orderItemsAdapter.notifyDataSetChanged();
    }

    private void updateToken() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String newToken = FirebaseInstanceId.getInstance().getToken();
        Token token = new Token(newToken);
        FirebaseDatabase.getInstance().getReference("AdminTokens").child(user.getUid()).setValue(token);
    }

    private void sendNotifications(String userToken, String title, String message) {
        Data data = new Data(title, message);
        NotificationSender sender = new NotificationSender(data, userToken);
        apiService.sendNotifcation(sender).enqueue(new Callback<MyResponse>() {
            @Override
            public void onResponse(Call<MyResponse> call, Response<MyResponse> response) {
                if (response.code() == 200) {
                    if (response.body().success != 1) {
                        Toast.makeText(OrderActivity.this, "Failed ", Toast.LENGTH_LONG).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<MyResponse> call, Throwable t) {
            }
        });
    }

    public static OrderActivity getInstance() {
        return instance;
    }
}
