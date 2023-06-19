package com.example.mymvvm.data.datasourse.remote;

import com.example.mymvvm.data.model.PlaceHolderModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;

public interface ApiInterface {
    @Headers({"Content-Type: application/json"})
    @GET("todos")
    Call<List<PlaceHolderModel>> getPlaceHolderData();

}
