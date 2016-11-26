package com.mashazavolnyuk.currency;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ExchangeRate {

    @SerializedName("baseCurrency")
    @Expose
    private String baseCurrency;
    @SerializedName("currency")
    @Expose
    private String currency;
    @SerializedName("saleRateNB")
    @Expose
    private Double saleRateNB;
    @SerializedName("purchaseRateNB")
    @Expose
    private Double purchaseRateNB;
    @SerializedName("saleRate")
    @Expose
    private Double saleRate;
    @SerializedName("purchaseRate")
    @Expose
    private Double purchaseRate;

    /**
     *
     * @return
     * The baseCurrency
     */
    public String getBaseCurrency() {
        return baseCurrency;
    }

    /**
     *
     * @param baseCurrency
     * The baseCurrency
     */
    public void setBaseCurrency(String baseCurrency) {
        this.baseCurrency = baseCurrency;
    }

    /**
     *
     * @return
     * The currency
     */
    public String getCurrency() {
        return currency;
    }

    /**
     *
     * @param currency
     * The currency
     */
    public void setCurrency(String currency) {
        this.currency = currency;
    }

    /**
     *
     * @return
     * The saleRateNB
     */
    public Double getSaleRateNB() {
        return saleRateNB;
    }

    /**
     *
     * @param saleRateNB
     * The saleRateNB
     */
    public void setSaleRateNB(Double saleRateNB) {
        this.saleRateNB = saleRateNB;
    }

    /**
     *
     * @return
     * The purchaseRateNB
     */
    public Double getPurchaseRateNB() {
        return purchaseRateNB;
    }

    /**
     *
     * @param purchaseRateNB
     * The purchaseRateNB
     */
    public void setPurchaseRateNB(Double purchaseRateNB) {
        this.purchaseRateNB = purchaseRateNB;
    }

    /**
     *
     * @return
     * The saleRate
     */
    public Double getSaleRate() {
        return saleRate;
    }

    /**
     *
     * @param saleRate
     * The saleRate
     */
    public void setSaleRate(Double saleRate) {
        this.saleRate = saleRate;
    }

    /**
     *
     * @return
     * The purchaseRate
     */
    public Double getPurchaseRate() {
        return purchaseRate;
    }

    /**
     *
     * @param purchaseRate
     * The purchaseRate
     */
    public void setPurchaseRate(Double purchaseRate) {
        this.purchaseRate = purchaseRate;
    }
}
