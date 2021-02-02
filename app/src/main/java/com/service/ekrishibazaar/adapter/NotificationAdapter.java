package com.service.ekrishibazaar.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.service.ekrishibazaar.R;
import com.service.ekrishibazaar.model.CattleAdsModel;
import com.service.ekrishibazaar.model.NotificationModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.NotificationViewHolder> {
    private ArrayList<NotificationModel> notification_list;
    private Context context;

    public class NotificationViewHolder extends RecyclerView.ViewHolder {
        public TextView notification_tv, date_tv;


        public NotificationViewHolder(View itemView) {
            super(itemView);
            notification_tv = itemView.findViewById(R.id.notification_tv);
            date_tv = itemView.findViewById(R.id.date_tv);
        }
    }

    public NotificationAdapter(Context context, ArrayList<NotificationModel> list) {
        notification_list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_layout, parent, false);
        NotificationViewHolder evh = new NotificationViewHolder(v);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull final NotificationViewHolder holder, int i) {
        final NotificationModel currentItem = notification_list.get(i);
        holder.notification_tv.setText(currentItem.getMsg());
        holder.date_tv.setText(currentItem.getDate());
    }

    @Override
    public int getItemCount() {
        return notification_list.size();
    }

//    public void filterList(ArrayList<NotificationModel> filteredList) {
//        notification_list = filteredList;
//        notifyDataSetChanged();
//    }
}
