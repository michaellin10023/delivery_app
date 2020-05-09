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

import java.util.Random;

public class MyMessagingService extends FirebaseMessagingService {

    private static final String TAG = MyMessagingService.class.getSimpleName();
    public static final String UPDATE_BROADCAST = "myupdatebroadcast";
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // ...

        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d(TAG, "From: " + remoteMessage.getFrom());


        // Check if message contains a data payload.
        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
//            Toast.makeText(this,"Toast shown",Toast.LENGTH_SHORT).show();
        }

        // Check if message contains a notification payload.
        if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
//            Toast.makeText(this, "Toast shown1", Toast.LENGTH_SHORT).show();
        }

        showNotification(remoteMessage.getNotification().getTitle(),remoteMessage.getNotification().getBody());
        getApplicationContext().sendBroadcast(new Intent(UPDATE_BROADCAST));

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
