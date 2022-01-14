package com.ali.pf_monthly_task.Adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.ali.pf_monthly_task.Activities.ProductDetailActivity;
import com.ali.pf_monthly_task.R;
import com.ali.pf_monthly_task.models.Rating;
import com.ali.pf_monthly_task.utils.Singleton;
import com.ali.pf_monthly_task.models.Products;
import com.bumptech.glide.Glide;

import java.io.File;
import java.util.ArrayList;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.RecyclerViewHolder> {

    Singleton singleton = Singleton.getInstance();

    ArrayList<Products> list = singleton.productsList;
    private Context mcontext;
    public ProductsAdapter(Context mcontext) {

        this.mcontext = mcontext;
    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate Layout
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_layout, parent, false);
        return new RecyclerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {
    Products product = list.get(position);

        Rating rating =product.getRating();

        Glide.with(mcontext).load(product.getImage()).into(holder.imageView);
        holder.desc.setText(product.getDescription());
        holder.title.setText(product.getTitle());
        holder.id.setText(Math.round(product.getId())+"");
        holder.price.setText("Rs :"+Math.round(product.getPrice()));
     //   holder.stock.setText("Stock :"+Math.round(rating.count));

//
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(mcontext , ProductDetailActivity.class);
//                intent.putExtra("productDetail" , product);
//                mcontext.startActivity(intent);

                Intent intent = new Intent(mcontext , ProductDetailActivity.class);
                intent.putExtra("productId" , product.getId()+"");
                mcontext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        // this method returns the size of recyclerview
        return list.size();

    }

    // View Holder Class to handle Recycler View.
    public class RecyclerViewHolder extends RecyclerView.ViewHolder {

      //  private TextView courseTV;
        private ImageView imageView;
        private TextView desc , price , stock , title , id;

        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
         //   courseTV = itemView.findViewById(R.id.idTVCourse);
            imageView = itemView.findViewById(R.id.ivProduct);
            desc = itemView.findViewById(R.id.tvProduct);
            price = itemView.findViewById(R.id.tvPrice);
            stock = itemView.findViewById(R.id.tvCount);
            title = itemView.findViewById(R.id.tvTitle);
            id = itemView.findViewById(R.id.tv_id);
        }
    }

}

