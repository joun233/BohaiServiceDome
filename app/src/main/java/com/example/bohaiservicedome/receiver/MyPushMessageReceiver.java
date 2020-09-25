package com.example.bohaiservicedome.receiver;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;

import com.example.bohaiservicedome.R;
import com.example.bohaiservicedome.activity.WebViewActivity;
import com.example.bohaiservicedome.config.ConstantConfig;
import com.example.bohaiservicedome.entity.MessageEvent;
import com.example.bohaiservicedome.entity.WebUrl;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;

import androidx.core.app.NotificationCompat;
import cn.bmob.push.PushConstants;
import cn.bmob.v3.util.BmobNotificationManager;

/**
 * @author: created by JiangNan
 * @Date: 2020/4/4 11
 */
public class MyPushMessageReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if(intent.getAction().equals(PushConstants.ACTION_MESSAGE)){
            Log.d("bmob", "客户端收到推送内容："+intent.getStringExtra("msg"));
            WebUrl webUrl = new Gson().fromJson(intent.getStringExtra("msg"), WebUrl.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("WebUrl", webUrl);
            EventBus.getDefault().post(new MessageEvent(ConstantConfig.NOTICE_PUSH));
            Intent pendingIntent = new Intent(context, WebViewActivity.class);
            pendingIntent.putExtras(bundle);
            pendingIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            Bitmap largeIcon = BitmapFactory.decodeResource(context.getResources(), R.mipmap.logo);
            BmobNotificationManager.getInstance(context).showNotification(largeIcon, "校内通知",
                    webUrl.getTitle(), webUrl.getUrl(), pendingIntent,
                    NotificationManager.IMPORTANCE_MIN, NotificationCompat.FLAG_ONLY_ALERT_ONCE);

        }
    }
}
