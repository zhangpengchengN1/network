package com.zpc.myapplication.network.server;

import com.zpc.myapplication.bean.AddressBean;

import retrofit2.http.GET;
import retrofit2.http.Header;
import rx.Observable;

public interface AddressControllerImpl {

    @GET("api/mine")
    Observable<AddressBean> addressList(@Header("token") String token);

}
