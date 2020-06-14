package com.example.cryptorate;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cryptorate.pojo.Rate;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private DatabaseReference firebaseReferencia = FirebaseDatabase.getInstance().getReference();
    private DatabaseReference usuarioReferencia = firebaseReferencia.child("usuarios").child("001");

    ArrayList<Rate> listRate;
    SQLiteDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //permite conexão com a Internet na Thread principal
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Usuario usuario = new Usuario();

        usuario.setNome("Mark");
        usuario.setSobrenome("Minho");
        usuario.setIdade(34);

        usuarioReferencia.child("001").setValue(usuario);

        usuarioReferencia.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.i("FIREBASE SELECT", dataSnapshot.getValue().toString());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void loadValues(String base_param, String quote_param) throws IOException, JSONException {
        Rate rate =  UpdateValue.updateValue(base_param,quote_param,"");
        TextView fiatMoneyValue = (TextView) findViewById(R.id.rate_value);

        DecimalFormat df = new DecimalFormat(",###.##");
        Float rateFloat = Float.parseFloat(rate.getRate());

        String rateStr = df.format(rateFloat);

        fiatMoneyValue.setText(rateStr);
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
    private String getCryptocurrenciesByCode(String name) {
        int i = -1;
        for (String cc : getResources().getStringArray(R.array.criptomoedas_name_array)) {
            i++;
            if (cc.equals(name))
                break;
        }
        return getResources().getStringArray(R.array.criptomoedas_iso_code_array)[i];
    }

    public void btRefreshOnClickView(View v) {
        // PEGAR O VALOR SELECIONADO NO SPINNER
        Spinner cryptocurrency = (Spinner) findViewById(R.id.spCriptocurrency);
        Spinner fiatMoney = (Spinner) findViewById(R.id.spFiatMoney);

        String base = (String) cryptocurrency.getSelectedItem();
        String quote = (String) fiatMoney.getSelectedItem();

        base = getCryptocurrenciesByCode(base);
        quote = getFiatMoneyByCode(quote);

        try {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.INTERNET) != PackageManager.PERMISSION_DENIED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.INTERNET}, 1);
            } else {
                loadValues(base, quote);
            }
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public void btListRatesOnClickView(View v) {
        Intent listRates = new Intent(this, ListRates.class);
        startActivity(listRates);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                    // PEGAR O VALOR SELECIONADO NO SPINNER
                    Spinner cryptocurrecy = (Spinner) findViewById(R.id.spCriptocurrency);
                    Spinner fiatMoney = (Spinner) findViewById(R.id.spFiatMoney);

                    String base = (String) cryptocurrecy.getSelectedItem();
                    String quote = (String) fiatMoney.getSelectedItem();

                    base = getCryptocurrenciesByCode(base);
                    quote = getFiatMoneyByCode(quote);

                    try {
                        loadValues(base, quote);
                    } catch (Exception e) {
                        Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
                    }

                } else {
                    Toast.makeText(this, "Não vai funcionar!!!", Toast.LENGTH_LONG).show();
                }
                return;
            }
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }
}
