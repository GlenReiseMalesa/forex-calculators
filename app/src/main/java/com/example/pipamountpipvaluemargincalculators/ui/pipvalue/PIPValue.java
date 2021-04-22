package com.example.pipamountpipvaluemargincalculators.ui.pipvalue;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PIPValue {

    double pipValue = 0.0;
    public String quoteCurrency = "";
    public String accountCurrency = "";
    private String mStrPipValue;

    /*
     * the actual calculation for the pips value
     */
    public String exactPipValueCalc(String AccountCurrency,double lotSize,String unitSizeName,String currencyPair,double ExchangeRate,int pipAmount) {

        accountCurrency = AccountCurrency;

        double nonJpyPip = 0.0001;//use this if the pair doesnt end with jpy
        double JpyPip = 0.01; //use this if the pair ends in jpy


        //different unit sizes
        int standard = 100000;
        int mini = 10000;
        int micro = 1000;


        //Check if pair is jpy or non jpy
        Pattern pairPattern = Pattern.compile("\\w{3}JPY");
        Matcher pairMatcher = pairPattern.matcher(currencyPair);

        if(pairMatcher.matches()) {

            if(unitSizeName == "standard lot") {
                pipValue =  pipValueFormula(lotSize,JpyPip,ExchangeRate,pipAmount,standard);
            }else if(unitSizeName == "mini lot") {
                pipValue =  pipValueFormula(lotSize,JpyPip,ExchangeRate,pipAmount,mini);
            }else if(unitSizeName == "micro lot") {
                pipValue =  pipValueFormula(lotSize,JpyPip,ExchangeRate,pipAmount,micro);
            }

        }else {

            if(unitSizeName == "standard lot") {
                pipValue =  pipValueFormula(lotSize,nonJpyPip,ExchangeRate,pipAmount,standard);
            }else if(unitSizeName == "mini lot") {
                pipValue =  pipValueFormula(lotSize,nonJpyPip,ExchangeRate,pipAmount,mini);
            }else if(unitSizeName == "micro lot") {
                pipValue =  pipValueFormula(lotSize,nonJpyPip,ExchangeRate,pipAmount,micro);
            }

        }


        mStrPipValue = String.format("%1.2f", pipValue);
        quoteCurrency =mStrPipValue;

        return  quoteCurrency;
    }


    /*
     *general pip value for different unit size lots
     */
    public double pipValueFormula(double lotSize,double onePip,double ExchangeRate,int pipAmount,int unitSize) {

        double nPips = (onePip * pipAmount);


        return ( nPips / ExchangeRate ) * (unitSize * lotSize);

    }




}
