package com.mashazavolnyuk.currency.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mashazavolnyuk.currency.Currency;
import com.mashazavolnyuk.currency.ExchangeRate;
import com.mashazavolnyuk.currency.R;
import com.mashazavolnyuk.currency.data.DataCurrencies;
import com.mashazavolnyuk.currency.event.MessageEvent;
import com.mashazavolnyuk.currency.interfaces.ICurrency;

import java.util.List;

/**
 * Created by Dark Maleficent on 26.11.2016.
 */

public class CurrentCourseAdapter extends RecyclerView.Adapter<CurrentCourseHolders> {

    private Context context;
    private List<Currency> currencies;
    private List<ExchangeRate> exchangeRates;

    public static final int MODE_HISTORY = 2;
    public static final int MODE_CURRENT_DAY = 1;
    public static int MODE = 1;


    public CurrentCourseAdapter(Context context, int MODE) {
        this.context = context;
        this.MODE = MODE;
        if (MODE == MODE_HISTORY) {
            this.MODE = MODE_HISTORY;
            currencies = DataCurrencies.getInstance().getCurrencies();
        }
        if (MODE == MODE_CURRENT_DAY) {
            this.MODE = MODE_CURRENT_DAY;
            exchangeRates = DataCurrencies.getInstance().getExchangeRates();
        }
    }

    @Override
    public CurrentCourseHolders onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(context).inflate(R.layout.item_course_currency, null);
        CurrentCourseHolders rcv = new CurrentCourseHolders(layoutView);
        return rcv;
    }

    @Override
    public void onBindViewHolder(CurrentCourseHolders holder, int position) {
        if (MODE == MODE_CURRENT_DAY) {
            holder.cciText.setText(currencies.get(position).getCcy());
            holder.saleText.setText(currencies.get(position).getSale());
            holder.buyText.setText(currencies.get(position).getBuy());
        }
        if (MODE == MODE_HISTORY) {
            holder.cciText.setText(exchangeRates.get(position).getCurrency());
            holder.saleText.setText(exchangeRates.get(position).getSaleRateNB().toString());
            holder.buyText.setText(exchangeRates.get(position).getPurchaseRateNB().toString());
        }
    }

    @Override
    public int getItemCount() {
        try {
            if (MODE == MODE_CURRENT_DAY)
                return currencies.size();
            if (MODE == MODE_HISTORY)
                return exchangeRates.size();
        } catch (NullPointerException e) {
            return 0;
        }


        return 0;
    }


    public void Changed() {
        if (MODE == MODE_CURRENT_DAY)
            currencies = DataCurrencies.getInstance().getCurrencies();
        if (MODE == MODE_HISTORY)
            exchangeRates = DataCurrencies.getInstance().getExchangeRates();
        notifyDataSetChanged();
    }
}
