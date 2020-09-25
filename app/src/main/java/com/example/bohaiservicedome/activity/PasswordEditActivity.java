package com.example.bohaiservicedome.activity;

import android.view.View;
import android.widget.EditText;

import com.example.bohaiservicedome.R;
import com.example.bohaiservicedome.base.BaseActivity;
import com.example.bohaiservicedome.utils.JudgeUtils;
import com.example.bohaiservicedome.utils.MethodsUtils;
import com.example.bohaiservicedome.utils.ToastUtils;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

public class PasswordEditActivity extends BaseActivity {
    @BindView(R.id.et_old_password)
    EditText oldPassword;
    @BindView(R.id.et_new_password)
    EditText newPassword;
    @BindView(R.id.et_again_password)
    EditText againPassword;

    @Override
    protected int contentViewID() {
        return R.layout.activity_password_edit;
    }

    @Override
    protected void initialize() {
        setTopTitle(getString(R.string.edit_password), true);
        setLeftBtnFinish();
    }

    @OnClick({R.id.btn_submit_password})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_submit_password:
                submitPassword();
                break;
            default:
        }
    }

    private void submitPassword() {
        String old_password_text = oldPassword.getText().toString();
        String new_password_text = newPassword.getText().toString();
        String again_password_text = againPassword.getText().toString();
        if (old_password_text == null || new_password_text == null || again_password_text == null) {
            ToastUtils.showShort(PasswordEditActivity.this, getString(R.string.tip_empty));
            return;
        }

        if (!JudgeUtils.isPassword(old_password_text)|| !JudgeUtils.isPassword(new_password_text) ||
                !JudgeUtils.isPassword(again_password_text)) {
            MethodsUtils.showErrorTip(this, getString(R.string.tip_password_not));
            return;
        }

        if (!new_password_text.equals(again_password_text)){
            ToastUtils.showShort(PasswordEditActivity.this, getString(R.string.pwd_not_the_same));
            return;
        }

        BmobUser.updateCurrentUserPassword(old_password_text, new_password_text, new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null){
                    ToastUtils.showShort(PasswordEditActivity.this, getString(R.string.update_password_success));
                }else {
                    ToastUtils.showShort(PasswordEditActivity.this, getString(R.string.update_password_failure));
                }
            }
        });
    }
}
