package com.example.bohaiservicedome.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import com.example.bohaiservicedome.R;
import com.example.bohaiservicedome.base.BaseActivity;
import com.example.bohaiservicedome.config.ConstantConfig;
import com.example.bohaiservicedome.entity.MyUser;
import com.example.bohaiservicedome.utils.JudgeUtils;
import com.example.bohaiservicedome.utils.MethodsUtils;
import com.example.bohaiservicedome.utils.PermissionsUtils;
import com.example.bohaiservicedome.utils.SharePreferenceUtil;
import com.example.bohaiservicedome.utils.StatusBarUtils;
import com.example.bohaiservicedome.utils.ToastUtils;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class LoginActivity extends BaseActivity {
    @BindView(R.id.login_edit_account)
    EditText mAccount;                        //用户名编辑
    @BindView(R.id.login_edit_pwd)
    EditText mPwd;                            //密码编辑
    @BindView(R.id.login_remember)
    CheckBox mRememberCheck;

    @Override
    protected int contentViewID() {
        return R.layout.activity_login;
    }

    @Override
    protected void initialize() {
        setTopTitle("", false);
        StatusBarUtils.setLightMode(LoginActivity.this);
        rememberPassword();
    }

    private void rememberPassword() {
        String username = (String) SharePreferenceUtil.get(this, ConstantConfig.USER_NAME, "");
        String password = (String) SharePreferenceUtil.get(this, ConstantConfig.PASSWORD, "");
        boolean choseRemember = (boolean) SharePreferenceUtil.get(this, ConstantConfig.PASSWORD_REMEMBER, false);

        //如果上次选了记住密码，那进入登录页面也自动勾选记住密码，并填上用户名和密码
        if (choseRemember) {
            mAccount.setText(username);
            mPwd.setText(password);
            mRememberCheck.setChecked(true);
        }

    }

    @OnClick({R.id.login_btn_login, R.id.login_btn_register})
    public void OnClick(View v) {
        switch (v.getId()) {
            case R.id.login_btn_login:
                login();
                break;
            case R.id.login_btn_register:
                startActivity(RegisterActivity.class);
                break;

            default:
        }

    }

    private void login() {
        if (TextUtils.isEmpty(mAccount.getText()) || TextUtils.isEmpty(mPwd.getText())) {
            MethodsUtils.showErrorTip(this, getString(R.string.tip_empty));
            return;
        }

        if (!JudgeUtils.isPassword(mPwd.getText().toString())) {
            MethodsUtils.showErrorTip(this, getString(R.string.tip_password_not));
            return;
        }

        if (!PermissionsUtils.isNetworkConnected(this)) {
            MethodsUtils.showErrorTip(this, getString(R.string.tip_no_signal));
            return;
        }

        final String musername = mAccount.getText().toString();
        final String mpassword = mPwd.getText().toString();
        MyUser myUser = new MyUser();
        myUser.setUsername(musername);
        myUser.setPassword(mpassword);
        myUser.login(new SaveListener<MyUser>() {
            @Override
            public void done(MyUser myUser, BmobException e) {
                if (e == null) {
                    SharePreferenceUtil.put(LoginActivity.this, ConstantConfig.USER_NAME, musername);
                    SharePreferenceUtil.put(LoginActivity.this, ConstantConfig.PASSWORD, mpassword);
                    if (mRememberCheck.isChecked()) {
                        SharePreferenceUtil.put(LoginActivity.this, ConstantConfig.PASSWORD_REMEMBER, true);
                    } else {
                        SharePreferenceUtil.put(LoginActivity.this, ConstantConfig.PASSWORD_REMEMBER, false);
                    }
                    ToastUtils.showShort(LoginActivity.this, getString(R.string.login_success));
                    startActivityFinish(MainActivity.class);

                } else {
                    ToastUtils.showShort(LoginActivity.this, getString(R.string.login_fail));
                }

            }
        });


    }
}
