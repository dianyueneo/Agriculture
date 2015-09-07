package com.cxwl.agriculture.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Response;
import com.cxwl.agriculture.R;

/**
 * Created by admin on 15/8/27.
 */
public abstract class BaseFragment extends Fragment{

    private RootLayout rootLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(rootLayout == null){
            rootLayout = new RootLayout(getActivity(), R.layout.loadpage_error, R.layout.loadpage_loading) {

                @Override
                protected void load(Response.Listener<String> listener, Response.ErrorListener errorListener) {
                    BaseFragment.this.load(listener, errorListener);
                }

                @Override
                protected View createSuccessView() {
                    return BaseFragment.this.createSuccessView();
                }
            };
        }else if(rootLayout.getState() == 1 || rootLayout.getState() ==3){
            rootLayout.show();
        }

        ViewGroup parent = (ViewGroup)rootLayout.getParent();
        if(parent != null){
            parent.removeView(rootLayout);
        }

        return rootLayout;
    }

    protected abstract void load(final Response.Listener<String> listener, final Response.ErrorListener errorListener);
    protected abstract View createSuccessView();
}
