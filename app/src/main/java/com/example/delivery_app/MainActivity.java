package com.example.delivery_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    EditText editTextUsername, editTextPassword, editTextEmail, editTextAddress, editTextPhoneNo;
    ProgressBar progressBar;
    FirebaseDatabase database;
    DatabaseReference reference;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextEmail =  findViewById(R.id.editTextEmail);
        editTextAddress = findViewById(R.id.editTextAddress);
        editTextPhoneNo = findViewById(R.id.editTextPhoneNo);
        findViewById(R.id.buttonSignUp).setOnClickListener(this);
        findViewById(R.id.textViewLogin).setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();
       reference = FirebaseDatabase.getInstance().getReference();

    }

    @Override
    protected void onStart() {
        super.onStart();

        if(mAuth.getCurrentUser() != null){            //handle the already login user

//            finish();
//            startActivity(new Intent(MainActivity.this, Login.class));
        }
    }
    private void registerUser() {
        final String username = editTextUsername.getText().toString().trim();
        final String email = editTextEmail.getText().toString().trim();
        final String password = editTextPassword.getText().toString().trim();
        final String phoneNo = editTextPhoneNo.getText().toString().trim();
        final String address = editTextAddress.getText().toString().trim();


        if(username.isEmpty()){
            editTextUsername.setError("Username is required");
            editTextUsername.requestFocus();
        }

        if (email.isEmpty()) {
            editTextEmail.setError("Email is required");
            editTextEmail.requestFocus();
            return;
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("Please enter a valid email");
            editTextEmail.requestFocus();
            return;
        }

        if (password.isEmpty()) {
            editTextPassword.setError("Password is required");
            editTextPassword.requestFocus();
            return;
        }

        if (password.length() < 6) {
            editTextPassword.setError("Minimum lenght of password should be 6");
            editTextPassword.requestFocus();
            return;
        }

        if(address.isEmpty()){
            editTextAddress.setError("Address is required");
            editTextAddress.requestFocus();
        }

        if(phoneNo.isEmpty()){
            editTextPhoneNo.setError("PhoneNo is required");
            editTextPhoneNo.requestFocus();
            return;
        }

        if(phoneNo.length() != 9){
            editTextPhoneNo.setError("enter a valid phone number");
            editTextPhoneNo.requestFocus();
            return;
        }

//      progressBar.setVisibility(View.VISIBLE);
//        System.out.print("Print: " + FirebaseDatabase.getInstance().getApp().getOptions().getProjectId());

        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            UserHelperClass helperClass = new UserHelperClass(username, email, address, phoneNo);
//                            FirebaseDatabase.getInstance().getReference("users").setValue("testing");
                            FirebaseDatabase.getInstance().getReference("users_info")
                                    .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                                    .setValue(helperClass).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
//                                    progressBar.setVisibility(View.GONE);
                                    if(task.isSuccessful()){
                                        Toast.makeText(MainActivity.this, "Signed up successfully", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(MainActivity.this, Homepage.class));
                                    }
                                }
                            });
                        }else{
                            Toast.makeText(MainActivity.this, task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }


    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.buttonSignUp:
                registerUser();
                break;
            case R.id.textViewLogin:
                startActivity(new Intent(MainActivity.this,Login.class));
                break;

        }
    }
}