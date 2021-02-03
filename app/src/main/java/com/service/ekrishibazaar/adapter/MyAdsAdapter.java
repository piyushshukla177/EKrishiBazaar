package com.service.ekrishibazaar.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.service.ekrishibazaar.AdaDetailsActivity;
import com.service.ekrishibazaar.MyAdsDetail;
import com.service.ekrishibazaar.R;
import com.service.ekrishibazaar.model.AgricultureAdsModel;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;

    public class MyAdsAdapter extends RecyclerView.Adapter<MyAdsAdapter.AgricultureViewHolder> {
        private ArrayList<AgricultureAdsModel> category_list;
        private Context context;

        public class AgricultureViewHolder extends RecyclerView.ViewHolder {
            public TextView address_textview, price_textview;
            ImageView category_image;
            CardView cardview;

            public AgricultureViewHolder(View itemView) {
                super(itemView);
                category_image = itemView.findViewById(R.id.category_image);
                address_textview = itemView.findViewById(R.id.address_textview);
                price_textview = itemView.findViewById(R.id.price_textview);
                cardview = itemView.findViewById(R.id.cardview);
            }
        }

        public MyAdsAdapter(Context context, ArrayList<AgricultureAdsModel> list) {
            category_list = list;
            this.context = context;
        }

        @NonNull
        @Override
        public MyAdsAdapter.AgricultureViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cattle_ads_layout, parent, false);
            MyAdsAdapter.AgricultureViewHolder evh = new MyAdsAdapter.AgricultureViewHolder(v);
            return evh;
        }

        @Override
        public void onBindViewHolder(@NonNull final MyAdsAdapter.AgricultureViewHolder holder, int i) {
            final AgricultureAdsModel currentItem = category_list.get(i);
            Picasso.get().load(currentItem.getProduct_image1()).resize(60, 60).into(holder.category_image);
//        Picasso.get().load(currentItem.getProduct_image1()).resize(60, 60).into(holder.category_image);
            holder.address_textview.setText(currentItem.getBlock() + ", " + currentItem.getDistrict());
            holder.price_textview.setText("Price  " + "₹ " + currentItem.getPrice());

            holder.cardview.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(context, MyAdsDetail.class);
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
                            intent.putExtra("product_name", currentItem.getProduct_name());
                            intent.putExtra("product_breed", currentItem.getProduct_breed());
                            intent.putExtra("product_status", currentItem.getStatus());
                            intent.putExtra("quantity", currentItem.getProduct_quantity());
                            intent.putExtra("price", currentItem.getPrice());
                            intent.putExtra("pacakging", currentItem.getPackaging_cost_bearer());
                            intent.putExtra("post_id", currentItem.getPost_id());
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

        public void filterList(ArrayList<AgricultureAdsModel> filteredList) {
            category_list = filteredList;
            notifyDataSetChanged();
        }
    }
