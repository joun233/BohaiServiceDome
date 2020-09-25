package com.example.bohaiservicedome.fragment;

import android.os.Bundle;
import android.view.View;

import com.example.bohaiservicedome.R;
import com.example.bohaiservicedome.activity.WebViewActivity;
import com.example.bohaiservicedome.adapter.NoticeAdapter;
import com.example.bohaiservicedome.base.BaseFragment;
import com.example.bohaiservicedome.config.ConstantConfig;
import com.example.bohaiservicedome.entity.MessageEvent;
import com.example.bohaiservicedome.entity.NoticeArticle;
import com.example.bohaiservicedome.entity.WebUrl;
import com.example.bohaiservicedome.utils.ToastUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * @author: JiangNan
 * @Date:2020/4/3
 */
public class NoticeFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.refresh_notice)
    SwipeRefreshLayout refreshNotice;
    @BindView(R.id.recyclerView_notice)
    RecyclerView recyclerViewNotice;

    public static Fragment getInstance() {
        Fragment fragment = new NoticeFragment();
        return fragment;
    }

    @Override
    protected int contentViewID() {
        return R.layout.fragment_notice;
    }

    @Override
    protected void initialize() {
        EventBus.getDefault().register(this);
        initView();
        queryNotice();
    }

    private void initView() {
        refreshNotice.setOnRefreshListener(this);
        refreshNotice.setColorSchemeColors(getResources().getColor(R.color.colorPrimary));
        recyclerViewNotice.setItemAnimator(new DefaultItemAnimator());
        recyclerViewNotice.setLayoutManager(new LinearLayoutManager(getActivity()));

    }

    private void queryNotice() {
        BmobQuery<NoticeArticle> noticeArticleBmobQuery = new BmobQuery<>();
        noticeArticleBmobQuery.order("-createdAt")
                .findObjects(new FindListener<NoticeArticle>() {
            @Override
            public void done(List<NoticeArticle> object, BmobException e) {
                if (e == null) {
                    setDataView(object);
                } else {
                    ToastUtils.showShort(getContext(), getString(R.string.tip_query_notice));
                }
            }
        });

    }

    private void setDataView(List<NoticeArticle> noticeArticleList) {
        NoticeAdapter noticeAdapter = new NoticeAdapter(noticeArticleList, getContext());
        recyclerViewNotice.setAdapter(noticeAdapter);
        noticeAdapter.setOnItemClickListener(new NoticeAdapter.OnItemClickListener() {
            @Override
            public void onClick(View v, int position) {
                skipWebView(noticeArticleList.get(position).getTitle(), noticeArticleList.get(position).getUrl());
            }
        });
    }

    private void skipWebView(String title, String url) {
        WebUrl webUrl = new WebUrl(title, url);
        Bundle bundle = new Bundle();
        bundle.putSerializable("WebUrl", webUrl);
        startActivity(WebViewActivity.class, bundle);
    }


    @Override
    public void onRefresh() {
        queryNotice();
        refreshNotice.setRefreshing(false);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(MessageEvent messageEvent) {
        switch (messageEvent.getMessage()) {
            case ConstantConfig.NOTICE_PUSH:
                queryNotice();
                break;
            default:
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

}
