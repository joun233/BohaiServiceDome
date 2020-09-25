package com.example.bohaiservicedome.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.OnOptionsSelectChangeListener;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bigkoo.pickerview.view.TimePickerView;
import com.example.bohaiservicedome.R;
import com.example.bohaiservicedome.base.BaseActivity;
import com.example.bohaiservicedome.config.ConstantConfig;
import com.example.bohaiservicedome.entity.MessageEvent;
import com.example.bohaiservicedome.entity.MyUser;
import com.example.bohaiservicedome.utils.FormatUtils;
import com.example.bohaiservicedome.utils.JudgeUtils;
import com.example.bohaiservicedome.utils.ToastUtils;
import com.google.android.material.textfield.TextInputEditText;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

public class InformationActivity extends BaseActivity {
    public static final String TAG = "InformationActivity";

    @BindView(R.id.info_telephone)
    TextInputEditText info_telephone;
    @BindView(R.id.info_email)
    TextInputEditText info_email;
    @BindView(R.id.info_fullName)
    TextInputEditText info_fullName;
    @BindView(R.id.info_says)
    TextInputEditText info_says;
    @BindView(R.id.info_gender)
    TextView info_gender;
    @BindView(R.id.info_birthday)
    TextView info_birthday;
    @BindView(R.id.info_collage)
    TextView info_collage;

    private OptionsPickerView genderPicker, collagePicker;
    private TimePickerView mTimePicker;

    @Override
    protected int contentViewID() {
        return R.layout.activity_information;
    }

    @Override
    protected void initialize() {
        setTopTitle(getString(R.string.profile_data), true);
        setLeftBtnFinish();
        setMyUser();
        initGender();
        initCollage();
        initDatePicker();
    }

    private void setMyUser() {
        MyUser userInfo = BmobUser.getCurrentUser(MyUser.class);
        if (userInfo.getFullName() != null) {
            info_fullName.setText(userInfo.getFullName());
        }
        if (userInfo.getEmail() != null) {
            info_email.setText(userInfo.getEmail());
        }
        if (userInfo.getMobilePhoneNumber() != null) {
            info_telephone.setText(userInfo.getMobilePhoneNumber());
        }
        if (userInfo.getSays() != null) {
            info_says.setText(userInfo.getSays());
        }
        if (userInfo.getGender() != null) {
            info_gender.setText(userInfo.getGender());
        }
        if (userInfo.getCollege() != null) {
            info_collage.setText(userInfo.getCollege());
        }
        if (userInfo.getBirthday() != null) {
            info_birthday.setText(userInfo.getBirthday());
        }

    }

    @OnClick({R.id.info_gender, R.id.info_collage, R.id.btn_info_save, R.id.info_birthday})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.info_gender:
                genderPicker.show();
                break;
            case R.id.info_birthday:
                mTimePicker.show();
                break;
            case R.id.info_collage:
                collagePicker.show();
                break;
            case R.id.btn_info_save:
                saveInfo();
                break;
            default:
        }
    }

    private void saveInfo() {
        if (TextUtils.isEmpty(info_fullName.getText())){
            ToastUtils.showShort(InformationActivity.this, getString(R.string.tip_fullName));
            return;
        }
        MyUser userInfo = BmobUser.getCurrentUser(MyUser.class);
        MyUser newUser = new MyUser();
        newUser.setFullName(info_fullName.getText().toString());
        if (info_telephone.getText().toString() != null){
            if (!JudgeUtils.isTelephone(info_telephone.getText().toString())){
                ToastUtils.showShort(InformationActivity.this, getString(R.string.tip_telephone_type));
                return;
            }
        }
        newUser.setMobilePhoneNumber(info_telephone.getText().toString());
        newUser.setGender(info_gender.getText().toString());
        if (info_email.getText().toString() != null){
            if (!JudgeUtils.isEmail(info_email.getText().toString())){
                ToastUtils.showShort(InformationActivity.this, getString(R.string.tip_email_type));
                return;
            }
            newUser.setEmail(info_email.getText().toString());
        }
        newUser.setSays(info_says.getText().toString());
        newUser.setBirthday(info_birthday.getText().toString());
        newUser.setCollege(info_collage.getText().toString());
        newUser.update(userInfo.getObjectId(), new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    ToastUtils.showShort(InformationActivity.this, getString(R.string.info_upload_success));
                    EventBus.getDefault().post(new MessageEvent(ConstantConfig.FULL_NAME_SUCCESS));
                } else {
                    ToastUtils.showShort(InformationActivity.this, getString(R.string.info_upload_failure));
                }
            }
        });
    }

    private void initCollage() {
        String[] collages = getResources().getStringArray(R.array.collages);
        List<String> collageList = Arrays.asList(collages);
        collagePicker = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                info_collage.setText(collageList.get(options1));
            }
        }).setTitleText(getString(R.string.collage))
                .setCancelText(getString(R.string.cancel))
                .setSubmitText(getString(R.string.sure))
                .setContentTextSize(18)
                .setTitleSize(20)
                .setOptionsSelectChangeListener(new OnOptionsSelectChangeListener() {
                    @Override
                    public void onOptionsSelectChanged(int options1, int options2, int options3) {
                    }
                }).build();
        collagePicker.setPicker(collageList);

    }

    private void initDatePicker() {
        mTimePicker = new TimePickerBuilder(this, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {
                info_birthday.setText(FormatUtils.getDateTimeString(date, FormatUtils.template_Date));
            }
        }).setTitleText(getString(R.string.birthday)).build();
    }

    private void initGender() {
        List<String> genderList = new ArrayList<>();
        genderList.add(getString(R.string.male));
        genderList.add(getString(R.string.female));
        genderPicker = new OptionsPickerBuilder(this, new OnOptionsSelectListener() {

            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                info_gender.setText(genderList.get(options1));
            }
        }).setTitleText(getString(R.string.sex))
                .setCancelText(getString(R.string.cancel))
                .setSubmitText(getString(R.string.sure))
                .setContentTextSize(18)
                .setTitleSize(20)
                .setOptionsSelectChangeListener(new OnOptionsSelectChangeListener() {
                    @Override
                    public void onOptionsSelectChanged(int options1, int options2, int options3) {
                    }
                }).build();
        genderPicker.setPicker(genderList);
    }
}
