package com.example.delivery_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessagingService;

public class Request extends AppCompatActivity implements View.OnClickListener{

    EditText editTextStore, editTextItems, editTextStreet, editTextCity, editTextState, editTextTime_start, editTextTime_end;
    ProgressBar progressBar;
    FirebaseDatabase database;
    DatabaseReference reference;
    FirebaseAuth mAuth;
    req_status req_status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request);
        findViewById(R.id.textViewHomepage).setOnClickListener(this);
        findViewById(R.id.ButtonPlaceOrder).setOnClickListener(this);

        editTextStore = findViewById(R.id.store);
        editTextItems = findViewById(R.id.items);
        editTextStreet = findViewById(R.id.req_street);
        editTextCity = findViewById(R.id.req_city);
        editTextState = findViewById(R.id.req_state);
        editTextTime_start = findViewById(R.id.req_start_time);
        editTextTime_end = findViewById(R.id.req_end_time);
        mAuth = FirebaseAuth.getInstance();
    }

    private void place_order() {
        final String store = editTextStore.getText().toString().trim();
        final String items = editTextItems.getText().toString().trim();
        final String street = editTextStreet.getText().toString().trim();
        final String city = editTextCity.getText().toString().trim();
        final String state = editTextState.getText().toString().trim();
        final String start_time = editTextTime_start.getText().toString().trim();
        final String end_time = editTextTime_end.getText().toString().trim();
        final String dev_token;

        if(store.isEmpty()){
            editTextStore.setError("Store is required");
            editTextStore.requestFocus();
            return;
        }

        if (items.isEmpty()) {
            editTextItems.setError("Item is required");
            editTextItems.requestFocus();
            return;
        }

        if (street.isEmpty()) {
            editTextStreet.setError("Street is required");
            editTextStreet.requestFocus();
            return;
        }

        if (city.isEmpty()) {
            editTextCity.setError("City is required");
            editTextCity.requestFocus();
            return;
        }

        if (state.isEmpty()) {
            editTextState.setError("State is required");
            editTextState.requestFocus();
            return;
        }

        if(start_time.isEmpty()){
            editTextTime_start.setError("Start time is required");
            editTextTime_start.requestFocus();
            return;
        }

        if(end_time.isEmpty()){
            editTextTime_end.setError("End time is required");
            editTextTime_end.requestFocus();
            return;
        }

        if(Integer.parseInt(start_time) > 24 || Integer.parseInt(start_time) < 0){
            editTextTime_start.setError("Should between 0 and 24");
            editTextTime_start.requestFocus();
            return;
        }

        if(Integer.parseInt(end_time) > 24 || Integer.parseInt(end_time) < 0){
            editTextTime_end.setError("Should between 0 and 24");
            editTextTime_end.requestFocus();
            return;
        }

        dev_token = SharedPrefManager.getInstance(this).getToken();
        Address address = new Address(street,city,state);
        RequestInfoHelperClass requestInfoHelperClass = new RequestInfoHelperClass(store,items,start_time,end_time,dev_token,address,req_status.pending);
        FirebaseDatabase.getInstance().getReference("requests")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .setValue(requestInfoHelperClass).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
//                                    progressBar.setVisibility(View.GONE);
                if(task.isSuccessful()){
                    Toast.makeText(Request.this, "Your order is placed successfully!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Request.this, Homepage.class));
                }
            }
        });

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
