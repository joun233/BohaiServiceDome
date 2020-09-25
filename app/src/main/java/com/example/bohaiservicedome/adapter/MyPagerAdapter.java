package com.example.bohaiservicedome.adapter;


import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import androidx.viewpager.widget.PagerAdapter;


public class MyPagerAdapter extends PagerAdapter {

    private List<View> mViews = new ArrayList<>();

    public MyPagerAdapter(List<View> views) {
        mViews = views;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(mViews.get(position));

    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {

        View view = mViews.get(position);
        container.addView(view);
        return view;
    }

    @Override
    public int getCount() {
        return mViews.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
