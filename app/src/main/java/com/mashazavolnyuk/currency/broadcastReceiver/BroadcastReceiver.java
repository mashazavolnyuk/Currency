package com.mashazavolnyuk.currency.broadcastReceiver;

import android.content.Context;
import android.content.Intent;

import com.mashazavolnyuk.currency.service.MsgPushService;

/**
 * Created by Dark Maleficent on 26.11.2016.
 */

public class BroadcastReceiver extends android.content.BroadcastReceiver {



    @Override
    public void onReceive(Context context, Intent intent) {

        Intent service = new Intent(context, MsgPushService.class);
        context.startService(service);
    }
}
