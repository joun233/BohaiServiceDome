package com.example.bohaiservicedome.activity;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.bohaiservicedome.R;
import com.example.bohaiservicedome.base.BaseActivity;
import com.example.bohaiservicedome.entity.MyUser;
import com.example.bohaiservicedome.entity.SignIn;
import com.example.bohaiservicedome.utils.DateUtils;
import com.example.bohaiservicedome.utils.FormatUtils;
import com.example.bohaiservicedome.utils.LogUtils;
import com.example.bohaiservicedome.utils.ToastUtils;
import com.example.bohaiservicedome.widget.EventDecorator;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnDateSelectedListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import butterknife.BindView;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class MySignInActivity extends BaseActivity implements OnDateSelectedListener {
    @BindView(R.id.my_sign_in_date)
    TextView mySignInDate;
    @BindView(R.id.my_sign_in_time)
    TextView mySignInTime;
    @BindView(R.id.my_sign_in_address)
    TextView mySignInAddress;
    @BindView(R.id.line_my_sign_in)
    LinearLayout lineMySignIn;
    @BindView(R.id.materialCalendarView_sign_in)
    MaterialCalendarView widget;
    MyUser myUser = BmobUser.getCurrentUser(MyUser.class);
    private List<CalendarDay> calendarDays = new ArrayList<>();

    @Override
    protected int contentViewID() {
        return R.layout.activity_my_sign_in;
    }

    @Override
    protected void initialize() {
        setTopTitle(getString(R.string.my_sign_in), true);
        setLeftBtnFinish();
        widget.setSelectedDate(CalendarDay.today());
        widget.state().edit().setMaximumDate(CalendarDay.today()).commit();
        widget.setOnDateChangedListener(this);
        initDate();
        querySignInState(Calendar.getInstance());

    }

    private void initDate() {
        BmobQuery<SignIn> signInBmobQuery = new BmobQuery<SignIn>();
        signInBmobQuery.addWhereEqualTo("username", myUser.getUsername());
        signInBmobQuery.findObjects(new FindListener<SignIn>() {
            @Override
            public void done(List<SignIn> object, BmobException e) {
                if (e == null) {
                    if (!object.isEmpty()) {
                        for (SignIn signIn : object) {
                            Date date = DateUtils.strToDate(signIn.getDate() + " " + signIn.getTime());
                            calendarDays.add(CalendarDay.from(date));
                        }
                        widget.addDecorator(new EventDecorator(ContextCompat.getColor(MySignInActivity.this, R.color.color_1396aa), calendarDays));
                    }
                } else {
                    LogUtils.e(e.getMessage());
                    ToastUtils.showShort(MySignInActivity.this, getString(R.string.query_failure));
                }
            }
        });
    }

    @Override
    public void onDateSelected(@NonNull MaterialCalendarView widget, @NonNull CalendarDay date, boolean selected) {
        querySignInState(date.getCalendar());
    }

    private void querySignInState(Calendar calendar) {
        BmobQuery<SignIn> signInBmobQuery = new BmobQuery<SignIn>();
        signInBmobQuery.addWhereEqualTo("username", myUser.getUsername());
        signInBmobQuery.addWhereEqualTo("date", FormatUtils.getDateTimeString(calendar.getTime(), FormatUtils.template_Date));
        signInBmobQuery.findObjects(new FindListener<SignIn>() {
            @Override
            public void done(List<SignIn> object, BmobException e) {
                if (e == null) {
                    if (!object.isEmpty()) {
                        lineMySignIn.setVisibility(View.VISIBLE);
                        SignIn signIn = object.get(0);
                        mySignInDate.setText(signIn.getDate());
                        mySignInTime.setText(signIn.getTime());
                        mySignInAddress.setText(signIn.getAddress());
                    } else {
                        lineMySignIn.setVisibility(View.GONE);
                    }
                } else {
                    ToastUtils.showShort(MySignInActivity.this, getString(R.string.query_failure));
                }
            }
        });
    }

}
