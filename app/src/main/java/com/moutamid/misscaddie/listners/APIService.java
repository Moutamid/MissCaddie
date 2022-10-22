package com.moutamid.misscaddie.listners;


import com.moutamid.misscaddie.Notifications.MyResponse;
import com.moutamid.misscaddie.Notifications.Sender;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIService {

    @Headers(

            {
                    "Content-Type:application/json",
                    "Authorization:key=AAAA17-nBzE:APA91bGz5f_tx6Mos82J2ImlX7-ysyB-xD3BsAMI6wDT4j0k_5YOGCl5ot65NuPv3eCAT1ybvJlQIVtYT69xoOnzX7PjNS96IMH_qKdJjC7-q9nkT-4CzmbO_WjdIGZW36gSiGmJOyox"
            }

    )

    @POST("fcm/send")
    Call<MyResponse> sendNotification(@Body Sender body);

}
