package com.example.rws.apiclient;

import com.example.rws.modelclass.CompilerModels;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface CompilerApi {

    @POST("execute/")
    Call<CompilerModels> createPost(@Body CompilerModels compilerModels);

}
