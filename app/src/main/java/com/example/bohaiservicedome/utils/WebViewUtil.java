package com.example.bohaiservicedome.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebResourceResponse;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

/**
* @author: JiangNan
* @Date:2020/4/2
*/
public class WebViewUtil {
    @SuppressLint("JavascriptInterface")
    public void ClientWebView(final Activity mactive, WebView webView, final ProgressBar progressBar, String url){
        WebSettings webSettings = webView.getSettings();
        //支持js
        webSettings.setJavaScriptEnabled(true);
        // 是否可访问本地文件，默认值 true
        webSettings.setAllowFileAccess(true);
        //自适应屏幕
        webSettings.setLoadWithOverviewMode(true);
        //动态更新内容
        webSettings.setCacheMode(WebSettings.LOAD_DEFAULT);
        //支持自动加载图片
        webSettings.setLoadsImagesAutomatically(true);
        // 是否支持缩放
        webSettings.setSupportZoom(true);
        // 设置出现缩放工具
        webSettings.setBuiltInZoomControls(true);
        //扩大比例的缩放
        webSettings.setUseWideViewPort(true);
        //自适应屏幕
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webSettings.setPluginState(WebSettings.PluginState.ON);
        // 是否可用Javascript(window.open)打开窗口，默认值 false
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        // 设置可现实js的alert弹窗
        webView.setWebChromeClient(new WebChromeClient());

        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                progressBar.setProgress(newProgress);
                if (newProgress == 100) {
                    progressBar.setVisibility(View.GONE);
                }
            }});
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
            @Override
            public WebResourceResponse shouldInterceptRequest(WebView webView, WebResourceRequest webResourceRequest) {
                WebResourceResponse response = null;
                response =  super.shouldInterceptRequest(webView, webResourceRequest);
                return response;

            }
            @Override
            public WebResourceResponse shouldInterceptRequest(WebView webView, String s) {
                WebResourceResponse response = null;
                response =  super.shouldInterceptRequest(webView, s);
                String[] namelist = s.split("/");
                return response;
            }
        });
        webView.loadUrl(url);
    }
}
