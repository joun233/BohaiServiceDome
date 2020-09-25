package com.example.bohaiservicedome.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.bohaiservicedome.R;
import com.example.bohaiservicedome.activity.InformationActivity;
import com.example.bohaiservicedome.activity.MySignInActivity;
import com.example.bohaiservicedome.activity.PasswordEditActivity;
import com.example.bohaiservicedome.activity.SelectPhotoActivity;
import com.example.bohaiservicedome.base.BaseFragment;
import com.example.bohaiservicedome.config.ConstantConfig;
import com.example.bohaiservicedome.entity.MessageEvent;
import com.example.bohaiservicedome.entity.MyUser;
import com.example.bohaiservicedome.utils.AppUtils;
import com.example.bohaiservicedome.utils.ImageUtils;
import com.example.bohaiservicedome.utils.MethodsUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import androidx.fragment.app.Fragment;
import butterknife.BindView;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;

/**
 * @author: JiangNan
 * @Date:2020/4/1
 */
public class ProfileFragment extends BaseFragment {

    @BindView(R.id.img_profile)
    SimpleDraweeView imgHead;
    @BindView(R.id.tv_profile_version)
    TextView tvVersion;
    @BindView(R.id.tv_profile_full_name)
    TextView tvFullName;
    @BindView(R.id.tv_profile_user_name)
    TextView tvUserName;

    public static Fragment getInstance() {
        Fragment fragment = new ProfileFragment();
        return fragment;
    }


    @Override
    protected int contentViewID() {
        return R.layout.fragment_profile;
    }

    @Override
    protected void initialize() {
        EventBus.getDefault().register(this);
        setUserName();
        setImgHead();
        tvVersion.setText(AppUtils.getVersionName(getContext()));
    }

    private void setUserName() {
        MyUser userInfo = BmobUser.getCurrentUser(MyUser.class);
        tvUserName.setText(userInfo.getUsername());
        tvFullName.setText(userInfo.getFullName());
    }

    private void setImgHead() {
        MyUser userInfo = BmobUser.getCurrentUser(MyUser.class);
        if (userInfo.getPhotoImage() == null) {
            return;
        }
        ImageUtils.setRoundImage(getContext(), imgHead, userInfo.getPhotoImage().getUrl());
    }


    @OnClick({R.id.btn_profile_logout, R.id.tv_information, R.id.tv_password_edit, R.id.tv_setting,
             R.id.tv_sign_in, R.id.img_profile})
    public void OnClick(View view) {
        switch (view.getId()) {
            case R.id.btn_profile_logout:
                MethodsUtils.showLogoutTip(getActivity(), getString(R.string.tip_logout));
                break;
            case R.id.img_profile:
                Bundle bundle = new Bundle();
                bundle.putString(ConstantConfig.SELECT_PHOTO, ConstantConfig.UPDATE_HEAD_IMAGES);
                startActivity(SelectPhotoActivity.class, bundle);
                break;
            case R.id.tv_information:
                startActivity(InformationActivity.class);
                break;
            case R.id.tv_sign_in:
                startActivity(MySignInActivity.class);
                break;
            case R.id.tv_password_edit:
                startActivity(PasswordEditActivity.class);
                break;
            case R.id.tv_setting:

                break;
            default:
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(MessageEvent messageEvent) {
        switch (messageEvent.getMessage()) {
            case ConstantConfig.HEAD_IMAGE_SUCCESS:
                setImgHead();
                break;
            case ConstantConfig.FULL_NAME_SUCCESS:
                setUserName();
                break;
            default:
        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
