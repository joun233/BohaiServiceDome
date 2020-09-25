package com.example.bohaiservicedome.activity;

import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.bohaiservicedome.R;
import com.example.bohaiservicedome.adapter.GuideViewPagerAdapter;
import com.example.bohaiservicedome.base.BaseActivity;
import com.example.bohaiservicedome.config.ConstantConfig;
import com.example.bohaiservicedome.utils.SharePreferenceUtil;
import com.example.bohaiservicedome.utils.StatusBarUtils;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.RequiresApi;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;

public class GuideActivity extends BaseActivity {

    @BindView(R.id.viewpager_guide)
    ViewPager vpGuide;

    @BindView(R.id.ll)
    LinearLayout llDot;

    private GuideViewPagerAdapter guideAdapter;
    private List<View> views = new ArrayList<>();
    // 引导页图片资源
    private static final int[] pics = {R.layout.layout_guide1, R.layout.layout_guide2, R.layout.layout_guide3};
    // 底部小点图片
    private ImageView[] dotViews;

    @Override
    protected int contentViewID() {
        return R.layout.activity_guide;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void initialize() {
        setTopTitle("",false);
        StatusBarUtils.setLightMode(GuideActivity.this);
        initBtn();
        // 初始化adapter
        guideAdapter = new GuideViewPagerAdapter(views);
        vpGuide.setAdapter(guideAdapter);
        vpGuide.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }
            // 当新的页面被选中时调用
            @Override
            public void onPageSelected(int position) {
                // 设置底部小点选中状态
                setCurDot(position);
            }
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        initDots();
    }

    private void initBtn() {
        // 初始化引导页视图列表
        int btnIndex = pics.length - 1;
        for (int i = 0; i < pics.length; i++) {
            View view = LayoutInflater.from(this).inflate(pics[i], null);
            views.add(view);
            if (i == btnIndex) {
                view.findViewById(R.id.btn_enter).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // 如果切换到后台，就设置下次不进入功能引导页
                        SharePreferenceUtil.put(GuideActivity.this, ConstantConfig.FIRST_OPEN, true);
                        startActivityFinish(LoginActivity.class);
                    }
                });
            }
        }
    }

    /**
     * 设置当前指示点
     * @param position
     */
    private void setCurDot(int position) {
        for (int i = 0; i < dotViews.length; i++) {
            if (position == i) {
                dotViews[i].setSelected(true);
            } else {
                dotViews[i].setSelected(false);
            }
        }
    }

    private void initDots() {
        LinearLayout.LayoutParams mParams = new LinearLayout.LayoutParams(45, 40);
        //设置小圆点左右之间的间隔
        mParams.setMargins(6, 0, 6, 0);
        dotViews = new ImageView[pics.length];
        //判断小圆点的数量，从0开始，0表示第一个
        for (int i = 0; i < pics.length; i++) {
            ImageView imageView = new ImageView(this);
            imageView.setLayoutParams(mParams);
            imageView.setImageResource(R.drawable.selector_dot);
            if (i == 0) {
                //默认启动时，选中第一个小圆点
                imageView.setSelected(true);
            } else {
                imageView.setSelected(false);
            }
            //得到每个小圆点的引用，用于滑动页面时，（onPageSelected方法中）更改它们的状态。
            dotViews[i] = imageView;
            //添加到布局里面显示
            llDot.addView(imageView);
        }

    }

}