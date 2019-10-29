package com.example.jimmy.e_commerce;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

public class FoodDetails extends AppCompatActivity {
        TextView food_name,food_description,food_price,food_quantity;
        ImageView food_image;
        CollapsingToolbarLayout collapsingToolbarLayout;
        FloatingActionButton btnCart;
        ElegantNumberButton numberButton;
    private String userid;



        String foodpricestr,foodquantitystr,foodnamestr,foodid,fooddescriptionstr;
    int foodpriceint,foodquantityint;

        FirebaseDatabase database;
        DatabaseReference food,cartref;
        FirebaseAuth auth;
        FirebaseUser myuser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_details);

        //firebase
        auth=FirebaseAuth.getInstance();
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        userid=user.getUid();
        cartref=FirebaseDatabase.getInstance().getReference().child("users").child(userid);
        database=FirebaseDatabase.getInstance();
        food=database.getReference("Products");

        foodid=getIntent().getStringExtra("Foodid");
        //init view
        numberButton=(ElegantNumberButton)findViewById(R.id.number_button);
        btnCart=(FloatingActionButton)findViewById(R.id.btncart);


        btnCart=(FloatingActionButton)findViewById(R.id.btncart);
        food_name=(TextView)findViewById(R.id.food_name);
        food_description=(TextView)findViewById(R.id.food_description);
        food_price=(TextView)findViewById(R.id.food_price);
        food_quantity=(TextView)findViewById(R.id.food_Quantity);

        food_image=(ImageView)findViewById(R.id.img_food);
        collapsingToolbarLayout=(CollapsingToolbarLayout)findViewById(R.id.collapsing);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppbar);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppbar);


        if(getIntent() !=null)
        {
            foodpricestr = getIntent().getStringExtra("FoodPrice");
            foodquantitystr=getIntent().getStringExtra("FoodQuantity");
            fooddescriptionstr=getIntent().getStringExtra("FoodDescribtion");
            food_name.setText("Name: "+getIntent().getStringExtra("FoodName"));
            food_description.setText("Description: "+getIntent().getStringExtra("FoodDescribtion"));
            food_price.setText("Price = "+getIntent().getStringExtra("FoodPrice"));
            food_quantity.setText("Quantity: "+getIntent().getStringExtra("FoodQuantity"));
            Picasso.get().load(getIntent().getStringExtra("FoodImage")).into(food_image);
            foodnamestr=food_name.getText().toString();
            collapsingToolbarLayout.setTitle(foodnamestr);
        }
        foodpriceint=Integer.parseInt(foodpricestr);
        foodquantityint=Integer.parseInt(foodquantitystr);







        btnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                HashMap<String,Object> foodmap=new HashMap<>();
                foodmap.put("Description",fooddescriptionstr);
                foodmap.put("Quantity",numberButton.getNumber());
                foodmap.put("Name",foodnamestr);
                foodmap.put("Price",foodpricestr);
                //foodmap.put("Image",food_image);

                cartref.child("Cart").child(foodnamestr).updateChildren(foodmap).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful())
                        {
                            Toast.makeText(getApplicationContext(),"Item Added to Cart",Toast.LENGTH_LONG).show();
                            Intent i=new Intent(FoodDetails.this,ADD_Cart.class);
                            startActivity(i);
                        }
                        else  {
                            Toast.makeText(getApplicationContext(),"Failed ",Toast.LENGTH_LONG).show();
                            Toast.makeText(getApplicationContext(),"Failed To Add to Cart",Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }
        });

    }
}
