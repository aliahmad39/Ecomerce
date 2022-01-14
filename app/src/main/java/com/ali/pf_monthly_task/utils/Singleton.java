package com.ali.pf_monthly_task.utils;

import android.content.DialogInterface;
import android.widget.Toast;

import com.ali.pf_monthly_task.Api.ApiClient;
import com.ali.pf_monthly_task.Api.ApiInterface;
import com.ali.pf_monthly_task.R;
import com.ali.pf_monthly_task.models.Login;
import com.ali.pf_monthly_task.models.Products;
import com.kaopiz.kprogresshud.KProgressHUD;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;

public class Singleton {
    // Static variable reference of single_instance
    // of type Singleton
    private static Singleton single_instance = new Singleton();
   // public KProgressHUD hud;
public int sortedIndex = 0;
public int limitIndex = 0;
    public  String[] sorting = {"Sorted by", "Asc", "desc"};
    public  String[] limit = {"Limit...", "4", "8", "12", "16", "20"};
    // Declaring a variable of type String
    public ArrayList<Login>  usersList = new ArrayList<>();
    public ArrayList<Products>  productsList = new ArrayList<>();
    public ArrayList<String> categoryList = new ArrayList<>();
    Retrofit retrofit = ApiClient.getClient();
    public ApiInterface apiInterface = retrofit.create(ApiInterface.class);

//    public Call<List<Products>> prodCall = apiInterface.getProducts();
//    public Call<List<String>> categoryCall= apiInterface.getCategory();
//    Call<List<Products>> prodCall = apiInterface.getProducts();
//    Call<List<Products>> prodCall = apiInterface.getProducts();
//    Call<List<Products>> prodCall = apiInterface.getProducts();
//    Call<List<Products>> prodCall = apiInterface.getProducts();
//    Call<List<Products>> prodCall = apiInterface.getProducts();





    // Constructor
    // Here we will be creating private constructor
    // restricted to this class itself

    // Static method
    // Static method to create instance of Singleton class
    public static Singleton getInstance()
    {
        if (single_instance == null)
            single_instance = new Singleton();

        return single_instance;
    }

    public boolean isSavedListAdded = false;




}

