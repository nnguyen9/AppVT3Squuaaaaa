package com.hanj.appvtsquuaaaaa;

import java.util.List;

import retrofit.Call;
import retrofit.http.GET;
import retrofit.http.Headers;
import retrofit.http.POST;
import retrofit.http.Path;
import retrofit.http.Query;

/**
 * Created by Nam on 2/27/2016.
 */
public interface DivitServer {
    @GET("users")
    Call<List<User>> getUsers();

    @POST("users")
    Call<User> signUpUser(
            @Query("first_name") String firstName,
            @Query("last_name") String lastName,
            @Query("phone") String phone,
            @Query("cap_id") int capId,
            @Query("password") String password
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
            "Content-type: application/json"
    })
    @POST("users/{id}/processBills")
    Call<String> processBills(
            @Path("id") String phone,
            @Query("bills") String billsJson
    );

}
