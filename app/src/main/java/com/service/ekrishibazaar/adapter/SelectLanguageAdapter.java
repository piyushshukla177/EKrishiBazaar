package com.service.ekrishibazaar.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.service.ekrishibazaar.R;
import com.service.ekrishibazaar.SelectLanguageActivity;
import com.service.ekrishibazaar.model.SelectLanguageModel;

import java.util.ArrayList;

public class SelectLanguageAdapter extends RecyclerView.Adapter<SelectLanguageAdapter.SelectLanguageViewHolder> {
    private ArrayList<SelectLanguageModel> category_list;
    private Context context;

    public class SelectLanguageViewHolder extends RecyclerView.ViewHolder {
        public RadioButton language_name_radio;
        CardView language_card;

        public SelectLanguageViewHolder(View itemView) {
            super(itemView);
            language_name_radio = itemView.findViewById(R.id.language_name_radio);
            language_card = itemView.findViewById(R.id.language_card);
//            menu_imageview = itemView.findViewById(R.id.menu_imageview);
//            add_products_imageview = itemView.findViewById(R.id.add_products_imageview);
        }
    }

    public SelectLanguageAdapter(Context context, ArrayList<SelectLanguageModel> list) {
        category_list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public SelectLanguageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.select_language_layout, parent, false);
        SelectLanguageViewHolder evh = new SelectLanguageViewHolder(v);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull final SelectLanguageViewHolder holder, int i) {
        final SelectLanguageModel currentItem = category_list.get(i);
        holder.language_name_radio.setText(currentItem.getLang_name());
        holder.language_name_radio.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        holder.language_name_radio.setChecked(true);
                        ((SelectLanguageActivity) context).setLanguage(currentItem.getLang_code(), currentItem.getEng_name());
                    }
                }
        );
        holder.language_card.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        holder.language_name_radio.setChecked(true);
                        ((SelectLanguageActivity) context).setLanguage(currentItem.getLang_code(), currentItem.getEng_name());
                    }
                }
        );
    }

    @Override
    public int getItemCount() {
        return category_list.size();
    }
}