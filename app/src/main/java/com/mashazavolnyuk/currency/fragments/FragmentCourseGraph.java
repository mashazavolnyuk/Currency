package com.mashazavolnyuk.currency.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.Viewport;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.mashazavolnyuk.currency.API.ApiClient;
import com.mashazavolnyuk.currency.API.IRequest;
import com.mashazavolnyuk.currency.DataPeriod;
import com.mashazavolnyuk.currency.ExchangeRate;
import com.mashazavolnyuk.currency.R;
import com.mashazavolnyuk.currency.util.DateUtil;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Dark Maleficent on 26.11.2016.
 */

public class FragmentCourseGraph extends Fragment {


    String data;
    private volatile List<DataPeriod> dataPeriods;
    Date currentDate;
    GraphView graph;
    LineGraphSeries<DataPoint> series;
    private static final int DAY_COUNT = 30;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_graph, container, false);
        graph = (GraphView) v.findViewById(R.id.graph);
        series=new LineGraphSeries<>();
        //graph.addSeries(series);
        graph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(getActivity()));
        graph.getGridLabelRenderer().setHorizontalAxisTitle("day");
        graph.getGridLabelRenderer().setVerticalAxisTitle("course");
        Viewport viewport = graph.getViewport();
        viewport.setMinY(0);
        viewport.setMinX(DAY_COUNT);
        viewport.setScrollable(true);
        dataPeriods = new LinkedList<>();
        adjustData();
        return v;
    }


    private void adjustData() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        currentDate = new Date();
        List<String> days = new ArrayList<>(DAY_COUNT);
        for (int i = 0; i < DAY_COUNT; i++) {
            String previousDay = DateUtil.getPreviousDay(currentDate, i, dateFormat);
            days.add(previousDay);
        }
        getDataFullDataMounth(days);
    }

    private void getDataFullDataMounth(List<String> days) {
        for (String date : days) {
            makerequest(date);
        }
    }

    private void draw() {
        int i = 1;
        for (DataPeriod dataPeriod : dataPeriods) {
            List<ExchangeRate> exchangeRates = dataPeriod.getExchangeRate();
            if(exchangeRates.size()!=0){
                for(ExchangeRate exchangeRate:exchangeRates){
                    if(exchangeRate.getCurrency().equals("USD")){
                Double saleRateNB=exchangeRate.getSaleRateNB();
                series.appendData(new DataPoint(i++,saleRateNB),true,DAY_COUNT+4);
            }
            }
            }//if
        }//for

        graph.addSeries(series);

}

    private void makerequest(String date) {
        IRequest apiService =
                ApiClient.getClient().create(IRequest.class);
        Call<DataPeriod> call1 = apiService.getCurrencyByData(date);
        call1.enqueue(new Callback<DataPeriod>() {
            @Override
            public void onResponse(Call<DataPeriod> call, Response<DataPeriod> response) {
                addDataPeriod(response.body());

            }

            @Override
            public void onFailure(Call<DataPeriod> call, Throwable t) {
            }
        });
    }

    private synchronized void addDataPeriod(DataPeriod dataPeriod){
        dataPeriods.add(dataPeriod);
        if(dataPeriods.size() == DAY_COUNT)
            draw();
    }

}


