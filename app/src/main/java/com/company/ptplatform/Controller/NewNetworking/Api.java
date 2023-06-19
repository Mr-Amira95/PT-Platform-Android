package com.company.ptplatform.Controller.NewNetworking;

import com.company.ptplatform.Model.Beans.FoodHome.Foodhome;
import com.company.ptplatform.Model.Beans.General;

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

    @Headers("Accept: application/json")
    @FormUrlEncoded
    @POST
    Call<General> supportForm(@Url String url, @FieldMap Map<String, Object> params);

    @GET
    @Headers({"Accept: application/json"})
    Call<Foodhome> getFood(@Url String url, @QueryMap Map<String, Object> params);

}
