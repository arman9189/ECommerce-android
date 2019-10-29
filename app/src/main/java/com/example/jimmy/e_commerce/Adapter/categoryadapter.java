package com.example.jimmy.e_commerce.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jimmy.e_commerce.Models.Categories;
import com.example.jimmy.e_commerce.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class categoryadapter extends RecyclerView.Adapter<categoryadapter.categoryadapterViewHolder> {


    public  Context c;
    public  ArrayList<Categories> arrayList;
    public categoryadapter (Context c, ArrayList<Categories> arrayList)
    {
            this.c=c;
            this.arrayList=arrayList;
    }
    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public categoryadapterViewHolder onCreateViewHolder( ViewGroup viewGroup, int i) {
        View v=LayoutInflater.from(viewGroup.getContext())
                .inflate(R.layout.category_item,viewGroup,false);
        return new categoryadapterViewHolder(v);
    }

    @Override
    public void onBindViewHolder(categoryadapterViewHolder holder, int i) {

        Categories categoryitem=arrayList.get(i);
        holder.t1.setText(categoryitem.getName());
        Picasso.get().load(categoryitem.getImage()).into(holder.i1);

    }

    public class categoryadapterViewHolder extends RecyclerView.ViewHolder {
        public TextView t1;
        public ImageView i1;
        public categoryadapterViewHolder(View itemView) {
            super(itemView);
            t1=(TextView)itemView.findViewById(R.id.category_name);
           i1=(ImageView)itemView.findViewById(R.id.category_image);

        }
    }




}
