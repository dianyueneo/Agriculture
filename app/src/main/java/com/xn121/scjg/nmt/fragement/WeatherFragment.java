package com.xn121.scjg.nmt.fragement;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.RetryPolicy;
import com.android.volley.toolbox.Volley;
import com.xn121.scjg.nmt.R;
import com.xn121.scjg.nmt.chart.ChartView;
import com.xn121.scjg.nmt.chart.LineData;

import java.util.ArrayList;

/**
 * Created by admin on 7/16/15.
 */
public class WeatherFragment extends Fragment {

    private View rootView;
    private RequestQueue requestQueue;
    private RetryPolicy retryPolicy;

    private ChartView chartView;

    private String[] date = {"星期一","星期二","星期三","星期四","星期五","星期六","星期七"};
    private int[] maxTemperature = {15,20,20,18,17,18,19};
    private int[] minTemperature = {5,5,7,5,3,4,4};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(rootView == null){
            rootView = inflater.inflate(R.layout.fragment_weather, null);
            init();
        }
        ViewGroup parent = (ViewGroup)rootView.getParent();
        if(parent != null){
            parent.removeView(rootView);
        }
        return rootView;
    }

    private void init(){
        chartView = (ChartView)rootView.findViewById(R.id.chart);
        chartView.chartViewType = ChartView.ChartViewType.ChartViewLine;

        setData();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        requestQueue = Volley.newRequestQueue(activity);
        retryPolicy = new DefaultRetryPolicy(5000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
    }

    private void setData(){
        ArrayList<String> xlabels = new ArrayList<String>();
        ArrayList<Number> dataArrayList_max = new ArrayList<Number>();
        ArrayList<Number> dataArrayList_min = new ArrayList<Number>();
        for (int i = 0; i < 7; i++) {
            xlabels.add(date[i]);
            dataArrayList_max.add(maxTemperature[i]);
            dataArrayList_min.add(minTemperature[i]);
        }

        LineData lineData_max = new LineData();
        lineData_max.colorValue = Color.parseColor("#009688");
        lineData_max.dataList = dataArrayList_max;

        LineData lineData_min = new LineData();
        lineData_min.colorValue = Color.parseColor("#6D6D6D");
        lineData_min.dataList = dataArrayList_min;

        ArrayList<LineData> daArrayList = new ArrayList<LineData>();
        daArrayList.add(lineData_max);
        daArrayList.add(lineData_min);

        chartView.dataArray = daArrayList;
        chartView.xAxisLabels = xlabels;
        chartView.showYLabels = false;
        chartView.showXLabels = false;
        chartView.drawYScaleLine = true;
        chartView.drawXScaleLine = false;
        chartView.YAxisDataCount = 4;

        chartView.invalidate();
    }
}
