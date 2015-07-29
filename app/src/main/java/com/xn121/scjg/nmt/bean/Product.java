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

    public String getTitle(){
        String str = null;

        switch (code){
            case 0://没有扩展

                break;
            case 1://时间有扩展,例如查询 6 月 1 日但无数据,返回了 6 月 3 日的结果;

                break;
            case 2://农产品有扩展,例如查询黄花鱼,查无此鱼,返回其他水产的结果;

                break;
            case 4://市场扩展到了省份,例如查询北京新发地市场的某菜价,返回全北京各个市场的 该菜价价格;

                break;
            case 8://省份扩展到了全国。

                break;
            case 3://时间、农产品扩展

                break;
            case 5://时间、省份扩展

                break;
            case 7://时间、农产品、省份扩展

                break;



        }

        return str;
    }
}
