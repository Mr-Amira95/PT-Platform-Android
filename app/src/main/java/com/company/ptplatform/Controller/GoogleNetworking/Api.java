package com.company.ptplatform.Controller.GoogleNetworking;

import com.company.ptplatform.Model.Beans.General;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Url;

public interface Api {

    @GET
    @Headers({"Accept: application/json"})
    Call<General> getProduct(@Url String url);

}
