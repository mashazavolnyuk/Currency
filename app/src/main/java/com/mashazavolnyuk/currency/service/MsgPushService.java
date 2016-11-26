package com.mashazavolnyuk.currency.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * Created by Dark Maleficent on 26.11.2016.
 */

public class MsgPushService extends Service {

    

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
