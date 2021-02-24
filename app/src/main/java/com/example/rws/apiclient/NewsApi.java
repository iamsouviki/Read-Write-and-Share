package com.example.rws.apiclient;

import com.example.rws.modelclass.AllNews;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public class NewsApi {

    final public static String JSON_URL = "https://newsapi.org/v2/";
    final public static String API_KEY = "fbd87b550860461dbcb6c96c7e6f42ba";

    public static PostService postService = null;


    public static PostService getPostService(){

        if(postService==null){
            Retrofit retrofit = new Retrofit.Builder().baseUrl(JSON_URL).addConverterFactory(GsonConverterFactory.create()).build();
            postService=retrofit.create(PostService.class);
        }

        return postService;
    }


    public interface PostService{
        @GET("top-headlines?country=in&category=technology&apiKey="+API_KEY)
        Call<AllNews>  getNews();
    }

}
