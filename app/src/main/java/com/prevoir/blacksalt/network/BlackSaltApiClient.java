package com.prevoir.blacksalt.network;

import android.content.Context;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by anshul.garg on 30/08/17.
 */

public class BlackSaltApiClient {
    public static final String BASE_URL = "http://localhost:8000";
    private static Retrofit retrofit = null;
    private static BlackSaltApiService blackSaltApiService;
    private static Retrofit getClient(Context context) {
        if (retrofit == null) {
            OkHttpClient.Builder okHttpClientBuilder = new OkHttpClient().newBuilder().addInterceptor(new BlackSaltNetworkInterceptor(context));
            retrofit = new Retrofit.Builder()
                    .baseUrl(getBaseUrl())
                    .addConverterFactory(JSONConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClientBuilder.build())
                    .build();
        }
        return retrofit;
    }

    public static synchronized BlackSaltApiService getBlackSaltApiService(Context context){
        if(blackSaltApiService == null){
            blackSaltApiService = getClient(context).create(BlackSaltApiService.class);
        }
        return blackSaltApiService;
    }

    private static String getBaseUrl() {
        return BASE_URL;
    }
}
