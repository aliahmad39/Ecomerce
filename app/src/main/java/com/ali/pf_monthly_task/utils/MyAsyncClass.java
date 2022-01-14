package com.ali.pf_monthly_task.utils;


import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ali.pf_monthly_task.Activities.MainActivity;
import com.ali.pf_monthly_task.Adapters.CategoryAdapter;
import com.ali.pf_monthly_task.CallBacks.OnDataLoad;
import com.ali.pf_monthly_task.R;
import com.ali.pf_monthly_task.models.Products;

import com.kaopiz.kprogresshud.KProgressHUD;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyAsyncClass extends AsyncTask<String, Integer, String> {

    Singleton singleton = Singleton.getInstance();
    ArrayList<Products> productsList = singleton.productsList;
    ArrayList<String> prdCtgry = singleton.categoryList;
    private KProgressHUD hud;
    Context context;
    String string;

    String type;
    String categoryType;


    public OnDataLoad onDataLoad;

    public void setInterface(OnDataLoad onDataLoad1) {
        onDataLoad = onDataLoad1;
    }

    public MyAsyncClass(Context context,  String type) {
        this.context = context;
        this.type = type;
    }


    @Override
    protected String doInBackground(String... strings) {

        if (type.equals("All")) {
            getCategoryProducts("");
        }
        else if (type.equals("Asc") || type.equals("desc")) {
          categoryType = singleton.categoryList.get(CategoryAdapter.selectedPos);

            if (categoryType.equals("All")) {

                getCategoryProducts("");

                Log.d("MySorting", "sorting if:" +categoryType);
            }else{
                getCategoryProducts("/category/"+categoryType );
                Log.d("MySorting", "sorting else:" +categoryType);
            }

        }
        else if (type.equals("4") || type.equals("8") || type.equals("12") || type.equals("16") || type.equals("20")) {
            categoryType = singleton.categoryList.get(CategoryAdapter.selectedPos);
            if (categoryType.equals("All")) {
                getCategoryProducts("");
                Log.d("MySorting", "limit if :" +categoryType);
            }else{
                getCategoryProducts("/category/"+categoryType );
                Log.d("MySorting", "limit else :" +categoryType);
            }

        }
        else if (type.equals("category")) {
            singleton.apiInterface.getCategory().enqueue(new Callback<List<String>>() {
                @Override
                public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                    if (response.isSuccessful()) {
                        //  productsList.clear();
                        //  Toast.makeText(context, "else category", Toast.LENGTH_SHORT).show();
                        //   productsList.addAll(response.body());
                        //   Log.d("MyList", "else category" );
                        //   Log.d("MyList", "list1 :" + productsList.size());
                        //     tryAgain.setVisibility(View.GONE);
                        prdCtgry.clear();
                        prdCtgry.add("All");
                        prdCtgry.addAll(response.body());

                        Log.d("MyList", "list category :" + response.body().toString());
                        Log.d("MyList", "list1 category :" + prdCtgry.size());
                        if (!prdCtgry.isEmpty()) {
                            //   Toast.makeText(context, "", Toast.LENGTH_SHORT).show();
                            onPostExecute("category");
                        }

                        Toast.makeText(context, "Category List size :" + prdCtgry.size(), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(context, "Category Error :" + response.errorBody(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<List<String>> call, Throwable t) {
                    //   tryAgain.setVisibility(View.VISIBLE);
                    hud.dismiss();
                    MainActivity.layoutNotFound.setVisibility(View.VISIBLE);
                    MainActivity.btnTryAgain.setVisibility(View.VISIBLE);
                    MainActivity.loadProduct.setVisibility(View.GONE);
                    Toast.makeText(context, "Network Error try later!", Toast.LENGTH_LONG).show();
                }
            });
        }
        else {
            //    Log.d("MyList", "list :" +category);

            getCategoryProducts("/category/"+type );
        }
        return string;
    }

    private void getCategoryProducts(String string) {
        singleton.apiInterface.getCategoryProducts(string, singleton.sorting[singleton.sortedIndex], singleton.limit[singleton.limitIndex]).enqueue(new Callback<List<Products>>() {
            @Override
            public void onResponse(Call<List<Products>> call, Response<List<Products>> response) {
                productsList.clear();
                //  Toast.makeText(context, "else category", Toast.LENGTH_SHORT).show();
                productsList.addAll(response.body());
                Log.d("MyList", "list else :" + response.body().toString());
                Log.d("MyList", "list1 else :" + productsList.size());

                if (!productsList.isEmpty()) {
                    //   Toast.makeText(context, "", Toast.LENGTH_SHORT).show();
                    onPostExecute("product");
                }
            }

            @Override
            public void onFailure(Call<List<Products>> call, Throwable t) {
                hud.dismiss();
//                new Handler().postDelayed(new Runnable() {
//                    @Override
//                    public void run() {
//                        linearLayout.setVisibility(View.VISIBLE);
//                        textView.setText(t.getMessage()+"\nPlease Ensure Availability of Your Network Connection");
//                     //   Toast.makeText(context, t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
//                        hud.dismiss();
//                    }
//                },1000);
                Toast.makeText(context, "Network Error try later!", Toast.LENGTH_LONG).show();

                MainActivity.layoutNotFound.setVisibility(View.VISIBLE);
                MainActivity.loadProduct.setVisibility(View.GONE);
            }
        });
    }

    @Override
    protected void onPreExecute() {
        kProgressHud();
        MainActivity.layoutNotFound.setVisibility(View.GONE);
        super.onPreExecute();
    }

    private void kProgressHud() {
        hud = KProgressHUD.create(context)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setWindowColor(context.getResources().getColor(R.color.purple_500))
                .setLabel("Please wait")
                .setAnimationSpeed(2)
                .setCancellable(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface
                                                 dialogInterface) {
                        Toast.makeText(context, "You " + "cancelled manually!", Toast.LENGTH_SHORT).show();
                    }
                });
        hud.show();
    }

    @Override
    protected void onPostExecute(String s) {
        if (s != null) {
            //     Toast.makeText(context, "null", Toast.LENGTH_SHORT).show();
            MainActivity.layoutNotFound.setVisibility(View.GONE);
            MainActivity.loadProduct.setVisibility(View.VISIBLE);
            if (s.equals("product")) {
                //       Toast.makeText(context, "done", Toast.LENGTH_SHORT).show();

                Log.d("MyList", "on post product :" + productsList.size());
                onDataLoad.updateAdapter(s);
                hud.dismiss();
            } else {
                MainActivity.btnTryAgain.setVisibility(View.GONE);
                Log.d("MyList", "on post category :" + prdCtgry.size());
                onDataLoad.updateAdapter(s);
                hud.dismiss();
            }
        }
        //  Toast.makeText(context, "post :", Toast.LENGTH_SHORT).show();


        super.onPostExecute(s);
    }


}

