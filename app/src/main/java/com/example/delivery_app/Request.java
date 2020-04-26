package com.example.delivery_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Request extends AppCompatActivity implements View.OnClickListener{

    EditText editTextStore, editTextItems, editTextAddress, editTextTime_window;
    ProgressBar progressBar;
    FirebaseDatabase database;
    DatabaseReference reference;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);
        findViewById(R.id.textViewHomepage).setOnClickListener(this);
        findViewById(R.id.ButtonPlaceOrder).setOnClickListener(this);

        editTextStore = findViewById(R.id.store);
        editTextItems = findViewById(R.id.items);
        editTextAddress = findViewById(R.id.address);
        editTextTime_window = findViewById(R.id.time_window);
        mAuth = FirebaseAuth.getInstance();
    }

    private void place_order() {
        final String store = editTextStore.getText().toString().trim();
        final String items = editTextItems.getText().toString().trim();
        final String address = editTextAddress.getText().toString().trim();
        final String time_window = editTextTime_window.getText().toString().trim();

        RequestInfoHelperClass requestInfoHelperClass = new RequestInfoHelperClass(store,items,address,time_window);
        FirebaseDatabase.getInstance().getReference("users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("request")
                .push().setValue(requestInfoHelperClass).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
//                                    progressBar.setVisibility(View.GONE);
                if(task.isSuccessful()){
                    Toast.makeText(Request.this, "Your order is placed successfully!", Toast.LENGTH_SHORT).show();
                }
            }
        });;

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.textViewHomepage:
                finish();
                break;
            case R.id.ButtonPlaceOrder:
                place_order();
                break;
        }
    }


}
