package com.example.delivery_app;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Fulfill extends AppCompatActivity implements View.OnClickListener{

    EditText editTextFStore, editTextFStreet, editTextFCity, editTextFState, editTextFStart, editTextFEnd;
    ProgressBar progressBar;
    FirebaseAuth mAuth;
    ful_status ful_status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fulfill);
        findViewById(R.id.ful_textViewHomepage).setOnClickListener(this);
        findViewById(R.id.fPlaceRequest).setOnClickListener(this);

        editTextFStore = findViewById(R.id.ful_store);
        editTextFStreet = findViewById(R.id.ful_street);
        editTextFCity = findViewById(R.id.ful_city);
        editTextFState = findViewById(R.id.ful_state);
        editTextFStart = findViewById(R.id.ful_start_time);
        editTextFEnd = findViewById(R.id.ful_end_time);

        mAuth = FirebaseAuth.getInstance();
    }
    private void place_fulfillment() {
        final String store = editTextFStore.getText().toString().trim();
        final String street = editTextFStreet.getText().toString().trim();
        final String city = editTextFCity.getText().toString().trim();
        final String state = editTextFState.getText().toString().trim();
        final String start = editTextFStart.getText().toString().trim();
        final String end = editTextFEnd.getText().toString().trim();
        final String dev_token;

        if(store.isEmpty()){
            editTextFStore.setError("Store is required");
            editTextFStore.requestFocus();
            return;
        }

        if (street.isEmpty()) {
            editTextFStreet.setError("Street is required");
            editTextFStreet.requestFocus();
            return;
        }

        if (city.isEmpty()) {
            editTextFCity.setError("City is required");
            editTextFCity.requestFocus();
            return;
        }

        if (state.isEmpty()) {
            editTextFState.setError("State is required");
            editTextFState.requestFocus();
            return;
        }

        if(start.isEmpty()){
            editTextFStart.setError("Start time is required");
            editTextFStart.requestFocus();
            return;
        }

        if(end.isEmpty()){
            editTextFEnd.setError("End time is required");
            editTextFEnd.requestFocus();
            return;
        }

        if(Integer.parseInt(start) > 24 || Integer.parseInt(start) < 0){
            editTextFStart.setError("Should between 0 and 24");
            editTextFStart.requestFocus();
            return;
        }

        if(Integer.parseInt(end) > 24 || Integer.parseInt(end) < 0){
            editTextFEnd.setError("Should between 0 and 24");
            editTextFEnd.requestFocus();
            return;
        }
        dev_token = SharedPrefManager.getInstance(this).getToken();
        Address address = new Address(street,city,state);
        FulfillInfoHelper fulfillInfo = new FulfillInfoHelper(store,start,end,dev_token,address,ful_status.pending);
        FirebaseDatabase.getInstance().getReference("fulfill")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .setValue(fulfillInfo).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
//                                    progressBar.setVisibility(View.GONE);
                if(task.isSuccessful()){
                    Toast.makeText(Fulfill.this, "Your order is placed successfully!", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(Fulfill.this, Homepage.class));
                }
            }
        });

    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.ful_textViewHomepage:
                finish();
                break;
            case R.id.fPlaceRequest:
                place_fulfillment();
                break;
        }
    }
}
