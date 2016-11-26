package com.mashazavolnyuk.currency.fragments;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mashazavolnyuk.currency.API.ApiClient;
import com.mashazavolnyuk.currency.API.IRequest;
import com.mashazavolnyuk.currency.Currency;
import com.mashazavolnyuk.currency.R;
import com.mashazavolnyuk.currency.adapter.CurrentCourseAdapter;
import com.mashazavolnyuk.currency.data.DataCurrencies;
import com.mashazavolnyuk.currency.event.MessageEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by Dark Maleficent on 26.11.2016.
 */

public class FragmentCurrentCourse extends Fragment {

    List<Currency> currencyList;
    RecyclerView recyclerView;
    SwipeRefreshLayout swipeRefreshLayout;
//    EventBus eventBus=EventBus.getDefault();
    CurrentCourseAdapter currentCourseAdapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_current_course, container, false);
        recyclerView=(RecyclerView) v.findViewById(R.id.rcvCurrentCourse);
        swipeRefreshLayout= (SwipeRefreshLayout) v.findViewById(R.id.swipe_container);
        currentCourseAdapter=new CurrentCourseAdapter(getActivity());
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(currentCourseAdapter);
        setHasOptionsMenu(true);
        request();
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                request();
            }
        });
        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent,R.color.colorPrimary, R.color.colorPrimaryDark,R.color.colorAccent);
        return v;
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onStop() {
        super.onStop();

    }

    private void request() {
        IRequest apiService =
                ApiClient.getClient().create(IRequest.class);
        Call<List<Currency>> call = apiService.getCurrency();
        call.enqueue(new Callback<List<Currency>>() {
            @Override
            public void onResponse(Call<List<Currency>> call, Response<List<Currency>> response) {
                currencyList = response.body();
                DataCurrencies.getInstance().setCurrencies(currencyList);
               // eventBus.post(new MessageEvent("up"));
                currentCourseAdapter.Changed();
                swipeRefreshLayout.setRefreshing(false);

            }

            @Override
            public void onFailure(Call<List<Currency>> call, Throwable t) {

            }
        });

    }

}
