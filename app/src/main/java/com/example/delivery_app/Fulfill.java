package com.example.delivery_app;

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

    EditText editTextFStore, editTextFAddress, editTextFTimeWindow;
    ProgressBar progressBar;
    FirebaseDatabase database;
    DatabaseReference reference;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fulfill);
        findViewById(R.id.textViewHomepage).setOnClickListener(this);
        findViewById(R.id.fPlaceRequest).setOnClickListener(this);

        editTextFStore = findViewById(R.id.fstore);
        editTextFAddress = findViewById(R.id.faddress);
        editTextFTimeWindow = findViewById(R.id.fTimeWindow);
        mAuth = FirebaseAuth.getInstance();
    }
    private void place_fulfillment() {
        final String store = editTextFStore.getText().toString().trim();
        final String myAddress = editTextFAddress.getText().toString().trim();
        final String timeWindow = editTextFTimeWindow.getText().toString().trim();

        FulfillInfoHelper fulfillInfo = new FulfillInfoHelper(store,myAddress,timeWindow);
        FirebaseDatabase.getInstance().getReference("users")
                .child(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("fulfill")
                .push().setValue(fulfillInfo).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
//                                    progressBar.setVisibility(View.GONE);
                if(task.isSuccessful()){
                    Toast.makeText(Fulfill.this, "Your fulfillment order is placed for matching!", Toast.LENGTH_SHORT).show();
                }
            }
        });;

    }
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.textViewHomepage:
                finish();
                break;
            case R.id.fPlaceRequest:
                place_fulfillment();
                break;
        }
    }
}
