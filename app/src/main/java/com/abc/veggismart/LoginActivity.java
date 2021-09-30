package com.abc.veggismart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    private EditText email;
    private EditText password;
    private Button loginButton;
    private TextView signUP;
    private TextView forgotPassword;
    private ProgressBar loading;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email=(EditText)findViewById(R.id.emailIDLogin);
        password=(EditText)findViewById(R.id.passwordLogin);
        loginButton=(Button)findViewById(R.id.loginButton);
        signUP=(TextView)findViewById(R.id.registerLogin);
        forgotPassword=(TextView)findViewById(R.id.forgotPasswordLogin);
        loading=(ProgressBar)findViewById(R.id.loadingLogin);
        loading.setVisibility(View.GONE);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userLogin();
            }
        });
        signUP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toRegister=new Intent(LoginActivity.this,SignUpActivity.class);
                startActivity(toRegister);
            }
        });
        forgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toForgotPassword=new Intent(LoginActivity.this,ForgotPasswordActivity.class);
                startActivity(toForgotPassword);
            }
        });
    }
    private void userLogin()
    {
        String email1, password1;
        email1=email.getText().toString().trim();
        password1=password.getText().toString().trim();

        if(email1.isEmpty())
        {
            email.setError("Email is required");
            email.requestFocus();
            return;
        }
        if(!Patterns.EMAIL_ADDRESS.matcher(email1).matches())
        {
            email.setError("Please enter a valid email id");
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
            password.setError("Length of the password should be atleast 6 characters");
            password.requestFocus();
            return;
        }
        loading.setVisibility(View.VISIBLE);
        mAuth= FirebaseAuth.getInstance();
        mAuth.signInWithEmailAndPassword(email1,password1).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    FirebaseUser user=FirebaseAuth.getInstance().getCurrentUser();
                    if(user.isEmailVerified())
                    {
                        Toast.makeText(LoginActivity.this, "Signed in successfully",Toast.LENGTH_LONG).show();
                        Intent toHomePage=new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(toHomePage);
                    }
                    else
                    {
                        loading.setVisibility(View.GONE);
                        user.sendEmailVerification();
                        Toast.makeText(LoginActivity.this,"Check your email to verify your account",Toast.LENGTH_LONG).show();
                    }
                }
                else
                {
                    loading.setVisibility(View.GONE);
                    Toast.makeText(LoginActivity.this, "Sign-in failed! please try again!", Toast.LENGTH_LONG).show();
                }
            }
        });

    }
}