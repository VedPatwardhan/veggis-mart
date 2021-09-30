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
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class ForgotPasswordActivity extends AppCompatActivity {

    private EditText email;
    private Button reset;
    private ProgressBar progressBar;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);
        email=(EditText)findViewById(R.id.emailReset);
        reset=(Button)findViewById(R.id.buttonReset);
        progressBar=(ProgressBar)findViewById(R.id.progressBarReset);
        mAuth=FirebaseAuth.getInstance();
        reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetPassword();
            }
        });
    }

    private void resetPassword()
    {
        String email1;
        email1=email.getText().toString().trim();
        if(email1.isEmpty())
        {
            email.setError("Email is required");
            email.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email1).matches())
        {
            email.setError("Please provide a valid email");
            email.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        mAuth.sendPasswordResetEmail(email1).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if(task.isSuccessful())
                {
                    Toast.makeText(ForgotPasswordActivity.this,"Reset link has been sent to your email",Toast.LENGTH_LONG).show();
                    Intent toLogin=new Intent(ForgotPasswordActivity.this,MainActivity.class);
                    startActivity(toLogin);
                }
                else
                {
                    Toast.makeText(ForgotPasswordActivity.this,"Reset link could not be sent, please try again later",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}