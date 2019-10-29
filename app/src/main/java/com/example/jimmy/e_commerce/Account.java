package com.example.jimmy.e_commerce;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.jimmy.e_commerce.Models.Customer;
import com.google.firebase.auth.FirebaseAuth;

import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static android.content.ContentValues.TAG;

public class Account extends Activity {

    private Button signOut,showallcat,cart,oredr;
    private ProgressBar progressBar;
    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseAuth auth;
    private FirebaseDatabase myfirebasedatabase;
    private DatabaseReference myref;
    private String userid;
    private TextView name,username,gender,jop,email,birthdate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        //get firebase auth instance
        auth = FirebaseAuth.getInstance();
        myfirebasedatabase=FirebaseDatabase.getInstance();
        //get current user
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            userid=user.getUid();
        Log.i(TAG, "onCreate: "+userid);
        myref=myfirebasedatabase.getReference("users").child(userid);

        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    // user auth state is changed - user is null
                    // launch login activity
                    startActivity(new Intent(Account.this, LoginActivity.class));
                    finish();
                }
            }
        };

        name=(TextView)findViewById(R.id.namev);
        username=(TextView)findViewById(R.id.unamev);
        gender=(TextView)findViewById(R.id.genderv);
        jop=(TextView)findViewById(R.id.jopv);
        email=(TextView)findViewById(R.id.emailv);
        birthdate=(TextView)findViewById(R.id.bdatev);
        signOut = (Button) findViewById(R.id.signoutbtn);
        showallcat = (Button) findViewById(R.id.showall);
        oredr = (Button) findViewById(R.id.oredr);
        cart=(Button)findViewById(R.id.cartnavigate);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        if (progressBar != null) {
            progressBar.setVisibility(View.GONE);
        }

        showallcat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent i=new Intent(Account.this,Categorylist.class);
                startActivity(i);
            }
        });
        oredr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(Account.this,order_chart.class);
                startActivity(i);
            }
        });
        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent r=new Intent(Account.this,ADD_Cart.class);
                //r.putExtra("email",email.getText().toString());
                SharedPreferences reqcode = PreferenceManager.getDefaultSharedPreferences(Account.this);
                //now get Editor
                SharedPreferences.Editor editorreqcode = reqcode.edit();
                //put your value
                String info=email.getText().toString();
                editorreqcode.putString("email",info);

                //commits your edits
                editorreqcode.commit();

                startActivity(r);
            }
        });

        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signOut();
            }
        });



        myref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                showData(dataSnapshot);
            }



            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
    private void showData(DataSnapshot ds) {
            Customer cust=new Customer();
            cust.setCus_Username(ds.getValue(Customer.class).getCus_Username());
            cust.setCus_name(ds.getValue(Customer.class).getCus_name());
            cust.setCus_Job(ds.getValue(Customer.class).getCus_Job());
            cust.setCus_Gender(ds.getValue(Customer.class).getCus_Gender());
            cust.setCus_Email(ds.getValue(Customer.class).getCus_Email());
            cust.setCus_Birthdate(ds.getValue(Customer.class).getCus_Birthdate());
            Log.d(TAG, "showData: Uname: "+cust.getCus_Username());
            Log.d(TAG, "showData: name: "+cust.getCus_name());
            Log.d(TAG, "showData: Job: "+cust.getCus_Job());
            Log.d(TAG, "showData: Gender: "+cust.getCus_Gender());
            Log.d(TAG,"showData: E-Mail: "+cust.getCus_Email());
            Log.d(TAG,"showData: Birthdate:"+cust.getCus_Birthdate());

            name.setText(cust.getCus_name());
            username.setText(cust.getCus_Username());
            gender.setText(cust.getCus_Gender());
            jop.setText(cust.getCus_Job());
            email.setText(cust.getCus_Email());
            birthdate.setText(cust.getCus_Birthdate());
        }


    //sign out method
    public void signOut() {
        auth.signOut();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //progressBar.setVisibility(View.GONE);
    }

    @Override
    public void onStart() {
        super.onStart();
        auth.addAuthStateListener(authListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (authListener != null) {
            auth.removeAuthStateListener(authListener);
        }
    }
}
