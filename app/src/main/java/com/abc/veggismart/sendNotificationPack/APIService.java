package com.abc.veggismart.sendNotificationPack;

import retrofit2.Call;
import retrofit2.http.Body; import retrofit2.http.Headers; import retrofit2.http.POST;
public interface APIService { @Headers(
        {
                "Content-Type:application/json",
                "Authorization:key=AAAAOTJgTCk:APA91bFGqucPvzSnEX-Ewu1lVKE_XYdLmeJZOrgoAJKQdZiCRo6i4utKPRA0sfYT9DWnz0wnTNrdxXChO4TUcejoJnSNqGscXG04xj68-O7ut8Q1wkTecdX2v9eqtSUSYzXSQKpvtw1b"
        } )
@POST("fcm/send")
Call<MyResponse> sendNotifcation(@Body NotificationSender body); }
