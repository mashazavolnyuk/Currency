package com.mashazavolnyuk.currency.broadcastReceiver;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

import com.mashazavolnyuk.currency.service.MsgPushService;

/**
 * Created by Dark Maleficent on 26.11.2016.
 */

public class BroadcastReceiver extends android.content.BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {





        Intent service = new Intent(context, MsgPushService.class);
//        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
//        NetworkInfo wifi = connMgr.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
//        NetworkInfo mobile = connMgr.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

//        boolean isConnected = wifi != null && wifi.isConnected() ||
//                mobile != null && mobile.isConnected();
//        if (isOnline(context)) {
//            Log.d("Network Available ", "YES");
            context.startService(service);
//        } else {
//            Log.d("Network Available ", "NO");
//            context.stopService(service);
//        }


    }
    public boolean isOnline(Context context) {

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        //should check null because in airplane mode it will be null
        return (netInfo != null && netInfo.isConnected());
    }
}
