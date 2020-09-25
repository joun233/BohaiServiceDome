package com.example.bohaiservicedome.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import com.example.bohaiservicedome.R;
import com.example.bohaiservicedome.activity.LoginActivity;
import com.example.bohaiservicedome.widget.MyCenterPopupView;
import com.lxj.xpopup.XPopup;

import cn.bmob.v3.BmobUser;


public class MethodsUtils {
    /**
     * 错误提示弹窗
     * @param context
     * @param msg
     */
    public static void showErrorTip(Context context, String msg) {
        MyCenterPopupView centerPopupView = new MyCenterPopupView(context);
        XPopup.get(context).asCustom(centerPopupView).show();
        centerPopupView.setSinglePopup("", msg
                , context.getResources().getString(R.string.ok)
                , new MyCenterPopupView.PositiveClickListener() {
                    @Override
                    public void onPositiveClick() {

                    }
                });
    }

    /**
     * 退出登录
     * @param activity
     * @param msg
     */
    public static void showLogoutTip(final Activity activity, String msg) {
        MyCenterPopupView centerPopupView = new MyCenterPopupView(activity);
        XPopup.get(activity).asCustom(centerPopupView).show();
        centerPopupView.setDoublePopup("", msg
                , activity.getResources().getString(R.string.no)
                , activity.getResources().getString(R.string.yes), new MyCenterPopupView.PositiveClickListener() {
                    @Override
                    public void onPositiveClick() {

                    }
                }, new MyCenterPopupView.NegativeClickListener() {
                    @Override
                    public void onNegativeClick() {
                        BmobUser.logOut();
                        ActivityUtils.removeAll();
                        activity.startActivity(new Intent(activity, LoginActivity.class));
                    }
                });
    }

}
