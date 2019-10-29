package com.example.jimmy.e_commerce;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Properties;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import android.widget.TextView;
import android.widget.Toast;

import com.amulyakhare.textdrawable.TextDrawable;
import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.jimmy.e_commerce.Mail.SendMail;
import com.example.jimmy.e_commerce.Maps.MapActivity;
import com.example.jimmy.e_commerce.Models.Orders;
import com.example.jimmy.e_commerce.ViewHolder.CartViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class ADD_Cart extends AppCompatActivity {


    private static final String TAG = "ADD_Cart";
    private static final int ERROR_Dialogue_Request=9001;

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;


     Dialog myDialog;
     int totalprice=0;
    private FirebaseAuth auth;
    private FirebaseDatabase myfirebasedatabase;
    private String userid;
    TextView addressview;
    Button btnPlace,btnsubmit,showtot;
    Button close,ok;
     ElegantNumberButton numberButton;
       public String FOODNAME,editQuantity,addressstr,email,currentDate,currenttime;
    DatabaseReference myref,orderref;
    FirebaseRecyclerOptions<Orders> options;
    FirebaseRecyclerAdapter<Orders,CartViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add__cart);


        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(ADD_Cart.this);
       email= sharedPref.getString("email", "sss");
        //email = getIntent().getStringExtra("email");
        myDialog=new Dialog(this);
        //get firebase auth instance
        auth = FirebaseAuth.getInstance();
        myfirebasedatabase=FirebaseDatabase.getInstance();
        //get current user
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        userid=user.getUid();
        //firebase

        myref=FirebaseDatabase.getInstance().getReference().child("users").child(userid).child("Cart");
        orderref=FirebaseDatabase.getInstance().getReference().child("users").child(userid);




        //init
        recyclerView=(RecyclerView)findViewById(R.id.listCart);
        recyclerView.setHasFixedSize(true);
        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        btnPlace=(Button) findViewById(R.id.btnplaceorder);
        btnsubmit=(Button) findViewById(R.id.btnsubmitorder);
        showtot=(Button) findViewById(R.id.showtot);
        options = new FirebaseRecyclerOptions.Builder<Orders>()
                .setQuery(myref,Orders.class).build();


        adapter=new FirebaseRecyclerAdapter<Orders,CartViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final CartViewHolder holder, final int position, @NonNull final Orders model) {

                holder.txtproductname.setText(model.getName());
                holder.txtproductprice.setText(model.getPrice()+" $");


                editQuantity=model.getQuantity();
                TextDrawable drawable=TextDrawable.builder()
                        .buildRound(model.getQuantity(),Color.RED);
                holder.imageView.setImageDrawable(drawable);

                holder.deletebtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                          FOODNAME=model.getName();
                            DatabaseReference deleteref = FirebaseDatabase.getInstance().getReference().child("users").child(userid).child("Cart").child(FOODNAME);
                          deleteref.removeValue();
                            Toast.makeText(ADD_Cart.this, FOODNAME+"deleted", Toast.LENGTH_LONG).show();
                        Intent i=new Intent(ADD_Cart.this,ADD_Cart.class);
                        startActivity(i);
                        finish();
                    }
                });
                holder.editbtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        FOODNAME=model.getName();
                        myDialog.setContentView(R.layout.editbutton);
                        numberButton =(ElegantNumberButton) myDialog.findViewById(R.id.number_button);
                        ok = (Button) myDialog.findViewById(R.id.confirm);
                        close = (Button) myDialog.findViewById(R.id.caancel);

                        ok.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                editQuantity= numberButton.getNumber();
                                model.setQuantity(editQuantity);
                                TextDrawable drawable2=TextDrawable.builder()
                                        .buildRound(model.getQuantity(),Color.RED);
                                holder.imageView.setImageDrawable(drawable2);

                                Log.d("ABCD", editQuantity+" "+FOODNAME+" "+userid.toString());

                                DatabaseReference editref = FirebaseDatabase.getInstance().getReference().child("users").child(userid).child("Cart").child(FOODNAME).child("Quantity");
                                editref.setValue(editQuantity);
                                //int k=(Integer.parseInt(primequantity))-(Integer.parseInt(editQuantity));
                                //DatabaseReference editqua = FirebaseDatabase.getInstance().getReference().child("Products").child(FOODNAME).child("Quantity");
                                //editqua.setValue(String.valueOf(k));
                                myDialog.dismiss();
                                Intent i=new Intent(ADD_Cart.this,ADD_Cart.class);
                                startActivity(i);
                                finish();
                            }
                        });

                        close.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                myDialog.dismiss();
                            }
                        });
                        myDialog.show();
                        }
                });
                totalprice+=(Integer.parseInt(model.getPrice()))*(Integer.parseInt(model.getQuantity()));


            }




            @NonNull
            @Override
            public CartViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View view=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cart_layout,viewGroup,false);


                return new CartViewHolder(view);
            }

        };

        GridLayoutManager gridLayoutManager=new GridLayoutManager(getApplicationContext(),1);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(adapter);


        btnPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isSERVICEOK()){
                    Intent intent=new Intent(ADD_Cart.this,MapActivity.class);
                    startActivity(intent);
                    finish();

                }
            }
        });

        if(getIntent() !=null)
        {
            addressstr = getIntent().getStringExtra("address");

        }


        addressview=(TextView)findViewById(R.id.addressview);
        addressview.setText(addressstr);

        btnsubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((TextUtils.isEmpty(addressstr))) {
                    Toast.makeText(ADD_Cart.this,"Please PLace The Order",Toast.LENGTH_LONG).show();
                }
                else if(totalprice ==0)
                {
                    Toast.makeText(ADD_Cart.this,"Please Add Products to ur cart",Toast.LENGTH_LONG).show();
                }
                else {

                    Calendar calendar = Calendar.getInstance();
                    currentDate = DateFormat.getDateInstance(DateFormat.FULL).format(calendar.getTime());
                    SimpleDateFormat format=new SimpleDateFormat("HH:mm:ss");
                    currenttime=format.format(calendar.getTime());

                        String yalhwy=String.valueOf(totalprice);
                    HashMap<String,Object> ordermap=new HashMap<>();
                    ordermap.put("ID",currentDate+"   "+currenttime);
                    ordermap.put("Date",currentDate);
                    ordermap.put("Time",currenttime);
                    ordermap.put("TotalPrice",yalhwy);
                    ordermap.put("Location",addressstr);
                    orderref.child("Order").child(currentDate+"   "+currenttime).updateChildren(ordermap).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful())
                            {
                                Toast.makeText(getApplicationContext(),"Submitted Cart successfully",Toast.LENGTH_LONG).show();
                            }
                            else  {

                               Toast.makeText(getApplicationContext(),"Failed To Submit Cart",Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                   sendEmail();
                   startActivity(new Intent(ADD_Cart.this,order_chart.class));
                   finish();
                }
            }
        });
        showtot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ADD_Cart.this,"Your Total price: "+totalprice,Toast.LENGTH_LONG).show();
            }
        });

    }

    ////googlemaps
    public boolean  isSERVICEOK(){
        Log.d(TAG,"isSERVICEOK:checking google services version");
        int avalible=GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(ADD_Cart.this);
        if(avalible==ConnectionResult.SUCCESS) {
            /////everything is ok and user can make map requests
            Log.d(TAG, "isSERVICEOK: Google Play Services is Working");
            return true;
        }
        else if(GoogleApiAvailability.getInstance().isUserResolvableError(avalible)){
            ////an error occured but we can fix it
            Log.d(TAG, "isSERVICEOK: an error occured but we can fix it");
            Dialog dialog=GoogleApiAvailability.getInstance().getErrorDialog(ADD_Cart.this,avalible,ERROR_Dialogue_Request);
            dialog.show();
        }
        else {
           Toast.makeText(this,"You cant make up requsets",Toast.LENGTH_LONG).show();
        }
        return false;
    }


    @Override
    protected void onStart() {
        super.onStart();
        if(adapter!=null)
            adapter.startListening();
    }

    @Override
    protected void onStop() {
        if (adapter!=null)
            adapter.stopListening();
        super.onStop();
    }

    private void sendEmail() {
        //Getting content for email
        String email2=email;
        String subject = "E-commerce APP Food Order";
        String message ="Hello there!" +"\n"+
                "You Have been orderd Products from Our App " +"\n"+
                "We hope U Enjoying Using Our App" +"\n"+
                "IF u have any issues we are Pleased to recieve it on " +"\n"+
                "Mohamedgamalfcis@gmail.com";


        //Creating SendMail object
        SendMail sm = new SendMail(this, email2, subject, message);

        //Executing sendmail to send email
        sm.execute();
    }


}
