package com.example.pipamountpipvaluemargincalculators.ui.margin;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.pipamountpipvaluemargincalculators.PopPup;
import com.example.pipamountpipvaluemargincalculators.R;
import com.example.pipamountpipvaluemargincalculators.Rates;
import com.example.pipamountpipvaluemargincalculators.RatesApi;
import com.example.pipamountpipvaluemargincalculators.ui.DataCentre;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MarginFragment extends Fragment {


    private View mRoot;
    private Spinner mSpinnerAccountCurrencies;
    private Spinner mSpinnerAccountType;
    private Spinner mSpinnerCurrencyPair;
    private Spinner mSpinnerLeverage;
    private EditText mEditTextLotSize;
    private String mAccountCurrency;
    private String mAccountType;
    private String mCurrencyPair;
    private String mLeverage;
    private String mLotSize;
    private Margin mMargin;
    private double mMyExchangeRate;
    private View mBtn_calculate;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        mRoot = inflater.inflate(R.layout.fragment_margin, container, false);


        //admob
        //initializing the sdk
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        AdView AdView = mRoot.findViewById(R.id.adview2);
        AdRequest adRequest = new AdRequest.Builder().build();
        AdView.loadAd(adRequest);


        //Get view references
        viewReferences();

        //get reference to our margin class
        mMargin = new Margin();

        mBtn_calculate = mRoot.findViewById(R.id.button_margin_calculate);
        mBtn_calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //on button click the values
                viewValues();

                //calculate our margin
                marginCalc();
            }
        });


        return mRoot;
    }

    private void marginCalc() {


        double dblLotSize;

        //handle the entries
        if(mLotSize.length() == 0){
            dblLotSize = Double.parseDouble("0.00001");
        }else{

            try {
                dblLotSize = Double.parseDouble(mLotSize);
            }catch(Exception e) {
                dblLotSize = 0.00001;
            }
        }




        if (dblLotSize == 0.00001 ){
            Toast.makeText(getContext(),"please enter appropriate values before calculating!",Toast.LENGTH_SHORT).show();
        }else{

            //if the appropriate value have been entered but a short number is entered
            try {
                mBtn_calculate.setBackground(getResources().getDrawable(R.drawable.button_background_loading));
                GetPairExchangeRates(dblLotSize);
            }catch (Exception e){
                Toast.makeText(getContext(), "please try entering new values!", Toast.LENGTH_SHORT).show();
            }

        }
    }


    private void GetPairExchangeRates(double dblLotSize) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.openrates.io")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RatesApi ratesApi = retrofit.create(com.example.pipamountpipvaluemargincalculators.RatesApi.class);

        Call<Rates> call = ratesApi.getRates(mCurrencyPair.substring(0,3));

        call.enqueue(new Callback<Rates>() {
            @Override
            public void onResponse(Call<Rates> call, Response<Rates> response) {
                if(!response.isSuccessful()) {
                    Toast.makeText(getContext(),"network error!",Toast.LENGTH_LONG).show();
                }

                //get the exchange rate value
                Rates rate = response.body();
                mMyExchangeRate = rate.getExchangeRate().get(mCurrencyPair.substring(3)).getAsDouble();




                GetBaseCurrencyExchangeRates(dblLotSize);


            }

            @Override
            public void onFailure(Call<Rates> call, Throwable t) {
                Toast.makeText(getContext(),"network error!",Toast.LENGTH_LONG).show();
            }
        });


    }

    public void GetBaseCurrencyExchangeRates(double dblLotSize){
        //convert the pip value to account currency
        double PairMargin =Double.parseDouble( mMargin.exactMarginCalc(mAccountCurrency,dblLotSize,mAccountType,mCurrencyPair,mMyExchangeRate,Integer.parseInt(mLeverage)).replace(',','.'));


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.openrates.io")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RatesApi ratesApi = retrofit.create(com.example.pipamountpipvaluemargincalculators.RatesApi.class);

        Call<Rates> call2 = ratesApi.getRates(mAccountCurrency);

        call2.enqueue(new Callback<Rates>() {
            @Override
            public void onResponse(Call<Rates> call, Response<Rates> response) {

                if (!response.isSuccessful()) {
                    Toast.makeText(getContext(), "network error!", Toast.LENGTH_LONG).show();
                }

                //get the exchange rate value
                Rates rate = response.body();

                if(!mCurrencyPair.substring(0, 3).equals(mAccountCurrency)) {

                    mMyExchangeRate = rate.getExchangeRate().get(mCurrencyPair.substring(3)).getAsDouble();

                }else{
                    mMyExchangeRate = 1;
                }

                 //change button color back
                  mBtn_calculate.setBackground(getResources().getDrawable(R.drawable.button_background));


                   /*
                      this is where we start the activity for our poppup
                   */
                    Intent intent = new Intent(getContext(), PopPup.class);
                    String strMargin = String.format("%1.2f", (PairMargin / mMyExchangeRate));
                    intent.putExtra(PopPup.MarginRes,((String) ( strMargin +" "+ mAccountCurrency)));
                    startActivity(intent);

            }


            @Override
            public void onFailure(Call<Rates> call, Throwable t) {
                Toast.makeText(getContext(), "network error!", Toast.LENGTH_LONG).show();
            }
        });


    }

    private void viewValues() {
        mAccountCurrency = mSpinnerAccountCurrencies.getSelectedItem().toString();
        mAccountType = mSpinnerAccountType.getSelectedItem().toString();
        mCurrencyPair = mSpinnerCurrencyPair.getSelectedItem().toString();
        mLeverage = mSpinnerLeverage.getSelectedItem().toString();
        mLotSize = mEditTextLotSize.getText().toString();
    }

    private void viewReferences() {
        mSpinnerAccountCurrencies = mRoot.findViewById(R.id.spinner_margin_accountcurrencies);
        mSpinnerAccountType = mRoot.findViewById(R.id.spinner_margin_accounttype);
        mSpinnerCurrencyPair = mRoot.findViewById(R.id.spinner_margin_currencypair);
        mSpinnerLeverage = mRoot.findViewById(R.id.spinner_margin_leverage);
        mEditTextLotSize = mRoot.findViewById(R.id.editText_margin_lotsize);

        //Populating the spinners
        //Get reference to the datacentre
        DataCentre myDataCentre = new DataCentre();

        //populating the account currencies spinner
        List<String> AccountCurrencies = myDataCentre.AccountCurrencies;
        ArrayAdapter<String> AdapterAccountCurrencies = new ArrayAdapter<>(mRoot.getContext(),android.R.layout.simple_spinner_item,AccountCurrencies);
        AdapterAccountCurrencies.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerAccountCurrencies.setAdapter(AdapterAccountCurrencies);


        //populating the account type spinner
        List<String> AccountType = myDataCentre.AccountType;
        ArrayAdapter<String> AdapterAccountType = new ArrayAdapter<>(mRoot.getContext(),android.R.layout.simple_spinner_item,AccountType);
        AdapterAccountType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerAccountType.setAdapter(AdapterAccountType);


        //populating the account currencies spinner
        List<String> CurrencyPair = myDataCentre.CurrencyPairs;
        ArrayAdapter<String> AdapterCurrencyPair = new ArrayAdapter<>(mRoot.getContext(),android.R.layout.simple_spinner_item,CurrencyPair);
        AdapterCurrencyPair.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerCurrencyPair.setAdapter(AdapterCurrencyPair);

        //populating the account currencies spinner
        List<String> Leverage = myDataCentre.Leverage;
        ArrayAdapter<String> AdapterLeverage = new ArrayAdapter<>(mRoot.getContext(),android.R.layout.simple_spinner_item,Leverage);
        AdapterLeverage.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerLeverage.setAdapter(AdapterLeverage);
    }
}
