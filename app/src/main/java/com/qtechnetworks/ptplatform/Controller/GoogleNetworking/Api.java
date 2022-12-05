package com.qtechnetworks.ptplatform.Controller.GoogleNetworking;

import com.qtechnetworks.ptplatform.Model.Beans.FoodHome.Foodhome;
import com.qtechnetworks.ptplatform.Model.Beans.General;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

public interface Api {

    @GET
    @Headers({"Accept: application/json"})
    Call<General> getProduct(@Url String url);

}
