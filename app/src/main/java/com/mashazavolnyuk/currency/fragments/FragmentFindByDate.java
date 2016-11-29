package com.mashazavolnyuk.currency.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mashazavolnyuk.currency.API.ApiClient;
import com.mashazavolnyuk.currency.API.IRequest;
import com.mashazavolnyuk.currency.Currency;
import com.mashazavolnyuk.currency.DataPeriod;
import com.mashazavolnyuk.currency.ExchangeRate;
import com.mashazavolnyuk.currency.R;
import com.mashazavolnyuk.currency.adapter.CurrentCourseAdapter;
import com.mashazavolnyuk.currency.data.DataCurrencies;

import java.util.LinkedList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Dark Maleficent on 29.11.2016.
 */

public class FragmentFindByDate extends Fragment {

    List<ExchangeRate> exchangeRates;
    RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefreshLayout;
    CurrentCourseAdapter currentCourseAdapter;
    public static String DATA_FIND = null;
    private volatile List<DataPeriod> dataPeriods;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_current_course, container, false);
        recyclerView = (RecyclerView) v.findViewById(R.id.rcvCurrentCourse);
        swipeRefreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.swipe_container);
        currentCourseAdapter = new CurrentCourseAdapter(getActivity(),CurrentCourseAdapter.MODE_HISTORY);

        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(currentCourseAdapter);
        setHasOptionsMenu(true);
        dataPeriods=new LinkedList<>();
        exchangeRates=new LinkedList<>();
        makerequest(DATA_FIND);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                makerequest(DATA_FIND);
            }
        });
        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent, R.color.colorPrimary, R.color.colorPrimaryDark, R.color.colorAccent);
        return v;
    }


    private void makerequest(String date) {
        IRequest apiService =
                ApiClient.getClient().create(IRequest.class);
        Call<DataPeriod> call1 = apiService.getCurrencyByData(date);
        call1.enqueue(new Callback<DataPeriod>() {
            @Override
            public void onResponse(Call<DataPeriod> call, Response<DataPeriod> response) {
                exchangeRates=response.body().getExchangeRate();
                DataCurrencies.getInstance().setExchangeRates(exchangeRates);
                currentCourseAdapter.Changed();
                swipeRefreshLayout.setRefreshing(false);
            }
            @Override
            public void onFailure(Call<DataPeriod> call, Throwable t) {
            }
        });
    }




}
