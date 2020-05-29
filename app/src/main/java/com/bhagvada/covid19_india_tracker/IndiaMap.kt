package com.bhagvada.covid19_india_tracker

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import androidx.core.view.get
import kotlinx.android.synthetic.main.activity_india_map.*

class IndiaMap : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_india_map)

        val myWebView : WebView = findViewById(R.id.webview)
        myWebView.getSettings().setJavaScriptEnabled(true);
        myWebView.getSettings().setLoadWithOverviewMode(true);
        myWebView.getSettings().setUseWideViewPort(true);
        myWebView.setScrollBarStyle(WebView.SCROLLBARS_OUTSIDE_OVERLAY);
        myWebView.setScrollbarFadingEnabled(false);
        myWebView.setInitialScale(1);
        myWebView.getSettings().setBuiltInZoomControls(true);
        myWebView.loadUrl("file:///android_asset/india map/index.html")
    }

}
