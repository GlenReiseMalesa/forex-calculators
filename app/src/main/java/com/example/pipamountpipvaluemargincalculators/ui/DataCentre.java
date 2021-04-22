package com.example.pipamountpipvaluemargincalculators.ui;

import java.util.ArrayList;
import java.util.List;

public class DataCentre {

    public List<String> AccountCurrencies = new ArrayList<String>();
    public List<String> AccountType = new ArrayList<String>();
    public List<String> Leverage = new ArrayList<String>();
    public List<String> CurrencyPairs = new ArrayList<String>();


    public DataCentre() {

        initialiseCurrencies();
        initialiseUnitSizes();
        initialiseLeverage();
        initialisePairs();

    }

    public void initialiseCurrencies() {

        AccountCurrencies.add("USD");
        AccountCurrencies.add("EUR");
        AccountCurrencies.add("GBP");
        AccountCurrencies.add("CHF");
        AccountCurrencies.add("ZAR");
        AccountCurrencies.add("JPY");
        AccountCurrencies.add("AUD");
        AccountCurrencies.add("RUB");
        AccountCurrencies.add("PLN");
        AccountCurrencies.add("HUF");
        AccountCurrencies.add("SGD");

    }

    public void initialiseUnitSizes() {

        AccountType.add("standard lot");
        AccountType.add("mini lot");
        AccountType.add("micro lot");
    }

    public void initialiseLeverage() {

        Leverage.add("1");
        Leverage.add("2");
        Leverage.add("3");
        Leverage.add("5");
        Leverage.add("10");
        Leverage.add("15");
        Leverage.add("20");
        Leverage.add("25");
        Leverage.add("30");
        Leverage.add("50");
        Leverage.add("66");
        Leverage.add("100");
        Leverage.add("200");
        Leverage.add("300");
        Leverage.add("400");
        Leverage.add("500");
        Leverage.add("888");

    }

    public void initialisePairs() {

        CurrencyPairs.add("AUDNZD");
        CurrencyPairs.add("AUDCAD");
        CurrencyPairs.add("AUDCHF");
        CurrencyPairs.add("AUDJPY");
        CurrencyPairs.add("AUDUSD");

        CurrencyPairs.add("CADCHF");
        CurrencyPairs.add("CADJPY");

        CurrencyPairs.add("CHFJPY");

        CurrencyPairs.add("EURCAD");
        CurrencyPairs.add("EURAUD");
        CurrencyPairs.add("EURCHF");
        CurrencyPairs.add("EURGBP");
        CurrencyPairs.add("EURHKD");
        CurrencyPairs.add("EURJPY");
        CurrencyPairs.add("EURNOK");
        CurrencyPairs.add("EURNZD");
        CurrencyPairs.add("EURPLN");
        CurrencyPairs.add("EURSEK");
        CurrencyPairs.add("EURSGD");
        CurrencyPairs.add("EURTRY");
        CurrencyPairs.add("EURUSD");
        CurrencyPairs.add("EURZAR");

        CurrencyPairs.add("GBPAUD");
        CurrencyPairs.add("GBPCAD");
        CurrencyPairs.add("GBPCHF");
        CurrencyPairs.add("GBPJPY");
        CurrencyPairs.add("GBPNZD");
        CurrencyPairs.add("GBPSGD");
        CurrencyPairs.add("GBPUSD");
        CurrencyPairs.add("GBPZAR");

        CurrencyPairs.add("NZDCAD");
        CurrencyPairs.add("NZDCHF");
        CurrencyPairs.add("NZDJPY");
        CurrencyPairs.add("NZDUSD");

        CurrencyPairs.add("USDCAD");
        CurrencyPairs.add("USDCNH");
        CurrencyPairs.add("USDCHF");
        CurrencyPairs.add("USDCZK");
        CurrencyPairs.add("USDHKD");
        CurrencyPairs.add("USDJPY");
        CurrencyPairs.add("USDKWD");
        CurrencyPairs.add("USDMXN");
        CurrencyPairs.add("USDNOK");
        CurrencyPairs.add("USDPLN");
        CurrencyPairs.add("USDRUB");
        CurrencyPairs.add("USDSEK");
        CurrencyPairs.add("USDSGD");
        CurrencyPairs.add("USDTRY");
        CurrencyPairs.add("USDZAR");

    }

}
