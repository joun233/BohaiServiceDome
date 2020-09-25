package com.example.bohaiservicedome.activity;

import android.content.Intent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.bohaiservicedome.R;
import com.example.bohaiservicedome.base.BaseActivity;
import com.example.bohaiservicedome.config.ConstantConfig;
import com.example.bohaiservicedome.entity.LostInformation;
import com.example.bohaiservicedome.entity.MessageEvent;
import com.example.bohaiservicedome.entity.MyUser;
import com.example.bohaiservicedome.utils.ToastUtils;
import com.google.android.material.textfield.TextInputEditText;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

public class EditLostActivity extends BaseActivity {
    @BindView(R.id.lost_username)
    TextView lostUsername;
    @BindView(R.id.lost_et_title)
    TextInputEditText etLostTitle;
    @BindView(R.id.lost_et_contact)
    TextInputEditText etLostContact;
    @BindView(R.id.lost_et_desc)
    EditText etLostDesc;
    private boolean isEditLost = false;
    LostInformation lostInformation;

    @Override
    protected int contentViewID() {
        return R.layout.activity_edit_lost;
    }

    @Override
    protected void initialize() {
        setTopTitle(getString(R.string.add_lost), true);
        setLeftBtnFinish();
        MyUser myUser = BmobUser.getCurrentUser(MyUser.class);
        lostUsername.setText(myUser.getUsername());
        Intent intent = getIntent();
        lostInformation = (LostInformation) intent.getSerializableExtra("editData");
        if (lostInformation != null) {
            isEditLost = true;
            setLostText(lostInformation);
        }
    }

    private void setLostText(LostInformation lostInformation) {
        lostUsername.setText(lostInformation.getUsername());
        etLostTitle.setText(lostInformation.getTitle());
        etLostContact.setText(lostInformation.getPhoneNum());
        etLostDesc.setText(lostInformation.getDesc());
    }

    @OnClick(R.id.btn_lost)
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_lost:
                if (isEditLost == true) {
                    updateLostInfo();
                } else {
                    addLostInfo();
                }
                break;
            default:
        }
    }

    private void addLostInfo() {
        if (etLostTitle.getText().toString().isEmpty() || etLostContact.getText().toString().isEmpty()
                || etLostDesc.getText().toString().isEmpty()) {
            ToastUtils.showShort(EditLostActivity.this, getString(R.string.tip_empty));
            return;
        }
        MyUser myUser = BmobUser.getCurrentUser(MyUser.class);
        LostInformation newLostInformation = new LostInformation();
        newLostInformation.setUsername(myUser.getUsername());
        newLostInformation.setTitle(etLostTitle.getText().toString());
        newLostInformation.setPhoneNum(etLostContact.getText().toString());
        newLostInformation.setDesc(etLostDesc.getText().toString());
        newLostInformation.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if (e == null) {
                    ToastUtils.showShort(EditLostActivity.this, getString(R.string.lost_add_success));
                    EventBus.getDefault().post(new MessageEvent(ConstantConfig.ADD_LOST_PUSH));
                } else {
                    ToastUtils.showShort(EditLostActivity.this, getString(R.string.lost_add_failure));
                }
            }
        });
    }

    private void updateLostInfo() {
        if (etLostTitle.getText().toString().isEmpty() || etLostContact.getText().toString().isEmpty()
                || etLostDesc.getText().toString().isEmpty()) {
            ToastUtils.showShort(EditLostActivity.this, getString(R.string.tip_empty));
            return;
        }
        MyUser myUser = BmobUser.getCurrentUser(MyUser.class);
        LostInformation newLostInformation = new LostInformation();
        newLostInformation.setUsername(myUser.getUsername());
        newLostInformation.setTitle(etLostTitle.getText().toString());
        newLostInformation.setPhoneNum(etLostContact.getText().toString());
        newLostInformation.setDesc(etLostDesc.getText().toString());
        newLostInformation.update(lostInformation.getObjectId(), new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    ToastUtils.showShort(EditLostActivity.this, getString(R.string.lost_update_success));
                    EventBus.getDefault().post(new MessageEvent(ConstantConfig.UPDATE_LOST_PUSH));
                } else {
                    ToastUtils.showShort(EditLostActivity.this, getString(R.string.lost_update_failure));
                }
            }
        });
    }
}
