package com.xn121.scjg.nmt.bean;

/**
 * Created by hongge on 15/7/26.
 */
public class Profit {

    private String start;
    private String end;
    private Double profit;

    public Profit() {
    }

    public Profit(String start, String end, Double profit) {
        this.start = start;
        this.end = end;
        this.profit = profit;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public Double getProfit() {
        return profit;
    }

    public void setProfit(Double profit) {
        this.profit = profit;
    }
}
