package com.ekrishibazaar.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.ekrishibazaar.MyAdsDetail;
import com.ekrishibazaar.R;
import com.ekrishibazaar.ViewMyOfferAdsActivity;
import com.ekrishibazaar.model.MyOffersModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MyOffersAdapter extends RecyclerView.Adapter<MyOffersAdapter.MyOffersViewHolder> {
    private ArrayList<MyOffersModel> my_offer_list;
    private Context context;

    public class MyOffersViewHolder extends RecyclerView.ViewHolder {
        public TextView offer_by_tv, offer_price_tv, contact_number_tv;
        ImageView profile_imageview;
        CardView cardview;
        LinearLayout view_ads_linear;

        public MyOffersViewHolder(View itemView) {
            super(itemView);
            profile_imageview = itemView.findViewById(R.id.profile_imageview);
            offer_by_tv = itemView.findViewById(R.id.offer_by_tv);
            offer_price_tv = itemView.findViewById(R.id.offer_price_tv);
            contact_number_tv = itemView.findViewById(R.id.contact_number_tv);
            cardview = itemView.findViewById(R.id.cardview);
            view_ads_linear = itemView.findViewById(R.id.view_ads_linear);
        }
    }

    public MyOffersAdapter(Context context, ArrayList<MyOffersModel> list) {
        my_offer_list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public MyOffersAdapter.MyOffersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.my_offers_layout, parent, false);
        MyOffersAdapter.MyOffersViewHolder evh = new MyOffersAdapter.MyOffersViewHolder(v);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull final MyOffersAdapter.MyOffersViewHolder holder, int i) {
        final MyOffersModel currentItem = my_offer_list.get(i);
        Picasso.get().load(currentItem.getProfile_image_url()).fit().into(holder.profile_imageview);
        holder.offer_by_tv.setText(currentItem.getOffer_by());
        holder.offer_price_tv.setText("₹ " + currentItem.getOffer_price());
        holder.contact_number_tv.setText(currentItem.getContact_number());
        holder.view_ads_linear.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, ViewMyOfferAdsActivity.class);
                        intent.putExtra("post_id", currentItem.getPost_id());
                        context.startActivity(intent);
                    }
                }
        );
    }

    @Override
    public int getItemCount() {
        return my_offer_list.size();
    }

    public void filterList(ArrayList<MyOffersModel> filteredList) {
        my_offer_list = filteredList;
        notifyDataSetChanged();
    }
}
