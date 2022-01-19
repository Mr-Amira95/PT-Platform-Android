package com.qtechnetworks.ptplatform.Controller.networking;


import java.util.Map;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;


public interface RetrofitServices {

    @GET
    @Headers({"Accept: application/json"})
    Observable<ResponseBody> get(@Url String url, @QueryMap Map<String, Object> params, @Header("Authorization") String authHeader, @Header("Accept-Language") String language);

    @GET
    @Headers({"Accept: application/json"})
    Call<ResponseBody> getpri(@Url String url,@Header("Accept-Language") String language);

    @Headers({"Accept: application/json"})
    @FormUrlEncoded
    @POST
    Call<ResponseBody> post(@Url String url, @FieldMap Map<String, Object> params,@Header("Authorization") String authHeader,@Header("Accept-Language") String language);


}
