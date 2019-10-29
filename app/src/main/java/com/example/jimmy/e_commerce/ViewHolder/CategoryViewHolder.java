package com.example.jimmy.e_commerce.ViewHolder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jimmy.e_commerce.Interface.ItemclickListner;
import com.example.jimmy.e_commerce.R;

public class CategoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener  {

    public TextView txtcategoryName;
    public ImageView imageView;
    private ItemclickListner itemclickListner;

    public CategoryViewHolder(@NonNull View itemView) {
        super(itemView);
        txtcategoryName = (TextView) itemView.findViewById(R.id.category_name);
        imageView = (ImageView) itemView.findViewById(R.id.category_image);

itemView.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {
        itemclickListner.onClick(v,getAdapterPosition(),false);
    }
}