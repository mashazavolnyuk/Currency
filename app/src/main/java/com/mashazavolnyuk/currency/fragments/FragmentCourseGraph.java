package com.mashazavolnyuk.currency.fragments;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.LegendRenderer;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.helper.StaticLabelsFormatter;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.mashazavolnyuk.currency.API.ApiClient;
import com.mashazavolnyuk.currency.API.IRequest;
import com.mashazavolnyuk.currency.DataPeriod;
import com.mashazavolnyuk.currency.ExchangeRate;
import com.mashazavolnyuk.currency.R;
import com.mashazavolnyuk.currency.interfaces.ICorrectDesigner;
import com.mashazavolnyuk.currency.util.DateUtil;

import java.lang.ref.WeakReference;
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

    private volatile List<DataPeriod> dataPeriods;
    StaticLabelsFormatter staticLabelsFormatter;
    Date currentDate;
    GraphView graph;
    BarGraphSeries<DataPoint> seriesSaleRateNB;
    BarGraphSeries<DataPoint> seriesPurchaseRateNB;
    List<String> formatGraph;
    private static final int DAY_COUNT = 30;
    String TYPE_GRAPH_CURRENCY;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_graph, container, false);
        graph = (GraphView) v.findViewById(R.id.graph);
        staticLabelsFormatter = new StaticLabelsFormatter(graph);
        dataPeriods = new LinkedList<>();


        ((AppCompatActivity) getActivity()).getSupportActionBar().setDisplayShowTitleEnabled(false);
        String[] items = new String[]{"USD", "EUR",};
        final Spinner spinner = new Spinner(getActivity());
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(), R.layout.item_spinner, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.getBackground().setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
        ((ICorrectDesigner) getActivity()).addChild(spinner, 1);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String currency = spinner.getSelectedItem().toString();
                if (!currency.equals(TYPE_GRAPH_CURRENCY)) {
                    TYPE_GRAPH_CURRENCY = currency;
                    adjustData();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        adjustData();
        return v;
    }


    private void adjustData() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        SimpleDateFormat dateGraph = new SimpleDateFormat("dd");
        currentDate = new Date();
        formatGraph = new ArrayList<>(DAY_COUNT);
        List<String> days = new ArrayList<>(DAY_COUNT);
        for (int i = 0; i < DAY_COUNT; i++) {
            String previousDay = DateUtil.getPreviousDay(currentDate, i, dateFormat);
            String previousforGraph = DateUtil.getPreviousDay(currentDate, i, dateGraph);
            formatGraph.add(previousforGraph);
            days.add(previousDay);
        }
        getDataFullDataMounth(days);
    }

    private void getDataFullDataMounth(List<String> days) {
        String[] stockArr = new String[DAY_COUNT];
        stockArr = formatGraph.toArray(stockArr);
        staticLabelsFormatter.setHorizontalLabels(stockArr);
        dataPeriods.clear();
        for (String date : days) {
            makerequest(date);
        }
    }

    private void adjustGraphView() {
        graph.removeAllSeries();
        seriesSaleRateNB = new BarGraphSeries<>();
        seriesPurchaseRateNB = new BarGraphSeries<>();
        //graph.addSeries(seriesSaleRateNB);
        seriesSaleRateNB.setSpacing(20);
        seriesSaleRateNB.setTitle("sale");
        seriesSaleRateNB.setColor(Color.RED);
        seriesPurchaseRateNB.setSpacing(20);
        seriesPurchaseRateNB.setTitle("purchase");
        seriesPurchaseRateNB.setColor(Color.BLUE);
        graph.getGridLabelRenderer().setTextSize(16);
//        graph.getGridLabelRenderer().setLabelsSpace(30);
        graph.setTitle("Situation last 30 days");
        graph.setTitleColor(Color.RED);
        graph.getLegendRenderer().setAlign(LegendRenderer.LegendAlign.TOP);
        graph.getLegendRenderer().setFixedPosition(0, 27);
        graph.getLegendRenderer().setWidth(graph.getWidth() / 2);
        graph.getLegendRenderer().setTextSize(70);
        graph.getLegendRenderer().setTextColor(R.color.white);
        graph.getLegendRenderer().setBackgroundColor(R.color.white);
        graph.getLegendRenderer().setVisible(true);
        graph.getLegendRenderer().setSpacing(30);
        graph.getGridLabelRenderer().setLabelsSpace(50);
        graph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(getActivity()));
        graph.getGridLabelRenderer().setHorizontalAxisTitle("day");
        graph.getGridLabelRenderer().setVerticalAxisTitle("course");
        graph.getGridLabelRenderer().setVerticalLabelsSecondScaleColor(Color.RED);
        // activate horizontal zooming and scrolling
        graph.getViewport().setScalable(true);
        // activate horizontal scrolling
        graph.getViewport().setScrollable(true);
        // activate horizontal and vertical zooming and scrolling
        graph.getViewport().setScalableY(true);
        // activate vertical scrolling
        graph.getViewport().setScrollableY(true);

    }

    private void draw() {

        // adjustGraphView();
        AsyncTDraw AsyncTDraw = new AsyncTDraw();
        AsyncTDraw.execute();
//        int i = 1;
//        for (DataPeriod dataPeriod : dataPeriods) {
//            List<ExchangeRate> exchangeRates = dataPeriod.getExchangeRate();
//            if (exchangeRates != null && exchangeRates.size() != 0) {
//                for (ExchangeRate exchangeRate : exchangeRates) {
//                    if (exchangeRate.getCurrency().equals(TYPE_GRAPH_CURRENCY)) {
//                        Double saleRateNB = exchangeRate.getSaleRateNB();
//                        Double purchaseRateNB = exchangeRate.getPurchaseRateNB();
//                        seriesSaleRateNB.appendData(new DataPoint(i++, saleRateNB), true, DAY_COUNT);
//                        seriesPurchaseRateNB.appendData(new DataPoint(i++, purchaseRateNB), true, DAY_COUNT);
//                    }
//                }
//            }//if
//        }//for
//        graph.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);
//        graph.addSeries(seriesSaleRateNB);
//        graph.addSeries(seriesPurchaseRateNB);

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

    private synchronized void addDataPeriod(DataPeriod dataPeriod) {
        dataPeriods.add(dataPeriod);
        if (dataPeriods.size() == DAY_COUNT)
            draw();
    }

    class AsyncTDraw extends AsyncTask<Void, Void, Void> {
        @Override
        protected void onPreExecute() {
            adjustGraphView();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            int i = 1;
            for (DataPeriod dataPeriod : dataPeriods) {
                List<ExchangeRate> exchangeRates = dataPeriod.getExchangeRate();
                if (exchangeRates != null && exchangeRates.size() != 0) {
                    for (ExchangeRate exchangeRate : exchangeRates) {
                        if (exchangeRate.getCurrency().equals(TYPE_GRAPH_CURRENCY)) {
                            Double saleRateNB = exchangeRate.getSaleRateNB();
                            Double purchaseRateNB = exchangeRate.getPurchaseRateNB();
                            seriesSaleRateNB.appendData(new DataPoint(i++, saleRateNB), true, DAY_COUNT);
                            seriesPurchaseRateNB.appendData(new DataPoint(i++, purchaseRateNB), true, DAY_COUNT);
                        }
                    }
                }//if
            }//for
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            graph.getGridLabelRenderer().setLabelFormatter(staticLabelsFormatter);
            graph.addSeries(seriesSaleRateNB);
            graph.addSeries(seriesPurchaseRateNB);
        }
    }


}


