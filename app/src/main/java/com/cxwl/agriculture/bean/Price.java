package com.cxwl.agriculture.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by admin on 15/7/29.
 */
public class Price implements Parcelable{

    private String product;
    private String province;
    private String name;
    private String address;
    private Double lat;
    private Double lon;
    private String date;
    private String price;

    public Price() {
    }

    public Price(Parcel in){
        this.product = in.readString();
        this.province = in.readString();
        this.name = in.readString();
        this.address = in.readString();
        this.lat = in.readDouble();
        this.lon = in.readDouble();
        this.date = in.readString();
        this.price = in.readString();
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    @Override
    public int describeContents() {
        return 0;
    }

//    private String product;
//    private String province;
//    private String name;
//    private String address;
//    private Double lat;
//    private Double lon;
//    private String date;
//    private String price;
    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(product);
        parcel.writeString(province);
        parcel.writeString(name);
        parcel.writeString(address);
        parcel.writeDouble(lat);
        parcel.writeDouble(lon);
        parcel.writeString(date);
        parcel.writeString(price);
    }

    public static final Creator<Price> CREATOR = new Creator<Price>() {
        @Override
        public Price createFromParcel(Parcel parcel) {
            return new Price(parcel);
        }

        @Override
        public Price[] newArray(int i) {
            return new Price[i];
        }
    };
}
