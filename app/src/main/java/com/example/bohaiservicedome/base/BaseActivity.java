package com.example.bohaiservicedome.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bohaiservicedome.R;
import com.example.bohaiservicedome.utils.ActivityUtils;
import com.example.bohaiservicedome.utils.StatusBarUtils;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import butterknife.ButterKnife;

/**
 * @author: created by JiangNan
 * @Date: 2020/3/15 14
 */
public abstract class BaseActivity extends AppCompatActivity {
    protected Activity mContext;
    ImageButton btnLeft;
    TextView tvTitle;
    ImageButton btnRight;
    RelativeLayout rlTopbar;
    FrameLayout viewContent;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityUtils.addActivity(this);
        mContext = this;
        setContentView(R.layout.activity_base);
        initTop();
        LayoutInflater.from(mContext).inflate(contentViewID(), viewContent);
        StatusBarUtils.setColorNoTranslucent(this, getResources().getColor(R.color.colorPrimaryDark));
        ButterKnife.bind(mContext);
        initialize();
    }

    /**
     * Activity 关联布局文件
     *
     * @return
     */
    protected abstract int contentViewID();

    /**
     * 对象初始化，方法调用
     */
    protected abstract void initialize();

    private void initTop() {
        btnLeft = findViewById(R.id.btn_left);
        btnRight = findViewById(R.id.btn_right);
        tvTitle = findViewById(R.id.top_title);
        rlTopbar = findViewById(R.id.rl_topbar);
        viewContent = findViewById(R.id.view_content);
    }

    /**
     * 设置标题名称
     * @param topName 标题名
     * @param show 是否显示标题栏
     */
    protected void setTopTitle(String topName, boolean show){
        if (show) {
            if (!TextUtils.isEmpty(topName)) {
                tvTitle.setText(topName);
            }
        } else {
            rlTopbar.setVisibility(View.GONE);
        }
    }

    /**
     * 设置标题栏左侧的按钮
     * @param show 是否显示左侧的按钮
     * @param ResId 图片id
     * @param leftClick 左边按钮点击事件
     */
    protected void setLeftBtn(boolean show, int ResId, View.OnClickListener leftClick) {
        if (show) {
            btnLeft.setImageDrawable(mContext.getResources().getDrawable(ResId));
            btnLeft.setOnClickListener(leftClick);
        } else {
            btnLeft.setVisibility(View.GONE);
        }
    }

    /**
     * 设置标题栏左侧的按钮-返回按钮
     */
    protected void setLeftBtnFinish() {
        btnLeft.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.ic_back));
        btnLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    /**
     * 设置标题栏左侧的按钮-返回按钮
     */
    protected void setLeftBtnToActivity(final Class<?> clz) {
        btnLeft.setImageDrawable(mContext.getResources().getDrawable(R.mipmap.ic_back));
        btnLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityFinish(clz);
            }
        });
    }

    /**
     * 设置标题栏右侧的按钮
     *
     * @param show       是否显示右边按钮
     * @param ResId      图片id
     * @param rightClick 右边按钮点击事件
     */
    protected void setRightBtn(boolean show, int ResId, View.OnClickListener rightClick) {
        if (show) {
            btnRight.setImageDrawable(mContext.getResources().getDrawable(ResId));
            btnRight.setOnClickListener(rightClick);
        } else {
            btnRight.setVisibility(View.GONE);
        }
    }

    public void startActivity(Class<?> clz) {
        startActivity(clz, null);
    }

    public void startActivity(Class<?> clz, Bundle bundle) {
        Intent intent = new Intent(this, clz);
        if (null != bundle){
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    public void startActivityFinish(Class<?> clz)
    {
        startActivity(clz, null);
        finish();
    }

    public void startActivityFinish(Class<?> clz, Bundle bundle) {
        Intent intent = new Intent(this, clz);
        if (null != bundle){
            intent.putExtras(bundle);
        }
        startActivity(intent);
        finish();
    }

    private long lastClickTime;
    /**
     * 实现连续点击两次才退出应用程序
     */
    public void exit() {
        if ((System.currentTimeMillis() - lastClickTime) > 2000) {
            showToast(getString(R.string.tip_exit));
            lastClickTime = System.currentTimeMillis();
        } else {
            ActivityUtils.removeAll();
            System.exit(0);
        }
    }

    /**
     * toast短时间提示
     * @param text
     */
    public void showToast(String text) {
        Toast.makeText(mContext, text, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        ActivityUtils.removeActivity(this);
        ButterKnife.bind(this).unbind();
        super.onDestroy();
    }
}
