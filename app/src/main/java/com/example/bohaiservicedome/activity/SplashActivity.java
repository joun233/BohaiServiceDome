package com.example.bohaiservicedome.activity;

import android.os.Handler;

import com.example.bohaiservicedome.R;
import com.example.bohaiservicedome.base.BaseActivity;
import com.example.bohaiservicedome.config.ConstantConfig;
import com.example.bohaiservicedome.entity.MyUser;
import com.example.bohaiservicedome.utils.SharePreferenceUtil;
import com.example.bohaiservicedome.utils.StatusBarUtils;

import cn.bmob.v3.BmobUser;

public class SplashActivity extends BaseActivity {
    private Handler mHandler = new Handler();

    @Override
    protected int contentViewID() {
        return R.layout.activity_splash;
    }

    @Override
    protected void initialize() {

        StatusBarUtils.setLightMode(SplashActivity.this);
        setTopTitle("",false);
        boolean isFirstOpen = (boolean) SharePreferenceUtil.get(SplashActivity.this, ConstantConfig.FIRST_OPEN, false);
        if (!isFirstOpen) {
            startActivityFinish(GuideActivity.class);
            return;
        }
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                BmobUser bmobUser = BmobUser.getCurrentUser(MyUser.class);
                if(bmobUser != null){
                    startActivityFinish(MainActivity.class);
                } else {
                    startActivityFinish(LoginActivity.class);
                }
            }
        },3000);
    }
}
