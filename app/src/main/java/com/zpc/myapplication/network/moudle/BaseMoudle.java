package com.zpc.myapplication.network.moudle;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;


import androidx.fragment.app.Fragment;

import com.zpc.myapplication.network.callback.LifeCycleModel;
import com.zpc.myapplication.network.callback.OnResponse;
import com.zpc.myapplication.weight.WeiboDialogUtils;

import java.util.ArrayList;
import java.util.List;

import rx.Subscriber;
import rx.Subscription;


public class BaseMoudle implements LifeCycleModel {
    protected List<OnResponse> listeners;
    protected OnResponse onResponse;
    protected Context context;
    protected Fragment fragment;
    //rejava+retrofit2
    Subscription subscribe;
    String dataLoadingText="加载中";
    public BaseMoudle(Context context) {
        this.context = context;
        listeners = new ArrayList<>();
    }

    public BaseMoudle(Activity context) {
        this.context = context.getApplicationContext();
        listeners = new ArrayList<>();
    }

    public BaseMoudle(Fragment fragment) {
        this.fragment = fragment;
        context = fragment.getActivity();
        listeners = new ArrayList<>();
    }
    public void setListener(OnResponse onResponse) {
        this.onResponse = onResponse;
        if (listeners != null) {
            listeners.add(onResponse);
        }
    }

    public void clearListener() {
        if (listeners != null) {
            fragment = null;
            context = null;
            listeners.clear();
        }
    }


    @Override
    public void onCreateView() {
        if (subscribe != null && !subscribe.isUnsubscribed()) {
            subscribe.unsubscribe();
        }
    }

    @Override
    public void onResume() {

    }

    @Override
    public void onPause() {

    }

    @Override
    public void onDestroyView() {

    }

    class ObserverAdpater<T> extends Subscriber<T> {
        private Dialog mWeiboDialog;
        @Override
        public void onCompleted() {

        }

        @Override
        public void onError(Throwable e) {
          if(mWeiboDialog!=null){
              mWeiboDialog.dismiss();
              WeiboDialogUtils.closeDialog(mWeiboDialog);
          }
        }

        @Override
        public void onNext(T t) {
            mWeiboDialog.dismiss();
        }

        @Override
        public void onStart() {
            super.onStart();
            mWeiboDialog = WeiboDialogUtils.createLoadingDialog(context, dataLoadingText);
            mWeiboDialog.show();
        }
    }
}
