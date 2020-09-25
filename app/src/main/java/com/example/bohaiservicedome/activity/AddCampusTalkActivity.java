package com.example.bohaiservicedome.activity;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.bohaiservicedome.R;
import com.example.bohaiservicedome.base.BaseActivity;
import com.example.bohaiservicedome.config.ConstantConfig;
import com.example.bohaiservicedome.entity.CampusTalk;
import com.example.bohaiservicedome.entity.MessageEvent;
import com.example.bohaiservicedome.entity.MyUser;
import com.example.bohaiservicedome.entity.UriEvent;
import com.example.bohaiservicedome.utils.FileUtils;
import com.example.bohaiservicedome.utils.ToastUtils;
import com.facebook.drawee.view.SimpleDraweeView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UploadFileListener;

public class AddCampusTalkActivity extends BaseActivity {
    public static final String TAG = "AddCampusTalkActivity";

    @BindView(R.id.et_campus_talk)
    EditText etCampusTalk;
    @BindView(R.id.add_img_campus)
    SimpleDraweeView imgCampusPhoto;
    Uri mUri;
    @Override
    protected int contentViewID() {
        return R.layout.activity_add_campus_talk;
    }

    @Override
    protected void initialize() {
        EventBus.getDefault().register(this);
        setTopTitle(getString(R.string.add_campus_talk), true);
        setLeftBtnFinish();
    }

    @OnClick({R.id.btn_add_campus_talk, R.id.add_img_campus})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.btn_add_campus_talk:
                saveCampusTalk();
                break;
            case R.id.add_img_campus:
                Bundle bundle = new Bundle();
                bundle.putString(ConstantConfig.SELECT_PHOTO, ConstantConfig.SELECT_CAMPUS_IMAGES);
                startActivity(SelectPhotoActivity.class, bundle);
                break;
            default:
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(UriEvent uriEvent) {
        switch (uriEvent.getMessage()){
            case ConstantConfig.SELECT_CAMPUS_IMAGES:
                mUri = uriEvent.getUri();
                imgCampusPhoto.setImageURI(mUri);
                break;

        }
    }

    private void saveCampusTalk() {
        if (mUri == null){
            ToastUtils.showShort(AddCampusTalkActivity.this, getString(R.string.tip_images_empty));
            return;
        }
        String textCampusTalk = etCampusTalk.getText().toString();
        if (textCampusTalk.isEmpty()){
            ToastUtils.showShort(AddCampusTalkActivity.this, getString(R.string.tip_empty));
            return;
        }

        final BmobFile bmobFile = new BmobFile(FileUtils.uriToFile(mUri,this));
        bmobFile.uploadblock(new UploadFileListener() {
            @Override
            public void done(BmobException e) {
                if (e == null) {
                    MyUser myUser = BmobUser.getCurrentUser(MyUser.class);
                    CampusTalk campusTalk = new CampusTalk();
                    campusTalk.setUsername(myUser.getUsername());
                    campusTalk.setContent(textCampusTalk);
                    campusTalk.setImage(bmobFile);
                    campusTalk.save(new SaveListener<String>() {
                        @Override
                        public void done(String s, BmobException e) {
                            if (e == null){
                                ToastUtils.showShort(AddCampusTalkActivity.this, getString(R.string.publish_success));
                                EventBus.getDefault().post(new MessageEvent(ConstantConfig.PUBLISH_CAMPUS_SUCCESS));
                                finish();
                            }else {
                                ToastUtils.showShort(AddCampusTalkActivity.this, getString(R.string.publish_fail));
                            }
                        }
                    });
                } else {
                    ToastUtils.showShort(AddCampusTalkActivity.this, getString(R.string.publish_fail));
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
