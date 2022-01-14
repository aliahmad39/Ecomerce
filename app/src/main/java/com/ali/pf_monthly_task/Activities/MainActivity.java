package com.ali.pf_monthly_task.Activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.ali.pf_monthly_task.Adapters.CategoryAdapter;
import com.ali.pf_monthly_task.utils.MyAsyncClass;
import com.ali.pf_monthly_task.CallBacks.OnDataLoad;
import com.ali.pf_monthly_task.R;
import com.ali.pf_monthly_task.Adapters.ProductsAdapter;
import com.ali.pf_monthly_task.utils.Singleton;

public class MainActivity extends AppCompatActivity implements OnDataLoad {
    Singleton singleton = Singleton.getInstance();
    RecyclerView category, products;
    ImageView back;
    public static Spinner spLimit, spSort;
    CategoryAdapter categoryAdapter;
    MyAsyncClass myAsyncClass;
    ProductsAdapter productAdapter;

    public static TextView tvNotFound;
    public static LinearLayout loadProduct;
    public static RelativeLayout layoutNotFound;
    public static Button btnTryAgain;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bindViews();
        callProducts("category");
        setLimitSpinner(this);
        setSortSpinnner(this);
        callSpinners();
        goBack();

    }

    private void goBack() {
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @SuppressLint("ResourceAsColor")
    private void callSpinners() {
        spLimit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i != 0) {
                    //   setSortSpinnner(MainActivity.this);
                    //   categoryAdapter.selectedPos = 0;
                 //   categoryAdapter.notifyDataSetChanged();
                    //     category.smoothScrollToPosition(0);
                    //   spLimit.setBackgroundColor(R.color.colorAccent);
                    singleton.limitIndex = i;
                    callProducts(singleton.limit[i]);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        spSort.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i != 0) {
                    //  setLimitSpinner(MainActivity.this);
                    //     categoryAdapter.selectedPos = 0;
                    //     categoryAdapter.notifyDataSetChanged();
                    //     category.smoothScrollToPosition(0);


                    //  category.setBackgroundColor(R.color.colorAccent);
                    singleton.sortedIndex = i;
                    callProducts(singleton.sorting[i]);
                    Toast.makeText(MainActivity.this, singleton.sorting[i], Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }


    public void setLimitSpinner(Context context) {
        ArrayAdapter limitAdapter = new ArrayAdapter(context, android.R.layout.simple_spinner_item, singleton.limit);
        limitAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spLimit.setAdapter(limitAdapter);
    }

    public void setSortSpinnner(Context context) {
        ArrayAdapter sortAdapter = new ArrayAdapter(context, android.R.layout.simple_spinner_item, singleton.sorting);
        sortAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spSort.setAdapter(sortAdapter);
    }


    public void callProducts(String type) {
        myAsyncClass = new MyAsyncClass(MainActivity.this, type);
        myAsyncClass.setInterface(MainActivity.this);
        myAsyncClass.execute();
    }

    public void setCategoryAdapter() {
        //  RecyclerViewLayoutManager = new LinearLayoutManager(getApplicationContext());
        //  category.setLayoutManager(RecyclerViewLayoutManager);
        categoryAdapter = new CategoryAdapter(MainActivity.this);
        category.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayoutManager.HORIZONTAL, false));
        category.setAdapter(categoryAdapter);
    }

    public void bindViews() {
        category = findViewById(R.id.re_category);
        products = findViewById(R.id.re_Products);
        spLimit = findViewById(R.id.spinner_linit);
        spSort = findViewById(R.id.spinner_sort);
        loadProduct = findViewById(R.id.load_product);
        layoutNotFound = findViewById(R.id.layout_notFound);
        tvNotFound = findViewById(R.id.tv_notFound);
        btnTryAgain = findViewById(R.id.btn_tryAgain);
        back = findViewById(R.id.iv_Back);

    }

    @Override
    public void updateAdapter(String type) {
        if (type.equals("product")) {
            setProductAdapter();
        } else {
            setCategoryAdapter();
            callProducts("All");
        }

    }

    private void setProductAdapter() {
        productAdapter = new ProductsAdapter(this);
        products.setLayoutManager(new GridLayoutManager(this, 2));
        products.setAdapter(productAdapter);

    }

    public void tryAgain(View view) {
        //   MainActivity.btnTryAgain.setVisibility(View.GONE);
        MainActivity.layoutNotFound.setVisibility(View.GONE);
        callProducts("category");
    }

//    @Override
//    public void onBackPressed() {
//        if(singleton.categoryCall.){
//            Toast.makeText(this, "on back if", Toast.LENGTH_SHORT).show();
//            singleton.categoryCall.cancel();
//            singleton.hud.dismiss();
//        }else if(singleton.prodCall.isExecuted()){
//            Toast.makeText(this, "on back if else", Toast.LENGTH_SHORT).show();
//            singleton.prodCall.cancel();
//            singleton.hud.dismiss();
//        }else{
//            super.onBackPressed();
//        }
//    }
}