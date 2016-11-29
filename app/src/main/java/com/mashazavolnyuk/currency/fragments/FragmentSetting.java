package com.mashazavolnyuk.currency.fragments;

import android.app.AlarmManager;
import android.app.Dialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TimePicker;

import com.mashazavolnyuk.currency.R;
import com.mashazavolnyuk.currency.service.MsgPushService;

import java.util.Calendar;

/**
 * Created by Dark Maleficent on 28.11.2016.
 */

public class FragmentSetting extends Fragment {

    SharedPreferences sPref;
    final int  TIME_DIALOG_ID=1;
    int hour,minute;
    AlarmManager alarmManager;
    PendingIntent pendingIntent;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_setting, container, false);
        FloatingActionButton floatingActionButton=(FloatingActionButton) v.findViewById(R.id.fabSettingTime);
        floatingActionButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                createDialog(TIME_DIALOG_ID).show();
                return false;
            }
        });


        return v;
    }

    public Dialog createDialog(int id) {
        switch (id) {
            case TIME_DIALOG_ID:
                hour=Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
                minute=Calendar.getInstance().get(Calendar.MINUTE);
                return new TimePickerDialog(getActivity(), timePickerListener, hour, minute, true);

        }
        return null;
    }

    private TimePickerDialog.OnTimeSetListener timePickerListener = new TimePickerDialog.OnTimeSetListener() {

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minutes) {
            hour   = hourOfDay;
            minute = minutes;
            setStartTimeService();
        }

    };

    private void setStartTimeService(){
        Intent intent = new Intent(getActivity(), MsgPushService.class);
        pendingIntent=PendingIntent.getService(getActivity(), 0, intent, 0);
        alarmManager=(AlarmManager)getActivity().getSystemService(Context.ALARM_SERVICE);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);

        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                1000 * 60 * 20, pendingIntent);

//        alarmManager.set(AlarmManager.RTC, System.currentTimeMillis() + 4000, pendingIntent);
//        alarmManager.setRepeating(AlarmManager.ELAPSED_REALTIME,
//                hour + minute, 5000, pendingIntent);

    }
}
