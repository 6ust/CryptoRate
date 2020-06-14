package com.example.cryptorate;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.drawable.AdaptiveIconDrawable;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.cryptorate.pojo.Rate;

import org.json.JSONException;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ListRates extends AppCompatActivity {

    private LayoutInflater mInflater;
    private ArrayList<Rate> fiatMoneyAL;
    private int mViewResourceId;
    ListView listView;
    ArrayList<Rate> listRate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_rates);

        //permite conexão com a Internet na Thread principal
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
    }

    public void btAtualizarListaOnClickView(View v) throws IOException, JSONException {

        // PEGAR O VALOR SELECIONADO NO SPINNER
        Spinner fiatMoney = (Spinner) findViewById(R.id.spFiatMoneyLR);

        String quote = (String) fiatMoney.getSelectedItem();

        quote = getFiatMoneyByCode(quote);

        ListView listView=(ListView)findViewById(R.id.listview);

        //create ArrayList of String
        final ArrayList<String> arrayList=new ArrayList<>();

        String[] cryptocurrencyListLR = getResources().getStringArray(R.array.criptomoedas_name_array);
        String[] cryptocurrencyISOListLR = getResources().getStringArray(R.array.criptomoedas_iso_code_array);

        String rateTemp;

        for (int i = 0; i < cryptocurrencyISOListLR.length; i++) {
            arrayList.add(cryptocurrencyListLR[i]);
            try {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_DENIED) {
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET}, 1);

                    rateTemp = loadValues(cryptocurrencyISOListLR[i], quote);
                    arrayList.add(rateTemp);
                } else {
                }
            } catch (Exception e) {
                Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }

        //Create Adapter
        ArrayAdapter arrayAdapter=new ArrayAdapter(this,android.R.layout.simple_list_item_1,arrayList);

        //assign adapter to listview
        listView.setAdapter(arrayAdapter);
        //        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        //            @Override
        //            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        //                Toast.makeText(MainActivity.this,"clicked item:"+i+" "+arrayList.get(i).toString(),Toast.LENGTH_SHORT).show();
        //            }
        //        });
        //add listener to listview


    }

    private String loadValues(String base_param, String quote_param) throws IOException, JSONException {
        Rate rate = UpdateValue.updateValue(base_param, quote_param, "");

        DecimalFormat df = new DecimalFormat(",###.##");
        Float rateFloat = Float.parseFloat(rate.getRate());

        String rateStr = df.format(rateFloat);

        return rateStr;
    }

    //ALTERAÇÃO DE MOEDA NOME PARA MOEDA ISO CODE
    private String getFiatMoneyByCode(String name) {
        int i = -1;
        for (String cc : getResources().getStringArray(R.array.moedas_name)) {
            i++;
            if (cc.equals(name))
                break;
        }

        return getResources().getStringArray(R.array.moedas_iso_code_array)[i];
    }

    //ALTERAÇÃO DE CRIPTOMOEDA NOME PARA MOEDA ISO CODE
//    private String getCryptocurrenciesByCode(String name) {
//        int i = -1;
//        for (String cc : getResources().getStringArray(R.array.criptomoedas_name_array)) {
//            i++;
//            if (cc.equals(name))
//                break;
//        }
//        return getResources().getStringArray(R.array.criptomoedas_iso_code_array)[i];
//    }

}
