package com.zpc.myapplication.network.api;


public class BaiKeRetrofit extends HttpManager{
    public BaiKeRetrofit() {
        super();
    }
    private volatile static BaiKeRetrofit api = null;
    public static BaiKeRetrofit getInstance() {
        if (api == null) {
            synchronized (BaiKeRetrofit.class) {
                if (api == null) {
                    api = new BaiKeRetrofit();
                }
            }
        }
        return api;
    }
}
