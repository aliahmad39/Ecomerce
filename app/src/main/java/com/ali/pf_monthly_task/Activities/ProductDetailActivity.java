package com.ali.pf_monthly_task.Activities;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ali.pf_monthly_task.R;
import com.ali.pf_monthly_task.models.Products;
import com.ali.pf_monthly_task.models.Rating;
import com.ali.pf_monthly_task.utils.Singleton;
import com.bumptech.glide.Glide;
import com.kaopiz.kprogresshud.KProgressHUD;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductDetailActivity extends AppCompatActivity {
    TextView name , desc , tvQty , tvPrice , tvCategory , tvStock ;
    ImageView image , back;
    Button order;
    int qty = 1;
    int price;
    Products product;
    RatingBar ratingBar;
    private KProgressHUD hud;
    String productId;
    Singleton singleton = Singleton.getInstance();
    RelativeLayout notFound;
    LinearLayout productData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_detail);


        bindViews();
        getData();
     //   updateViews();
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

    private void getData() {
//        if (getIntent().hasExtra("productDetail")) {
//            product = (Products) getIntent().getSerializableExtra("productDetail");
//            price = (int) product.getPrice();
//        }
        getSingleProduct();
    }

    private void getSingleProduct() {
        notFound.setVisibility(View.GONE);
        productData.setVisibility(View.GONE);
        kProgressHud();

        if (getIntent().hasExtra("productId")) {
            productId = getIntent().getStringExtra("productId");
        }

        singleton.apiInterface.getSingleProducts(productId).enqueue(new Callback<Products>() {
            @Override
            public void onResponse(Call<Products> call, Response<Products> response) {
                hud.dismiss();
                product = response.body();
                price = (int) product.getPrice();
                updateViews();
            }

            @Override
            public void onFailure(Call<Products> call, Throwable t) {
                Toast.makeText(ProductDetailActivity.this, "Network Error, try later!", Toast.LENGTH_LONG).show();
                    hud.dismiss();
                notFound.setVisibility(View.VISIBLE);
                productData.setVisibility(View.GONE);
            }
        });
    }

    private void updateViews() {
        notFound.setVisibility(View.GONE);
        productData.setVisibility(View.VISIBLE);
        tvQty.setText(qty + "");
        name.setText(product.getTitle()+"");
        desc.setText(product.getDescription()+"");
        tvCategory.setText(product.getCategory()+"");

        tvPrice.setText("Rs :"+price);
        Glide.with(ProductDetailActivity.this).load(product.getImage()).into(image);
        Rating rating =product.getRating();
        ratingBar.setRating(rating.getRate());
        tvStock.setText("Available Stock : "+Math.round(rating.count)+" pcs");


    }


    private void bindViews() {
        tvQty = findViewById(R.id.tvquantity);
        name = findViewById(R.id.tvMedicineName);
        desc = findViewById(R.id.product_detail);
        order = findViewById(R.id.btnAddtoCart);
        tvPrice = findViewById(R.id.product_detail_price);
        tvCategory = findViewById(R.id.tv_Category);
        tvStock = findViewById(R.id.tv_stock);
        image = findViewById(R.id.ivproduct);
        ratingBar = findViewById(R.id.ratingBar);
        back = findViewById(R.id.ivBack);
        notFound = findViewById(R.id.layout_detailNotFound);
        productData = findViewById(R.id.layout_detail);
    }

    public void AddQuantity(View view) {
        qty += 1;

        tvQty.setText(qty + "");
        tvPrice.setText("Rs :" + price * qty);
    }

    public void SubQuantity(View view) {
        if (qty > 1)
            qty -= 1;

        tvQty.setText(qty + "");
        tvPrice.setText("Rs: " + price * qty);
    }




    public void showDialog(View view) {
        openDialog(product);
    }

    private void openDialog(Products obj) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ProductDetailActivity.this);
        ViewGroup viewGroup = findViewById(android.R.id.content);
        View dialogView = LayoutInflater.from(ProductDetailActivity.this).inflate(R.layout.custom_dialogue, viewGroup, false);
        builder.setView(dialogView);
        AlertDialog alertDialog = builder.create();
        alertDialog.setCancelable(false);
        ImageView imageView = dialogView.findViewById(R.id.imageView);
        TextView title = dialogView.findViewById(R.id.textView);
        TextView desc = dialogView.findViewById(R.id.textView1);
        title.setText(obj.getTitle());
        desc.setText(obj.getDescription());
        Glide.with(ProductDetailActivity.this).load(product.getImage()).into(imageView);

        dialogView.findViewById(R.id.btn_cancel).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });
        dialogView.findViewById(R.id.btn_delete).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                Toast.makeText(ProductDetailActivity.this, "Order placed Successfully!", Toast.LENGTH_SHORT).show();
            }
        });
        alertDialog.show();
    }


    private void kProgressHud() {
        hud = KProgressHUD.create(ProductDetailActivity.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setWindowColor(getResources().getColor(R.color.purple_500))
                .setLabel("Please wait")
                .setAnimationSpeed(2)
                .setCancellable(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface
                                                 dialogInterface) {
                        Toast.makeText(ProductDetailActivity.this, "You " + "cancelled manually!", Toast.LENGTH_SHORT).show();
                    }
                });
        hud.show();
    }


    public void tryAgain(View view) {
        notFound.setVisibility(View.GONE);
        productData.setVisibility(View.GONE);
        getSingleProduct();
    }
}
