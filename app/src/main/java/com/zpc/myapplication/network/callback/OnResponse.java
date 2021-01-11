package com.zpc.myapplication.network.callback;


import org.json.JSONException;


public interface OnResponse<T> {

    void success(int requestCode, String url, T t) throws JSONException;

    void fail(int requestCode, Exception e);
}
