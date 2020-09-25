package com.example.bohaiservicedome.widget;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.bohaiservicedome.R;
import com.lxj.xpopup.core.CenterPopupView;

import androidx.annotation.NonNull;

/**
 * Created by gaofang on 2019/1/18.
 * 从中心位置弹框显示
 */

public class MyCenterPopupView extends CenterPopupView {
    private TextView tvTitle;
    private TextView tvContent;
    private Button btnNegative;
    private Button btnPositive;
    private NegativeClickListener mNegativeListener;
    private PositiveClickListener mPositiveListener;


    public MyCenterPopupView(@NonNull Context context) {
        super(context);
    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.popup_center_tip;
    }


    @Override
    protected void initPopupContent() {
        super.initPopupContent();
        tvTitle = findViewById(R.id.tv_title);
        tvContent = findViewById(R.id.tv_content);
        btnPositive = findViewById(R.id.btn_positive);
        btnNegative = findViewById(R.id.btn_negative);
    }

    /**
     * 单个按钮显示弹窗
     *
     * @param titleStr         标题
     * @param contentStr       内容
     * @param positiveStr      右边按钮文字
     * @param positiveListener 右边响应接口
     */
    public void setSinglePopup(String titleStr, String contentStr, String positiveStr, PositiveClickListener positiveListener) {
        this.mPositiveListener = positiveListener;
        if (!TextUtils.isEmpty(titleStr)) {
            tvTitle.setText(titleStr);
        } else {
            tvTitle.setVisibility(View.GONE);
        }
        if (!TextUtils.isEmpty(contentStr)) {
            tvContent.setText(contentStr);
        } else {
            tvContent.setVisibility(View.GONE);
        }
        btnNegative.setVisibility(View.GONE);
        btnPositive.setText(positiveStr);
        btnPositive.setOnClickListener(mPListener);

    }

    /**
     * 两个按钮显示弹窗
     * @param titleStr
     * @param contentStr
     * @param positiveStr
     * @param negativeStr
     * @param positiveListener
     * @param negativeListener
     */
    public void setDoublePopup(String titleStr, String contentStr, String positiveStr,
                               String negativeStr, PositiveClickListener positiveListener,
                               NegativeClickListener negativeListener) {
        this.mPositiveListener = positiveListener;
        this.mNegativeListener = negativeListener;

        if (!TextUtils.isEmpty(titleStr)) {
            tvTitle.setText(titleStr);
        } else {
            tvTitle.setVisibility(View.GONE);
        }
        if (!TextUtils.isEmpty(contentStr)) {
            tvContent.setText(contentStr);
        } else {
            tvContent.setVisibility(View.GONE);
        }
        btnPositive.setText(positiveStr);
        btnNegative.setText(negativeStr);
        btnPositive.setOnClickListener(mPListener);
        btnNegative.setOnClickListener(mNListener);

    }

    private OnClickListener mPListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            mPositiveListener.onPositiveClick();
            dismiss();
        }
    };

    private OnClickListener mNListener = new OnClickListener() {
        @Override
        public void onClick(View v) {
            mNegativeListener.onNegativeClick();
            dismiss();
        }
    };


    public interface NegativeClickListener {
        void onNegativeClick();

    }

    public interface PositiveClickListener {
        void onPositiveClick();
    }

}
