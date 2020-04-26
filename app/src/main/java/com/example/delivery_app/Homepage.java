package com.example.delivery_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class Homepage extends AppCompatActivity implements View.OnClickListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        findViewById(R.id.buttonRequest).setOnClickListener(this);
        findViewById(R.id.buttonLogout).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.buttonRequest:
                startActivity(new Intent(Homepage.this, Request.class));
                break;
            case R.id.buttonLogout:
                startActivity(new Intent(Homepage.this, MainActivity.class));
                break;
        }
    }
}
