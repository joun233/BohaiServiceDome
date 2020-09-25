package com.example.bohaiservicedome.activity;

import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClientOption;
import com.example.bohaiservicedome.R;
import com.example.bohaiservicedome.base.BaseActivity;
import com.example.bohaiservicedome.entity.MyUser;
import com.example.bohaiservicedome.entity.SignIn;
import com.example.bohaiservicedome.service.LocationService;
import com.example.bohaiservicedome.utils.DateUtils;
import com.example.bohaiservicedome.utils.FormatUtils;
import com.example.bohaiservicedome.utils.LogUtils;
import com.example.bohaiservicedome.utils.ToastUtils;

import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

public class SignInActivity extends BaseActivity {
    public static final String TAG = "SignInActivity";
    @BindView(R.id.sign_calendar)
    TextView signCalender;
    @BindView(R.id.line_sign_result)
    LinearLayout lineSignResult;
    @BindView(R.id.sign_in_result)
    TextView signInResult;
    @BindView(R.id.sign_in_time)
    TextView signInTime;
    @BindView(R.id.sign_address)
    TextView signAddress;
    @BindView(R.id.btn_sign_in)
    Button btnSignIn;
    private LocationService mLocationService;
    private boolean isAgain = false;
    SignIn signIn;
    MyUser myUser = BmobUser.getCurrentUser(MyUser.class);
    @Override
    protected int contentViewID() {
        return R.layout.activity_sign_in;
    }


    @Override
    protected void initialize() {
        setTopTitle(getString(R.string.signIn), true);
        setLeftBtnFinish();
        setDate();
        setLocation();
        querySignInState();
    }

    /**
     * 查询今日签到状态
     */
    private void querySignInState() {
        BmobQuery<SignIn> signInBmobQuery = new BmobQuery<SignIn>();
        signInBmobQuery.addWhereEqualTo("username", myUser.getUsername());
        signInBmobQuery.addWhereEqualTo("date", FormatUtils.getDateTimeString(Calendar.getInstance().getTime(), FormatUtils.template_Date));
        signInBmobQuery.findObjects(new FindListener<SignIn>() {
            @Override
            public void done(List<SignIn> object, BmobException e) {
                if (e == null) {
                    if (object.isEmpty()){
                        isAgain = false;
                        btnSignIn.setVisibility(View.VISIBLE);
                    } else {
                        isAgain = true;
                        signIn = object.get(0);
                        btnSignIn.setVisibility(View.GONE);
                        lineSignResult.setVisibility(View.VISIBLE);
                        signAddress.setText(signIn.getAddress());
                        signInTime.setText(signIn.getTime());
                        signInResult.setText(getString(R.string.sign_in_success));
                    }
                } else {
                    isAgain = false;
                    btnSignIn.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    private void setLocation() {
        // 初始化 LocationClient
        mLocationService = new LocationService(this);
        // 注册监听
        mLocationService.registerListener(mListener);
        LocationClientOption option = mLocationService.getOption();
        // 签到场景 只进行一次定位返回最接近真实位置的定位结果（定位速度可能会延迟1-3s）
        option.setLocationPurpose(LocationClientOption.BDLocationPurpose.SignIn);
        // 设置定位参数
        mLocationService.setLocationOption(option);
    }


    /*****
     * 定位结果回调，重写onReceiveLocation方法
     */
    private BDAbstractLocationListener mListener = new BDAbstractLocationListener() {

        /**
         * 定位请求回调函数
         *
         * @param location 定位结果
         */
        @Override
        public void onReceiveLocation(BDLocation location) {
            if (null != location && location.getLocType() != BDLocation.TypeServerError &&
                    location.getLocType() != BDLocation.TypeOffLineLocationFail &&
                    location.getLocType() != BDLocation.TypeCriteriaException) {
                String address = location.getAddrStr();  //获取详细地址信息
                LogUtils.d(TAG,address);
                if (!isAgain) {
                    saveSignIn(address);
                } else {
                    updateSignIn(address);
                }
            } else {
                signInResult.setText(getString(R.string.sign_in_failure));
            }
        }
    };


    private void setDate() {
        String dateString = FormatUtils.getDateTimeString(Calendar.getInstance().getTime(), FormatUtils.template_Date);
        String weekString = DateUtils.getDayOfWeek();
        String CalendarString = dateString + " " + weekString;
        signCalender.setText(CalendarString);
    }

    @OnClick({R.id.btn_sign_in, R.id.btn_sign_again})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_sign_in:
                signIn();
                break;
            case R.id.btn_sign_again:
                isAgain = true;
                signIn();
                break;
            default:
        }

    }

    /**
     * 更新签到数据
     * @param address
     */
    private void updateSignIn(String address) {
        Calendar calendar = Calendar.getInstance();
        SignIn newSignIn = new SignIn();
        newSignIn.setUsername(myUser.getUsername());
        newSignIn.setAddress(address);
        newSignIn.setDate(FormatUtils.getDateTimeString(calendar.getTime(), FormatUtils.template_Date));
        newSignIn.setTime(FormatUtils.getDateTimeString(calendar.getTime(), FormatUtils.template_Time));
        newSignIn.update(signIn.getObjectId(), new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    signAddress.setText(address);
                    signInTime.setText(FormatUtils.getDateTimeString(Calendar.getInstance().getTime(), FormatUtils.template_Time));
                    signInResult.setText(getString(R.string.sign_in_success));
                    ToastUtils.showShort(SignInActivity.this, getString(R.string.sign_in_success));
                } else {
                    ToastUtils.showShort(SignInActivity.this, getString(R.string.sign_in_failure));
                }
            }
        });
    }

    /**
     * 保存签到数据
     * @param address
     */
    private void saveSignIn(String address) {
        Calendar calendar = Calendar.getInstance();
        signIn = new SignIn();
        signIn.setUsername(myUser.getUsername());
        signIn.setAddress(address);
        signIn.setDate(FormatUtils.getDateTimeString(calendar.getTime(), FormatUtils.template_Date));
        signIn.setTime(FormatUtils.getDateTimeString(calendar.getTime(), FormatUtils.template_Time));
        signIn.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null) {
                    btnSignIn.setVisibility(View.GONE);
                    lineSignResult.setVisibility(View.VISIBLE);
                    signAddress.setText(address);
                    signInTime.setText(FormatUtils.getDateTimeString(Calendar.getInstance().getTime(), FormatUtils.template_Time));
                    signInResult.setText(getString(R.string.sign_in_success));
                    ToastUtils.showShort(SignInActivity.this, getString(R.string.sign_in_success));
                } else {
                    ToastUtils.showShort(SignInActivity.this, getString(R.string.sign_in_failure));
                }
            }
        });

    }

    /**
     * 签到
     */
    private void signIn() {
        if (mLocationService.isStart()) {
            mLocationService.requestLocation();
            return;
        }
        //签到只需调用startLocation即可
        mLocationService.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mLocationService != null) {
            mLocationService.unregisterListener(mListener);
            mLocationService.stop();
        }
    }
}
