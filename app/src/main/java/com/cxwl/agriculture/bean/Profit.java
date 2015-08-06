package com.cxwl.agriculture.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by hongge on 15/7/26.
 */
public class Profit implements Parcelable{

    private String name_end;
    private Double profit;
    private Double distance;
    private Double fuelprice;
    private Double time;
    private Double price;
    private Double lon_end;
    private Double lat_end;

    private String name_start;
    private Double lon_start;
    private Double lat_start;

    public Profit() {
    }

    public Profit(Parcel in) {
        this.name_end = in.readString();
        this.profit = in.readDouble();
        this.distance = in.readDouble();
        this.fuelprice = in.readDouble();
        this.time = in.readDouble();
        this.price = in.readDouble();
        this.lon_end = in.readDouble();
        this.lat_end = in.readDouble();
        this.name_start = in.readString();
        this.lon_start = in.readDouble();
        this.lat_start = in.readDouble();
    }

    public String getName_end() {
        return name_end;
    }

    public void setName_end(String name_end) {
        this.name_end = name_end;
    }

    public Double getProfit() {
        return profit;
    }

    public void setProfit(Double profit) {
        this.profit = profit;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public Double getFuelprice() {
        return fuelprice;
    }

    public void setFuelprice(Double fuelprice) {
        this.fuelprice = fuelprice;
    }

    public Double getTime() {
        return time;
    }

    public void setTime(Double time) {
        this.time = time;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getLon_end() {
        return lon_end;
    }

    public void setLon_end(Double lon_end) {
        this.lon_end = lon_end;
    }

    public Double getLat_end() {
        return lat_end;
    }

    public void setLat_end(Double lat_end) {
        this.lat_end = lat_end;
    }

    public String getName_start() {
        return name_start;
    }

    public void setName_start(String name_start) {
        this.name_start = name_start;
    }

    public Double getLon_start() {
        return lon_start;
    }

    public void setLon_start(Double lon_start) {
        this.lon_start = lon_start;
    }

    public Double getLat_start() {
        return lat_start;
    }

    public void setLat_start(Double lat_start) {
        this.lat_start = lat_start;
    }

    @Override
    public int describeContents() {
        return 0;
    }

//    private String name_end;
//    private Double profit;
//    private Double distance;
//    private Double fuelprice;
//    private Double time;
//    private Double price;
//    private Double lon_end;
//    private Double lat_end;
//
//    private String name_start;
//    private Double lon_start;
//    private Double lat_start;
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name_end);
        dest.writeDouble(profit);
        dest.writeDouble(distance);
        dest.writeDouble(fuelprice);
        dest.writeDouble(time);
        dest.writeDouble(price);
        dest.writeDouble(lon_end);
        dest.writeDouble(lat_end);

        dest.writeString(name_start);
        dest.writeDouble(lat_start);
        dest.writeDouble(lat_start);
    }

    public static final Creator<Profit> CREATOR = new Creator<Profit>(){

        @Override
        public Profit createFromParcel(Parcel source) {
            return new Profit(source);
        }

        @Override
        public Profit[] newArray(int size) {
            return new Profit[size];
        }
    };
}

