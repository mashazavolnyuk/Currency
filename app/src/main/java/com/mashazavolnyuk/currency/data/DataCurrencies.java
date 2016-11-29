package com.mashazavolnyuk.currency.data;

import com.mashazavolnyuk.currency.Currency;
import com.mashazavolnyuk.currency.ExchangeRate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dark Maleficent on 26.11.2016.
 */
public class DataCurrencies {
    List<Currency> currencies = new ArrayList<>();
    List<ExchangeRate> exchangeRates = new ArrayList<>();
    private static DataCurrencies instance = new DataCurrencies();

    public static DataCurrencies getInstance() {
        if (instance == null)
            synchronized (DataCurrencies.class) {
                if (instance == null)
                    instance = new DataCurrencies();

            }
        return instance;
    }

    private DataCurrencies() {
    }

    public List<Currency> getCurrencies() {
        return currencies;
    }

    public void setCurrencies(List<Currency> currencies) {
        this.currencies = currencies;
    }

    public void setExchangeRates(List<ExchangeRate> exchangeRates){
        this.exchangeRates=exchangeRates;
    }

    public List<ExchangeRate> getExchangeRates(){
        return exchangeRates;
    }

}
