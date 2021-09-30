package com.abc.veggismart.sendNotificationPack;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;

import com.abc.veggismart.MainActivity;
import com.abc.veggismart.OrderActivity;
import com.abc.veggismart.R;
import com.abc.veggismart.onNotificationClickActivity;
import com.google.firebase.messaging.FirebaseMessagingService; import com.google.firebase.messaging.RemoteMessage;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    String title,message;
    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        title=remoteMessage.getData().get("Title");
        message=remoteMessage.getData().get("Message");
        Intent toNotification = new Intent(OrderActivity.getInstance(), onNotificationClickActivity.class);
        PendingIntent pendingIntent=PendingIntent.getActivity(MyFirebaseMessagingService.this, 100, toNotification, PendingIntent.FLAG_CANCEL_CURRENT);

        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(getApplicationContext())
                        .setSmallIcon(R.drawable.ic_baseline_notifications_active_24)
                        .setContentTitle(title)
                        .setSound(defaultSoundUri)
                        .setContentText(message)
                        .setContentIntent(pendingIntent)
                        .setAutoCancel(true);
        NotificationManager manager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());
    } }