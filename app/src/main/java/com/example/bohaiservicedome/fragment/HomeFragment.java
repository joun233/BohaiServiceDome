package com.example.bohaiservicedome.fragment;

import android.content.res.TypedArray;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.example.bohaiservicedome.R;
import com.example.bohaiservicedome.activity.AddCampusTalkActivity;
import com.example.bohaiservicedome.activity.SignInActivity;
import com.example.bohaiservicedome.activity.WebViewActivity;
import com.example.bohaiservicedome.adapter.CampusTalkAdapter;
import com.example.bohaiservicedome.adapter.MyGridAdapter;
import com.example.bohaiservicedome.adapter.MyPagerAdapter;
import com.example.bohaiservicedome.base.BaseFragment;
import com.example.bohaiservicedome.config.ConstantConfig;
import com.example.bohaiservicedome.entity.CampusTalk;
import com.example.bohaiservicedome.entity.HomeIconInfo;
import com.example.bohaiservicedome.entity.ImageSlider;
import com.example.bohaiservicedome.entity.MessageEvent;
import com.example.bohaiservicedome.entity.WebUrl;
import com.example.bohaiservicedome.utils.LogUtils;
import com.example.bohaiservicedome.utils.ToastUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;
import butterknife.OnClick;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * @author: JiangNan
 * @Date:2020/4/1
 */
public class HomeFragment extends BaseFragment {
    @BindView(R.id.slider)
    SliderLayout sliderLayout;
    @BindView(R.id.custom_indicator)
    PagerIndicator indicator;
    @BindView(R.id.viewPager_home)
    ViewPager viewPager_home;
    @BindView(R.id.dot)
    LinearLayout llDot;
    @BindView(R.id.recycler_campusTalk)
    RecyclerView recyclerViewCampusTalk;
    private List<View> mViews = new ArrayList<>();
    private List<HomeIconInfo> mPagerOneData = new ArrayList<>();
    private List<HomeIconInfo> mPagerTwoData = new ArrayList<>();
    private String[] iconName;
    private TypedArray typedArray;
    CampusTalkAdapter campusTalkAdapter;
    // 底部小点图片
    private ImageView[] dotViews;

    public static Fragment getInstance() {
        Fragment fragment = new HomeFragment();
        return fragment;
    }

    @Override
    protected int contentViewID() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initialize() {
        EventBus.getDefault().register(this);
        iconName = getResources().getStringArray(R.array.home_bar_labels);
        typedArray = getResources().obtainTypedArray(R.array.home_bar_icon);
        SpacesItemDecoration decoration=new SpacesItemDecoration(10);
        recyclerViewCampusTalk.addItemDecoration(decoration);
        recyclerViewCampusTalk.setLayoutManager(new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL));
        initImageSlider();
        initData();
        initGridView();
        setViewPagerListener();
        initDots();
        queryCampusTacks();

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(MessageEvent messageEvent) {
        switch (messageEvent.getMessage()) {
            case ConstantConfig.PUBLISH_CAMPUS_SUCCESS:
                queryCampusTacks();
                campusTalkAdapter.notifyDataSetChanged();
                break;
            default:
        }

    }

    private void queryCampusTacks() {
        BmobQuery<CampusTalk> query = new BmobQuery<>();
        query.order("-createdAt");
        query.findObjects(new FindListener<CampusTalk>() {
            @Override
            public void done(List<CampusTalk> object, BmobException e) {
                if (e == null) {
                    setRecycler(object);
                } else {
                    LogUtils.d(e.getMessage());
                }
            }
        });
    }

    private void setRecycler(List<CampusTalk> mCampusTalks) {
        campusTalkAdapter = new CampusTalkAdapter(mCampusTalks);
        recyclerViewCampusTalk.setAdapter(campusTalkAdapter);
    }

    @OnClick({R.id.home_sign_in, R.id.add_campusTalk})
    public void onClick(View view){
        switch (view.getId()){
            case R.id.home_sign_in:
                startActivity(SignInActivity.class);
                break;
            case R.id.add_campusTalk:
                startActivity(AddCampusTalkActivity.class);
                break;
            default:
        }
    }

    private void setViewPagerListener() {
        viewPager_home.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                // 设置底部小点选中状态
                setCurDot(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void initGridView() {
        View pagerOne = getActivity().getLayoutInflater().inflate(R.layout.home_gridview, null);
        GridView gridView01 = (GridView) pagerOne.findViewById(R.id.gridView);
        gridView01.setAdapter(new MyGridAdapter(mPagerOneData, this.getActivity()));
        gridView01.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0 && (int) l == 0) {
                    skipWebView(iconName[0], "http://cas.bhu.edu.cn/cas/login?service=http://i.bhu.edu.cn:80/dcp/index.jsp");
                }
                if (i == 1 && (int) l == 0) {
                    skipWebView(iconName[1], "http://210.47.176.33:8080/Default.aspx?url=s_App8.aspx");
                }
                if (i == 2 && (int) l == 0) {
                    skipWebView(iconName[2], "http://jw.bhu.edu.cn/");
                }
                if (i == 3 && (int) l == 0) {
                    skipWebView(iconName[3], "https://mp.weixin.qq.com/s/M57gThPoBixlHqCFOqrd2g");
                }
                if (i == 4 && (int) l == 0) {
                    skipWebView(iconName[4], "https://weixiao.qq.com/weixin_project/calendar/schoolCalendar.html?weixinOpenInfo=gh_edfb3f596431");
                }
                if (i == 5 && (int) l == 0) {
                    skipWebView(iconName[5], "http://mp.weixin.qq.com/mp/homepage?__biz=MjM5NTcwNjIyMA==&hid=3&sn=432db4d2d354b16ca15762f2fb0bfb1b&scene=18#wechat_redirect");
                }
                if (i == 6 && (int) l == 0) {
                    skipWebView(iconName[6], "http://210.47.176.3/page/depart/bdlab/zxscx/index.asp?nsukey=WuYMrfUaRBDgGFs8jRN/tGxrHzwWHxSOIFTGAOgV4wMZSsyDgyiF4kuzqbNf7uTlBf21xSxwzLMJRtCxBXNYT9rU8v3hQT81oPeD+IHJ+hvk2PBaNEBElKQWfLLkzuSpc1Sm3MY4ZXETOeGGYnY2pEbkhwJsAtHu1IXobnsHgsxiG0nyZXl6L5EL/3svB0zmEYf61lkOFbehHr7w0LcxaQ==");
                }
                if (i == 7 && (int) l == 0) {
                    skipWebView(iconName[7], "http://www.bhu.edu.cn/newsgl/depart/hq/jlhd.asp");
                }

            }
        });
        View pagerTwo = getActivity().getLayoutInflater().inflate(R.layout.home_gridview, null);
        GridView gridView02 = (GridView) pagerTwo.findViewById(R.id.gridView);
        gridView02.setAdapter(new MyGridAdapter(mPagerTwoData, this.getActivity()));
        gridView02.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0 && (int) l == 0) {
                    skipWebView(iconName[8], "http://www.bhu.edu.cn/fpage/pgeneral/index.asp");
                }
                if (i == 1 && (int) l == 0) {
                    skipWebView(iconName[9], "http://sf1.bhu.edu.cn:8800/");
                }
                if (i == 2 && (int) l == 0) {
                    skipWebView(iconName[10], "http://sf.bhu.edu.cn/");
                }
                if (i == 3 && (int) l == 0) {
                    skipWebView(iconName[11], "http://210.47.178.67/login.aspx");
                }
                if (i == 4 && (int) l == 0) {
                    skipWebView(iconName[12], "http://210.47.177.84/reader/redr_verify.php");
                }
                if (i == 5 && (int) l == 0) {
                    skipWebView(iconName[13], "https://boda360.kuaizhan.com/33/6/p24984441932969");
                }
                if (i == 6 && (int) l == 0) {
                    skipWebView(iconName[14], "http://notice.woai662.net/studentTzs/appLogic.php?type=trigger&;media_id=gh_edfb3f596431");
                }
                if (i == 7 && (int) l == 0) {
                    skipWebView(iconName[15], "https://m.bysjy.com.cn/student/chance/news.html?token=yxqqnn0600000028&types=毕业生风采");
                }
            }
        });
        mViews.add(pagerOne);
        mViews.add(pagerTwo);
        viewPager_home.setAdapter(new MyPagerAdapter(mViews));

    }

    private void initData() {
        for (int i = 0; i < iconName.length; i++) {
            if (i < 8) {
                mPagerOneData.add(new HomeIconInfo(iconName[i], typedArray.getResourceId(i, 0)));
            } else {
                mPagerTwoData.add(new HomeIconInfo(iconName[i], typedArray.getResourceId(i, 0)));
            }
        }
    }

    private void initImageSlider() {
        BmobQuery<ImageSlider> imageSliderBmobQuery = new BmobQuery<>();
        imageSliderBmobQuery.findObjects(new FindListener<ImageSlider>() {
            @Override
            public void done(List<ImageSlider> object, BmobException e) {
                if (e == null) {
                    showSlider(object);
                } else {
                    ToastUtils.showShort(getContext(), getString(R.string.query_failure));
                }
            }
        });
    }

    private void showSlider(List<ImageSlider> mImageSliderList) {
        for (int i = 0; i < mImageSliderList.size(); i++) {
            //新建三个展示View，并且添加到SliderLayout
            TextSliderView tsv = new TextSliderView(getActivity());
            tsv.image(mImageSliderList.get(i).getImageUrl().getFileUrl()).description(mImageSliderList.get(i).getSliderTitle());
            final int finalI = i;
            tsv.setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
                @Override
                public void onSliderClick(BaseSliderView slider) {
                    skipWebView(mImageSliderList.get(finalI).getSliderTitle(), mImageSliderList.get(finalI).getWebUrl());
                }
            });
            sliderLayout.addSlider(tsv);
        }
        //对SliderLayout进行一些自定义的配置
        sliderLayout.setCustomAnimation(new DescriptionAnimation());
        sliderLayout.setPresetTransformer(SliderLayout.Transformer.Accordion);
        sliderLayout.setDuration(3000);
        sliderLayout.setCustomIndicator(indicator);
    }

    private void skipWebView(String title, String url) {
        WebUrl webUrl = new WebUrl(title, url);
        Bundle bundle = new Bundle();
        bundle.putSerializable("WebUrl", webUrl);
        startActivity(WebViewActivity.class, bundle);
    }

    /**
     * 设置当前指示点
     *
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
        mParams.setMargins(6, 0, 6, 0);//设置小圆点左右之间的间隔
        dotViews = new ImageView[mViews.size()];
        //判断小圆点的数量，从0开始，0表示第一个
        for (int i = 0; i < mViews.size(); i++) {
            ImageView imageView = new ImageView(getContext());
            imageView.setLayoutParams(mParams);
            imageView.setImageResource(R.drawable.selector_home_dot);
            if (i == 0) {
                imageView.setSelected(true);//默认启动时，选中第一个小圆点
            } else {
                imageView.setSelected(false);
            }
            dotViews[i] = imageView;//得到每个小圆点的引用，用于滑动页面时，（onPageSelected方法中）更改它们的状态。
            llDot.addView(imageView);//添加到布局里面显示
        }

    }

    public class SpacesItemDecoration extends RecyclerView.ItemDecoration {
        private int space;
        public SpacesItemDecoration(int space) {
            this.space=space;
        }
        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            outRect.left=space;
            outRect.right=space;
            outRect.bottom=space;
            if(parent.getChildAdapterPosition(view)==0){
                outRect.top=space;
            }
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
