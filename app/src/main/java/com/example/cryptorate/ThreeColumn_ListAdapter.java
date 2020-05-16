package com.example.cryptorate;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class ThreeColumn_ListAdapter extends ArrayAdapter<FiatMoney> {

    private LayoutInflater mInflater;
    private ArrayList<FiatMoney> fiatMoney;
    private int mViewResourceId;

    public ThreeColumn_ListAdapter(Context context, int textViewResourceId, ArrayList<FiatMoney> fiatMoney) {
        super(context, textViewResourceId, fiatMoney);
        this.fiatMoney = fiatMoney;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mViewResourceId = textViewResourceId;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = mInflater.inflate(mViewResourceId, null);

        FiatMoney fiatMoneyPos = fiatMoney.get(position);

        if (fiatMoneyPos != null) {
            TextView firstName = (TextView) convertView.findViewById(R.id.textCryptocurrencyName);
            TextView lastName = (TextView) convertView.findViewById(R.id.textEspace);
            TextView favFood = (TextView) convertView.findViewById(R.id.textValue);
            if (firstName != null) {
                firstName.setText(fiatMoneyPos.getId_quote());
            }
            if (lastName != null) {
                lastName.setText("");
            }
            if (favFood != null) {
                favFood.setText(fiatMoneyPos.getRate());
            }
        }

        return convertView;
    }
}
