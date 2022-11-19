package com.qtechnetworks.ptplatform.Controller.NewNetworking;

import com.qtechnetworks.ptplatform.Model.Beans.FoodHome.Foodhome;
import com.qtechnetworks.ptplatform.Model.Beans.General;

import java.util.ArrayList;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
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
