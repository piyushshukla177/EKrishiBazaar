package com.service.ekrishibazaar;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.service.ekrishibazaar.adapter.SliderAdapterExample;
import com.service.ekrishibazaar.model.SliderItem;
import com.service.ekrishibazaar.util.ApiHelper;
import com.service.ekrishibazaar.util.MakeOfferSheet;
import com.service.ekrishibazaar.util.PrefsHelper;
import com.smarteist.autoimageslider.SliderView;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MyAdsDetail extends AppCompatActivity {
    private ApiHelper apiHelper;
    Context context;

    ArrayList<SliderItem> slider_list = new ArrayList();
    String token;

    ImageView back_image, profile_imageview;
    TextView additional_info_tv, vid_tv, mobile_number_tv, joined_tv, state_tv, district_tv, block_tv, village_tv, product_name_tv, product_breed_tv, product_status_tv,
            product_quantity_tv, packing_avialable_tv, price_tv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_ads_detail);

        init();
    }

    MakeOfferSheet bottomSheet;

    void init() {
        context = this;

        back_image = findViewById(R.id.back_image);
        profile_imageview = findViewById(R.id.profile_imageview);
//        name_tv = findViewById(R.id.name_tv);
//        vid_tv = findViewById(R.id.vid_tv);
//        mobile_number_tv = findViewById(R.id.mobile_number_tv);
//        joined_tv = findViewById(R.id.joined_tv);
        state_tv = findViewById(R.id.state_tv);
        additional_info_tv = findViewById(R.id.additional_info_tv);
        district_tv = findViewById(R.id.district_tv);
        block_tv = findViewById(R.id.block_tv);
        village_tv = findViewById(R.id.village_tv);
        product_name_tv = findViewById(R.id.product_name_tv);
        product_breed_tv = findViewById(R.id.product_breed_tv);
        product_status_tv = findViewById(R.id.product_status_tv);
        product_quantity_tv = findViewById(R.id.product_quantity_tv);
        packing_avialable_tv = findViewById(R.id.packing_avialable_tv);
//        view_profile_btn = findViewById(R.id.view_profile_btn);
//        make_offer_btn = findViewById(R.id.make_offer_btn);
        price_tv = findViewById(R.id.price_tv);
        token = PrefsHelper.getString(context, "token");
        back_image.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MyAdsDetail.super.onBackPressed();
                    }
                }
        );

        Intent intent = getIntent();

        String post_id = intent.getStringExtra("post_id");
        getAdsDetails(post_id);
    }

    private void getAdsDetails(String post_id) {
        final ProgressDialog mProgressDialog = new ProgressDialog(context);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage("Loading...");
        mProgressDialog.setCancelable(false);
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.setOnCancelListener(new Dialog.OnCancelListener() {

            @Override
            public void onCancel(DialogInterface dialog) {
                // DO SOME STUFF HERE
            }
        });
        mProgressDialog.show();
        String url = "https://ekrishibazaar.com/api/ads/agriads/" + post_id + "/?toedit=" + post_id;
        StringRequest postRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.v("response", response);
                        try {
                            mProgressDialog.dismiss();
                            JSONObject obj = new JSONObject(response);
                            JSONObject product_obj = obj.getJSONObject("product");
                            JSONObject category_obj = product_obj.getJSONObject("category");
                            JSONObject product_breed_obj = obj.getJSONObject("product_breed");
                            JSONObject product_status_obj = obj.getJSONObject("product_status");
                            JSONObject product_packaging_type_obj = obj.getJSONObject("product_packaging_type");
                            JSONObject state_obj = obj.getJSONObject("state");
                            JSONObject district_obj = obj.getJSONObject("district");
                            JSONObject block_obj = obj.getJSONObject("block");

                            String category_name = category_obj.getString("category_name");
                            product_name_tv.setText(product_obj.getString("product_name"));

                            String product_status = product_status_obj.getString("product_status");
                            String quantity = obj.getString("product_quantity");
                            price_tv.setText(obj.getString("price") + " / " + obj.getString("product_price_by"));
                            product_quantity_tv.setText(quantity + " / " + obj.getString("product_quantity_by"));
                            packing_avialable_tv.setText(obj.getString("product_packaging_available") + " ," + product_packaging_type_obj.getString("product_packaging_type"));

                            state_tv.setText(state_obj.getString("state_name"));
                            district_tv.setText(district_obj.getString("district_name"));
                            block_tv.setText(block_obj.getString("block_name"));
                            village_tv.setText(obj.getString("village"));
                            product_breed_tv.setText(product_breed_obj.getString("product_breed"));
                            product_status_tv.setText(product_status);
                            additional_info_tv.setText(obj.getString("additional_information"));
//                            who_pay_charges_spinner.setText(obj.getString("packaging_cost_bearer"));
//                            Picasso.get().load(obj.getString("product_image1")).resize(60, 60).into(product_image_imageview1);
//                            Picasso.get().load(obj.getString("product_image2")).resize(60, 60).into(product_image_imageview2);
//                            Picasso.get().load(obj.getString("product_image3")).resize(60, 60).into(product_image_imageview3);

//                            product_status_tv.setText(intent.getStringExtra("product_status"));
//                            product_quantity_tv.setText(intent.getStringExtra("quantity"));
//                            packing_avialable_tv.setText(intent.getStringExtra("pacakging"));

                            startSlider(obj.getString("product_image1"), obj.getString("product_image2"), obj.getString("product_image3"));
                            mProgressDialog.dismiss();
                        } catch (Exception e) {
                            e.printStackTrace();
                            mProgressDialog.dismiss();
                        }
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        error.printStackTrace();
                        mProgressDialog.dismiss();
                        String errorCode = "";
                        if (error instanceof TimeoutError) {
                            errorCode = "Time out Error";
                        } else if (error instanceof NoConnectionError) {
                            errorCode = "No Internet Connection Error";
                        } else if (error instanceof AuthFailureError) {
                            errorCode = "Auth Failure Error";
                        } else if (error instanceof ServerError) {
                            errorCode = "Server Error";
                        } else if (error instanceof NetworkError) {
                            errorCode = "Network Error";
                        } else if (error instanceof ParseError) {
                            errorCode = "Parse Error";
                        }
                        Toast.makeText(context, "Error.Response: " + errorCode, Toast.LENGTH_SHORT).show();
                    }
                }
        ) {

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "Token " + token);
                return params;
            }

//            @Override
//            protected Map<String, String> getParams() {
//                Map<String, String> params = new HashMap<String, String>();
//                params.put("super_category", "Sellads");
//                Log.v("request", params.toString());
//                return params;
//            }
        };
        postRequest.setRetryPolicy(new DefaultRetryPolicy(30000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(context).add(postRequest);
    }

    void startSlider(String image1, String image2, String image3) {
        SliderItem s = new SliderItem();
        s.setDescription("");
        s.setImageUrl(image1);
        slider_list.add(s);

        SliderItem s1 = new SliderItem();
        s1.setDescription("");
        s1.setImageUrl(image2);
        slider_list.add(s1);

        SliderItem s2 = new SliderItem();
        s2.setDescription("");
        s2.setImageUrl(image3);
        slider_list.add(s2);

        SliderView sliderView = findViewById(R.id.imageSlider);
        sliderView.setSliderAdapter(new SliderAdapterExample(MyAdsDetail.this, slider_list));
        sliderView.run();
    }
}

/*
{"detail":"You already made similar offer"}
2021-01-27 13:46:25.424 2273-2567/com.service.ekrishibazaar E/params: {vid=100000414, price=25.0, phonenumber=9780222643, postid =100000070, category=agricultural, offered_price=5}
2021-01-27 13:47:12.744 2273-2920/com.service.ekrishibazaar E/params: {vid=100000414, price=25.0, phonenumber=9780222643, postid =100000070, category=agricultural, offered_price=4}
2021-01-27 13:48:02.856 2273-3294/com.service.ekrishibazaar E/params: {vid=100000414, price=25.0, phonenumber=9780222643, postid =100000070, category=agricultural, offered_price=15}
 */