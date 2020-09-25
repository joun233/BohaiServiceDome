package com.example.bohaiservicedome.activity;

import android.content.Intent;
import android.view.View;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.example.bohaiservicedome.R;
import com.example.bohaiservicedome.base.BaseActivity;
import com.example.bohaiservicedome.entity.WebUrl;
import com.example.bohaiservicedome.utils.ToastUtils;
import com.example.bohaiservicedome.utils.WebViewUtil;

import butterknife.BindView;
import butterknife.OnClick;

public class WebViewActivity extends BaseActivity {

    @BindView(R.id.progressBar)
    ProgressBar progressBar;
    @BindView(R.id.webView)
    WebView webView;

    @Override
    protected int contentViewID() {
        return R.layout.activity_webview;
    }

    @Override
    protected void initialize() {
        Intent intent = getIntent();
        WebUrl webUrl = (WebUrl) intent.getSerializableExtra("WebUrl");
        setTopTitle(webUrl.getTitle(), true);
        setLeftBtnFinish();
        WebViewUtil webViewUtil = new WebViewUtil();
        webViewUtil.ClientWebView(this,webView,progressBar,webUrl.getUrl());
    }

    @OnClick({R.id.arrow_left, R.id.arrow_right})
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.arrow_left:
                if (webView.canGoBack()) {
                    webView.goBack();
                } else {
                    ToastUtils.showShort(WebViewActivity.this,getString(R.string.tip_web_left));
                    return;
                }
                break;
            case R.id.arrow_right:
                if (webView.canGoForward()) {
                    webView.goForward();
                } else {
                    ToastUtils.showShort(WebViewActivity.this,getString(R.string.tip_web_right));
                    return;
                }
                break;
            default:
        }

    }
}
