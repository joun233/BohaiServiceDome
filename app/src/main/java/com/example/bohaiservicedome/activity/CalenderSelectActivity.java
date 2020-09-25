package com.example.bohaiservicedome.activity;

import android.view.WindowManager;

import com.example.bohaiservicedome.R;
import com.example.bohaiservicedome.base.BaseActivity;
import com.example.bohaiservicedome.config.ConstantConfig;
import com.example.bohaiservicedome.entity.CalenderEvent;
import com.example.bohaiservicedome.utils.StatusBarUtils;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import org.greenrobot.eventbus.EventBus;

import androidx.annotation.NonNull;
import butterknife.BindView;

public class CalenderSelectActivity extends BaseActivity implements OnDateSelectedListener {
    @BindView(R.id.materialCalendarView)
    MaterialCalendarView widget;

    @Override
    protected int contentViewID() {
        return R.layout.activity_calender_select;
    }

    @Override
    protected void initialize() {
        StatusBarUtils.setTransparent(this);
        getSupportActionBar().hide();
        setTopTitle("",false);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        widget.setSelectedDate(CalendarDay.today());
        widget.state().edit().setMaximumDate(CalendarDay.today()).commit();
        widget.setOnDateChangedListener(this);
    }

    @Override
    public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
        EventBus.getDefault().post(new CalenderEvent(ConstantConfig.CALENDER_SELECT,date.getCalendar()));
        finish();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.bottom_silent,R.anim.bottom_out);
    }
}
