package com.androprogrammer.tutorials.samples;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.graphics.Bitmap;
import android.net.http.SslError;
import android.os.Build;
import android.os.Bundle;
import android.transition.Explode;
import android.transition.Transition;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.webkit.JavascriptInterface;
import android.webkit.SslErrorHandler;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.androprogrammer.tutorials.R;
import com.androprogrammer.tutorials.activities.Baseactivity;
import com.androprogrammer.tutorials.util.Common;

import butterknife.Bind;
import butterknife.ButterKnife;

public class WebViewCustomizationDemo extends Baseactivity {

    @Bind(R.id.progress_loading)
    ProgressBar progressLoading;
    @Bind(R.id.wv_customization)
    WebView wvCustomization;

    private View viewLayout;

    private String pageUrl = "https://www.androprogrammer.com/errorPage.html";

    private String DEFAULT_ERROR_PAGE_PATH = "file:///android_asset/html/default_error_page.html";

    private static final String TAG = "WebViewCustomization";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);

            //set the transition
            Transition ts = new Explode();
            ts.setDuration(5000);
            getWindow().setEnterTransition(ts);
            getWindow().setExitTransition(ts);
        }

        super.onCreate(savedInstanceState);

        setReference();

        setSimpleToolbar(true);
        setToolbarElevation(getResources().getDimension(R.dimen.elevation_normal));

        setToolbarSubTittle(this.getClass().getSimpleName());

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public void setReference() {

        viewLayout = LayoutInflater.from(this).inflate(R.layout.activity_web_customization_demo, container);

        ButterKnife.bind(this, viewLayout);

        wvCustomization.clearHistory();
        wvCustomization.getSettings().setAppCacheEnabled(false);
        wvCustomization.getSettings().setJavaScriptEnabled(true);

        wvCustomization.addJavascriptInterface(new SimpleWebJavascriptInterface(this), "Android");
        wvCustomization.setWebViewClient(new MyWebViewClient());

        wvCustomization.loadUrl(DEFAULT_ERROR_PAGE_PATH);

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                finish();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (wvCustomization.canGoBack()) {
            wvCustomization.goBack();
        } else {
            finish();
        }
    }

    private class MyWebViewClient extends WebViewClient {

        @Override public boolean shouldOverrideUrlLoading(WebView view, String url) {

            Log.d(TAG, "--" + url);

            return true;
        }

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            String message = null;
            switch (error.getPrimaryError()) {
                case SslError.SSL_UNTRUSTED:
                    message = "The certificate authority is not trusted.";
                    break;
                case SslError.SSL_EXPIRED:
                    message = "The certificate has expired.";
                    break;
                case SslError.SSL_IDMISMATCH:
                    message = "The certificate Hostname mismatch.";
                    break;
                case SslError.SSL_INVALID:
                    message = "SSL connection is invalid.";
                    break;
            }

            // display error dialog with title and message
            Common.showDialog(WebViewCustomizationDemo.this, "SSL Certificate Error", message,
                    getResources().getString(android.R.string.ok),
                    null, null, 1);
        }

        @SuppressLint("NewApi")
        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            if (request.isForMainFrame() && error != null) {
                view.loadUrl(DEFAULT_ERROR_PAGE_PATH);
            }
        }

        @Override
        public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
            if (errorCode != WebViewClient.ERROR_UNSUPPORTED_SCHEME && errorCode != WebViewClient.ERROR_HOST_LOOKUP) {
                view.loadUrl(DEFAULT_ERROR_PAGE_PATH);
            }
        }

        @Override public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            if (progressLoading != null) {
                progressLoading.setVisibility(View.VISIBLE);
                wvCustomization.setVisibility(View.GONE);
            }
        }

        @Override public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            if (progressLoading != null) {
                progressLoading.setVisibility(View.GONE);
                wvCustomization.setVisibility(View.VISIBLE);
            }
        }
    }

    public class SimpleWebJavascriptInterface {

        Activity activity;

        public SimpleWebJavascriptInterface(Activity activity) {
            this.activity = activity;
        }

        @JavascriptInterface
        public void reloadWebPage() {

            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    wvCustomization.loadUrl(pageUrl);
                }
            });

        }
    }
}
