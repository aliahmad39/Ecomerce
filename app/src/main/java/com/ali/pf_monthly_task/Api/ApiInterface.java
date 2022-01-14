package com.ali.pf_monthly_task.Api;

import android.view.ViewDebug;

import com.ali.pf_monthly_task.models.Login;
import com.ali.pf_monthly_task.models.Products;

import java.lang.annotation.Target;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface ApiInterface {
  //String category = "products/category/jewelery";


    @GET("users")
    Call<List<Login>> getUsers();

    @GET("products")
    Call<List<Products>> getProducts(@Query("sort") String sort , @Query("limit") String limit);

    @GET("products/categories")
    Call<List<String>> getCategory();

    @GET("products")
    Call<List<Products>> getLimitedProduct(
           @Query("limit") String limit
    );

    @GET("products")
    Call<List<Products>> getSortedProduct(
            @Query("sort") String sort
    );

//    @GET(value = category)
//   Call<List<Products>> getCategoryProducts();

//    @GET
//    Call<Users> getUsers(@Url String url);

//    @GET("users/{user_id}/playlists")
//    Call<List<Playlist> getUserPlaylists(@Path(value = "user_id", encoded = true) String userId);


    @GET("products{user_id}")
   Call<List<Products>> getCategoryProducts(@Path(value = "user_id", encoded = true) String userId , @Query("sort") String sort , @Query("limit") String limit);









//    https://fakestoreapi.com/products?sort=desc




    @GET("products/{user_id}")
    Call<Products> getSingleProducts(@Path(value = "user_id", encoded = true) String userId);






}

