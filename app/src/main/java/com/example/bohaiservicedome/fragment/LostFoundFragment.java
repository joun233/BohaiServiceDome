package com.example.bohaiservicedome.fragment;

import android.os.Bundle;
import android.view.View;

import com.example.bohaiservicedome.R;
import com.example.bohaiservicedome.activity.EditLostActivity;
import com.example.bohaiservicedome.adapter.LostAndFoundAdapter;
import com.example.bohaiservicedome.base.BaseFragment;
import com.example.bohaiservicedome.config.ConstantConfig;
import com.example.bohaiservicedome.entity.LostInformation;
import com.example.bohaiservicedome.entity.MessageEvent;
import com.example.bohaiservicedome.utils.ToastUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;
import butterknife.OnClick;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * @author: JiangNan
 * @Date:2020/4/3
 */
public class LostFoundFragment extends BaseFragment implements LostAndFoundAdapter.ItemClickListener {
    @BindView(R.id.recyclerview_lost)
    RecyclerView recyclerViewLost;
    LostAndFoundAdapter lostAndFoundAdapter;
    List<LostInformation> mLostInformationList;

    public static Fragment getInstance() {
        Fragment fragment = new LostFoundFragment();
        return fragment;
    }

    @Override
    protected int contentViewID() {
        return R.layout.fragment_lost_found;
    }

    @Override
    protected void initialize() {
        EventBus.getDefault().register(this);
        lostAndFoundAdapter = new LostAndFoundAdapter(getContext());
        lostAndFoundAdapter.setLongClickListener(this);
        recyclerViewLost.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false));
        queryData();
    }

    /**
     * 查询显示数据
     */
    private void queryData() {
        BmobQuery<LostInformation> lostInformationBmobQuery = new BmobQuery<>();
        lostInformationBmobQuery.order("-updatedAt");//排序
        lostInformationBmobQuery.findObjects(new FindListener<LostInformation>() {
            @Override
            public void done(List<LostInformation> object, BmobException e) {
                if (e == null) {
                    mLostInformationList = object;
                    lostAndFoundAdapter.setLostInformationList(mLostInformationList);
                    recyclerViewLost.setAdapter(lostAndFoundAdapter);
                } else {
                    ToastUtils.showShort(getContext(), getString(R.string.tip_query_lost));
                }
            }
        });
    }
    /**
     * 编辑和删除
     * @param position
     * @param code
     */
    @Override
    public void onEditOrDeleteClick(int position, int code) {
        if (code == LostAndFoundAdapter.EDIT_CODE) {
            Bundle bundle = new Bundle();
            bundle.putSerializable("editData", mLostInformationList.get(position));
            startActivity(EditLostActivity.class, bundle);
        }
        if (code == LostAndFoundAdapter.DELETE_CODE) {
            deleteItemData(position);
        }
    }

    private void deleteItemData(int position) {
        if (!mLostInformationList.isEmpty()) {
            LostInformation lostInformation = new LostInformation();
            lostInformation.setObjectId(mLostInformationList.get(position).getObjectId());
            lostInformation.delete(new UpdateListener() {
                @Override
                public void done(BmobException e) {
                    if (e == null) {
                        mLostInformationList.remove(position);
                        lostAndFoundAdapter.setLostInformationList(mLostInformationList);
                        lostAndFoundAdapter.notifyDataSetChanged();
                    } else {
                        ToastUtils.showShort(getContext(), getString(R.string.delete_failure));
                    }
                }
            });
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(MessageEvent messageEvent) {
        switch (messageEvent.getMessage()) {
            case ConstantConfig.ADD_LOST_PUSH:
            case ConstantConfig.UPDATE_LOST_PUSH:
                refreshData();
                break;
            default:

        }
    }


    private void refreshData() {
        BmobQuery<LostInformation> lostInformationBmobQuery = new BmobQuery<>();
        lostInformationBmobQuery.order("-updatedAt");//排序
        lostInformationBmobQuery.findObjects(new FindListener<LostInformation>() {
            @Override
            public void done(List<LostInformation> object, BmobException e) {
                if (e == null) {
                    mLostInformationList = object;
                    lostAndFoundAdapter.setLostInformationList(mLostInformationList);
                    lostAndFoundAdapter.notifyDataSetChanged();
                } else {
                    ToastUtils.showShort(getContext(), getString(R.string.tip_query_lost));
                }
            }
        });
    }

    @OnClick(R.id.btn_add_lost)
    public void OnClick(View view){
        startActivity(EditLostActivity.class);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }
}
