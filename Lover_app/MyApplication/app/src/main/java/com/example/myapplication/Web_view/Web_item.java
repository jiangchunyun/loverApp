package com.example.myapplication.Web_view;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import com.example.myapplication.R;

/**
 * Created by 上官刀刀 on 2017/10/2.
 */

public class Web_item extends Activity {
    private WebView web_item;
    private TextView sign;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.web_item);
        Intent intent = getIntent();
        web_item= (WebView) findViewById(R.id.web_item);
        sign= (TextView) findViewById(R.id.sign);
        String url = intent.getStringExtra("url");
        web_item.loadUrl(url);
        web_item.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                return super.shouldOverrideUrlLoading(view, request);
            }
        });
        web_item.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (newProgress==100){
                    sign.setText("详细信息");

                }else{
                    sign.setText("加载中.....");
                }
            }
        });
        WebSettings webSettings = web_item.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setSupportZoom(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setDomStorageEnabled(true);
        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        web_item.setInitialScale(70);
    }
}
