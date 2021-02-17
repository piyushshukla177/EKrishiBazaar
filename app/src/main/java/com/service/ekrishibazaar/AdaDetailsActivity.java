package com.service.ekrishibazaar;

import androidx.annotation.NonNull;
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

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AdaDetailsActivity extends AppCompatActivity implements MakeOfferSheet.MakeOfferListener {
    private ApiHelper apiHelper;

    String category_type, image1, image2, image3, user_first_name, user_last_name, vid, date_joined, user_mobile_no, profile_image, state,
            price, district, block, village, post_id;
    Context context;

    ArrayList<SliderItem> slider_list = new ArrayList();
    String token;

    ImageView back_image, profile_imageview;
    TextView name_tv, vid_tv, mobile_number_tv, joined_tv, state_tv, district_tv, block_tv, village_tv, product_name_tv, product_breed_tv, product_status_tv,
            product_quantity_tv, packing_avialable_tv, posted_on_tv, additional_info_tv, post_id_tv;
    Button view_profile_btn, make_offer_btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ada_details);

        init();
    }

    MakeOfferSheet bottomSheet;

    void init() {
        context = this;

        back_image = findViewById(R.id.back_image);
        profile_imageview = findViewById(R.id.profile_imageview);
        name_tv = findViewById(R.id.name_tv);
        vid_tv = findViewById(R.id.vid_tv);
        mobile_number_tv = findViewById(R.id.mobile_number_tv);
        joined_tv = findViewById(R.id.joined_tv);
        state_tv = findViewById(R.id.state_tv);
        district_tv = findViewById(R.id.district_tv);
        block_tv = findViewById(R.id.block_tv);
        village_tv = findViewById(R.id.village_tv);
        product_name_tv = findViewById(R.id.product_name_tv);
        product_breed_tv = findViewById(R.id.product_breed_tv);
        product_status_tv = findViewById(R.id.product_status_tv);
        product_quantity_tv = findViewById(R.id.product_quantity_tv);
        packing_avialable_tv = findViewById(R.id.packing_avialable_tv);
        additional_info_tv = findViewById(R.id.additional_info_tv);
        view_profile_btn = findViewById(R.id.view_profile_btn);
        make_offer_btn = findViewById(R.id.make_offer_btn);
        post_id_tv = findViewById(R.id.post_id_tv);
        posted_on_tv = findViewById(R.id.posted_on_tv);
        token = PrefsHelper.getString(context, "token");
        back_image.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AdaDetailsActivity.super.onBackPressed();
                    }
                }
        );

        view_profile_btn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(AdaDetailsActivity.this, ViewProfileActivity.class);
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
        sliderView.setSliderAdapter(new SliderAdapterExample(AdaDetailsActivity.this, slider_list));
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
        product_name_tv.setText(intent.getStringExtra("product_name"));
        product_breed_tv.setText(intent.getStringExtra("product_breed"));
        product_status_tv.setText(intent.getStringExtra("product_status"));
        product_quantity_tv.setText(intent.getStringExtra("quantity"));
        packing_avialable_tv.setText(intent.getStringExtra("pacakging"));
        additional_info_tv.setText(intent.getStringExtra("additional_info"));
        posted_on_tv.setText(intent.getStringExtra("posted_on"));
        post_id_tv.setText(intent.getStringExtra("post_id"));

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
                    Toast.makeText(AdaDetailsActivity.this, x, Toast.LENGTH_SHORT).show();
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
                Toast.makeText(AdaDetailsActivity.this, "Offer is already sent", Toast.LENGTH_SHORT).show();
                if (!call.isCanceled()) {
                }
                t.printStackTrace();
            }
        });
    }

//    private void MakeOffer(String phone, String actual_price, String offer_price) {
//        final ProgressDialog mProgressDialog = new ProgressDialog(this);
//        mProgressDialog.setIndeterminate(true);
//        mProgressDialog.setMessage("Loading...");
//        mProgressDialog.show();
//
//        StringRequest postRequest = new StringRequest(Request.Method.POST, "https://ekrishibazaar.com/api/ads/offer/",
//                new Response.Listener<String>() {
//                    @Override
//                    public void onResponse(String response) {
//                        try {
//                            Log.e("response", response.toString());
//                            mProgressDialog.dismiss();
//                            Object json = new JSONTokener(response).nextValue();
//                            if (json instanceof JSONObject) {
//                                JSONObject obj = new JSONObject(response);
//                                Toast.makeText(getApplicationContext(), obj.getString("detail"), Toast.LENGTH_SHORT).show();
//                            } else {
//                                Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_SHORT).show();
//                                bottomSheet.dismiss();
//                            }
//                            JSONObject jsonObject = new JSONObject(response);
//                            Log.e("Response", response.toString());
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                    }
//                },
//                new Response.ErrorListener() {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        error.printStackTrace();
//                        mProgressDialog.dismiss();
//                        String errorCode = "";
//                        if (error instanceof TimeoutError) {
//                            errorCode = "Time out Error";
//                        } else if (error instanceof NoConnectionError) {
//                            errorCode = "No Internet Connection Error";
//                        } else if (error instanceof AuthFailureError) {
//                            errorCode = "Auth Failure Error";
//                        } else if (error instanceof ServerError) {
//                            errorCode = "Server Error";
//                        } else if (error instanceof NetworkError) {
//                            errorCode = "Network Error";
//                        } else if (error instanceof ParseError) {
//                            errorCode = "Parse Error";
//                        }
//                        Toast.makeText(context, "Enter Valid Details", Toast.LENGTH_SHORT).show();
//                    }
//                }
//        ) {
//
//
//            @Override
//            protected Map<String, String> getParams() {
//                Map<String, String> params = new HashMap<String, String>();
//                params.put("postid ", post_id);
//                params.put("category", category_type);
//                params.put("price", actual_price);
//                params.put("offered_price", offer_price);
//                params.put("phonenumber", user_mobile_no);
//                params.put("vid", vid_tv.getText().toString());
//                Log.e("params", params.toString());
//                return params;
//            }
//
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                HashMap<String, String> headers = new HashMap<>();
//                headers.put("Authorization", "Token " + token);
//                return headers;
//            }
//        };
//        postRequest.setRetryPolicy(new DefaultRetryPolicy(30000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//        Volley.newRequestQueue(this).add(postRequest);
//    }


}

/*
{"detail":"You already made similar offer"}
2021-01-27 13:46:25.424 2273-2567/com.service.ekrishibazaar E/params: {vid=100000414, price=25.0, phonenumber=9780222643, postid =100000070, category=agricultural, offered_price=5}
2021-01-27 13:47:12.744 2273-2920/com.service.ekrishibazaar E/params: {vid=100000414, price=25.0, phonenumber=9780222643, postid =100000070, category=agricultural, offered_price=4}
2021-01-27 13:48:02.856 2273-3294/com.service.ekrishibazaar E/params: {vid=100000414, price=25.0, phonenumber=9780222643, postid =100000070, category=agricultural, offered_price=15}
 */