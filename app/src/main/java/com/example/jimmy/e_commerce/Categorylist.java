package com.example.jimmy.e_commerce;

import android.content.Intent;
import android.speech.RecognizerIntent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jimmy.e_commerce.Adapter.categoryadapter;
import com.example.jimmy.e_commerce.Models.Categories;
import com.example.jimmy.e_commerce.ViewHolder.CategoryViewHolder;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class Categorylist extends AppCompatActivity {

    RecyclerView recyclerView;
///////search by text
    EditText searchcat;

    ArrayList<Categories> arrayList;
    ImageButton voicebtn,cameraimg;
    int voicecode=1;
    //////
    DatabaseReference databaseReference,serchref;
    FirebaseRecyclerOptions<Categories>options;
    FirebaseRecyclerAdapter<Categories,CategoryViewHolder>adapter;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==voicecode && resultCode==RESULT_OK)
        {
            ArrayList<String>text=data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

            searchcat= (EditText) findViewById(R.id.searchcat);
            searchcat.setText(text.get(0));

        }
        else {
            searchcat= (EditText) findViewById(R.id.searchcat);
            IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
            if (result != null) {
                if (result.getContents() == null) {
                    Toast.makeText(this, "Cancelled", Toast.LENGTH_LONG).show();
                } else {
                    searchcat.setText(result.getContents());
                    //Toast.makeText(this, "Scanned: " + result.getContents(), Toast.LENGTH_LONG).show();
                }
            } else {
                super.onActivityResult(requestCode, resultCode, data);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categorylist);

        arrayList=new ArrayList<>();



        searchcat= (EditText) findViewById(R.id.searchcat);
        searchcat.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                    search(s.toString());


            }
        });
        //firebase
        databaseReference=FirebaseDatabase.getInstance().getReference().child("Category");

        options = new FirebaseRecyclerOptions.Builder<Categories>()
                .setQuery(databaseReference,Categories.class).build();

        recyclerView=(RecyclerView)findViewById(R.id.catlist);
        recyclerView.setHasFixedSize(true);

        adapter=new FirebaseRecyclerAdapter<Categories, CategoryViewHolder>(options) {
            @Override
            protected void onBindViewHolder(CategoryViewHolder holder, final int position, final Categories model) {
                holder.txtcategoryName.setText(model.getName());
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent i=new Intent(Categorylist.this,Foodlist.class);
                        i.putExtra("categoryid",model.getID());
                        Toast.makeText(getApplicationContext(),model.getName()+" Category",Toast.LENGTH_LONG).show();
                        startActivity(i);
                    }
                });

                Picasso.get().load(model.getImage()).into(holder.imageView,new Callback() {
                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError(Exception e) {


                    }
                });

            }

            @NonNull
            @Override
            public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View view=LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.category_item,viewGroup,false);
                return new CategoryViewHolder(view);
            }
        };

        GridLayoutManager gridLayoutManager=new GridLayoutManager(getApplicationContext(),1);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(adapter);

        voicebtn=(ImageButton)findViewById(R.id.imageButton);
        cameraimg=(ImageButton)findViewById(R.id.cameraimg);
        voicebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                startActivityForResult(i,voicecode);
            }
        });
        cameraimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new IntentIntegrator(Categorylist.this).initiateScan();

            }
        });

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



    private void search(String s) {
        String k=s.toUpperCase();
        serchref=FirebaseDatabase.getInstance().getReference().child("Category");
        Query query=serchref.orderByChild("Name")
                .startAt(k)
                .endAt("\uf8ff"+k);

        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChildren())
                {
                    arrayList.clear();
                    for(DataSnapshot dss:dataSnapshot.getChildren())
                    {
                        final Categories categoryitem=dss.getValue(Categories.class);
                        arrayList.add(categoryitem);
                    }

                    categoryadapter categoryadapter=new categoryadapter(getApplicationContext(),arrayList);
                    recyclerView.setAdapter(categoryadapter);
                    categoryadapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
