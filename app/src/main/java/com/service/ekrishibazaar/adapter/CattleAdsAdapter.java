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
import com.service.ekrishibazaar.CattleDetailsActivity;
import com.service.ekrishibazaar.R;
import com.service.ekrishibazaar.model.CattleAdsModel;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;

public class CattleAdsAdapter extends RecyclerView.Adapter<CattleAdsAdapter.FreshRecmViewHolder> {
    private ArrayList<CattleAdsModel> category_list;
    private Context context;

    public class FreshRecmViewHolder extends RecyclerView.ViewHolder {
        public TextView address_textview, price_textview;
        ImageView category_image;
        CardView cardview;

        public FreshRecmViewHolder(View itemView) {
            super(itemView);
            category_image = itemView.findViewById(R.id.category_image);
            address_textview = itemView.findViewById(R.id.address_textview);
            price_textview = itemView.findViewById(R.id.price_textview);
            cardview = itemView.findViewById(R.id.cardview);

        }
    }

    public CattleAdsAdapter(Context context, ArrayList<CattleAdsModel> list) {
        category_list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public FreshRecmViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cattle_ads_layout, parent, false);
        FreshRecmViewHolder evh = new FreshRecmViewHolder(v);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull final FreshRecmViewHolder holder, int i) {
        final CattleAdsModel currentItem = category_list.get(i);
        Picasso.get().load(currentItem.getProduct_image1()).fit().into(holder.category_image);
        holder.address_textview.setText(currentItem.getBlock() + ", " + currentItem.getDistrict());
        holder.price_textview.setText("Price  " + "₹ " + currentItem.getPrice());
        holder.cardview.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, CattleDetailsActivity.class);
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
                        intent.putExtra("cattle_type", currentItem.getCattle_name());
                        intent.putExtra("cattle_breed", currentItem.getCattle_breed());
                        intent.putExtra("number_of_births", currentItem.getNumber_of_child());
                        intent.putExtra("milk_per_day_tv", currentItem.getMilk_per_day());
                        intent.putExtra("If_preganent", currentItem.getIf_preganent());
                        intent.putExtra("price", currentItem.getPrice());
                        intent.putExtra("post_id", currentItem.getPost_id());
//                      intent.putExtra("additional_details", currentItem.getAd);
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

    public void filterList(ArrayList<CattleAdsModel> filteredList) {
        category_list = filteredList;
        notifyDataSetChanged();
    }
}
