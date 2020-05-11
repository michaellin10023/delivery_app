package com.example.delivery_app;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;
import java.util.Random;

public class MyMessagingService extends FirebaseMessagingService {

    private static final String TAG = MyMessagingService.class.getSimpleName();
    public static final String SEEKER_BROADCAST = "seekerbroadcast";
    public static final String VOLUNTEER_BROADCAST = "volunteerbroadcast";
    public static final String CONFIRMATION_BROADCAST = "confirmbroadcast";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {

        Log.d("seeker:",Boolean.toString(Request.seeker));
        Log.d("vol:",Boolean.toString(Fulfill.volunteer));

        if(Request.seeker == false && Fulfill.volunteer == false){

            Map<String,String> extraData = remoteMessage.getData();
            String uid = extraData.get("uid");
            if(uid == null)
                Log.d("did i get","this is null");
            else
                Log.d("uid:",uid);
            Intent intent = new Intent(CONFIRMATION_BROADCAST);
            intent.putExtra("uid",uid);
            getApplicationContext().sendBroadcast(intent);
        }

        showNotification(remoteMessage.getNotification().getTitle(),remoteMessage.getNotification().getBody());

        if(Request.seeker == true){
            Request.seeker = false;
            String uid = remoteMessage.getData().get("UID");
            Intent intent = new Intent(SEEKER_BROADCAST);
            intent.putExtra("uid",uid);
            getApplicationContext().sendBroadcast(intent);
        }

        if(Fulfill.volunteer == true){
            Fulfill.volunteer = false;
            String uid = remoteMessage.getData().get("UID");
            Intent intent = new Intent(VOLUNTEER_BROADCAST);
            intent.putExtra("uid",uid);
            getApplicationContext().sendBroadcast(intent);
        }


    }

    private void showNotification(String title, String body){
        NotificationManager notificationManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        String NOTIFICATION_CHANNEL_ID = "com.example.test";

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel notificationChannel = new NotificationChannel(NOTIFICATION_CHANNEL_ID,"Notification",
            NotificationManager.IMPORTANCE_DEFAULT);

            notificationChannel.setDescription("example channel");
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.BLUE);
            notificationChannel.setVibrationPattern(new long[]{0,1000,500,1000});
            notificationChannel.enableLights(true);
            notificationManager.createNotificationChannel(notificationChannel);
        }

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID);

        notificationBuilder.setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_ALL)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.drawable.ic_notification)
                .setContentTitle(title)
                .setContentText(body)
                .setContentInfo("Info");

        notificationManager.notify(new Random().nextInt(),notificationBuilder.build());
    }

    /**
     * Called if InstanceID token is updated. This may occur if the security of
     *      * the previous token had been compromised. Note that this is called when the InstanceID token
     *      * is initially generated so this is where you would retrieve the token.
     */
    @Override
    public void onNewToken(String token) {

        Log.d(TAG, "Refreshed token: " + token);
        storeToken(token);
    }

    private void storeToken(String token){
        SharedPrefManager.getInstance(getApplicationContext()).storeToken(token);
    }


}
