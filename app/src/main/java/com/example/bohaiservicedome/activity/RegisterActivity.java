package com.example.bohaiservicedome.activity;

import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import com.example.bohaiservicedome.R;
import com.example.bohaiservicedome.base.BaseActivity;
import com.example.bohaiservicedome.entity.MyUser;
import com.example.bohaiservicedome.utils.JudgeUtils;
import com.example.bohaiservicedome.utils.MethodsUtils;
import com.example.bohaiservicedome.utils.PermissionsUtils;
import com.example.bohaiservicedome.utils.StatusBarUtils;
import com.example.bohaiservicedome.utils.ToastUtils;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class RegisterActivity extends BaseActivity {
    @BindView(R.id.edit_user_name)
    EditText mAccount;
    @BindView(R.id.edit_full_name)
    EditText mName;
    @BindView(R.id.edit_pwd_old)
    EditText mPwd;
    @BindView(R.id.edit_pwd_new)
    EditText mPwdCheck;

    @Override
    protected int contentViewID() {
        return R.layout.activity_register;
    }

    @Override
    protected void initialize() {
        setTopTitle("", false);
        StatusBarUtils.setLightMode(RegisterActivity.this);
    }

    @OnClick({R.id.register_btn_sure, R.id.register_btn_cancel})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.register_btn_sure:
                userRegister();
                break;
            case R.id.register_btn_cancel:
                finish();
                break;
            default:
        }
    }

    private void userRegister() {

        if (TextUtils.isEmpty(mAccount.getText()) || TextUtils.isEmpty(mPwd.getText()) ||
                TextUtils.isEmpty(mPwdCheck.getText())) {
            MethodsUtils.showErrorTip(this,getString(R.string.tip_empty));
            return;
        }

        if (!JudgeUtils.isPassword(mPwd.getText().toString())){
            MethodsUtils.showErrorTip(this,getString(R.string.tip_password_not));
            return;
        }

        if (!PermissionsUtils.isNetworkConnected(this)) {
            MethodsUtils.showErrorTip(this,getString(R.string.tip_no_signal));
            return;
        }
        String userName = mAccount.getText().toString();
        String fullName = mName.getText().toString();
        String userPwd = mPwd.getText().toString();
        String userPwdCheck = mPwdCheck.getText().toString();

        if (userPwd.equals(userPwdCheck) == false) {     //两次密码输入不一样
            ToastUtils.showShort(RegisterActivity.this,getString(R.string.pwd_not_the_same));
            return;
        }

        MyUser myUser = new MyUser();
        myUser.setUsername(userName);
        myUser.setFullName(fullName);
        myUser.setPassword(userPwd);
        myUser.signUp(new SaveListener<MyUser>() {
            @Override
            public void done(MyUser myUser, BmobException e) {
                if (e == null) {
                    ToastUtils.showShort(RegisterActivity.this,getString(R.string.register_success));
                    startActivityFinish(LoginActivity.class);
                } else {
                    ToastUtils.showShort(RegisterActivity.this,getString(R.string.register_fail));
                }
            }
        });
    }
}
