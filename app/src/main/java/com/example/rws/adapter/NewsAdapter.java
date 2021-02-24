package com.example.rws.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.rws.R;
import com.example.rws.modelclass.Article;
import com.example.rws.modelclass.Source;
import com.squareup.picasso.Picasso;

import java.util.List;

public class NewsAdapter extends BaseAdapter {
    List<Article> articleList ;
    Context context;

    public NewsAdapter(List<Article> articleList, Context context){
        this.articleList = articleList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return articleList.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Article article = articleList.get(position);
        View view = LayoutInflater.from(context).inflate(R.layout.news_view,null);

        ImageView newsimage = view.findViewById(R.id.newsimage);
        if(!TextUtils.isEmpty(article.getUrlToImage())) {
            Picasso.get().load(article.getUrlToImage()).into(newsimage);
        }

        TextView author = view.findViewById(R.id.newsauthor);
        if(article.getAuthor()!=null){
            author.setText("Author : "+article.getAuthor().toString());
        }else{
            author.setText("");
        }

        TextView source = view.findViewById(R.id.newssource);
        Source source1 = article.getSource();
        if(!TextUtils.isEmpty(source1.getName())){
            source.setText("Source : "+source1.getName());
        }

       TextView date = view.findViewById(R.id.newstime);
        if(!TextUtils.isEmpty(article.getPublishedAt())){
            String tmdt = article.getPublishedAt();
            String arr[] = tmdt.split("T");
            date.setText("Date : "+arr[0]+"\nTime : "+arr[1]);
        }

      TextView title = view.findViewById(R.id.newstitle);
        if(!TextUtils.isEmpty(article.getTitle())){
            title.setText(article.getTitle());
        }

        TextView descp = view.findViewById(R.id.newsdescription);
        if(!TextUtils.isEmpty(article.getDescription())){
            descp.setText(article.getDescription());
        }



        return view;
    }
}
