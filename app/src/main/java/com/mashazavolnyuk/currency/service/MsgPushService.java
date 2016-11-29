package com.mashazavolnyuk.currency.service;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.Toast;

import com.mashazavolnyuk.currency.API.ApiClient;
import com.mashazavolnyuk.currency.API.IRequest;
import com.mashazavolnyuk.currency.Currency;
import com.mashazavolnyuk.currency.MainActivity;
import com.mashazavolnyuk.currency.R;
import com.mashazavolnyuk.currency.data.DataCurrencies;

import java.util.LinkedList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Dark Maleficent on 26.11.2016.
 */

public class MsgPushService extends Service {
    int notifId = 1;
    NotificationManager notificationManager;
    List<Currency> currencyList;

    @Override
    public void onCreate() {
        super.onCreate();
        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        currencyList = new LinkedList<>();

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Toast.makeText(this, "Service Started", Toast.LENGTH_LONG).show();
        loadData();
        return Service.START_STICKY;
    }

    @Override
    public void onDestroy() {

        super.onDestroy();
        Toast.makeText(this, "Service Destroy", Toast.LENGTH_LONG).show();
    }


    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }


    private void addNotification() {
        StringBuilder stringBuilder = new StringBuilder();
        if (currencyList.size() > 0) {

            for (Currency currency : currencyList) {
                stringBuilder.append(currency.getCcy());
                stringBuilder.append("sale");
                stringBuilder.append(currency.getSale());
                stringBuilder.append("/");
                stringBuilder.append("buy");
                stringBuilder.append(currency.getBuy());
                stringBuilder.append(System.getProperty("line.separator"));
            }


        } else
            Toast.makeText(getApplicationContext(), "data is absent", Toast.LENGTH_SHORT).show();
        NotificationCompat.Builder builder =
                new NotificationCompat.Builder(this)
                        .setSmallIcon(R.mipmap.ic_currency_eur_white_48dp)
                        .setContentTitle("Course")
                        .setStyle(new NotificationCompat.BigTextStyle()
                                .bigText(stringBuilder));


        Intent notificationIntent = new Intent(this, MainActivity.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);

        // Add as notification
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(notifId, builder.build());
    }

    private void loadData() {
        IRequest apiService =
                ApiClient.getClient().create(IRequest.class);
        Call<List<Currency>> call = apiService.getCurrency();
        call.enqueue(new Callback<List<Currency>>() {
            @Override
            public void onResponse(Call<List<Currency>> call, Response<List<Currency>> response) {
                currencyList = response.body();
                Log.d("Service", "" + currencyList.size());
                addNotification();
            }

            @Override
            public void onFailure(Call<List<Currency>> call, Throwable t) {

            }
        });
    }


}


