package com.example.music;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AdapterShop extends RecyclerView.Adapter<AdapterShop.Holdershop> {
    private Context context;
    public ArrayList<ModelSeller> sellerList;

    public AdapterShop(Context context, ArrayList<ModelSeller> sellerList) {
        this.context = context;
        this.sellerList = sellerList;
    }

    @NonNull
    @Override
    public Holdershop onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(context).inflate(R.layout.row_shop,parent,false);
        return new Holdershop(view);

    }

    @Override
    public void onBindViewHolder(@NonNull Holdershop holder, int position) {
        ModelSeller modelSeller = sellerList.get(position);
        String uid = modelSeller.getUid();
        String username = modelSeller.getUsername();

        holder.sellername.setText(username);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context,Buyer_dashboard.class);
                intent.putExtra("shopUid",uid);
                context.startActivity(intent);

            }
        });


    }

    @Override
    public int getItemCount() {
        return sellerList.size();
    }

    class Holdershop extends RecyclerView.ViewHolder {
        TextView sellername;

        public Holdershop(@NonNull View itemView) {
            super(itemView);
            sellername = itemView.findViewById(R.id.sellername);
        }


    }

}
