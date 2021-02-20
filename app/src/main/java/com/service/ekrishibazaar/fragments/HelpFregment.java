package com.service.ekrishibazaar.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.service.ekrishibazaar.AboutusActivity;
import com.service.ekrishibazaar.ContactUsActivity;
import com.service.ekrishibazaar.MoreServicesActivity;
import com.service.ekrishibazaar.R;
import com.service.ekrishibazaar.SelectCategoryActivity;

public class HelpFregment extends Fragment {

    LinearLayout post_sell_ads_linear, post_buy_linear, more_services_linear;
    ImageView back_image;

    Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_help, container, false);
        init(root);
        return root;
    }

    void init(View root) {
        context=getActivity();
        post_sell_ads_linear = root.findViewById(R.id.post_sell_ads_linear);
        more_services_linear = root.findViewById(R.id.more_services_linear);
        post_buy_linear = root.findViewById(R.id.post_buy_linear);
        post_sell_ads_linear.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), SelectCategoryActivity.class);
                        intent.putExtra("super_category", "Sellads");
                        startActivity(intent);
                    }
                }
        );
        post_buy_linear.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), SelectCategoryActivity.class);
                        intent.putExtra("super_category", "BuyOffer");
                        startActivity(intent);
                    }
                }
        );
        more_services_linear.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), SelectCategoryActivity.class);
                        intent.putExtra("super_category", "BuyBackOffer");
                        startActivity(intent);
                    }
                }
        );
    }
}

