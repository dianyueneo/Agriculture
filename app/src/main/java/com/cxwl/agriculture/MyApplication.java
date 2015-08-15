package com.cxwl.agriculture;

import android.app.Application;

import com.cxwl.agriculture.bean.Profit;
import com.xn121.scjg.nmt.scaleview.ContextProvider;

import java.util.HashMap;
import java.util.List;

/**
 * Created by hongge on 15/7/28.
 */
public class MyApplication extends Application{

    private List<Profit> profitList;

    private String location;
    private String lat;
    private String lon;
    private String cityId;
    private String weatherId;

    private HashMap<String, String> warning;

    @Override
    public void onCreate() {
        super.onCreate();
        ContextProvider.init(this);
    }

    public List<Profit> getProfitList() {
        return profitList;
    }

    public void setProfitList(List<Profit> profitList) {
        this.profitList = profitList;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }

    public String getCityId() {
        return cityId;
    }

    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    public String getWeatherId() {
        return weatherId;
    }

    public void setWeatherId(String weatherId) {
        this.weatherId = weatherId;
    }

    public HashMap<String, String> getWarning() {
        return warning;
    }

    public void setWarning(HashMap<String, String> warning) {
        this.warning = warning;
    }
}
