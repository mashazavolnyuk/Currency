package com.mashazavolnyuk.currency;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Dark Maleficent on 26.11.2016.
 */

public class DataPeriod {
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("bank")
    @Expose
    private String bank;
    @SerializedName("baseCurrency")
    @Expose
    private Integer baseCurrency;
    @SerializedName("baseCurrencyLit")
    @Expose
    private String baseCurrencyLit;
    @SerializedName("exchangeRate")
    @Expose
    private List<ExchangeRate> exchangeRate = new ArrayList<ExchangeRate>();

    /**
     *
     * @return
     * The date
     */
    public String getDate() {
        return date;
    }

    /**
     *
     * @param date
     * The date
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     *
     * @return
     * The bank
     */
    public String getBank() {
        return bank;
    }

    /**
     *
     * @param bank
     * The bank
     */
    public void setBank(String bank) {
        this.bank = bank;
    }

    /**
     *
     * @return
     * The baseCurrency
     */
    public Integer getBaseCurrency() {
        return baseCurrency;
    }

    /**
     *
     * @param baseCurrency
     * The baseCurrency
     */
    public void setBaseCurrency(Integer baseCurrency) {
        this.baseCurrency = baseCurrency;
    }

    /**
     *
     * @return
     * The baseCurrencyLit
     */
    public String getBaseCurrencyLit() {
        return baseCurrencyLit;
    }

    /**
     *
     * @param baseCurrencyLit
     * The baseCurrencyLit
     */
    public void setBaseCurrencyLit(String baseCurrencyLit) {
        this.baseCurrencyLit = baseCurrencyLit;
    }

    /**
     *
     * @return
     * The exchangeRate
     */
    public List<ExchangeRate> getExchangeRate() {
        return exchangeRate;
    }

    /**
     *
     * @param exchangeRate
     * The exchangeRate
     */
    public void setExchangeRate(List<ExchangeRate> exchangeRate) {
        this.exchangeRate = exchangeRate;
    }
}
