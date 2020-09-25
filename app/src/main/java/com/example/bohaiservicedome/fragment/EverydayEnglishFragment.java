package com.example.bohaiservicedome.fragment;


import android.media.MediaPlayer;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.bohaiservicedome.R;
import com.example.bohaiservicedome.activity.CalenderSelectActivity;
import com.example.bohaiservicedome.base.BaseFragment;
import com.example.bohaiservicedome.config.ConstantConfig;
import com.example.bohaiservicedome.entity.CalenderEvent;
import com.example.bohaiservicedome.entity.EnglishSentence;
import com.example.bohaiservicedome.utils.DateUtils;
import com.example.bohaiservicedome.utils.FormatUtils;
import com.example.bohaiservicedome.utils.HttpUtils;
import com.example.bohaiservicedome.utils.LogUtils;
import com.facebook.drawee.view.SimpleDraweeView;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;

import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author: JiangNan
 * @Date:2020/4/6
 */
public class EverydayEnglishFragment extends BaseFragment {
    public static final String TAG = "EverydayEnglishFragment";
    @BindView(R.id.tv_year)
    TextView tv_year;
    @BindView(R.id.tv_month)
    TextView tv_month;
    @BindView(R.id.tv_day)
    TextView tv_day;
    @BindView(R.id.calendar_left)
    ImageView calendar_left;
    @BindView(R.id.calendar_right)
    ImageView calendar_right;
    @BindView(R.id.img_english)
    SimpleDraweeView imgEnglish;


    private Calendar calendar;
    EnglishSentence.NewslistBean mNewslistBean;


    public static Fragment getInstance() {
        Fragment fragment = new EverydayEnglishFragment();
        return fragment;
    }


    @Override
    protected int contentViewID() {
        return R.layout.fragment_everyday_english;
    }

    @Override
    protected void initialize() {
        EventBus.getDefault().register(this);
        calendar = Calendar.getInstance();
        setTextDate(calendar);
        getHttpData(calendar);
        return;
    }

    private void setTextDate(Calendar calendar) {
        tv_year.setText(calendar.get(Calendar.YEAR) + "");
        tv_month.setText(calendar.get(Calendar.MONTH) + 1 + "");
        tv_day.setText(calendar.get(Calendar.DAY_OF_MONTH) + "");
        changeArrowImage(calendar);
    }


    /**
     * 根据日期通过okgo获取json解析每日一句数据
     * @param calendar
     */
    private void getHttpData(Calendar calendar) {
        String date = FormatUtils.getDateTimeString(calendar.getTime(), FormatUtils.template_Date);
        try {
            StringCallback callback = new StringCallback() {
                @Override
                public void onSuccess(Response<String> response) {
                    try {
                        String json = response.body().toString();
                        JSONObject jsonObject = new JSONObject(json);
                        int code = jsonObject.getInt("code");
                        if (code == 200) {
                            JSONArray jsonArray = jsonObject.getJSONArray("newslist");
                            List<EnglishSentence.NewslistBean> newslistBeanList = new Gson().fromJson(jsonArray.toString(),
                                    new TypeToken<List<EnglishSentence.NewslistBean>>() {
                                    }.getType());
                            LogUtils.d(TAG, newslistBeanList.get(0).toString());
                            mNewslistBean = newslistBeanList.get(0);
                            setTextImg(mNewslistBean);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                @Override
                public void onError(Response<String> response) {
                    super.onError(response);
                }
            };
            HttpUtils.getEnglishSentence(getContext(), date, callback);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void changeArrowImage(Calendar calendar){
        if (DateUtils.isTody(calendar)){
            calendar_left.setImageResource(R.mipmap.arrow_left);
            calendar_right.setImageResource(R.mipmap.right_gay);
        } else {
            calendar_left.setImageResource(R.mipmap.arrow_left);
            calendar_right.setImageResource(R.mipmap.arrow_right);
        }
    }

    private void setTextImg(EnglishSentence.NewslistBean newslistBean) {
        if (newslistBean.getImgurl().isEmpty()){
           return;
        }
        imgEnglish.setImageURI(newslistBean.getImgurl());
    }

    @OnClick({R.id.english_mediaPlayer, R.id.calendar_left, R.id.calendar_right, R.id.calender_select})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.english_mediaPlayer:
                if (mNewslistBean == null){
                    return;
                }
                onPlayer(mNewslistBean);
                break;
            case R.id.calendar_left:
                calendar.add(Calendar.DATE, -1);
                setTextDate(calendar);
                getHttpData(calendar);
                break;
            case R.id.calendar_right:
                if (DateUtils.isTody(calendar)){
                    return;
                }
                calendar.add(Calendar.DATE, 1);
                setTextDate(calendar);
                getHttpData(calendar);
                break;
            case R.id.calender_select:
                startActivity(CalenderSelectActivity.class);
                getActivity().overridePendingTransition(R.anim.bottom_in,R.anim.bottom_silent);
                break;
            default:
        }
    }

    /**
     * 在线音频播放
     * @param newslistBean
     */
    private void onPlayer(EnglishSentence.NewslistBean newslistBean) {
        if (newslistBean.getTts() != null) {
            try {
                MediaPlayer mediaPlayer = new MediaPlayer();
                mediaPlayer.setDataSource(newslistBean.getTts());
                mediaPlayer.prepare();
                mediaPlayer.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(CalenderEvent calenderEvent) {
        switch (calenderEvent.getMessage()) {
            case ConstantConfig.CALENDER_SELECT:
                calendar = calenderEvent.getCalendar();
                setTextDate(calendar);
                getHttpData(calendar);
                break;
            default:
        }

    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
        OkGo.getInstance().cancelAll();
    }
}
