package com.prevoir.blacksalt.network;

import com.prevoir.blacksalt.models.Booking;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by anshul.garg on 30/08/17.
 */

public interface BlackSaltApiService {
    @GET("api/bookings")
    Call<Booking[]> getAllBookings();
//    @PUT("notifications/{notificationId}/{type}")
//    Call<Object> putNotificationEvent(@Path("notificationId") String notificationId, @Path("type") String type);
//    @POST("user/fitnessData/android")
//    Call<Object> sendFitnessData(@Body JSONObject result);
}
