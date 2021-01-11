package com.zpc.myapplication.network.moudle;


import android.content.Context;

import com.zpc.myapplication.bean.AddressBean;
import com.zpc.myapplication.network.api.BaiKeRetrofit;
import com.zpc.myapplication.network.server.AddressControllerImpl;
import com.zpc.myapplication.utils.SPUtils;

import org.json.JSONException;


import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class AddressControllerMoudle extends BaseMoudle {

    public static final int POINE_LIST = 1000;
    private AddressControllerImpl controller;

    public AddressControllerMoudle(Context context) {
        super(context);
        controller = BaiKeRetrofit.getInstance().getMyServer(AddressControllerImpl.class);
    }

    public void addressList() {
        String token = (String) SPUtils.get(context, "isLogin", "");
        controller.addressList(token)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<AddressBean>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        Exception exception = (Exception) e;
                        onResponse.fail(POINE_LIST, exception);
                    }

                    @Override
                    public void onNext(AddressBean commonControllerBean) {
                        try {
                            onResponse.success(POINE_LIST, "", commonControllerBean);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
    }





}
