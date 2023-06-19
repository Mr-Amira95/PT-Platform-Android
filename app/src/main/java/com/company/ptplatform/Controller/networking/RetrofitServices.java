package com.company.ptplatform.Controller.networking;


import com.google.gson.JsonObject;
import com.company.ptplatform.Model.Beans.Challenge.ChallengeID;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
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


public interface RetrofitServices {

    @GET
    @Headers({"Accept: application/json"})
    Observable<ResponseBody> get(@Url String url, @QueryMap Map<String, Object> params);

    @Headers({"Accept: application/json"})
    @FormUrlEncoded
    @POST
    Observable<ResponseBody> post(@Url String url, @FieldMap Map<String, Object> params);

    @Headers({"Accept: application/json"})
    @POST
    Observable<ResponseBody> postChallenge(@Url String url, @Body ChallengeID request);

    @Headers({"Accept: application/json"})
    @POST
    Observable<ResponseBody> postRaw(@Url String url, @Body Map<String, Object> params);

    @Headers("Accept: application/json")
    @POST
    Observable<ResponseBody> postLogin(@Url String url, @Body JsonObject params);

    @Headers({"Accept: application/json"})
    @FormUrlEncoded
    @PUT
    Observable<ResponseBody> put(@Url String url, @FieldMap Map<String, Object> params);

    @Headers({"Accept: application/json"})
    @DELETE
    Observable<ResponseBody> delete(@Url String url);

    @Headers({"Accept: application/json"})
    @POST
    @Multipart
    Observable<ResponseBody> uploadfile(@Url String url, @Part MultipartBody.Part part);

}

