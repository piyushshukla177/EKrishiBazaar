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

import com.service.ekrishibazaar.MyAdsDetail;
import com.service.ekrishibazaar.PostAgriculturalMachinaryAdsActivity;
import com.service.ekrishibazaar.PostCattleAdsActivity;
import com.service.ekrishibazaar.PostFertilizerAdsActivity;
import com.service.ekrishibazaar.PostLabourAdsActivity;
import com.service.ekrishibazaar.PostOtherAgriAdsActivity;
import com.service.ekrishibazaar.PostSellAdsActivity;
import com.service.ekrishibazaar.PostServiceRentAdsActivity;
import com.service.ekrishibazaar.PostTreeAndWoodsAdsActivity;
import com.service.ekrishibazaar.R;
import com.service.ekrishibazaar.fragments.MyAdsFragment;
import com.service.ekrishibazaar.model.MyAdsModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class MyAdsAdapter extends RecyclerView.Adapter<MyAdsAdapter.AgricultureViewHolder> {
    private ArrayList<MyAdsModel> category_list;
    private Context context;

    public class AgricultureViewHolder extends RecyclerView.ViewHolder {
        public TextView address_textview, price_textview;
        ImageView category_image, delete_icon, edit_icon, sold_icon;
        CardView cardview;

        public AgricultureViewHolder(View itemView) {
            super(itemView);
            category_image = itemView.findViewById(R.id.category_image);
            address_textview = itemView.findViewById(R.id.address_textview);
            price_textview = itemView.findViewById(R.id.price_textview);
            cardview = itemView.findViewById(R.id.cardview);
            delete_icon = itemView.findViewById(R.id.delete_icon);
            edit_icon = itemView.findViewById(R.id.edit_icon);
            sold_icon = itemView.findViewById(R.id.sold_icon);
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
        holder.edit_icon.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (currentItem.getCategory_name().equalsIgnoreCase("Cattle")) {
                            Intent intent = new Intent(context, PostCattleAdsActivity.class);
                            intent.putExtra("super_category", "edit");
                            intent.putExtra("post_id", currentItem.getPost_id());
                            intent.putExtra("category", currentItem.getCategory_name());
                            context.startActivity(intent);
                        } else if (currentItem.getCategory_name().equalsIgnoreCase("Fertilizers and Pesticides")) {
                            Intent intent = new Intent(context, PostFertilizerAdsActivity.class);
                            intent.putExtra("super_category", "edit");
                            intent.putExtra("post_id", currentItem.getPost_id());
                            intent.putExtra("category", currentItem.getCategory_name());
                            context.startActivity(intent);
                        } else if (currentItem.getCategory_name().equalsIgnoreCase("Labour in Rent")) {
                            Intent intent = new Intent(context, PostLabourAdsActivity.class);
                            intent.putExtra("super_category", "edit");
                            intent.putExtra("post_id", currentItem.getPost_id());
                            intent.putExtra("category", currentItem.getCategory_name());
                            context.startActivity(intent);
                        } else if (currentItem.getCategory_name().equalsIgnoreCase("Agricultural machinary")) {
                            Intent intent = new Intent(context, PostAgriculturalMachinaryAdsActivity.class);
                            intent.putExtra("super_category", "edit");
                            intent.putExtra("post_id", currentItem.getPost_id());
                            intent.putExtra("category", currentItem.getCategory_name());
                            context.startActivity(intent);
                        } else if (currentItem.getCategory_name().equalsIgnoreCase("Other Agri Product")) {
                            Intent intent = new Intent(context, PostOtherAgriAdsActivity.class);
                            intent.putExtra("super_category", "edit");
                            intent.putExtra("post_id", currentItem.getPost_id());
                            intent.putExtra("category", currentItem.getCategory_name());
                            context.startActivity(intent);
                        } else if (currentItem.getCategory_name().equalsIgnoreCase("Service in Rent")) {
                            Intent intent = new Intent(context, PostServiceRentAdsActivity.class);
                            intent.putExtra("super_category", "edit");
                            intent.putExtra("post_id", currentItem.getPost_id());
                            intent.putExtra("category", currentItem.getCategory_name());
                            context.startActivity(intent);
                        } else if (currentItem.getCategory_name().equalsIgnoreCase("Tree and Woods")) {
                            Intent intent = new Intent(context, PostTreeAndWoodsAdsActivity.class);
                            intent.putExtra("super_category", "edit");
                            intent.putExtra("post_id", currentItem.getPost_id());
                            intent.putExtra("category", currentItem.getCategory_name());
                            context.startActivity(intent);
                        } else if (currentItem.getCategory_name().equals("Fruits") || currentItem.getCategory_name().equals("Pulses") || currentItem.getCategory_name().equals("Medicinal plants") || currentItem.getCategory_name().equals("Dairy Product") || currentItem.getCategory_name().equals("Vegetable") || currentItem.getCategory_name().equals("Grains")
                                || currentItem.getCategory_name().equals("Flower") || currentItem.getCategory_name().equals("oilseeds")) {
                            Intent intent = new Intent(context, PostSellAdsActivity.class);
                            intent.putExtra("super_category", "edit");
                            intent.putExtra("post_id", currentItem.getPost_id());
                            intent.putExtra("category", currentItem.getCategory_name());
                            context.startActivity(intent);
                        }
                    }
                }
        );
        holder.delete_icon.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (currentItem.getCategory_name().equalsIgnoreCase("Cattle")) {
                            MyAdsFragment.mmm.DeleteCattleAds(currentItem.getPost_id());
                        } else if (currentItem.getCategory_name().equalsIgnoreCase("Fertilizers and Pesticides")) {
                            MyAdsFragment.mmm.DeleteFertilizerAds(currentItem.getPost_id());
                        } else if (currentItem.getCategory_name().equals("Fruits") || currentItem.getCategory_name().equals("Pulses") || currentItem.getCategory_name().equals("Medicinal plants") || currentItem.getCategory_name().equals("Dairy Product") || currentItem.getCategory_name().equals("Vegetable") || currentItem.getCategory_name().equals("Grains")
                                || currentItem.getCategory_name().equals("Flower") || currentItem.getCategory_name().equals("oilseeds")) {
                            MyAdsFragment.mmm.DeleteAds(currentItem.getPost_id());
                        } else if (currentItem.getCategory_name().equalsIgnoreCase("Labour in Rent")) {
                            MyAdsFragment.mmm.DeleteLabourinRentAds(currentItem.getPost_id());
                        } else if (currentItem.getCategory_name().equalsIgnoreCase("Agricultural machinary")) {
                            MyAdsFragment.mmm.DeleteAgriMachinary(currentItem.getPost_id());
                        } else if (currentItem.getCategory_name().equalsIgnoreCase("Other Agri Product")) {
                            MyAdsFragment.mmm.DeleteOtherAgriProduct(currentItem.getPost_id());
                        } else if (currentItem.getCategory_name().equalsIgnoreCase("Service in Rent")) {
                            MyAdsFragment.mmm.DeleteServiceInRent(currentItem.getPost_id());
                        } else if (currentItem.getCategory_name().equalsIgnoreCase("Tree and Woods")) {
                            MyAdsFragment.mmm.DeleteTreeAndWoods(currentItem.getPost_id());
                        }
                    }
                }
        );
        holder.sold_icon.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (currentItem.getCategory_name().equalsIgnoreCase("Cattle")) {
                            MyAdsFragment.mmm.MarkCattleAsSold(currentItem.getPost_id());
                        } else if (currentItem.getCategory_name().equals("Fruits") || currentItem.getCategory_name().equals("Pulses") || currentItem.getCategory_name().equals("Medicinal plants") || currentItem.getCategory_name().equals("Dairy Product") || currentItem.getCategory_name().equals("Vegetable") || currentItem.getCategory_name().equals("Grains")
                                || currentItem.getCategory_name().equals("Flower") || currentItem.getCategory_name().equals("oilseeds")) {
                            MyAdsFragment.mmm.MarkAsSold(currentItem.getPost_id());
                        } else if (currentItem.getCategory_name().equalsIgnoreCase("Fertilizers and Pesticides")) {
                            MyAdsFragment.mmm.MarkFertilizerAsSold(currentItem.getPost_id());
                        } else if (currentItem.getCategory_name().equalsIgnoreCase("Labour in Rent")) {
                            MyAdsFragment.mmm.MarkLabourinRentAsSold(currentItem.getPost_id());
                        } else if (currentItem.getCategory_name().equalsIgnoreCase("Agricultural machinary")) {
                            MyAdsFragment.mmm.MarkAgriMachinarytAsSold(currentItem.getPost_id());
                        } else if (currentItem.getCategory_name().equalsIgnoreCase("Other Agri Product")) {
                            MyAdsFragment.mmm.MarkOtherAgriProductAsSold(currentItem.getPost_id());
                        } else if (currentItem.getCategory_name().equalsIgnoreCase("Service in Rent")) {
                            MyAdsFragment.mmm.MarkServiceInRentAsSold(currentItem.getPost_id());
                        } else if (currentItem.getCategory_name().equalsIgnoreCase("Tree and Woods")) {
                            MyAdsFragment.mmm.MarkTreeeAndWoodsAsSold(currentItem.getPost_id());
                        }
                    }
                }
        );

        holder.category_image.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
//                        if (currentItem.getCategory_name().equalsIgnoreCase("Cattle")) {
                        Intent intent = new Intent(context, MyAdsDetail.class);
                        intent.putExtra("post_id", currentItem.getPost_id());
                        intent.putExtra("category", currentItem.getCategory_name());
                        context.startActivity(intent);
//                        else if (currentItem.getCategory_name().equals("Fruits") || currentItem.getCategory_name().equals("Pulses") || currentItem.getCategory_name().equals("Medicinal plants") || currentItem.getCategory_name().equals("Dairy Product") || currentItem.getCategory_name().equals("Vegetable") || currentItem.getCategory_name().equals("Grains")
//                                || currentItem.getCategory_name().equals("Flower") || currentItem.getCategory_name().equals("oilseeds")) {
//                            Intent intent = new Intent(context, MyAdsDetail.class);
//                            intent.putExtra("post_id", currentItem.getPost_id());
//                            intent.putExtra("category", currentItem.getCategory_name());
//                            context.startActivity(intent);
//                        } else if (currentItem.getCategory_name().equalsIgnoreCase("Fertilizers and Pesticides")) {
//                            Intent intent = new Intent(context, MyAdsDetail.class);
//                            intent.putExtra("post_id", currentItem.getPost_id());
//                            intent.putExtra("category", currentItem.getCategory_name());
//                            context.startActivity(intent);
//                        } else if (currentItem.getCategory_name().equalsIgnoreCase("Labour in Rent")) {
//                            Intent intent = new Intent(context, MyAdsDetail.class);
//                            intent.putExtra("post_id", currentItem.getPost_id());
//                            intent.putExtra("category", currentItem.getCategory_name());
//                            context.startActivity(intent);
//                        } else if (currentItem.getCategory_name().equalsIgnoreCase("Agricultural machinary")) {
//                            Intent intent = new Intent(context, MyAdsDetail.class);
//                            intent.putExtra("post_id", currentItem.getPost_id());
//                            intent.putExtra("category", currentItem.getCategory_name());
//                            context.startActivity(intent);
//                        }
//                        else if (currentItem.getCategory_name().equalsIgnoreCase("Other Agri Product")) {
//                            Intent intent = new Intent(context, MyAdsDetail.class);
//                            intent.putExtra("post_id", currentItem.getPost_id());
//                            intent.putExtra("category", currentItem.getCategory_name());
//                            context.startActivity(intent);
//                        }else if (currentItem.getCategory_name().equalsIgnoreCase("Service in Rent")) {
//                            Intent intent = new Intent(context, MyAdsDetail.class);
//                            intent.putExtra("post_id", currentItem.getPost_id());
//                            intent.putExtra("category", currentItem.getCategory_name());
//                            context.startActivity(intent);
//                        }
//                        else if (currentItem.getCategory_name().equalsIgnoreCase("Tree and Woods")) {
//                            Intent intent = new Intent(context, MyAdsDetail.class);
//                            intent.putExtra("post_id", currentItem.getPost_id());
//                            intent.putExtra("category", currentItem.getCategory_name());
//                            context.startActivity(intent);
//                        }
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

/*
2021-02-08 17:17:44.898 10910-11941/? E/post_ads_params: {cattle_breed=Jamunapari, number_of_child=1, cattle_name=Goat, product_price=1222, milk_produced=2 Liter, if_preganent=1222, super_category=null, additional_information=dddddd, price_status=Fixed, district=Lakshadweep, preganency_month=2, block=Kalpeni, state=Lakshadweep, category=Cattle, village=sddddd}
2021-02-08 17:17:52.950 10910-11941/? E/photo_params: {}
 */
