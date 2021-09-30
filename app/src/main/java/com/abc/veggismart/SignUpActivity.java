package com.abc.veggismart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.abc.veggismart.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

public class SignUpActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText name,id,password,phoneNumber,email,address;
    private ProgressBar loading;
    private Button register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        mAuth=FirebaseAuth.getInstance();
        name=(EditText)findViewById(R.id.nameRegister);
        id=(EditText)findViewById(R.id.idRegister);
        password=(EditText)findViewById(R.id.passwordRegister);
        phoneNumber=(EditText)findViewById(R.id.phoneNumberRegister);
        email=(EditText)findViewById(R.id.emailRegister);
        address=(EditText)findViewById(R.id.addressRegister);
        loading=(ProgressBar) findViewById(R.id.progressBarRegister);
        loading.setVisibility(View.INVISIBLE);
        register=(Button)findViewById(R.id.registerButton);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
    }

    private void registerUser() {
        final String email1=email.getText().toString().trim();
        final String name1=name.getText().toString().trim();
        final String id1=id.getText().toString().trim();
        final String phoneNumber1=phoneNumber.getText().toString().trim();
        final String address1=address.getText().toString().trim();
        String password1=password.getText().toString().trim();

        if(name1.isEmpty())
        {
            name.setError("Name is required");
            name.requestFocus();
            return;
        }
        if(id1.isEmpty())
        {
            id.setError("ID is required");
            id.requestFocus();
            return;
        }
        if(email1.isEmpty())
        {
            email.setError("Email ID is required");
            email.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email1).matches())
        {
            email.setError("Please provide a valid email ID");
            email.requestFocus();
            return;
        }
        if(password1.isEmpty())
        {
            password.setError("Password is required");
            password.requestFocus();
            return;
        }
        if(password1.length()<6)
        {
            password.setError("Password should be of minimum 6 characters");
            password.requestFocus();
            return;
        }
        if(phoneNumber1.isEmpty())
        {
            phoneNumber.setError("Phone Number is required");
            phoneNumber.requestFocus();
            return;
        }
        if(!Patterns.PHONE.matcher(phoneNumber1).matches()) {
            phoneNumber.setError("Please provide a valid phone number");
            phoneNumber.requestFocus();
            return;
        }
        if(address1.isEmpty())
        {
            address.setError("Address is required");
            address.requestFocus();
            return;
        }
        mAuth.createUserWithEmailAndPassword(email1,password1)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            User usr=new User(name1, email1, id1, phoneNumber1, address1);
                            FirebaseDatabase.getInstance().getReference("Users")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(usr).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful())
                                    {
                                        Toast.makeText(SignUpActivity.this,"Your account has been created",Toast.LENGTH_LONG).show();
                                        loading.setVisibility(View.VISIBLE);
                                        Intent backToMain=new Intent(SignUpActivity.this,LoginActivity.class);
                                        startActivity(backToMain);
                                    }
                                    else
                                    {
                                        Toast.makeText(SignUpActivity.this,"Failed to register! try again!",Toast.LENGTH_LONG).show();
                                        loading.setVisibility(View.GONE);
                                    }
                                }
                            });
                        }
                        else
                        {
                            Toast.makeText(SignUpActivity.this,"Failed to register! try again!",Toast.LENGTH_LONG).show();
                            loading.setVisibility(View.GONE);
                        }
                    }
                });
    }
}