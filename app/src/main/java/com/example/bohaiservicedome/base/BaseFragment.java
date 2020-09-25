package com.example.bohaiservicedome.base;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author: created by JiangNan
 * @Date: 2020/4/1 17
 */
public abstract class BaseFragment extends Fragment {
    private Unbinder mUnbinder;
    private View mView;
    private Context mContext;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mContext = getActivity();
        if (contentViewID() != 0) {
            mView = inflater.inflate(contentViewID(), container, false);
            return mView;
        } else {
            return super.onCreateView(inflater, container, savedInstanceState);
        }
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

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mUnbinder = ButterKnife.bind(this, view);
        initialize();

    }

    /**
     * 页面跳转
     *
     * @param clz
     */
    public void startActivity(Class<?> clz) {
        startActivity(clz, null);
    }

    /**
     * 页面跳转,跳转后关闭界面
     *
     * @param clz
     */
    public void startActivityFinish(Class<?> clz) {
        startActivity(clz, null);
        getActivity().finish();
    }


    public void startActivity(Class<?> clz, Bundle bundle) {
        Intent intent = new Intent(getActivity(), clz);
        if (null != bundle) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mUnbinder.unbind();//视图销毁时必须解绑
    }

}
