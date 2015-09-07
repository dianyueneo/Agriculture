package com.cxwl.agriculture.view;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;

import com.android.volley.Response;
import com.android.volley.VolleyError;

/**
 * Created by admin on 15/8/27.
 */
public abstract class RootLayout extends FrameLayout{

    private static final int STATE_UNLOADED = 1;
    private static final int STATE_LOADING = 2;
    private static final int STATE_ERROR = 3;
    private static final int STATE_SUCCEED = 4;

    private View mLoadingView, mErrorView, mSucceedView;

    private int page_error, page_loading;
    private LayoutInflater layoutInflater;

    private int mState;

    public RootLayout(Context context, int page_error, int page_loading){
        super(context);
        this.page_error = page_error;
        this.page_loading = page_loading;
        layoutInflater = LayoutInflater.from(context);
        init();
    }

    private void init(){
        mState = STATE_UNLOADED;
        mLoadingView = addLayout(page_loading);
        show();
    }

    private View addLayout(int layoutId){
        View view = null;
        if(layoutId != 0){
            view = layoutInflater.inflate(layoutId, null);
            addView(view, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        }
        return view;
    }

    private void showPageView(){
        switch (mState){
            case STATE_LOADING:
                mLoadingView.setVisibility(VISIBLE);
                if(mErrorView != null){
                    mErrorView.setVisibility(INVISIBLE);
                }
                break;
            case STATE_ERROR:
                if(mErrorView == null){
                    mErrorView = addLayout(page_error);
                }
                mErrorView.setVisibility(VISIBLE);
                mErrorView.setOnClickListener(new OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        show();
                    }
                });

                mLoadingView.setVisibility(INVISIBLE);
                break;
            case STATE_SUCCEED:
                if(mSucceedView == null){
                    mSucceedView = createSuccessView();
                    addView(mSucceedView, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
                }
                mSucceedView.setVisibility(VISIBLE);

                mLoadingView.setVisibility(INVISIBLE);
                if(mErrorView != null){
                    mErrorView.setVisibility(INVISIBLE);
                }

                break;
            default:
                break;

        }
    }

    public int getState(){
        return mState;
    }

    public void show(){
        if(mState == STATE_ERROR || mState == STATE_UNLOADED){
            mState = STATE_LOADING;
            load(new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    mState = STATE_SUCCEED;
                    showPageView();
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    mState = STATE_ERROR;
                    showPageView();
                }
            });

        }
        showPageView();
    }

    protected abstract void load(final Response.Listener<String> listener, final Response.ErrorListener errorListener);
    protected abstract View createSuccessView();

}
