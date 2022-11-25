package com.example.music;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class FirstBuyerDashboard extends AppCompatActivity {
    TextView tabSellersTv,tabOrdersTv;
    RelativeLayout sellersRl,ordersRl;
    //firebase instance
    FirebaseAuth firebaseAuth;
    RecyclerView shopsRv,ordersRv;
    ArrayList <ModelSeller> sellerList;
    private AdapterShop adapterShop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_buyer_dashboard);
        tabSellersTv = findViewById(R.id.tabSellersTv);
        sellersRl = findViewById(R.id.sellersRl);
        ordersRl = findViewById(R.id.ordersRl);
        shopsRv = findViewById(R.id.shopsRv);

        //initialise firebse instance
        firebaseAuth = FirebaseAuth.getInstance();

        checkUser();
        showSellersUI();

        tabSellersTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showSellersUI();
            }
        });

    }

    private void showSellersUI() {
        sellersRl.setVisibility(View.VISIBLE);
        ordersRl.setVisibility(View.GONE);
        tabSellersTv.setTextColor(getResources().getColor(R.color.black));
        tabSellersTv.setBackgroundResource(R.drawable.layout);
//
//        tabOrdersTv.setTextColor(getResources().getColor(R.color.white));
//        tabOrdersTv.setBackgroundColor(getResources().getColor(android.R.color.transparent));
    }
    private void showOrdersUI() {
        sellersRl.setVisibility(View.GONE);
        ordersRl.setVisibility(View.VISIBLE);
        tabOrdersTv.setTextColor(getResources().getColor(R.color.black));
        tabOrdersTv.setBackgroundResource(R.drawable.layout);

        tabSellersTv.setTextColor(getResources().getColor(R.color.white));
        tabSellersTv.setBackgroundColor(getResources().getColor(android.R.color.transparent));
    }

    private void checkUser() {
        FirebaseUser user = firebaseAuth.getCurrentUser();
        if(user==null){
            Intent i = new Intent(getApplicationContext(),login.class);
            startActivity(i);
            finish();
        }else{
            loadInfo();
        }
    }
    private void loadInfo() {
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users");
        ref.orderByChild("uid").equalTo(firebaseAuth.getUid())
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                        for(DataSnapshot ds: datasnapshot.getChildren()){
                            // String name = ""+ds.child("username").getValue();

                            Toast.makeText(FirstBuyerDashboard.this, "Info", Toast.LENGTH_SHORT).show();
                            loadShops();
//                            loadOrders();


                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }
    //load orders from DB
//    private void loadOrders() {
//        ordersList = new ArrayList<>();
//        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users");
//        ref.addValueEventListener(new ValueEventListener()
//        {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot datasnapshot) {
//                ordersList.clear();
//                for(DataSnapshot ds:datasnapshot.getChildren()){
//                    String uid = ds.getRef().getKey();
//                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users").child(uid).child("Orders");
//                    ref.orderByChild("orderBy").equalTo(firebaseAuth.getUid())
//
//                            .addValueEventListener(new ValueEventListener() {
//                                @Override
//                                public void onDataChange(@NonNull DataSnapshot datasnapshot) {
//                                    if(datasnapshot.exists()){
//                                        for(DataSnapshot ds: datasnapshot.getChildren()){
//                                            ModelOrderUser modelOrderUser = ds.getValue(ModelOrderUser.class);
//                                            ordersList.add(modelOrderUser);
//                                        }
//                                        adapterOrderUser = new AdapterOrderUser(FirstBuyerDashboard.this,ordersList);
//                                        ordersRv.setAdapter(adapterOrderUser);
//
//                                    }
//                                }
//
//                                @Override
//                                public void onCancelled(@NonNull DatabaseError error) {
//
//                                }
//                            });
//
//                }
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//
//    }

    //load available sellers from DB
    private void loadShops() {

        sellerList = new ArrayList<>();
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Users");
        ref.orderByChild("accounttype").equalTo("Producer")
                .addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot datasnapshot) {
                        sellerList.clear();
                        for(DataSnapshot ds:datasnapshot.getChildren()){
                            ModelSeller modelSeller = ds.getValue(ModelSeller.class);
                            sellerList.add(modelSeller);
                        }
                        adapterShop = new AdapterShop(FirstBuyerDashboard.this,sellerList);
                        shopsRv.setAdapter(adapterShop);


                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
    }

}