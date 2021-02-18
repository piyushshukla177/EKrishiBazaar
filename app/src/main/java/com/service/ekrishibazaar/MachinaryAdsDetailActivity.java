package com.service.ekrishibazaar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.service.ekrishibazaar.adapter.SliderAdapterExample;
import com.service.ekrishibazaar.model.SliderItem;
import com.service.ekrishibazaar.util.ApiHelper;
import com.service.ekrishibazaar.util.MakeOfferSheet;
import com.service.ekrishibazaar.util.PrefsHelper;
import com.smarteist.autoimageslider.SliderView;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MachinaryAdsDetailActivity extends AppCompatActivity implements MakeOfferSheet.MakeOfferListener {

    TextView name_tv, vid_tv, mobile_number_tv, joined_tv, state_tv, district_tv, block_tv, village_tv, machine_type_tv, machine_brand_tv, warranty_status_tv,
            who_pay_pkg_charges_tv, price_tv, posted_on_tv, additional_details_tv, post_id_tv;

    Context context;

    ArrayList<SliderItem> slider_list = new ArrayList();
    ImageView back_image, profile_imageview,verified_imageview;

    Button view_profile_btn, make_offer_btn;
    String category_type, image1, image2, image3, user_first_name, user_last_name, vid, date_joined, user_mobile_no, profile_image, state, district, block, village, post_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_machinary_ads_detail);
        init();
    }

    String token;
    private ApiHelper apiHelper;
    MakeOfferSheet bottomSheet;

    void init() {
        context = this;
        token = PrefsHelper.getString(context, "token");

        machine_type_tv = findViewById(R.id.machine_type_tv);
        machine_brand_tv = findViewById(R.id.machine_brand_tv);
        warranty_status_tv = findViewById(R.id.warranty_status_tv);
        price_tv = findViewById(R.id.price_tv);
        who_pay_pkg_charges_tv = findViewById(R.id.who_pay_pkg_charges_tv);

        additional_details_tv = findViewById(R.id.additional_details_tv);
        name_tv = findViewById(R.id.name_tv);
        vid_tv = findViewById(R.id.vid_tv);
        mobile_number_tv = findViewById(R.id.mobile_number_tv);
        joined_tv = findViewById(R.id.joined_tv);
        state_tv = findViewById(R.id.state_tv);
        district_tv = findViewById(R.id.district_tv);
        block_tv = findViewById(R.id.block_tv);
        village_tv = findViewById(R.id.village_tv);
        view_profile_btn = findViewById(R.id.view_profile_btn);
        make_offer_btn = findViewById(R.id.make_offer_btn);
        profile_imageview = findViewById(R.id.profile_imageview);
        back_image = findViewById(R.id.back_image);
        post_id_tv = findViewById(R.id.post_id_tv);
        posted_on_tv = findViewById(R.id.posted_on_tv);
        verified_imageview = findViewById(R.id.verified_imageview);

        back_image.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MachinaryAdsDetailActivity.super.onBackPressed();
                    }
                }
        );

        Intent intent = getIntent();
        category_type = intent.getStringExtra("category_type");
        image1 = intent.getStringExtra("image1");
        image2 = intent.getStringExtra("image2");
        image3 = intent.getStringExtra("image3");
        user_first_name = intent.getStringExtra("user_first_name");
        user_last_name = intent.getStringExtra("user_last_name");
        user_mobile_no = intent.getStringExtra("mobile");
        vid = intent.getStringExtra("vid");
        date_joined = intent.getStringExtra("joined_on");
        profile_image = intent.getStringExtra("profile_image");
        state = intent.getStringExtra("state");
        district = intent.getStringExtra("district");
        block = intent.getStringExtra("block");
        village = intent.getStringExtra("village");
        post_id = intent.getStringExtra("post_id");
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
        sliderView.setSliderAdapter(new SliderAdapterExample(MachinaryAdsDetailActivity.this, slider_list));
        sliderView.run();
        Glide.with(context)
                .load(profile_image)
                .fitCenter()
                .into(profile_imageview);

        name_tv.setText(user_first_name + "" + user_last_name);
        vid_tv.setText(vid);
        mobile_number_tv.setText(user_mobile_no);
        if (date_joined != null && date_joined.length() > 10) {
            joined_tv.setText(date_joined.substring(0, 10));
        }
        state_tv.setText(state);
        district_tv.setText(district);
        block_tv.setText(block);
        village_tv.setText(village);

        machine_type_tv.setText(intent.getStringExtra("machine_type"));
        machine_brand_tv.setText(intent.getStringExtra("machine_brand"));
        warranty_status_tv.setText(intent.getStringExtra("warranty_status"));
        who_pay_pkg_charges_tv.setText(intent.getStringExtra("who_pay_pkg_charges"));
        price_tv.setText(intent.getStringExtra("price"));
        post_id_tv.setText(intent.getStringExtra("post_id"));
        posted_on_tv.setText(intent.getStringExtra("posted_on"));
        additional_details_tv.setText(intent.getStringExtra("additional_details"));

        view_profile_btn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MachinaryAdsDetailActivity.this, ViewProfileActivity.class);
                        intent.putExtra("profile_url", profile_image);
                        intent.putExtra("name", name_tv.getText());
                        intent.putExtra("vid", vid_tv.getText());
                        intent.putExtra("mobile_number", mobile_number_tv.getText());
                        intent.putExtra("block", block_tv.getText());
                        intent.putExtra("district", district_tv.getText());
                        intent.putExtra("state", state_tv.getText());
                        startActivity(intent);
                    }
                }
        );
        make_offer_btn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        MakeOfferSheet.actual_price = intent.getStringExtra("price");
                        bottomSheet = new MakeOfferSheet();
                        bottomSheet.show(getSupportFragmentManager(), "exampleBottomSheet");
                    }
                }
        );
        if (intent.getStringExtra("verified").equalsIgnoreCase("true")) {
            verified_imageview.setVisibility(View.VISIBLE);
        }
    }

    String phone, actual_price, offer_price;

    @Override
    public void onMakeOffer(String phone, String actual_price, String offer_price) {
        MakeOfferApi(phone, actual_price, offer_price);
        phone = this.phone;
        actual_price = actual_price;
    }

    private void MakeOfferApi(String phone, String actual_price, String offer_price) {

        final ProgressDialog mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage("Making Offer...");
        mProgressDialog.setCancelable(false);
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.setOnCancelListener(new Dialog.OnCancelListener() {

            @Override
            public void onCancel(DialogInterface dialog) {
                // DO SOME STUFF HERE
            }
        });
        mProgressDialog.show();
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(new Interceptor() {
            @Override
            public okhttp3.Response intercept(Chain chain) throws IOException {
                okhttp3.Request newRequest = chain.request().newBuilder()
                        .addHeader("Authorization", "Token " + token)
                        .build();
                return chain.proceed(newRequest);
            }
        }).build();

        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl("https://ekrishibazaar.com/api/ads/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        apiHelper = retrofit.create(ApiHelper.class);
        Call<String> loginCall = apiHelper.MakeOffer(post_id, category_type, actual_price, offer_price, user_mobile_no, vid);
        loginCall.enqueue(new Callback<String>() {

            @Override
            public void onResponse(Call<String> call, retrofit2.Response<String> response) {
                mProgressDialog.hide();
                if (response.body() instanceof String) {
                    String x = response.body();
                    Toast.makeText(MachinaryAdsDetailActivity.this, x, Toast.LENGTH_SHORT).show();
                    bottomSheet.dismiss();
                }

//                if(response.isSuccessful()){
//
//                }else {
//
//                }
            }

            @Override
            public void onFailure(@NonNull Call<String> call,
                                  @NonNull Throwable t) {
                mProgressDialog.hide();
                Toast.makeText(MachinaryAdsDetailActivity.this, "Offer is already sent", Toast.LENGTH_SHORT).show();
                if (!call.isCanceled()) {
                }
                t.printStackTrace();
            }
        });
    }

}