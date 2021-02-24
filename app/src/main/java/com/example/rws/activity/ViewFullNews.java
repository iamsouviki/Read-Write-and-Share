package com.example.rws.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import com.example.rws.R;

public class ViewFullNews extends AppCompatActivity {

    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_full_news);
        getSupportActionBar().setTitle("Full news articles");

        Intent intn = getIntent();
        int position = intn.getIntExtra("position", 0);

        WebView webView = findViewById(R.id.webviewofnews);
        progressBar = findViewById(R.id.progressbar);
        progressBar.setMax(100);

        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(NewsHomeActivity.urllist.get(position));
        webView.getSettings().setJavaScriptEnabled(true);

        webView.setWebChromeClient(new WebChromeClient(){
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                progressBar.setProgress(newProgress);
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
            }

            @Override
            public void onReceivedIcon(WebView view, Bitmap icon) {
                super.onReceivedIcon(view, icon);
            }
        });

    }
}