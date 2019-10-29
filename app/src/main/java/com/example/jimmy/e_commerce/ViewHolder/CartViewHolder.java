package com.example.jimmy.e_commerce.ViewHolder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jimmy.e_commerce.Interface.ItemclickListner;
import com.example.jimmy.e_commerce.R;

public class CartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView txtproductname,txtproductprice;
    public ImageView imageView;
    public Button editbtn,deletebtn;
    private ItemclickListner itemclickListner;

    public CartViewHolder(@NonNull View itemView) {
        super(itemView);
        txtproductname=(TextView) itemView.findViewById(R.id.cart_item_name);
        txtproductprice=(TextView) itemView.findViewById(R.id.cart_item_Price);
        imageView=(ImageView) itemView.findViewById(R.id.cart_item_count);
        deletebtn=(Button)itemView.findViewById(R.id.delete_btn);
        editbtn=(Button)itemView.findViewById(R.id.Edit);

        itemView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        itemclickListner.onClick(v,getAdapterPosition(),false);

    }
}
