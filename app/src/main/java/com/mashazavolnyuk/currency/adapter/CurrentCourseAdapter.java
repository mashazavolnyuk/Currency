package com.mashazavolnyuk.currency.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mashazavolnyuk.currency.R;
import com.mashazavolnyuk.currency.data.DataCurrencies;
import com.mashazavolnyuk.currency.event.MessageEvent;

import java.util.List;

/**
 * Created by Dark Maleficent on 26.11.2016.
 */

public class CurrentCourseAdapter extends RecyclerView.Adapter<CurrentCourseHolders> {

    private Context context;
    private List<com.mashazavolnyuk.currency.Currency> data;


    public CurrentCourseAdapter(Context context) {
        this.context = context;
        data = DataCurrencies.getInstance().getCurrencies();
    }
    @Override
    public CurrentCourseHolders onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(context).inflate(R.layout.item_course_currency, null);
        CurrentCourseHolders rcv = new CurrentCourseHolders(layoutView);
        return rcv;
    }

    @Override
    public void onBindViewHolder(CurrentCourseHolders holder, int position) {
        holder.cciText.setText(data.get(position).getCcy());
        holder.saleText.setText(data.get(position).getSale());
        holder.buyText.setText(data.get(position).getBuy());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }


    public void Changed(){
        data = DataCurrencies.getInstance().getCurrencies();
        notifyDataSetChanged();
    }
    public void onEvent(MessageEvent messageEvent){
        messageEvent.getMessage();
        notifyDataSetChanged();
    }

}
