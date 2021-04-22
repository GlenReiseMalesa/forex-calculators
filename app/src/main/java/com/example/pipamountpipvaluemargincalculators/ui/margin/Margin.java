package com.example.pipamountpipvaluemargincalculators.ui.margin;

public class Margin {

    double marginValue = 0.0;
    public String quoteCurrency = "";
    public String accountCurrency = "";

    /*
     * the actual calculation for the margin value
     */
    public String exactMarginCalc(String AccountCurrency,double lotSize,String unitSizeName,String currencyPair,double ExchangeRate,int leverage) {

        accountCurrency = AccountCurrency;

        //different unit sizes
        int standard = 100000;
        int mini = 10000;
        int micro = 1000;


        if(unitSizeName == "standard lot") {
            marginValue =  marginFormula(lotSize,leverage,ExchangeRate,standard);
        }else if(unitSizeName == "mini lot") {
            marginValue =  marginFormula(lotSize,leverage,ExchangeRate,mini);
        }else if(unitSizeName == "micro lot") {
            marginValue =  marginFormula(lotSize,leverage,ExchangeRate,micro);
        }




        String strmarginValue = String.format("%1.2f", marginValue);
        quoteCurrency = strmarginValue;

        return quoteCurrency;
    }


    /*
     *general margin for different unit size lots
     */
    public double marginFormula(double lotSize,int leverage,double ExchangeRate,int unitSize) {

        return ((unitSize * lotSize) / (leverage /    ExchangeRate) ) ;

    }


}
