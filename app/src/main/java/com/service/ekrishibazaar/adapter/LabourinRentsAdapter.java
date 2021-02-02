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
import com.service.ekrishibazaar.LabourAdDetailsActivity;
import com.service.ekrishibazaar.R;
import com.service.ekrishibazaar.model.CattleAdsModel;
import com.service.ekrishibazaar.model.LabourInRentModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class LabourinRentsAdapter extends RecyclerView.Adapter<com.service.ekrishibazaar.adapter.LabourinRentsAdapter.LabourinRentsViewHolder> {
    private ArrayList<LabourInRentModel> category_list;
    private Context context;

    public class LabourinRentsViewHolder extends RecyclerView.ViewHolder {
//      public TextView labour_expertise_textview, available_hour_textview, state_textview, district_textview, block_textview, posted_on_textview;
//      ImageView category_image;
//      Button view_ads_btn;
        public TextView address_textview, price_textview;
        ImageView category_image;
        CardView cardview;

        public LabourinRentsViewHolder(View itemView) {
            super(itemView);
            category_image = itemView.findViewById(R.id.category_image);
            address_textview = itemView.findViewById(R.id.address_textview);
            price_textview = itemView.findViewById(R.id.price_textview);
            cardview = itemView.findViewById(R.id.cardview);
//            category_image = itemView.findViewById(R.id.category_image);
//            labour_expertise_textview = itemView.findViewById(R.id.labour_expertise_textview);
//            available_hour_textview = itemView.findViewById(R.id.available_hour_textview);
//            state_textview = itemView.findViewById(R.id.state_textview);
//            district_textview = itemView.findViewById(R.id.district_textview);
//            block_textview = itemView.findViewById(R.id.block_textview);
//            posted_on_textview = itemView.findViewById(R.id.posted_on_textview);
//            view_ads_btn = itemView.findViewById(R.id.view_ads_btn);
        }
    }

    public LabourinRentsAdapter(Context context, ArrayList<LabourInRentModel> list) {
        category_list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public LabourinRentsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cattle_ads_layout, parent, false);
        LabourinRentsViewHolder evh = new LabourinRentsViewHolder(v);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull final LabourinRentsViewHolder holder, int i) {
        final LabourInRentModel currentItem = category_list.get(i);
        Picasso.get().load(currentItem.getProduct_image1()).resize(60, 60).into(holder.category_image);
//        Picasso.get().load(currentItem.getProduct_image1()).resize(60, 60).into(holder.category_image);
//        Picasso.get().load(currentItem.getProduct_image1()).resize(60, 60).into(holder.category_image);
        holder.address_textview.setText(currentItem.getBlock() + ", " + currentItem.getDistrict());
        holder.price_textview.setText("Price  " + "₹ " + currentItem.getPrice());
//        holder.labour_expertise_textview.setText(currentItem.getExpertise() + " : " + currentItem.getPrice() + "/" + currentItem.getPrice_by());
//        holder.state_textview.setText("State : " + currentItem.getState());
//        holder.state_textview.setText("District : " + currentItem.getDistrict());
//        holder.district_textview.setText("Block : " + currentItem.getBlock());
//        holder.block_textview.setText("Village " + currentItem.getVillage_name());
//        holder.posted_on_textview.setText("Posted On : " + currentItem.getPosted_on());
        holder.cardview.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, LabourAdDetailsActivity.class);
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
                        intent.putExtra("labour_expertise", currentItem.getExpertise());
                        intent.putExtra("available_hour", currentItem.getAvailable_labour());
                        intent.putExtra("reaching_time_status_tv", currentItem.getReaching_time_status());
                        intent.putExtra("price", currentItem.getPrice());
                        intent.putExtra("post_id", currentItem.getPost_id());
                        intent.putExtra("additional_details", currentItem.getAdditional_information());
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


    public void filterList(ArrayList<LabourInRentModel> filteredList) {
        category_list = filteredList;
        notifyDataSetChanged();
    }
}