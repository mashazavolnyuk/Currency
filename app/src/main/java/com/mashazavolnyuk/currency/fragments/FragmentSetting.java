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
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.SwitchCompat;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.TimePicker;

import com.mashazavolnyuk.currency.R;
import com.mashazavolnyuk.currency.service.MsgPushService;

import java.util.Calendar;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by Dark Maleficent on 28.11.2016.
 */

public class FragmentSetting extends Fragment {

    SharedPreferences sPref;
    final int TIME_DIALOG_ID = 1;
    final String SAVED_TEXT = "saved_text";
    final String SAVED_IS_CHECK = "saved_check";
    final int DEFAULT_HOUR = 9;
    final int DEFAULT_MINUTE = 0;

    int hour, minute;
    AlarmManager alarmManager;
    PendingIntent pendingIntent;
    TextView time;
    String value = "";

    boolean isCheck;
    SwitchCompat switchCompat;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_setting, container, false);
        time = (TextView) v.findViewById(R.id.tvTime);
        time.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                createDialog(TIME_DIALOG_ID).show();
                return false;
            }
        });
        switchCompat = (SwitchCompat) v.findViewById(R.id.scSetOwnTime);
        loadSettings();


        switchCompat.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                saveSettings();
                setAvailable(b);
            }
        });
        loadSettings();
        return v;
    }

    public Dialog createDialog(int id) {
        switch (id) {
            case TIME_DIALOG_ID:
                hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
                minute = Calendar.getInstance().get(Calendar.MINUTE);
                return new TimePickerDialog(getActivity(), timePickerListener, hour, minute, true);
        }
        return null;
    }

    private TimePickerDialog.OnTimeSetListener timePickerListener = new TimePickerDialog.OnTimeSetListener() {

        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minutes) {
            hour = hourOfDay;
            minute = minutes;
            saveSettings();

        }

    };

    private void setStartTimeService(int hour, int minute) {
        Intent intent = new Intent(getActivity(), MsgPushService.class);
        pendingIntent = PendingIntent.getService(getActivity(), 0, intent, 0);
        alarmManager = (AlarmManager) getActivity().getSystemService(Context.ALARM_SERVICE);

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(System.currentTimeMillis());
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                1000 * 60 * 20, pendingIntent);

    }

    private void saveSettings() {
        sPref = getActivity().getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor ed = sPref.edit();
        isCheck = switchCompat.isChecked();
        ed.putBoolean(SAVED_IS_CHECK, isCheck);
        ed.commit();
        if (isCheck) {
            setStartTimeService(hour, minute);
            value = hour + ":" + minute;
        } else {
            setStartTimeService(DEFAULT_HOUR, DEFAULT_MINUTE);
            value = DEFAULT_HOUR + ":" + DEFAULT_MINUTE;
        }
        ed.putString(SAVED_TEXT, value);
        update(value);

    }

    private void loadSettings() {
        sPref = getActivity().getPreferences(MODE_PRIVATE);
        String savedText = sPref.getString(SAVED_TEXT, value);
        switchCompat.setChecked(sPref.getBoolean(SAVED_IS_CHECK, isCheck));
        if (switchCompat.isChecked()) {
            time.setText(savedText);
            setAvailable(true);
        } else {
            time.setText(DEFAULT_HOUR + ":" + DEFAULT_MINUTE);
            setAvailable(false);
        }
    }

    private void setAvailable(boolean b) {
        if (b) {
            time.setEnabled(true);
            int color = ContextCompat.getColor(getActivity(), R.color.colorAccent);
            time.setTextColor(color);

        } else {
            int color = ContextCompat.getColor(getActivity(), R.color.colorSecondaryText);
            time.setTextColor(color);
            time.setEnabled(false);
        }

    }

    private void update(String value) {
        time.setText(value);

    }


}
