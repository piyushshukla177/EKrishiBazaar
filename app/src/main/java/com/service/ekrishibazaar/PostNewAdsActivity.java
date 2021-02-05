package com.service.ekrishibazaar;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class PostNewAdsActivity extends AppCompatActivity {

    LinearLayout post_sell_ads_linear, post_buy_linear, more_services_linear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_new_ads);

        post_sell_ads_linear = findViewById(R.id.post_sell_ads_linear);
        more_services_linear = findViewById(R.id.more_services_linear);
        post_buy_linear = findViewById(R.id.post_buy_linear);
        post_sell_ads_linear.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(PostNewAdsActivity.this, PostSellAdsActivity.class);
                        intent.putExtra("super_category", "Sellads");
                        startActivity(intent);
                    }
                }
        );
        post_buy_linear.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(PostNewAdsActivity.this, PostSellAdsActivity.class);
                        intent.putExtra("super_category", "BuyOffer");
                        startActivity(intent);
                    }
                }
        );
        more_services_linear.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(PostNewAdsActivity.this, PostSellAdsActivity.class);
                        intent.putExtra("super_category", "BuyBackOffer");
                        startActivity(intent);
                    }
                }
        );
    }
}