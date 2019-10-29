package com.example.jimmy.e_commerce.ViewHolder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jimmy.e_commerce.Interface.ItemclickListner;
import com.example.jimmy.e_commerce.R;

public class ProductViewHolder extends RecyclerView.ViewHolder  {

    public TextView productname;
    public ImageView imageproduct;



    public ProductViewHolder(@NonNull View itemView) {
        super(itemView);
        productname=(TextView)itemView.findViewById(R.id.Product_name);
        imageproduct=(ImageView)itemView.findViewById(R.id.product_image);

    }





}
