package com.service.ekrishibazaar.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.service.ekrishibazaar.MyAdsDetail;
import com.service.ekrishibazaar.PostSellAdsActivity;
import com.service.ekrishibazaar.R;
import com.service.ekrishibazaar.fragments.MyAddsFragment;
import com.service.ekrishibazaar.model.MyAdsModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MyAdsAdapter extends RecyclerView.Adapter<MyAdsAdapter.AgricultureViewHolder> {
    private ArrayList<MyAdsModel> category_list;
    private Context context;

    public class AgricultureViewHolder extends RecyclerView.ViewHolder {
        public TextView address_textview, price_textview;
        ImageView category_image;
        CardView cardview;
        Button edit_btn, sold_btn, delete_btn;

        public AgricultureViewHolder(View itemView) {
            super(itemView);
            category_image = itemView.findViewById(R.id.category_image);
            address_textview = itemView.findViewById(R.id.address_textview);
            price_textview = itemView.findViewById(R.id.price_textview);
            cardview = itemView.findViewById(R.id.cardview);
            edit_btn = itemView.findViewById(R.id.edit_btn);
            sold_btn = itemView.findViewById(R.id.sold_btn);
            delete_btn = itemView.findViewById(R.id.delete_btn);
        }
    }

    public MyAdsAdapter(Context context, ArrayList<MyAdsModel> list) {
        category_list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public MyAdsAdapter.AgricultureViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_ads_layout, parent, false);
        MyAdsAdapter.AgricultureViewHolder evh = new MyAdsAdapter.AgricultureViewHolder(v);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyAdsAdapter.AgricultureViewHolder holder, int i) {
        final MyAdsModel currentItem = category_list.get(i);
        Picasso.get().load(currentItem.getProduct_image1()).resize(60, 60).into(holder.category_image);
//      Picasso.get().load(currentItem.getProduct_image1()).resize(60, 60).into(holder.category_image);
        holder.address_textview.setText(currentItem.getBlock() + ", " + currentItem.getDistrict());
        holder.price_textview.setText("Price  " + "₹ " + currentItem.getPrice());
        holder.edit_btn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, PostSellAdsActivity.class);
                        intent.putExtra("super_category", "edit");
                        intent.putExtra("post_id", currentItem.getPost_id());
                        context.startActivity(intent);
                    }
                }
        );
        holder.delete_btn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MyAddsFragment.mmm.DeleteAds(currentItem.getPost_id());
                    }
                }
        );
        holder.sold_btn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MyAddsFragment.mmm.MarkAsSold(currentItem.getPost_id());
                    }
                }
        );

        holder.cardview.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, MyAdsDetail.class);
                        intent.putExtra("post_id", currentItem.getPost_id());
                        context.startActivity(intent);
                    }
                }
        );
    }

    @Override
    public int getItemCount() {
        return category_list.size();
    }

    public void filterList(ArrayList<MyAdsModel> filteredList) {
        category_list = filteredList;
        notifyDataSetChanged();
    }
}
