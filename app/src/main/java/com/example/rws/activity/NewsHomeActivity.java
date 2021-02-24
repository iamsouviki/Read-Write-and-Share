package com.example.rws.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.rws.R;
import com.example.rws.adapter.NewsAdapter;
import com.example.rws.apiclient.NewsApi;
import com.example.rws.modelclass.AllNews;
import com.example.rws.modelclass.Article;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsHomeActivity extends AppCompatActivity {

    ListView newslist;
    public static ArrayList<String> urllist = new ArrayList<>();
    public static AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_home);
        getSupportActionBar().hide();

        newslist = findViewById(R.id.newslistview);
        newslist.setClickable(true);
        newslist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(NewsHomeActivity.this, ViewFullNews.class);
                intent.putExtra("position",position);
                startActivity(intent);
            }
        });

        showalertdialog();

        getdata();
    }

    private void showalertdialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater layoutInflater = LayoutInflater.from(this);
        final View dialogview = layoutInflater.inflate(R.layout.loading_networkerror,null);
        builder.setView(dialogview);
        builder.setCancelable(false);
        alertDialog = builder.create();
        alertDialog.show();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dialogview.findViewById(R.id.longertime).setVisibility(View.VISIBLE);
            }
        }, 7000);
    }

    private void getdata() {
        Call<AllNews> news = NewsApi.getPostService().getNews();
        news.enqueue(new Callback<AllNews>() {
            @Override
            public void onResponse(Call<AllNews> call, Response<AllNews> response) {
                if(response.body().getStatus().equals("ok")){
                    alertDialog.dismiss();
                    List<Article> articles = response.body().getArticles();
                    if(urllist!=null) {
                        for (int k = 0; k < 20; k++) {
                            urllist.add(response.body().getArticles().get(k).getUrl());
                        }
                    }
                    if(articles.size()>0){
                        NewsAdapter newsAdapter = new NewsAdapter(articles, NewsHomeActivity.this);
                        newslist.setAdapter(newsAdapter);
                    }
                }
            }

            @Override
            public void onFailure(Call<AllNews> call, Throwable t) {
               
            }
        });
    }
}