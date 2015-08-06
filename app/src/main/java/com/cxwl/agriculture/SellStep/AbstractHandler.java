package com.cxwl.agriculture.SellStep;

/**
 * Created by hongge on 15/7/22.
 */
public abstract class AbstractHandler {
    private Handler handler;

    public Handler getHandler() {
        return handler;
    }

    public void setHandler(Handler handler) {
        this.handler = handler;
    }
}
