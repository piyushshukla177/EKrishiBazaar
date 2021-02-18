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

import com.service.ekrishibazaar.CattleDetailsActivity;
import com.service.ekrishibazaar.R;
import com.service.ekrishibazaar.ServiceAdsDetailActivity;
import com.service.ekrishibazaar.model.CattleAdsModel;
import com.service.ekrishibazaar.model.ServiceAdsModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ServiceAdsAdapter extends RecyclerView.Adapter<ServiceAdsAdapter.ServiceViewHolder> {
    private ArrayList<ServiceAdsModel> category_list;
    private Context context;


    public class ServiceViewHolder extends RecyclerView.ViewHolder {
        public TextView address_textview, price_textview;
        ImageView category_image;
        CardView cardview;

        public ServiceViewHolder(View itemView) {
            super(itemView);
            category_image = itemView.findViewById(R.id.category_image);
            address_textview = itemView.findViewById(R.id.address_textview);
            price_textview = itemView.findViewById(R.id.price_textview);
            cardview = itemView.findViewById(R.id.cardview);
//            category_image = itemView.findViewById(R.id.category_image);
//            service_name_textview = itemView.findViewById(R.id.service_name_textview);
//            service_rate_textview = itemView.findViewById(R.id.service_rate_textview);
//            state_textview = itemView.findViewById(R.id.state_textview);
//            district_textview = itemView.findViewById(R.id.district_textview);
//            block_textview = itemView.findViewById(R.id.block_textview);
//            posted_on_textview = itemView.findViewById(R.id.posted_on_textview);
//            view_add_btn = itemView.findViewById(R.id.view_add_btn);
        }
    }

    public ServiceAdsAdapter(Context context, ArrayList<ServiceAdsModel> list) {
        category_list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ServiceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cattle_ads_layout, parent, false);
        ServiceViewHolder evh = new ServiceViewHolder(v);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull final ServiceViewHolder holder, int i) {
        final ServiceAdsModel currentItem = category_list.get(i);
        Picasso.get().load(currentItem.getProduct_image1()).fit().into(holder.category_image);
//        Picasso.get().load(currentItem.getProduct_image1()).resize(60, 60).into(holder.category_image);
        holder.address_textview.setText(currentItem.getBlock() + ", " + currentItem.getDistrict());
        holder.price_textview.setText("Price  " + "₹ " + currentItem.getPrice());
        holder.cardview.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, ServiceAdsDetailActivity.class);
                        intent.putExtra("category_type", "agricultural");
                        intent.putExtra("image1", currentItem.getProduct_image1());
                        intent.putExtra("image2", currentItem.getProduct_image2());
                        intent.putExtra("image3", currentItem.getProduct_image3());
                        intent.putExtra("profile_image", currentItem.getProfile_image());
                        intent.putExtra("user_first_name", currentItem.getUser_first_name());
                        intent.putExtra("user_last_name", currentItem.getUser_last_name());
                        intent.putExtra("vid", currentItem.getVid());
                        intent.putExtra("joined_on", currentItem.getDate_joined());
                        intent.putExtra("mobile", currentItem.getMobile_number());
                        intent.putExtra("state", currentItem.getState());
                        intent.putExtra("district", currentItem.getDistrict());
                        intent.putExtra("block", currentItem.getBlock());
                        intent.putExtra("village", currentItem.getVillage_name());
                        intent.putExtra("machine_type", currentItem.getService_machine_name());
                        intent.putExtra("work", currentItem.getWork_name());
                        intent.putExtra("reaching_on_time", currentItem.getReaching_on_time());
                        intent.putExtra("price", currentItem.getPrice());
                        intent.putExtra("additional_details", currentItem.getAdditional_info());
                        intent.putExtra("post_id", currentItem.getPost_id());
                        intent.putExtra("posted_on", currentItem.getPosted_on());
                        intent.putExtra("verified", currentItem.getIsverified());
                        context.startActivity(intent);
                    }
                }
        );
    }

    @Override
    public int getItemCount()
    {
        return category_list.size();
    }

    public void filterList(ArrayList<ServiceAdsModel> filteredList) {
        category_list = filteredList;
        notifyDataSetChanged();
    }
}
