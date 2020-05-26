package com.example.cryptorate;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.cryptorate.pojo.Rate;

import java.util.ArrayList;

public class ThreeColumn_ListAdapter extends ArrayAdapter<Rate> {

    private LayoutInflater mInflater;
    private ArrayList<Rate> fiatMoney;
    private int mViewResourceId;

    public ThreeColumn_ListAdapter(Context context, int textViewResourceId, ArrayList<Rate> fiatMoney) {
        super(context, textViewResourceId, fiatMoney);
        this.fiatMoney = fiatMoney;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mViewResourceId = textViewResourceId;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = mInflater.inflate(mViewResourceId, null);

        Rate rate = fiatMoney.get(position);

        if (rate != null) {
            TextView firstName = (TextView) convertView.findViewById(R.id.textCryptocurrencyName);
            TextView lastName = (TextView) convertView.findViewById(R.id.textEspaceLR);
            TextView favFood = (TextView) convertView.findViewById(R.id.textValueLR);
            if (firstName != null) {
                firstName.setText(rate.getBase());
            }
            if (lastName != null) {
                lastName.setText("");
            }
            if (favFood != null) {
                favFood.setText(rate.getRate());
            }
        }

        return convertView;
    }
}
