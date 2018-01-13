package com.prevoir.blacksalt.network;

import android.content.Context;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by anshul.garg on 30/08/17.
 */

public class HeaderManager {
    public static Map<String, String> getSessionHeaders(Context context) {
        Map<String, String> sessionHeader = new HashMap<>();
        /*final MyModulePreference myModulePreference = new MyModulePreference(context);
        String accessToken = myModulePreference.getString(MyModulePreference.ACCESS_TOKEN,null);
        String sessionToken = myModulePreference.getString(MyModulePreference.SESSION_TOKEN,null);
        if(!TextUtils.isEmpty(accessToken)){
            sessionHeader.put("at" , accessToken);
        }
        if(!TextUtils.isEmpty(sessionToken)){
            sessionHeader.put("st" , sessionToken);
        }*/
        sessionHeader.put("Content-Type", "application/json");
        return sessionHeader;
    }
}
