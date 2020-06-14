package com.example.cryptorate;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.cryptorate.pojo.Rate;

import java.util.ArrayList;

public class ThreeColumn_ListAdapter extends ArrayAdapter<String> {

    private LayoutInflater mInflater;
    private ArrayList<String> fiatMoney;
    private int mViewResourceId;

    public ThreeColumn_ListAdapter(Context context, int textViewResourceId, ArrayList<String> fiatMoney) {
        super(context, textViewResourceId);
        this.fiatMoney = fiatMoney;
        mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        mViewResourceId = textViewResourceId;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        View listItemView = convertView;

        if(listItemView == null)
        {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_rates, parent, false);

        }

        View textContainer = listItemView.findViewById(R.id.text);;

//        TextView firstName = listItemView.findViewById(R.id.textCryptocurrencyLR);
        for (int pos = 0; pos < fiatMoney.size(); pos += 2 ) {
//            firstName.setText(fiatMoney.get(pos));
        }
        return listItemView;
    }
}
