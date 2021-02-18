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

import com.service.ekrishibazaar.FertilizerAdsDetailsActivity;
import com.service.ekrishibazaar.MachinaryAdsDetailActivity;
import com.service.ekrishibazaar.R;
import com.service.ekrishibazaar.model.FertilizerListModel;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;

public class FertilizerAdapter extends RecyclerView.Adapter<FertilizerAdapter.FertilizerViewHolder> {
    private ArrayList<FertilizerListModel> fertilizer_list;
    private Context context;

    public class FertilizerViewHolder extends RecyclerView.ViewHolder {
        public TextView address_textview, price_textview;
        ImageView category_image;
        CardView cardview;

        public FertilizerViewHolder(View itemView) {
            super(itemView);
            category_image = itemView.findViewById(R.id.category_image);
            address_textview = itemView.findViewById(R.id.address_textview);
            price_textview = itemView.findViewById(R.id.price_textview);
            cardview = itemView.findViewById(R.id.cardview);
        }
    }

    public FertilizerAdapter(Context context, ArrayList<FertilizerListModel> list) {
        fertilizer_list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public FertilizerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cattle_ads_layout, parent, false);
        FertilizerViewHolder evh = new FertilizerViewHolder(v);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull final FertilizerViewHolder holder, int i) {
        final FertilizerListModel currentItem = fertilizer_list.get(i);
        Picasso.get().load(currentItem.getProduct_image1()).fit().into(holder.category_image);
//        Picasso.get().load(currentItem.getProduct_image1()).resize(60, 60).into(holder.category_image);
        holder.address_textview.setText(currentItem.getBlock() + ", " + currentItem.getDistrict());
        holder.price_textview.setText("Price  " + "₹ " + currentItem.getPrice());
        holder.cardview.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, FertilizerAdsDetailsActivity.class);
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
                        intent.putExtra("fertilizer_name", currentItem.getChemical_product_name());
                        intent.putExtra("fertilizer_type", currentItem.getType_of_chemical());
                        intent.putExtra("price", currentItem.getPrice());
                        intent.putExtra("per_unit", currentItem.getPer_unit());
                        intent.putExtra("paking", currentItem.getPacking());
                        intent.putExtra("posted_on", currentItem.getPosted_on());
                        intent.putExtra("additional_details", currentItem.getAdditional_information());
                        intent.putExtra("post_id", currentItem.getPost_id());
                        intent.putExtra("posted_on", currentItem.getPosted_on());
                        intent.putExtra("verified", currentItem.getIsverified());
                        context.startActivity(intent);
                    }
                }
        );
    }

    @Override
    public int getItemCount() {
        return fertilizer_list.size();
    }

    public void filterList(ArrayList<FertilizerListModel> filteredList) {
        fertilizer_list = filteredList;
        notifyDataSetChanged();
    }
}
