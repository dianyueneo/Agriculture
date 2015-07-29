package com.xn121.scjg.nmt.bean;

import java.util.List;

/**
 * Created by admin on 15/7/29.
 */
public class Product {

    private int code;
    private String rrp;
    private String ap;
    private List<Price> list;
    private String title;
    private String date;
    private String province;
    private String product;

    private static final int CODE_1 = 1 << 0;//时间扩展
    private static final int CODE_2 = 1 << 1;//农产品扩展
    private static final int CODE_3 = 1 << 2;//市场扩展到省份
    private static final int CODE_4 = 1 << 3;//省份扩展到全国


    public List<Price> getList() {
        return list;
    }

    public void setList(List<Price> list) {
        this.list = list;
    }

    public String getRrp() {
        return rrp;
    }

    public void setRrp(String rrp) {
        this.rrp = rrp;
    }

    public String getAp() {
        return ap;
    }

    public void setAp(String ap) {
        this.ap = ap;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    public String getTitle(){
        String str_start = "没有找到";
        String str_end = "为您找到以下";
        String str = null;

        if(code == 0){
            str = "为您找到以下内容：";
        }else{
            if ((code & CODE_1) == CODE_1) {
                str_start += date;
                str_end += "日期";
            }

            if ((code & CODE_3) == CODE_3) {
                str_end += "省份";
            }

            if ((code & CODE_4) == CODE_4) {
                str_end += "全国";
            }

            if ((code & CODE_2) == CODE_2) {
                str_end += "产品";
            }else{
                str_end += product;
            }
            str_start += title;
            str_end += "的价格";
        }

        return str == null ?  str_start+str_end : str;
    }
}
