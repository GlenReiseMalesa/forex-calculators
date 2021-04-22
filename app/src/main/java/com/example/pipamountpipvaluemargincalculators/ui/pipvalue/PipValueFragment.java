package com.example.pipamountpipvaluemargincalculators.ui.pipvalue;

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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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

public class PipValueFragment extends Fragment {


    private View mRoot;
    private Spinner mSpinnerAccountCurrency;
    private Spinner mSpinnerAccountType;
    private Spinner mSpinnerCurrencyPair;
    private EditText mEditTextLotSize;
    private EditText mEditTextPipAmount;
    private String mPIPAmount;
    private String mLotSize;
    private String mCurrencyPair;
    private String mAccountType;
    private String mAccountCurrency;
    private PIPValue mPIPValue;
    double mMyExchangeRate;
    private View mBtn_calculate;


    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        mRoot = inflater.inflate(R.layout.fragment_pip_value, container, false);

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

        //get PIP value class reference
        mPIPValue = new PIPValue();

        //get out view references
        viewReferences();



        mBtn_calculate = mRoot.findViewById(R.id.button_pipvalue_calculate);
        mBtn_calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //get view values
                    viewValues();

                    //The calculations
                        PIPValueCalc();


                }
        });
        return mRoot;
    }

    private void PIPValueCalc() {


        double dblLotSize;
        int IntNPIPs;

        //handle entry errors
        if(mLotSize.length() == 0){
            dblLotSize = Double.parseDouble("0.00001");
        }else{

            try {
                dblLotSize = Double.parseDouble(mLotSize);
            }catch(Exception e) {
                dblLotSize = 0.00001;
            }
        }

        if (mPIPAmount.length() == 0){
            IntNPIPs = Integer.parseInt("0");
        }else{

            try {
                IntNPIPs = Integer.parseInt(mPIPAmount);
            }catch(Exception e) {
                IntNPIPs = 0;
            }

        }



        if( dblLotSize == 0.00001 | IntNPIPs == 0){
            Toast.makeText(getContext(),"please enter appropriate values before calculating!",Toast.LENGTH_SHORT).show();
        }else {

            //if the appropriate value have been entered but a short number is entered
            try {

                mBtn_calculate.setBackground(getResources().getDrawable(R.drawable.button_background_loading));
                GetPairExchangeRates(dblLotSize,IntNPIPs);

            }catch (Exception e){
                Toast.makeText(getContext(), "please try entering new values!", Toast.LENGTH_SHORT).show();
            }

        }

    }


    private void GetPairExchangeRates(double dblLotSize,int IntNPIPs) {

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

                GetBaseCurrencyExchangeRates(dblLotSize,IntNPIPs);

            }

            @Override
            public void onFailure(Call<Rates> call, Throwable t) {
                Toast.makeText(getContext(),"network error!",Toast.LENGTH_LONG).show();
            }
        });


    }

    public void GetBaseCurrencyExchangeRates(double dblLotSize,int IntNPIPs){
        //convert the pip value to account currency
        double PairPIPValue =Double.parseDouble( mPIPValue.exactPipValueCalc(mAccountCurrency,dblLotSize,mAccountType,mCurrencyPair,mMyExchangeRate,IntNPIPs).replace(',','.'));


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

                        mMyExchangeRate = rate.getExchangeRate().get(mCurrencyPair.substring(0, 3)).getAsDouble();

                    }else{
                        mMyExchangeRate = 1;
                    }

                    //change button color back
                    mBtn_calculate.setBackground(getResources().getDrawable(R.drawable.button_background));

                     /*
                      this is where we start the activity for our poppup
                     */
                    Intent intent = new Intent(getContext(), PopPup.class);
                    String strPIPValue = String.format("%1.2f", (PairPIPValue / mMyExchangeRate) );
                    intent.putExtra(PopPup.PIPValueRes,((String) (strPIPValue+" "+ mAccountCurrency)));
                    startActivity(intent);


                }


                @Override
                public void onFailure(Call<Rates> call, Throwable t) {
                    Toast.makeText(getContext(), "network error!", Toast.LENGTH_LONG).show();
                }
            });


    }

    private void viewValues() {
        mAccountCurrency = mSpinnerAccountCurrency.getSelectedItem().toString();
        mAccountType = mSpinnerAccountType.getSelectedItem().toString();
        mCurrencyPair = mSpinnerCurrencyPair.getSelectedItem().toString();
        mLotSize = mEditTextLotSize.getText().toString();
        mPIPAmount = mEditTextPipAmount.getText().toString();
    }

    private void viewReferences() {
        mSpinnerAccountCurrency = mRoot.findViewById(R.id.spinner_pipvalue_accountcurrency);
        mSpinnerAccountType = mRoot.findViewById(R.id.spinner_pipvalue_accounttype);
        mSpinnerCurrencyPair = mRoot.findViewById(R.id.spinner_pipvalue_currencypair);
        mEditTextLotSize = mRoot.findViewById(R.id.editText_pipvalue_lotsize);
        mEditTextPipAmount = mRoot.findViewById(R.id.editText_pipvalue_pipamount);

        //populating the spinners
        //get reference to out dataCentre
        DataCentre dataCentre = new DataCentre();

        //populating account currency spinner
        List<String> AccountCurrency = dataCentre.AccountCurrencies;
        ArrayAdapter<String> AdapterAccountCurrency = new ArrayAdapter<>(mRoot.getContext(),android.R.layout.simple_spinner_item,AccountCurrency);
        AdapterAccountCurrency.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerAccountCurrency.setAdapter(AdapterAccountCurrency);

        //populating account type spinner
        List<String> AccountType = dataCentre.AccountType;
        ArrayAdapter<String> AdapterAccountType = new ArrayAdapter<>(mRoot.getContext(),android.R.layout.simple_spinner_item,AccountType);
        AdapterAccountType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerAccountType.setAdapter(AdapterAccountType);

        //populating currency pair spinner
        List<String> CurrencyPair = dataCentre.CurrencyPairs;
        ArrayAdapter<String> AdapterCurrencyPair = new ArrayAdapter<>(mRoot.getContext(),android.R.layout.simple_spinner_item,CurrencyPair);
        AdapterCurrencyPair.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mSpinnerCurrencyPair.setAdapter(AdapterCurrencyPair);

    }
}
