package com.example.pipamountpipvaluemargincalculators;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

public class PopPup extends Activity {

    //Intent extras
    public static final String PIPAmountRes = "com.example.pipamountpipvaluemargincalculators.PIPAmountRes";
    public static final String PIPValueRes =  "com.example.pipamountpipvaluemargincalculators.PIPValueRes";
    public static final String MarginRes =  "com.example.pipamountpipvaluemargincalculators.MarginRes";



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_layout_poppup);

        /*
          to get width and height as percentages of our screen pixels we use displayMetrix
         */

        //admob
        //initializing the sdk
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        AdView AdView = findViewById(R.id.adview3);
        AdRequest adRequest = new AdRequest.Builder().build();
        AdView.loadAd(adRequest);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);

        int width = dm.widthPixels;
        int height = dm.heightPixels;

        getWindow().setLayout((int)(width*1),(int)(height*1.1));


        Intent intent = getIntent();

       //dealing with the pip amount class
        int PIPAmountResults = intent.getIntExtra(PIPAmountRes,-1);

        //dealing with the pip value class
        String PIPValueResults = intent.getStringExtra(PIPValueRes);

        //dealing with the margin class
        String MarginResults = intent.getStringExtra(MarginRes);


        TextView txtRes = findViewById(R.id.txt_poppup_result);

        if(PIPAmountResults != -1){

            txtRes.setText((PIPAmountResults+" pips"));

        }else if(PIPValueResults != null){


            txtRes.setText((""+PIPValueResults));

        }else if(MarginResults != null){

            txtRes.setText((""+MarginResults));

        }


        /*
          exit from our current activity
         */

        View exitBtn = findViewById(R.id.ExitFAB);
        exitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
