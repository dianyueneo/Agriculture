package com.xn121.scjg.nmt;

import android.app.Application;

import com.xn121.scjg.nmt.bean.Profit;

import java.util.List;

/**
 * Created by hongge on 15/7/28.
 */
public class MyApplication extends Application{

    private List<Profit> profitList;

    public List<Profit> getProfitList() {
        return profitList;
    }

    public void setProfitList(List<Profit> profitList) {
        this.profitList = profitList;
    }
}
