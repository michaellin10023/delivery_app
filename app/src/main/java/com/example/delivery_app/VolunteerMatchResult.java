package com.example.delivery_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class VolunteerMatchResult extends AppCompatActivity implements View.OnClickListener{

    private EditText etName, etAddress, etPhone, etItem;
    final FirebaseDatabase database = FirebaseDatabase.getInstance();
    String username, address, email, phone, item;
    DatabaseReference ref;
    String uid,mergeUID;
    private RequestQueue mRequestQueue;
    private String URL = "https://fcm.googleapis.com/fcm/send";
    String path;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volunteer_match_result);
        etName = findViewById(R.id.etName_vol);
        etAddress = findViewById(R.id.etAddress_vol);
        etPhone = findViewById(R.id.etphone_vol);
        etItem = findViewById(R.id.etItem_vol);
        findViewById(R.id.btnAccept_vol).setOnClickListener(this);
        findViewById(R.id.btnDecline_vol).setOnClickListener(this);
        findViewById(R.id.tvHomepage_vol).setOnClickListener(this);
        mRequestQueue = Volley.newRequestQueue(this);




        Intent i = getIntent();
        String uid = i.getStringExtra("uid");
        String path = "users_info/" + uid;
        mergeUID = FirebaseAuth.getInstance().getCurrentUser().getUid() + uid;

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
        path = "requests/" + uid;
        ref = database.getReference(path);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                RequestInfoHelperClass requestInfoHelperClass = dataSnapshot.getValue(RequestInfoHelperClass.class);
                item = requestInfoHelperClass.getItems();
                etItem.setText(item);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
    void sendNotification(){
        JSONObject mainObj = new JSONObject();
        try{
            mainObj.put("to","/topics/" + mergeUID);
            JSONObject notificationObj = new JSONObject();
            notificationObj.put("title","Confirmation!");
            notificationObj.put("body","Volunteer confirmed your order!");
            mainObj.put("notification",notificationObj);
            uid = FirebaseAuth.getInstance().getCurrentUser().getUid();
            JSONObject extraData = new JSONObject();
            extraData.put("uid",uid);
            mainObj.put("data",extraData);

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, URL,
                    mainObj, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            }
            ){
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String,String> header = new HashMap<>();
                    header.put("content-type","application/json");
                    header.put("authorization","key=AAAA85z6-pQ:APA91bGe_IpA44vYlIgULRgp_l8rO0y4Ymcb-eE2iW1IZ3IFDh_yrksnpIjcMW4uicW-IQMK8Fnn7dzfy-n6pCQuXkXT4xAtnjSIqc1p9mg1nccLAhDe95AE7ecejmLETd4LdunVan4S");
                    return header;
                }
            };
            mRequestQueue.add(request);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    void accept(){
        Log.d("mergeUID",mergeUID);
        path = "fulfill/" + FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseDatabase.getInstance().getReference(path).child("ful_status").setValue(ful_status.confirmed);
        path = "request/" + uid;
        FirebaseDatabase.getInstance().getReference(path).child("req_status").setValue(req_status.matched);
        sendNotification();
    }

    void decline(){
        path = "fulfill/" + FirebaseAuth.getInstance().getCurrentUser().getUid();
        FirebaseDatabase.getInstance().getReference(path).child("ful_status").setValue(ful_status.pending);
        path = "requests/" + uid;
        FirebaseDatabase.getInstance().getReference(path).child("req_status").setValue(req_status.pending);

    }


    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.tvHomepage_vol:
                finish();
                break;
            case R.id.btnAccept_vol:
                accept();
                break;
            case R.id.btnDecline_vol:
                decline();
                break;
        }
    }
}
