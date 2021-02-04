package com.service.ekrishibazaar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class PostNewAdsActivity extends AppCompatActivity {

    LinearLayout post_sell_ads_linear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_new_ads);

        post_sell_ads_linear = findViewById(R.id.post_sell_ads_linear);
        post_sell_ads_linear.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(PostNewAdsActivity.this, PostSellAdsActivity.class);
                        startActivity(intent);
                    }
                }
        );
    }
}