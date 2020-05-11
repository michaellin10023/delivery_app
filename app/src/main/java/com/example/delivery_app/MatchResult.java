package com.example.delivery_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MatchResult extends AppCompatActivity implements View.OnClickListener{

    private EditText etName, etAddress, etPhone;
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    String username, address, email, phone;
    DatabaseReference ref;
    String path,uid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_match_result);
        etName = findViewById(R.id.etName);
        etAddress = findViewById(R.id.etAddress);
        etPhone = findViewById(R.id.etphone);
        findViewById(R.id.tvHomepage).setOnClickListener(this);
        findViewById(R.id.btnAccept).setOnClickListener(this);
        findViewById(R.id.btnDecline).setOnClickListener(this);

        Intent i = getIntent();
        uid = i.getStringExtra("uid");
        String path = "users_info/" + uid;
        Toast.makeText(MatchResult.this,path,Toast.LENGTH_SHORT).show();
        ref = database.getReference(path);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                UserHelperClass userHelperClass = dataSnapshot.getValue(UserHelperClass.class);
                username = userHelperClass.getUsername();
                address = userHelperClass.getAddress();
                phone = userHelperClass.getPhone();
                etName.setText(username);
                etAddress.setText(address);
                etPhone.setText(phone);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    void accept(){
        path = "fulfill/" + uid;
        FirebaseDatabase.getInstance().getReference(path).child("ful_status").setValue(ful_status.completed);
        path = "requests/" + FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseDatabase.getInstance().getReference(path).child("req_status").setValue(req_status.completed);
    }

    void decline(){
        path = "fulfill/" + uid;
        FirebaseDatabase.getInstance().getReference(path).child("ful_status").setValue(ful_status.pending);
        path = "requests/" + FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseDatabase.getInstance().getReference(path).child("req_status").setValue(req_status.pending);
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.tvHomepage:
                finish();
                break;
            case R.id.btnAccept:
                accept();
                break;
            case R.id.btnDecline:
                decline();
                break;
        }
    }
}
