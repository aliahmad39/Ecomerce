package com.ali.pf_monthly_task.Api;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    public static Retrofit RETROFIT = null;

  public static String BASE_URL = "https://fakestoreapi.com/";



    public static Retrofit getClient() {
        if (RETROFIT == null) {
            OkHttpClient okHttpClient = new OkHttpClient.Builder().build();
            //    AndroidNetworking.initialize(getApplicationContext());

            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();

            //    AndroidNetworking.setParserFactory(new GsonParserFactory(gson));
            //    Gson gson = new GsonBuilder().create();
//
//            RETROFIT = new Retrofit.Builder()
//                    .baseUrl(BASE_URL + "ApiAuthentication/")
//                    .client(okHttpClient)
//                    .addConverterFactory(GsonConverterFactory.create(gson)).build();

            RETROFIT = new Retrofit.Builder()
                    .baseUrl(BASE_URL )
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create(gson)).build();
        }
        return RETROFIT;
    }

}
