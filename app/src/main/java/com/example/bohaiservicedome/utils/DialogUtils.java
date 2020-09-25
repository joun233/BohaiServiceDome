package com.example.bohaiservicedome.utils;

import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.bohaiservicedome.R;
import com.example.bohaiservicedome.config.ConstantConfig;
import com.example.bohaiservicedome.entity.MessageEvent;
import com.example.bohaiservicedome.entity.MsgLove;
import com.example.bohaiservicedome.entity.MyUser;

import org.greenrobot.eventbus.EventBus;

import androidx.appcompat.app.AlertDialog;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

/**
 * @author: created by JiangNan
 * @Date: 2020/4/5 18
 */
public class DialogUtils {
    public static void addLoveDialog(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.add_love_tip, null);
        EditText etLoveTo = view.findViewById(R.id.et_love_to);
        EditText etLoveFrom = view.findViewById(R.id.et_love_from);
        EditText etLoveSays = view.findViewById(R.id.et_love_says);
        Button btnLovePublish = view.findViewById(R.id.btn_love_publish);
        Button btnLoveCancel = view.findViewById(R.id.btn_love_cancel);
        final Dialog dialog = new AlertDialog.Builder(context)
                .setView(view)
                .setCancelable(true)
                .create();
        btnLovePublish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (TextUtils.isEmpty(etLoveTo.getText().toString()) || TextUtils.isEmpty(etLoveFrom.getText().toString())
                || TextUtils.isEmpty(etLoveSays.getText().toString())){
                    ToastUtils.showShort(context,context.getString(R.string.tip_empty));
                    return;
                }
                MsgLove msgLove = new MsgLove();
                MyUser myUser = MyUser.getCurrentUser(MyUser.class);
                msgLove.setUsername(myUser.getUsername());
                msgLove.setTo(etLoveTo.getText().toString());
                msgLove.setFrom(etLoveFrom.getText().toString());
                msgLove.setContent(etLoveSays.getText().toString());
                msgLove.save(new SaveListener<String>() {
                    @Override
                    public void done(String s, BmobException e) {
                        if (e == null) {
                            ToastUtils.showShort(context,context.getString(R.string.love_send));
                            EventBus.getDefault().post(new MessageEvent(ConstantConfig.UPDATE_LOVE));
                            dialog.dismiss();
                        } else {
                            ToastUtils.showShort(context,context.getString(R.string.publish_fail));
                        }
                    }
                });
            }
        });

        btnLoveCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}
