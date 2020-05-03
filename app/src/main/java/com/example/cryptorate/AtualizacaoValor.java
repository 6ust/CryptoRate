package com.example.cryptorate;

//ENVIARA DADOS E RETORNARA DADOS

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class AtualizacaoValor {

    private static String readStream(InputStream in) {
        BufferedReader r = new BufferedReader(new InputStreamReader(in));
        StringBuilder total = new StringBuilder();
        String line;

        try {
            while ((line = r.readLine()) != null) {
                total.append(line).append('\n');
            }
        } catch(Exception ex) {
            ex.printStackTrace();
        }
        return total.toString();
    }

    private static String request(String stringUrl) throws IOException {
        URL url = null;
        HttpURLConnection urlConnection = null;

        try {
            url = new URL(stringUrl);
            urlConnection = (HttpURLConnection) url.openConnection();
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            return readStream(in);
        } finally {
            urlConnection.disconnect();
        }
    }

    public static Moeda atualizarValor(String id_base, String id_quote, String rate) throws JSONException, IOException {

        String api_key = "3a345c669b08f4c310c81bc4e03727ac8deddff84498545358013172af78a152";

        String resposta = request("https://min-api.cryptocompare.com/data/price?fsym=" + id_base + "&tsyms=" + id_quote + "&api_key=" + api_key);
        JSONObject obj = new JSONObject(resposta);
        String id_rate = obj.getString(id_quote);

        return new Moeda(id_base, id_quote, id_rate);
    }

    // URL TESTE
    // VALIDO
    // https://min-api.cryptocompare.com/data/price?fsym=BTC&tsyms=USD,JPY,EUR&api_key=3a345c669b08f4c310c81bc4e03727ac8deddff84498545358013172af78a152

}
