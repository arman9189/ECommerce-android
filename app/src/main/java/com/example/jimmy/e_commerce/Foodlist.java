package com.example.jimmy.e_commerce;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.jimmy.e_commerce.Models.Categories;
import com.example.jimmy.e_commerce.Models.Products;
import com.example.jimmy.e_commerce.ViewHolder.CategoryViewHolder;
import com.example.jimmy.e_commerce.ViewHolder.ProductViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class Foodlist extends AppCompatActivity {

    RecyclerView recyclerView;

    String Category_id;
    Query databaseReference;
    FirebaseRecyclerOptions<Products> optionsproducts;
    FirebaseRecyclerAdapter<Products,ProductViewHolder> adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_foodlist);

        if (getIntent() != null)
            Category_id = getIntent().getStringExtra("categoryid");
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Products").orderByChild("MenuId").equalTo(Category_id);


        optionsproducts = new FirebaseRecyclerOptions.Builder<Products>()
                .setQuery(databaseReference,Products.class).build();

        recyclerView = (RecyclerView) findViewById(R.id.food_list);
        recyclerView.setHasFixedSize(true);

        if (!Category_id.isEmpty() && Category_id != null) {


            adapter = new FirebaseRecyclerAdapter<Products, ProductViewHolder>(optionsproducts) {
                @Override
                protected void onBindViewHolder(ProductViewHolder holder, int position, final Products model) {



                    holder.productname.setText(model.getName());
                    Glide.with(holder.imageproduct.getContext()).load(model.getImage()).into(holder.imageproduct);
                    holder.itemView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent i=new Intent(Foodlist.this,FoodDetails.class);
                            i.putExtra("Foodid",model.getID());
                            i.putExtra("FoodName",model.getName());
                            i.putExtra("FoodDescribtion",model.getDescription());
                            i.putExtra("FoodPrice",model.getPrice());
                            i.putExtra("FoodQuantity",model.getQuantity());
                            i.putExtra("FoodImage",model.getImage());
                            Toast.makeText(getApplicationContext(),model.getName()+" Food",Toast.LENGTH_LONG).show();
                            startActivity(i);
                        }
                    });
                }

                @NonNull
                @Override
                public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                    View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.product_item, viewGroup, false);
                    return new ProductViewHolder(view);


                }
            };

            GridLayoutManager gridLayoutManager = new GridLayoutManager(getApplicationContext(), 2);
            recyclerView.setLayoutManager(gridLayoutManager);
            recyclerView.setAdapter(adapter);

        }
    }

    @Override
    protected void onStart() {

        if(adapter!=null)
            adapter.startListening();
        super.onStart();
    }

    @Override
    protected void onStop() {
        if (adapter!=null)
            adapter.stopListening();
        super.onStop();
    }
}
