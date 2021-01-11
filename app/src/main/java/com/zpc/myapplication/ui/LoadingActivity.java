package com.zpc.myapplication.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import androidx.appcompat.app.AppCompatActivity;

import com.gyf.immersionbar.ImmersionBar;
import com.zpc.myapplication.MainActivity;
import com.zpc.myapplication.R;
import com.zpc.myapplication.utils.AppAFManager;


public class LoadingActivity extends AppCompatActivity {

    public Context mAppContext;
    public Context mActContext;
    public Handler mHandlerLogin;
    private Runnable mRunnableLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);
        if (!this.isTaskRoot()) {
            Intent intent = getIntent();
            if (intent != null) {
                String action = intent.getAction();
                if (intent.hasCategory(Intent.CATEGORY_LAUNCHER) && Intent.ACTION_MAIN.equals(action)) {
                    finish();
                    return;
                }
            }
        } else {
            setContentView(R.layout.activity_loading);
            //全屏
            getSupportActionBar().hide();
            ImmersionBar.with(this).init();
            ImmersionBar.with(this).hideStatusBar(getWindow());
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

            //常量
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
            mAppContext = this.getApplicationContext();
            mActContext = this;

            AppAFManager.getInstance().addActivity(this);
            initBaseView();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        AppAFManager.getInstance().finishActivity(this);
    }

    /**
     * 初始化数据
     */
    public void initBaseView() {
        mHandlerLogin = new Handler();
        mRunnableLogin = new Runnable() {
            @Override
            public void run() {
                splashJump();
            }
        };
        mHandlerLogin.postDelayed(mRunnableLogin, 3000);
    }

    /**
     * 跳转页面
     */
    public void splashJump() {
        Intent mIntent = new Intent();
        mIntent.setClass(mActContext, MainActivity.class);
        startActivity(mIntent);
        finish();
    }
}
