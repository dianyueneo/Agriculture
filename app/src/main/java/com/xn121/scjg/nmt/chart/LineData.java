package com.xn121.scjg.nmt.chart;

import java.util.ArrayList;

public class LineData {

    public enum TextDrawPlace{
        TextDrawUp,
        TextDrawDown,
        TextDrawNone
    }

    public ArrayList dataList;//= new ArrayList();
    public TextDrawPlace textDrawPlace = TextDrawPlace.TextDrawUp;
    public int colorValue;


    public int lineDataMaxValue(){
        if (dataList != null){
            int size = dataList.size();
            if (size > 0){
                int temp = ((Integer)dataList.get(0)).intValue();
                for(int i = 1 ; i < size; i++){
                    int te = ((Integer)dataList.get(i)).intValue();
                    if (temp < te) temp = te;
                }
                return temp;
            }
        }
        return 0;
    }

    public int lineDataMinValue(){
        if (dataList != null){
            int size = dataList.size();
            if (size > 0){
                int temp = ((Integer)dataList.get(0)).intValue();
                for(int i = 1 ; i < size; i++){
                    int te = ((Integer)dataList.get(i)).intValue();
                    if (temp > te) temp = te;
                }
                return temp;
            }
        }
        return 0;
    }

}
