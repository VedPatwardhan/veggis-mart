package com.abc.veggismart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.abc.veggismart.model.AllItems;
import com.abc.veggismart.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class ProfileActivity extends AppCompatActivity {

    private TextView name;
    private TextView email;
    private TextView ID;
    private TextView phone;
    private TextView address;
    private Button signOut;
    private ImageButton backButton;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        name = (TextView) findViewById(R.id.nameProfile);
        email = (TextView) findViewById(R.id.emailProfile);
        ID = (TextView) findViewById(R.id.idProfile);
        phone = (TextView) findViewById(R.id.phoneNumberProfile);
        address = (TextView) findViewById(R.id.addressProfile);
        signOut = (Button) findViewById(R.id.signOutProfile);
        backButton = (ImageButton) findViewById(R.id.backButtonProfile);

        databaseReference= FirebaseDatabase.getInstance().getReference().child("Users");

        Query query=databaseReference.child(FirebaseAuth.getInstance().getUid());
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user=new User();
                user.setName(snapshot.child("name").getValue().toString().trim());
                user.setEmail(snapshot.child("email").getValue().toString().trim());
                user.setAddress(snapshot.child("address").getValue().toString().trim());
                user.setID(snapshot.child("id").getValue().toString().trim());
                user.setPhoneNumber(snapshot.child("phoneNumber").getValue().toString().trim());

                name.setText(user.getName());
                email.setText(user.getEmail());
                address.setText(user.getAddress());
                ID.setText(user.getID());
                phone.setText(user.getPhoneNumber());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Toast.makeText(ProfileActivity.this, "Signing out", Toast.LENGTH_SHORT).show();
                Intent toHome=new Intent(ProfileActivity.this,MainActivity.class);
                startActivity(toHome);
            }
        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toHome=new Intent(ProfileActivity.this,MainActivity.class);
                startActivity(toHome);
            }
        });

    }
}