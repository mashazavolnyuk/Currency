package com.mashazavolnyuk.currency.API;

import com.mashazavolnyuk.currency.Currency;
import com.mashazavolnyuk.currency.DataPeriod;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Dark Maleficent on 26.11.2016.
 */

public interface IRequest {
    @GET("pubinfo?json&exchange&&coursid=3")
    Call<List<Currency>> getCurrency();

    @GET("exchange_rates?json")
    Call<DataPeriod> getCurrencyByData(@Query("date") String signature);
}
