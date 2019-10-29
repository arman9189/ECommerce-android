package com.example.jimmy.e_commerce;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.anychart.AnyChart;
import com.anychart.AnyChartView;
import com.anychart.chart.common.dataentry.DataEntry;
import com.anychart.chart.common.dataentry.ValueDataEntry;
import com.anychart.charts.Pie;
import com.example.jimmy.e_commerce.Models.Chart;
import com.example.jimmy.e_commerce.Models.Customer;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class order_chart extends AppCompatActivity {

    private String userid;
    private FirebaseDatabase myfirebasedatabase;
    DatabaseReference myref;
    ArrayList<Chart> cc=new ArrayList<Chart>();
    Button showch;
    private FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_chart);
        showch=(Button)findViewById(R.id.showch) ;
        auth = FirebaseAuth.getInstance();
        myfirebasedatabase=FirebaseDatabase.getInstance();
        //get current user
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        userid=user.getUid();
        myref=FirebaseDatabase.getInstance().getReference().child("users").child(userid).child("Order");

        myref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                for(DataSnapshot ds:dataSnapshot.getChildren())
                {
                    Chart c = ds.getValue( Chart.class );
                    cc.add( c );


                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



        showch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Pie pie = AnyChart.pie();
                List<DataEntry> data = new ArrayList<>();
                for (int i=0;i<cc.size();i++) {
                    int k=0;
                    k=Integer.parseInt(cc.get(i).getTotalPrice());
                    data.add(new ValueDataEntry(cc.get(i).getID(), k));

                }
                pie.data(data);

                AnyChartView anyChartView = (AnyChartView) findViewById(R.id.any_chart_view);
                anyChartView.setChart(pie);
            }
        });

    }
}
