package com.example.pipamountpipvaluemargincalculators.ui.pipamount;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.pipamountpipvaluemargincalculators.PopPup;
import com.example.pipamountpipvaluemargincalculators.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class PipAmountFragment extends Fragment {


    private View mRoot;
    private EditText mEditTextEntryPrice;
    private EditText mEditTextTargetPrice;
    private String mEntryPrice;
    private String mTargetPrice;
    private PIPAmount mPIPAmount;
    private View mBtn_calculate;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        mRoot = inflater.inflate(R.layout.fragment_pip_amount, container, false);

        //admob
        //initializing the sdk
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });
        AdView AdView = mRoot.findViewById(R.id.adview1);
        AdRequest adRequest = new AdRequest.Builder().build();
        AdView.loadAd(adRequest);

        //Get view references
        viewReferences();

        //PIPAmount class reference
        mPIPAmount = new PIPAmount();

        mBtn_calculate = mRoot.findViewById(R.id.button_pipamount_calculate);
        mBtn_calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                   // On button click get the values from our views
                   viewValues();

                   //Get pip amount results
                   PIPResults();

            }
        });
        return mRoot;
    }

    private void PIPResults() {

       //validate the entries
        if(mEntryPrice.length() == 0){
            mEntryPrice = "0.0";
        }

        if(mTargetPrice.length() == 0){
            mTargetPrice = "0.0";
        }

        //parse values to doubles
        double dblEntryPrice;
        double dblTargetPrice;

        try {
            dblEntryPrice = Double.parseDouble(mEntryPrice);
        }catch (Exception e){
            Toast.makeText(mRoot.getContext(),"please re-enter your values",Toast.LENGTH_LONG).show();
            dblEntryPrice = 0.00001;
        }

        try {
            dblTargetPrice = Double.parseDouble(mTargetPrice);
        }catch (Exception e){
            Toast.makeText(mRoot.getContext(),"please re-enter your values",Toast.LENGTH_LONG).show();
            dblTargetPrice = 0.00001;
        }

        if(mEntryPrice == "0.0" | mTargetPrice == "0.0" | dblEntryPrice == 0.00001 | dblTargetPrice == 0.00001){
            Toast.makeText(mRoot.getContext(),"please re-enter your values",Toast.LENGTH_LONG).show();
        }else{

            //if the appropriate values have been entered but a short value has been entered
            try{
                /*
                 this is where we start the activity for our poppup
               */



                Intent intent = new Intent(getContext(),PopPup.class);
                intent.putExtra(PopPup.PIPAmountRes,mPIPAmount.pipAmount(dblEntryPrice,dblTargetPrice));
                startActivity(intent);

            }catch (Exception e){
                Toast.makeText(mRoot.getContext(),"please re-enter your values",Toast.LENGTH_LONG).show();
            }
        }


    }

    private void viewValues() {
        mEntryPrice = mEditTextEntryPrice.getText().toString();
        mTargetPrice = mEditTextTargetPrice.getText().toString();

    }

    private void viewReferences() {
        mEditTextEntryPrice = mRoot.findViewById(R.id.editText_pipamount_entryprice);
        mEditTextTargetPrice = mRoot.findViewById(R.id.editText_pipamount_targetprice);
    }
}
