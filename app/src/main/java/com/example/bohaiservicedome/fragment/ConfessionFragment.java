package com.example.bohaiservicedome.fragment;

import android.view.View;
import android.widget.ListView;

import com.example.bohaiservicedome.R;
import com.example.bohaiservicedome.adapter.MsgLoveAdapter;
import com.example.bohaiservicedome.base.BaseFragment;
import com.example.bohaiservicedome.config.ConstantConfig;
import com.example.bohaiservicedome.entity.MessageEvent;
import com.example.bohaiservicedome.entity.MsgLove;
import com.example.bohaiservicedome.utils.DialogUtils;
import com.example.bohaiservicedome.utils.ToastUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import butterknife.BindView;
import butterknife.OnClick;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

/**
 * @author: JiangNan
 * @Date:2020/4/3
 */
public class ConfessionFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener {
    @BindView(R.id.msg_love_list)
    ListView msgListView;
    @BindView(R.id.refresh_love)
    SwipeRefreshLayout refreshLove;
    List<MsgLove> mMsgLoveList = new ArrayList<>();
    MsgLoveAdapter mMsgLoveAdapter;

    public static Fragment getInstance() {
        Fragment fragment = new ConfessionFragment();
        return fragment;
    }


    @Override
    protected int contentViewID() {
        return R.layout.fragment_confession;
    }

    @Override
    protected void initialize() {
        EventBus.getDefault().register(this);
        refreshLove.setOnRefreshListener(this);
        refreshLove.setColorSchemeColors(getResources().getColor(R.color.color_love));
        queryLoveMsg();

    }

    private void queryLoveMsg() {
        BmobQuery<MsgLove> msgLoveBmobQuery = new BmobQuery<MsgLove>();
        msgLoveBmobQuery.order("-createdAt");
        msgLoveBmobQuery.findObjects(new FindListener<MsgLove>() {
            @Override
            public void done(List<MsgLove> object, BmobException e) {
                if (e == null) {
                    mMsgLoveList = object;
                    mMsgLoveAdapter = new MsgLoveAdapter(getContext(), R.layout.item_confession, mMsgLoveList);
                    msgListView.setAdapter(mMsgLoveAdapter);
                } else {
                    ToastUtils.showShort(getContext(), getContext().getString(R.string.query_failure));
                }
            }
        });
    }

    @Override
    public void onRefresh() {
        queryLoveMsg();
        mMsgLoveAdapter.notifyDataSetChanged();
        refreshLove.setRefreshing(false);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(MessageEvent messageEvent) {
        switch (messageEvent.getMessage()) {
            case ConstantConfig.UPDATE_LOVE:
                onRefresh();
                break;
            default:
        }
    }


    @OnClick({R.id.btn_add_love})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_add_love:
                DialogUtils.addLoveDialog(getContext());
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
