package com.example.delivery_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.Map;

public class Homepage extends AppCompatActivity implements View.OnClickListener{


    private BroadcastReceiver seekerBroadcastReceiver;
    private BroadcastReceiver volunteerBroadcastReceiver;
    private BroadcastReceiver confirmationBroadcastReceiver;
    String mergeUID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        findViewById(R.id.buttonRequest).setOnClickListener(this);
        findViewById(R.id.buttonFulfill).setOnClickListener(this);
        findViewById(R.id.buttonLogout).setOnClickListener(this);

        seekerBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String path = "requests/" + FirebaseAuth.getInstance().getCurrentUser().getUid();
                FirebaseDatabase.getInstance().getReference(path).child("req_status").setValue(req_status.matched);
                String uid = intent.getStringExtra("uid");
                mergeUID = uid + FirebaseAuth.getInstance().getCurrentUser().getUid();
                FirebaseMessaging.getInstance().subscribeToTopic(mergeUID);

            }
        };

        volunteerBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String path = "fulfill/" + FirebaseAuth.getInstance().getCurrentUser().getUid();
                FirebaseDatabase.getInstance().getReference(path).child("ful_status").setValue(ful_status.matched);
                String uid = intent.getStringExtra("uid");
//                Log.d("check uid in homepage",uid);
                Intent newIntent = new Intent(Homepage.this,VolunteerMatchResult.class);
                newIntent.putExtra("uid",uid);
                startActivity(newIntent);
            }
        };


        confirmationBroadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {

                String uid = intent.getStringExtra("uid");
//                Log.d("check uid in homepage",uid);
                Intent newIntent = new Intent(Homepage.this,MatchResult.class);
                newIntent.putExtra("uid",uid);
                startActivity(newIntent);
            }
        };

        registerReceiver(seekerBroadcastReceiver, new IntentFilter(MyMessagingService.SEEKER_BROADCAST));
        registerReceiver(volunteerBroadcastReceiver, new IntentFilter(MyMessagingService.VOLUNTEER_BROADCAST));
        registerReceiver(confirmationBroadcastReceiver, new IntentFilter(MyMessagingService.CONFIRMATION_BROADCAST));
    }

    @Override
    protected void onStop() {
        super.onStop();
        unregisterReceiver(seekerBroadcastReceiver);
        unregisterReceiver(volunteerBroadcastReceiver);
        unregisterReceiver(confirmationBroadcastReceiver);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.buttonFulfill:
                startActivity(new Intent(Homepage.this, Fulfill.class));
                break;
            case R.id.buttonRequest:
                startActivity(new Intent(Homepage.this, Request.class));
                break;
            case R.id.buttonLogout:
                startActivity(new Intent(Homepage.this, MainActivity.class));
                break;
        }
    }

}
