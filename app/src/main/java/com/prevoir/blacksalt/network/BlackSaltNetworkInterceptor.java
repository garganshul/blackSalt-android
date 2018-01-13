package com.prevoir.blacksalt.network;

import android.content.Context;

import java.io.IOException;
import java.util.Map;

import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by anshul.garg on 30/08/17.
 */

class BlackSaltNetworkInterceptor implements okhttp3.Interceptor {
    private Context context;
    public BlackSaltNetworkInterceptor(Context context){
        this.context = context;
    }
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Request.Builder requestBuilder = request.newBuilder();

        Map<String, String> headers;
        headers = HeaderManager.getSessionHeaders(context);
        for (Map.Entry<String, String> entry : headers.entrySet()) {
            requestBuilder.header(entry.getKey(), entry.getValue());
        }

        Response response = chain.proceed(requestBuilder.build());
        return response;

    }
}
