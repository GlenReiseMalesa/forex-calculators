package com.example.pipamountpipvaluemargincalculators;

import com.google.gson.JsonObject;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Rates {
    @SerializedName("base")
    @Expose
    private String base;

    @SerializedName("rates")
    @Expose
    private JsonObject ExchangeRate;

    public JsonObject getExchangeRate() {
        return ExchangeRate;
    }

    public String getBase() {
        return base;
    }
}
