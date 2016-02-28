package com.hanj.appvtsquuaaaaa;

import android.content.Context;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.logging.HttpLoggingInterceptor;

import java.io.IOException;
import java.util.List;

import retrofit.GsonConverterFactory;
import retrofit.Retrofit;
import retrofit.Call;

/**
 * Created by Nam on 2/27/2016.
 */
public class DivitServerAccess {
    private static DivitServer server;
    private static Context context;

    public static void initialize(Context context) {
        if (server != null) {
            return;
        }

        DivitServerAccess.context = context;

        // Turn on logging for the http calls in retrofit
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        // set your desired log level to body
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient httpClient = new OkHttpClient();
        // add your other interceptors …
        // add logging as last interceptor
        httpClient.interceptors().add(logging);  // <-- this is the important line!
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://jnha.cloudapp.net/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(httpClient)
                .build();

        // Create the server
        server = retrofit.create(DivitServer.class);
    }

    public static DivitServer getServer() {
        return server;
    }
}
