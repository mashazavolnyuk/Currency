package com.mashazavolnyuk.currency.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.mashazavolnyuk.currency.R;

/**
 * Created by Dark Maleficent on 26.11.2016.
 */

public class CurrentCourseHolders extends RecyclerView.ViewHolder {
    TextView cciText;
    TextView saleText;
    TextView buyText;


    public CurrentCourseHolders(View itemView) {
        super(itemView);
        cciText=(TextView) itemView.findViewById(R.id.tv_course_currency);
        saleText=(TextView) itemView.findViewById(R.id.tv_course_currency_sale);
        buyText=(TextView) itemView.findViewById(R.id.tv_course_currency_buy);
    }
}
