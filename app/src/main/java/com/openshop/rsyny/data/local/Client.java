package com.openshop.rsyny.data.local;

import android.webkit.WebView;
import android.webkit.WebViewClient;

public class Client extends WebViewClient {
    @Override
    public boolean shouldOverrideUrlLoading(WebView view, String url) {
        view.loadUrl(url);
        return true;
    }
}
