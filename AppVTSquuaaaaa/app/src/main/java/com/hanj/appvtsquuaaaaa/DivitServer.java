package com.hanj.appvtsquuaaaaa;

import java.util.List;
import java.util.Map;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.Headers;
import retrofit.http.POST;
import retrofit.http.Path;
import retrofit.http.Query;
import retrofit.http.QueryMap;

/**
 * Created by Nam on 2/27/2016.
 */
public interface DivitServer {
    @GET("users")
    Call<List<User>> getUsers();

    @POST("users")
    Call<User> signUpUser(
            @QueryMap Map<String, String> params
    );

    @GET("users/{id}")
    Call<User> getUser(
            @Path("id") String phone
    );

    @GET("signin")
    Call<User> signIn(
            @Query("phone") String phone,
            @Query("password") String password
    );

    @Headers({
            "Content-Type: application/json"
    })
    @POST("users/{id}/processBills.json")
    Call<Void> processBills(
            @Path("id") String phone,
            @Body String billsJson
    );

}
