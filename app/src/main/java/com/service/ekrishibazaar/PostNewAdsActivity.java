package com.service.ekrishibazaar;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.service.ekrishibazaar.adapter.CategoryListAdapter;
import com.service.ekrishibazaar.model.CategoryListModel;
import com.service.ekrishibazaar.util.MyGridView;
import com.service.ekrishibazaar.util.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class PostNewAdsActivity extends AppCompatActivity {

    LinearLayout terms_and_condition_linear, ads_posting_guidelines_linear, legal_privacy_policy_linear, trust_and_safety_linear, buyers_faqs_linear,
            general_faqs_linear, be_varified_user_linear, about_us_linear, contact_us_linear;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_new_ads);
        init();
    }


    private void init() {
        terms_and_condition_linear = findViewById(R.id.terms_and_condition_linear);
        ads_posting_guidelines_linear = findViewById(R.id.ads_posting_guidelines_linear);
        legal_privacy_policy_linear = findViewById(R.id.legal_privacy_policy_linear);
        trust_and_safety_linear = findViewById(R.id.trust_and_safety_linear);
        buyers_faqs_linear = findViewById(R.id.buyers_faqs_linear);
        general_faqs_linear = findViewById(R.id.general_faqs_linear);
        be_varified_user_linear = findViewById(R.id.be_varified_user_linear);
        about_us_linear = findViewById(R.id.about_us_linear);
        contact_us_linear = findViewById(R.id.contact_us_linear);
        terms_and_condition_linear.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(PostNewAdsActivity.this, MoreServicesActivity.class);
                        intent.putExtra("url", "https://www.ekrishibazaar.com/terms-to-this-platform");
                        startActivity(intent);

//                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.ekrishibazaar.com/terms-to-this-platform"));
//                        startActivity(browserIntent);
                    }
                }
        );
        ads_posting_guidelines_linear.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(PostNewAdsActivity.this, MoreServicesActivity.class);
                        intent.putExtra("url", "https://www.ekrishibazaar.com/adpostingguidelines");
                        startActivity(intent);

//                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.ekrishibazaar.com/adpostingguidelines"));
//                        startActivity(browserIntent);
                    }
                }
        );
        legal_privacy_policy_linear.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(PostNewAdsActivity.this, MoreServicesActivity.class);
                        intent.putExtra("url", "https://www.ekrishibazaar.com/legalandprivacypolicy");
                        startActivity(intent);
//                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.ekrishibazaar.com/legalandprivacypolicym"));
//                        startActivity(browserIntent);
                    }
                }
        );
        trust_and_safety_linear.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(PostNewAdsActivity.this, MoreServicesActivity.class);
                        intent.putExtra("url", "https://www.ekrishibazaar.com/trust-and-safety");
                        startActivity(intent);
//                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.ekrishibazaar.com/trust-and-safety"));
//                        startActivity(browserIntent);
                    }
                }
        );
        buyers_faqs_linear.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(PostNewAdsActivity.this, MoreServicesActivity.class);
                        intent.putExtra("url", "https://www.ekrishibazaar.com/buyerfaq");
                        startActivity(intent);

//                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.ekrishibazaar.com/buyerfaq"));
//                        startActivity(browserIntent);
                    }
                }
        );
        general_faqs_linear.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(PostNewAdsActivity.this, MoreServicesActivity.class);
                        intent.putExtra("url", "https://www.ekrishibazaar.com/generalfaq");
                        startActivity(intent);

//                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.ekrishibazaar.com/generalfaq"));
//                        startActivity(browserIntent);
                    }
                }
        );
        be_varified_user_linear.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(PostNewAdsActivity.this, MoreServicesActivity.class);
                        intent.putExtra("url", "https://www.ekrishibazaar.com/beverifieduser");
                        startActivity(intent);

//                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.ekrishibazaar.com/beverifieduser"));
//                        startActivity(browserIntent);
                    }
                }
        );
        about_us_linear.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(PostNewAdsActivity.this, AboutusActivity.class);
                        startActivity(intent);
                    }
                }
        );
        contact_us_linear.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(PostNewAdsActivity.this, ContactUsActivity.class);
                        startActivity(intent);
                    }
                }
        );
    }
}

