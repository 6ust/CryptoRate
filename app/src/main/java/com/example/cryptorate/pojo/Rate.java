package com.example.cryptorate.pojo;

public class Rate {
    private String base;
    private String quote;
    private String rate;

    public Rate(String base, String quote, String rate) {
        this.base = base;
        this.quote = quote;
        this.rate = rate;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public String getBase() {
        return base;
    }

    public void setQuote(String quote) {
        this.quote = quote;
    }

    public String getQuote() {
        return quote;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getRate() {
        return rate;
    }
}
