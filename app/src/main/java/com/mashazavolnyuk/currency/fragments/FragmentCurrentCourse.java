package com.mashazavolnyuk.currency.fragments;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.Toast;

import com.mashazavolnyuk.currency.API.ApiClient;
import com.mashazavolnyuk.currency.API.IRequest;
import com.mashazavolnyuk.currency.Currency;
import com.mashazavolnyuk.currency.R;
import com.mashazavolnyuk.currency.adapter.CurrentCourseAdapter;
import com.mashazavolnyuk.currency.data.DataCurrencies;
import com.mashazavolnyuk.currency.interfaces.INavigation;

import java.util.Calendar;
import java.util.Date;
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
    CurrentCourseAdapter currentCourseAdapter;
    final int DATE_DIALOG_ID = 1;
    int year;
    int month;
    int day;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_current_course, container, false);
        recyclerView = (RecyclerView) v.findViewById(R.id.rcvCurrentCourse);
        swipeRefreshLayout = (SwipeRefreshLayout) v.findViewById(R.id.swipe_container);
        currentCourseAdapter = new CurrentCourseAdapter(getActivity(),CurrentCourseAdapter.MODE_CURRENT_DAY);
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
        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent, R.color.colorPrimary, R.color.colorPrimaryDark, R.color.colorAccent);
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

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        MenuInflater menuInflater = (getActivity()).getMenuInflater();
        menuInflater.inflate(R.menu.menu_course, menu);
        for (int j = 0; j < menu.size(); j++) {
            MenuItem item = menu.getItem(j);
            if (item.getItemId() == R.id.findByDate) {
                item.setShowAsActionFlags(MenuItem.SHOW_AS_ACTION_ALWAYS);
                item.setIcon(R.mipmap.ic_calendar_white_48dp);
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.findByDate:
                createDialog(DATE_DIALOG_ID).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    public Dialog createDialog(int id) {
        switch (id) {
            case DATE_DIALOG_ID:
                year = Calendar.getInstance().get(Calendar.YEAR);
                month = Calendar.getInstance().get(Calendar.MONTH);
                day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
                DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), myCallBack, year, month, day);
                datePickerDialog.getDatePicker().setMaxDate(new Date().getTime());
                return datePickerDialog;
        }
        return null;
    }

    DatePickerDialog.OnDateSetListener myCallBack = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            view.setMaxDate(day);
            StringBuilder  stringBuilder=new StringBuilder();
            stringBuilder.append(dayOfMonth);
            stringBuilder.append(".");
            stringBuilder.append(monthOfYear);
            stringBuilder.append(".");
            stringBuilder.append(year);
            ( (INavigation)getActivity()).toFindByDate(stringBuilder.toString());
        }
    };

    private void request() {
        IRequest apiService =
                ApiClient.getClient().create(IRequest.class);
        Call<List<Currency>> call = apiService.getCurrency();
        call.enqueue(new Callback<List<Currency>>() {
            @Override
            public void onResponse(Call<List<Currency>> call, Response<List<Currency>> response) {
                currencyList = response.body();
                DataCurrencies.getInstance().setCurrencies(currencyList);
                currentCourseAdapter.Changed();
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void onFailure(Call<List<Currency>> call, Throwable t) {

            }
        });

    }

}
