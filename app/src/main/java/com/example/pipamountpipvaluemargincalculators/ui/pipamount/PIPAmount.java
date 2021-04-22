package com.example.pipamountpipvaluemargincalculators.ui.pipamount;

import java.util.ArrayList;
import java.util.StringTokenizer;

public class PIPAmount {

    //jpy Entry variables
    private ArrayList<Integer> JpyEVBC = new ArrayList<Integer>();//before comma
    private ArrayList<Integer> JpyEVAC = new ArrayList<Integer>();//after comma

    //jpy Target variable
    private ArrayList<Integer> JpyTVBC = new ArrayList<Integer>();//before comma
    private ArrayList<Integer> JpyTVAC = new ArrayList<Integer>();//after comma


    //Nonjpy Entry variables
    private ArrayList<Integer> NonJpyEV = new ArrayList<Integer>();

    //Nonjpy Target variable
    private ArrayList<Integer> NonJpyTV = new ArrayList<Integer>();





    public int pipAmount(double Entry,double Target) {

        int NumberOfPips = 0;




        if(Double.toString(Entry).charAt(1) != '.') {
            //jpy currency pair alert
            NumberOfPips = JpyCalc(Entry,Target);
        }else if(Double.toString(Entry).charAt(1) == '.'){
            //the normal one
            NumberOfPips = NonJpyCalc(Entry,Target);
        }else{
            //jpy currency pair that have 3 values before the comma alert
            NumberOfPips = JpyCalcException(Entry,Target);
        }


        return NumberOfPips;
    }


    private static String[] myTokenizer(double Value) {

        String strValue = Double.toString(Value);
        StringTokenizer tk = new StringTokenizer(strValue,".");
        String myToken1 = tk.nextToken();
        String myToken2 = tk.nextToken();

        String[] pieces = {myToken1, myToken2+"0"};
        return pieces;

    }

    private int NonJpyCalc(double Entry,double Target) {
        //get value of a non jpy pair after the comma for entry and target
        int NumberOfPips = 0;
        for(int i = 0; i <= 3 ;i++) {
            NonJpyEV.add(Character.getNumericValue( myTokenizer(Entry)[1].charAt(i)));
            NonJpyTV.add(Character.getNumericValue( myTokenizer(Target)[1].charAt(i)));

        }

        if(NonJpyEV.get(0) > NonJpyTV.get(0)) {
            NumberOfPips = NumberOfPips + (NonJpyEV.get(0) - NonJpyTV.get(0))*1000;
        }else if(NonJpyEV.get(0) < NonJpyTV.get(0)) {
            NumberOfPips = NumberOfPips + (NonJpyTV.get(0) - NonJpyEV.get(0))*1000;
        }


        if(NonJpyEV.get(1) > NonJpyTV.get(1)) {
            NumberOfPips = NumberOfPips + (NonJpyEV.get(1) - NonJpyTV.get(1))*100;
        }else if(NonJpyEV.get(1) < NonJpyTV.get(1)) {
            NumberOfPips = NumberOfPips + (NonJpyTV.get(1) - NonJpyEV.get(1))*100;
        }


        if(NonJpyEV.get(2) > NonJpyTV.get(2)) {
            NumberOfPips = NumberOfPips + (NonJpyEV.get(2) - NonJpyTV.get(2))*10;
        }else if(NonJpyEV.get(2) < NonJpyTV.get(2)) {
            NumberOfPips = NumberOfPips + (NonJpyTV.get(2) - NonJpyEV.get(2))*10;
        }

        if(NonJpyEV.get(3) > NonJpyTV.get(3)) {
            NumberOfPips = NumberOfPips + (NonJpyEV.get(3) - NonJpyTV.get(3));
        }else if(NonJpyEV.get(3) < NonJpyTV.get(3)) {
            NumberOfPips = NumberOfPips + (NonJpyTV.get(3) - NonJpyEV.get(3));
        }

        return NumberOfPips;
    }





    private int JpyCalcException(double Entry,double Target) {
        //get value of a non jpy pair after the comma for entry and target
        int NumberOfPips = 0;

        //before the comma
        for(int i = 1; i <= 3 ;i++) {
            JpyEVBC.add(Character.getNumericValue( myTokenizer(Entry)[0].charAt(i)));
            JpyTVBC.add(Character.getNumericValue( myTokenizer(Target)[0].charAt(i)));

        }

        //after the comma
        for(int i = 0; i <= 1 ;i++) {
            JpyEVAC.add(Character.getNumericValue( myTokenizer(Entry)[1].charAt(i)));
            JpyTVAC.add(Character.getNumericValue( myTokenizer(Target)[1].charAt(i)));

        }

        //before the comma
        if(JpyEVBC.get(1) > JpyTVBC.get(1)) {
            NumberOfPips = NumberOfPips + (JpyEVBC.get(1)- JpyTVBC.get(1))*1000;
        }else if(JpyEVBC.get(1) < JpyTVBC.get(1)) {
            NumberOfPips = NumberOfPips + (JpyTVBC.get(1) - JpyEVBC.get(1))*1000;
        }


        if(JpyEVBC.get(2) > JpyTVBC.get(2)) {
            NumberOfPips = NumberOfPips + (JpyEVBC.get(2)- JpyTVBC.get(2))*100;
        }else if(JpyEVBC.get(2) < JpyTVBC.get(2)) {
            NumberOfPips = NumberOfPips + (JpyTVBC.get(2) - JpyEVBC.get(2))*100;
        }




        //After the comma
        if(JpyEVAC.get(0) > JpyTVAC.get(0)) {
            NumberOfPips = NumberOfPips + (JpyEVAC.get(0)- JpyTVAC.get(0))*10;
        }else if(JpyEVAC.get(0) < JpyTVAC.get(0)) {
            NumberOfPips = NumberOfPips + (JpyTVAC.get(0) - JpyEVAC.get(0))*10;
        }

        if(JpyEVAC.get(1) > JpyTVAC.get(1)) {
            NumberOfPips = NumberOfPips + (JpyEVAC.get(1)- JpyTVAC.get(1));
        }else if(JpyEVAC.get(1) < JpyTVAC.get(1)) {
            NumberOfPips = NumberOfPips + (JpyTVAC.get(1) - JpyEVAC.get(1));
        }
        return NumberOfPips;
    }


    //the one with 2 values before the comma
    private int JpyCalc(double Entry,double Target) {
        //get value of a non jpy pair after the comma for entry and target
        int NumberOfPips = 0;

        //before the comma
        for(int i = 1; i <= 2 ;i++) {
            JpyEVBC.add(Character.getNumericValue( myTokenizer(Entry)[0].charAt(i)));
            JpyTVBC.add(Character.getNumericValue( myTokenizer(Target)[0].charAt(i)));

        }

        //after the comma
        for(int i = 0; i <= 1 ;i++) {
            JpyEVAC.add(Character.getNumericValue( myTokenizer(Entry)[1].charAt(i)));
            JpyTVAC.add(Character.getNumericValue( myTokenizer(Target)[1].charAt(i)));

        }

        //before the comma
        if(JpyEVBC.get(0) > JpyTVBC.get(0)) {
            NumberOfPips = NumberOfPips + (JpyEVBC.get(0)- JpyTVBC.get(0))*1000;
        }else if(JpyEVBC.get(0) < JpyTVBC.get(0)) {
            NumberOfPips = NumberOfPips + (JpyTVBC.get(0) - JpyEVBC.get(0))*1000;
        }


        if(JpyEVBC.get(1) > JpyTVBC.get(1)) {
            NumberOfPips = NumberOfPips + (JpyEVBC.get(1)- JpyTVBC.get(1))*100;
        }else if(JpyEVBC.get(1) < JpyTVBC.get(1)) {
            NumberOfPips = NumberOfPips + (JpyTVBC.get(1) - JpyEVBC.get(1))*100;
        }


        //After the comma
        if(JpyEVAC.get(0) > JpyTVAC.get(0)) {
            NumberOfPips = NumberOfPips + (JpyEVAC.get(0)- JpyTVAC.get(0))*10;
        }else if(JpyEVAC.get(0) < JpyTVAC.get(0)) {
            NumberOfPips = NumberOfPips + (JpyTVAC.get(0) - JpyEVAC.get(0))*10;
        }

        if(JpyEVAC.get(1) > JpyTVAC.get(1)) {
            NumberOfPips = NumberOfPips + (JpyEVAC.get(1)- JpyTVAC.get(1));
        }else if(JpyEVAC.get(1) < JpyTVAC.get(1)) {
            NumberOfPips = NumberOfPips + (JpyTVAC.get(1) - JpyEVAC.get(1));
        }
        return NumberOfPips;
    }


}
