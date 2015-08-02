package com.xn121.scjg.nmt;

import android.app.Application;

import com.xn121.scjg.nmt.bean.Profit;

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
}
