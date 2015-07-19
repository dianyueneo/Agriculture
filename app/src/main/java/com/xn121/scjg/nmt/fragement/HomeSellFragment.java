package com.xn121.scjg.nmt.fragement;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.xn121.scjg.nmt.R;

/**
 * Created by hongge on 15/7/18.
 */
public class HomeSellFragment extends Fragment{

    private View rootView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(rootView == null){
            rootView = inflater.inflate(R.layout.fragment_home_sell, null);
        }
        ViewGroup parent = (ViewGroup)rootView.getParent();
        if(parent != null){
            parent.removeView(rootView);
        }

        initView();

        return rootView;
    }

    private void initView(){

    }
}
