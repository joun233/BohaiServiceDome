package com.example.bohaiservicedome.fragment;

import com.example.bohaiservicedome.R;
import com.example.bohaiservicedome.adapter.TitleFragmentPagerAdapter;
import com.example.bohaiservicedome.base.BaseFragment;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import butterknife.BindView;

/**
* @author: JiangNan
* @Date:2020/4/1
*/
public class ServiceFragment extends BaseFragment {
    @BindView(R.id.tab_service)
    TabLayout tabService;
    @BindView(R.id.viewpager_service)
    ViewPager viewPagerService;

    public static Fragment getInstance() {
        Fragment fragment = new ServiceFragment();
        return fragment;
    }


    @Override
    protected int contentViewID() {
        return R.layout.fragment_service;
    }

    @Override
    protected void initialize() {
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(NoticeFragment.getInstance());
        fragments.add(LostFoundFragment.getInstance());
        fragments.add(ConfessionFragment.getInstance());
        String[] titles = getResources().getStringArray(R.array.tab_service);
        TitleFragmentPagerAdapter titleFragmentPagerAdapter = new TitleFragmentPagerAdapter(getFragmentManager(), fragments, titles);
        viewPagerService.setAdapter(titleFragmentPagerAdapter);
        tabService.setupWithViewPager(viewPagerService);
    }

}
