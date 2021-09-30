package com.abc.veggismart;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class NotificationBroadcastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        intent=new Intent(OrderActivity.getInstance(),onNotificationClickActivity.class);
        context.startActivity(intent);
    }
}
