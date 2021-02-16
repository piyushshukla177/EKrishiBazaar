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
import android.widget.ImageView;
import android.widget.LinearLayout;
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
    Context context;
    LinearLayout agricultural_ads_linear, cattle_ads_linear, fertilizer_linear, labourInrent_linear, agricultural_machinary_ads_linear, other_agri_product_linear, service_in_rent_linear, tree_and_wood_linear;
    ArrayList<SliderItem> slider_list = new ArrayList();
    String token;

    ImageView back_image, profile_imageview;
    TextView additional_info_tv, vid_tv, mobile_number_tv, joined_tv, state_tv, district_tv, block_tv, village_tv, product_name_tv, product_breed_tv, product_status_tv,
            product_quantity_tv, packing_avialable_tv, price_tv,

    cattle_name_tv, cattle_breed_tv, cattle_price_tv, number_of_births_tv, milk_per_day_tv,
            chemical_name_tv, type_of_chemical_tv, per_unit_tv, paking_tv, fertilizer_price_tv, additional_details_tv,
            labour_expertise_tv, available_hour_tv, labour_price_tv, reaching_time_status_tv, labour_additional_information_tv,
            machine_type_tv, machine_brand_tv, warranty_status_tv, agriculture_machinary_price_tv, agriculture_machinary_additional_details_tv,
            agri_product_price_tv, agri_product_name_tv, otheragri_additional_info_tv,
            service_machine_name_tv, service_tv, reaching_in_time_tv, service_price_tv, service_additional_details_tv,
            wood_name_tv, wood_quantity_tv, wood_additional_details_tv, wood_price_tv;

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
        price_tv = findViewById(R.id.price_tv);
        cattle_name_tv = findViewById(R.id.cattle_name_tv);
        cattle_breed_tv = findViewById(R.id.cattle_breed_tv);
        cattle_price_tv = findViewById(R.id.cattle_price_tv);
        number_of_births_tv = findViewById(R.id.number_of_births_tv);
        milk_per_day_tv = findViewById(R.id.milk_per_day_tv);
        agricultural_ads_linear = findViewById(R.id.agricultural_ads_linear);
        cattle_ads_linear = findViewById(R.id.cattle_ads_linear);
        fertilizer_linear = findViewById(R.id.fertilizer_linear);
        labourInrent_linear = findViewById(R.id.labourInrent_linear);
        agricultural_machinary_ads_linear = findViewById(R.id.agricultural_machinary_ads_linear);
        other_agri_product_linear = findViewById(R.id.other_agri_product_linear);
        service_in_rent_linear = findViewById(R.id.service_in_rent_linear);
        tree_and_wood_linear = findViewById(R.id.tree_and_wood_linear);
        //fertilizer
        chemical_name_tv = findViewById(R.id.chemical_name_tv);
        type_of_chemical_tv = findViewById(R.id.type_of_chemical_tv);
        per_unit_tv = findViewById(R.id.per_unit_tv);
        paking_tv = findViewById(R.id.paking_tv);
        fertilizer_price_tv = findViewById(R.id.fertilizer_price_tv);
        additional_details_tv = findViewById(R.id.additional_details_tv);
        //labour in rent
        labour_expertise_tv = findViewById(R.id.labour_expertise_tv);
        available_hour_tv = findViewById(R.id.available_hour_tv);
        labour_price_tv = findViewById(R.id.labour_price_tv);
        reaching_time_status_tv = findViewById(R.id.reaching_time_status_tv);
        labour_additional_information_tv = findViewById(R.id.labour_additional_information_tv);

        //agriculture machinary
        machine_type_tv = findViewById(R.id.machine_type_tv);
        machine_brand_tv = findViewById(R.id.machine_brand_tv);
        warranty_status_tv = findViewById(R.id.warranty_status_tv);
        agriculture_machinary_price_tv = findViewById(R.id.agriculture_machinary_price_tv);
        agriculture_machinary_additional_details_tv = findViewById(R.id.agriculture_machinary_additional_details_tv);
        //other agri
        agri_product_price_tv = findViewById(R.id.agri_product_price_tv);
        agri_product_name_tv = findViewById(R.id.agri_product_name_tv);
        otheragri_additional_info_tv = findViewById(R.id.otheragri_additional_info_tv);

        //service
        service_machine_name_tv = findViewById(R.id.service_machine_name_tv);
        service_tv = findViewById(R.id.service_tv);
        reaching_in_time_tv = findViewById(R.id.reaching_in_time_tv);
        service_price_tv = findViewById(R.id.service_price_tv);
        service_additional_details_tv = findViewById(R.id.service_additional_details_tv);

        //wood
        wood_name_tv = findViewById(R.id.wood_name_tv);
        wood_quantity_tv = findViewById(R.id.wood_quantity_tv);
        wood_price_tv = findViewById(R.id.wood_price_tv);
        wood_additional_details_tv = findViewById(R.id.wood_additional_details_tv);

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
        String category = intent.getStringExtra("category");
        String post_id = intent.getStringExtra("post_id");
        if (category.equalsIgnoreCase("Cattle")) {
            agricultural_ads_linear.setVisibility(View.GONE);
            fertilizer_linear.setVisibility(View.GONE);
            labourInrent_linear.setVisibility(View.GONE);
            agricultural_machinary_ads_linear.setVisibility(View.GONE);
            other_agri_product_linear.setVisibility(View.GONE);
            service_in_rent_linear.setVisibility(View.GONE);
            tree_and_wood_linear.setVisibility(View.GONE);
            cattle_ads_linear.setVisibility(View.VISIBLE);

            getCattleDetails(post_id);
        } else if (category.equals("Fruits") || category.equals("Pulses") || category.equals("Medicinal plants") || category.equals("Dairy Product") || category.equals("Vegetable") || category.equals("Grains")
                || category.equals("Flower") || category.equals("oilseeds")) {
            fertilizer_linear.setVisibility(View.GONE);
            cattle_ads_linear.setVisibility(View.GONE);
            labourInrent_linear.setVisibility(View.GONE);
            agricultural_machinary_ads_linear.setVisibility(View.GONE);
            other_agri_product_linear.setVisibility(View.GONE);
            service_in_rent_linear.setVisibility(View.GONE);
            tree_and_wood_linear.setVisibility(View.GONE);
            agricultural_ads_linear.setVisibility(View.VISIBLE);

            getAdsDetails(post_id);
        } else if (category.equalsIgnoreCase("Fertilizers and Pesticides")) {
            agricultural_ads_linear.setVisibility(View.GONE);
            cattle_ads_linear.setVisibility(View.GONE);
            labourInrent_linear.setVisibility(View.GONE);
            agricultural_machinary_ads_linear.setVisibility(View.GONE);
            other_agri_product_linear.setVisibility(View.GONE);
            service_in_rent_linear.setVisibility(View.GONE);
            tree_and_wood_linear.setVisibility(View.GONE);
            fertilizer_linear.setVisibility(View.VISIBLE);
            getFertilizerAdsDetails(post_id);
        } else if (category.equalsIgnoreCase("Labour in Rent")) {
            agricultural_ads_linear.setVisibility(View.GONE);
            cattle_ads_linear.setVisibility(View.GONE);
            labourInrent_linear.setVisibility(View.VISIBLE);
            fertilizer_linear.setVisibility(View.GONE);
            other_agri_product_linear.setVisibility(View.GONE);
            agricultural_machinary_ads_linear.setVisibility(View.GONE);
            service_in_rent_linear.setVisibility(View.GONE);
            tree_and_wood_linear.setVisibility(View.GONE);
            getLabourAdsDetails(post_id);
        } else if (category.equalsIgnoreCase("Agricultural machinary")) {
            agricultural_ads_linear.setVisibility(View.GONE);
            cattle_ads_linear.setVisibility(View.GONE);
            labourInrent_linear.setVisibility(View.GONE);
            fertilizer_linear.setVisibility(View.GONE);
            other_agri_product_linear.setVisibility(View.GONE);
            service_in_rent_linear.setVisibility(View.GONE);
            tree_and_wood_linear.setVisibility(View.GONE);
            agricultural_machinary_ads_linear.setVisibility(View.VISIBLE);
            getAgricultureMachinaryAdsDetails(post_id);
        } else if (category.equalsIgnoreCase("Other Agri Product")) {
            agricultural_ads_linear.setVisibility(View.GONE);
            cattle_ads_linear.setVisibility(View.GONE);
            labourInrent_linear.setVisibility(View.GONE);
            fertilizer_linear.setVisibility(View.GONE);
            other_agri_product_linear.setVisibility(View.VISIBLE);
            service_in_rent_linear.setVisibility(View.GONE);
            tree_and_wood_linear.setVisibility(View.GONE);
            agricultural_machinary_ads_linear.setVisibility(View.GONE);
            getOtherAgriAdsDetails(post_id);
        } else if (category.equalsIgnoreCase("Service in Rent")) {
            agricultural_ads_linear.setVisibility(View.GONE);
            cattle_ads_linear.setVisibility(View.GONE);
            labourInrent_linear.setVisibility(View.GONE);
            fertilizer_linear.setVisibility(View.GONE);
            other_agri_product_linear.setVisibility(View.GONE);
            service_in_rent_linear.setVisibility(View.VISIBLE);
            agricultural_machinary_ads_linear.setVisibility(View.GONE);
            tree_and_wood_linear.setVisibility(View.GONE);
            getServiceInRentsDetails(post_id);
        } else if (category.equalsIgnoreCase("Tree and Woods")) {
            agricultural_ads_linear.setVisibility(View.GONE);
            cattle_ads_linear.setVisibility(View.GONE);
            labourInrent_linear.setVisibility(View.GONE);
            fertilizer_linear.setVisibility(View.GONE);
            other_agri_product_linear.setVisibility(View.GONE);
            service_in_rent_linear.setVisibility(View.GONE);
            agricultural_machinary_ads_linear.setVisibility(View.GONE);
            tree_and_wood_linear.setVisibility(View.VISIBLE);
            getTreeAndWoodsDetails(post_id);
        }
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
        };
        postRequest.setRetryPolicy(new DefaultRetryPolicy(30000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(context).add(postRequest);
    }

    private void getCattleDetails(String post_id) {
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
        String url = "https://ekrishibazaar.com/api/ads/cattleads/" + post_id + "/?toedit=" + post_id;
        StringRequest postRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.v("response", response);
                        try {
                            mProgressDialog.dismiss();
                            JSONObject obj = new JSONObject(response);
                            JSONObject cattle_type_obj = obj.getJSONObject("cattle_type");
                            JSONObject category_obj = cattle_type_obj.getJSONObject("category");
                            JSONObject cattle_breed_obj = obj.getJSONObject("cattle_breed");
                            JSONObject milk_produced_obj = obj.getJSONObject("milk_produced");

                            JSONObject state_obj = obj.getJSONObject("state");
                            JSONObject district_obj = obj.getJSONObject("district");
                            JSONObject block_obj = obj.getJSONObject("block");

                            String category_name = category_obj.getString("category_name");
                            cattle_name_tv.setText(cattle_type_obj.getString("cattle_name"));

                            cattle_price_tv.setText(obj.getString("price"));

                            state_tv.setText(state_obj.getString("state_name"));
                            district_tv.setText(district_obj.getString("district_name"));
                            block_tv.setText(block_obj.getString("block_name"));
                            village_tv.setText(obj.getString("village"));
                            cattle_breed_tv.setText(cattle_breed_obj.getString("cattle_breed"));
                            milk_per_day_tv.setText(milk_produced_obj.getString("milk_per_day"));
                            additional_info_tv.setText(obj.getString("additional_information"));
                            number_of_births_tv.setText(obj.getString("number_of_child"));

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

        };
        postRequest.setRetryPolicy(new DefaultRetryPolicy(30000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(context).add(postRequest);
    }

    private void getFertilizerAdsDetails(String post_id) {
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
        String url = "https://ekrishibazaar.com/api/ads/fertilizersandpestisides/" + post_id + "/";
        StringRequest postRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.v("response", response);
                        try {
                            mProgressDialog.dismiss();
                            JSONObject obj = new JSONObject(response);
                            JSONObject chemical_product_obj = obj.getJSONObject("chemical_product");
                            JSONObject category_obj = chemical_product_obj.getJSONObject("category");
                            JSONObject chemical_type_obj = obj.getJSONObject("chemical_type");

                            JSONObject state_obj = obj.getJSONObject("state");
                            JSONObject district_obj = obj.getJSONObject("district");
                            JSONObject block_obj = obj.getJSONObject("block");

                            String category_name = category_obj.getString("category_name");
                            chemical_name_tv.setText(chemical_product_obj.getString("chemical_product_name"));
                            type_of_chemical_tv.setText(chemical_type_obj.getString("type_of_chemical"));
                            fertilizer_price_tv.setText(obj.getString("price"));
                            per_unit_tv.setText(obj.getString("per_unit"));
                            paking_tv.setText(obj.getString("packing"));
                            additional_details_tv.setText(obj.getString("additional_information"));
                            state_tv.setText(state_obj.getString("state_name"));
                            district_tv.setText(district_obj.getString("district_name"));
                            block_tv.setText(block_obj.getString("block_name"));
                            village_tv.setText(obj.getString("village"));

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


        };
        postRequest.setRetryPolicy(new DefaultRetryPolicy(30000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(context).add(postRequest);
    }

    private void getLabourAdsDetails(String post_id) {
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
        String url = "https://ekrishibazaar.com/api/ads/labourinrentads/" + post_id + "/";
        StringRequest postRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.v("response", response);
                        try {
                            mProgressDialog.dismiss();
                            JSONObject obj = new JSONObject(response);
                            JSONObject labour_expertiset_obj = obj.getJSONObject("labour_expertise");
                            JSONObject available_labour_obj = obj.getJSONObject("available_labour");
                            JSONObject reaching_time_on_site_obj = obj.getJSONObject("reaching_time_on_site");

                            JSONObject state_obj = obj.getJSONObject("state");
                            JSONObject district_obj = obj.getJSONObject("district");
                            JSONObject block_obj = obj.getJSONObject("block");

                            labour_expertise_tv.setText(labour_expertiset_obj.getString("expertise"));
                            available_hour_tv.setText(available_labour_obj.getString("available_labour"));
                            reaching_time_status_tv.setText(reaching_time_on_site_obj.getString("reaching_time_status"));
                            labour_price_tv.setText(obj.getString("price"));
                            labour_additional_information_tv.setText(obj.getString("additional_information"));
                            state_tv.setText(state_obj.getString("state_name"));
                            district_tv.setText(district_obj.getString("district_name"));
                            block_tv.setText(block_obj.getString("block_name"));
                            village_tv.setText(obj.getString("village"));

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
        };
        postRequest.setRetryPolicy(new DefaultRetryPolicy(30000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(context).add(postRequest);
    }

    private void getAgricultureMachinaryAdsDetails(String post_id) {
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
        String url = "https://ekrishibazaar.com/api/ads/agricultralmachinaryads/" + post_id + "/";
        StringRequest postRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.v("response", response);
                        try {
                            mProgressDialog.dismiss();
                            JSONObject obj = new JSONObject(response);
                            JSONObject machine_obj = obj.getJSONObject("machine");
                            JSONObject machine_brand_obj = obj.getJSONObject("machine_brand");
                            JSONObject machine_status_obj = obj.getJSONObject("machine_status");
                            JSONObject warranty_status_obj = obj.getJSONObject("warranty_status");

                            JSONObject state_obj = obj.getJSONObject("state");
                            JSONObject district_obj = obj.getJSONObject("district");
                            JSONObject block_obj = obj.getJSONObject("block");

                            machine_type_tv.setText(machine_obj.getString("machine_name"));
                            machine_brand_tv.setText(machine_brand_obj.getString("machinery_brand"));
                            warranty_status_tv.setText(warranty_status_obj.getString("warranty_status"));
                            agriculture_machinary_price_tv.setText(obj.getString("price"));
                            agriculture_machinary_additional_details_tv.setText(obj.getString("additional_information"));
                            state_tv.setText(state_obj.getString("state_name"));
                            district_tv.setText(district_obj.getString("district_name"));
                            block_tv.setText(block_obj.getString("block_name"));
                            village_tv.setText(obj.getString("village"));

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
        };
        postRequest.setRetryPolicy(new DefaultRetryPolicy(30000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(context).add(postRequest);
    }

    private void getOtherAgriAdsDetails(String post_id) {
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
        String url = "https://ekrishibazaar.com/api/ads/otheragriproducts/" + post_id + "/";
        StringRequest postRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.v("response", response);
                        try {
                            mProgressDialog.dismiss();
                            JSONObject obj = new JSONObject(response);
                            JSONObject types_obj = obj.getJSONObject("types");

                            JSONObject state_obj = obj.getJSONObject("state");
                            JSONObject district_obj = obj.getJSONObject("district");
                            JSONObject block_obj = obj.getJSONObject("block");

                            agri_product_name_tv.setText(types_obj.getString("type_of_product"));
                            agri_product_price_tv.setText(obj.getString("price"));

                            otheragri_additional_info_tv.setText(obj.getString("additional_information"));
                            state_tv.setText(state_obj.getString("state_name"));
                            district_tv.setText(district_obj.getString("district_name"));
                            block_tv.setText(block_obj.getString("block_name"));
                            village_tv.setText(obj.getString("village"));

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
        };
        postRequest.setRetryPolicy(new DefaultRetryPolicy(30000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(context).add(postRequest);
    }

    private void getServiceInRentsDetails(String post_id) {
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
        String url = "https://ekrishibazaar.com/api/ads/serviceinrent/" + post_id + "/";
        StringRequest postRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.v("response", response);
                        try {
                            mProgressDialog.dismiss();
                            JSONObject obj = new JSONObject(response);
                            JSONObject select_work_obj = obj.getJSONObject("select_work");
                            JSONObject reaching_on_time_obj = obj.getJSONObject("reaching_on_time");
                            JSONObject service_machine_obj = obj.getJSONObject("service_machine");

                            JSONObject state_obj = obj.getJSONObject("state");
                            JSONObject district_obj = obj.getJSONObject("district");
                            JSONObject block_obj = obj.getJSONObject("block");
                            service_machine_name_tv.setText(service_machine_obj.getString("service_machine_name"));
                            service_tv.setText(select_work_obj.getString("work_name"));
                            reaching_in_time_tv.setText(reaching_on_time_obj.getString("service_status"));
                            service_price_tv.setText(obj.getString("price"));

                            service_additional_details_tv.setText(obj.getString("additional_information"));
                            state_tv.setText(state_obj.getString("state_name"));
                            district_tv.setText(district_obj.getString("district_name"));
                            block_tv.setText(block_obj.getString("block_name"));
                            village_tv.setText(obj.getString("village"));

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
        };
        postRequest.setRetryPolicy(new DefaultRetryPolicy(30000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(context).add(postRequest);
    }

    private void getTreeAndWoodsDetails(String post_id) {
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
        String url = "https://ekrishibazaar.com/api/ads/treeandwoodads/" + post_id + "/";
        StringRequest postRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.v("response", response);
                        try {
                            mProgressDialog.dismiss();
                            JSONObject obj = new JSONObject(response);
                            JSONObject wood_type_obj = obj.getJSONObject("wood_type");
                            JSONObject available_obj = obj.getJSONObject("available");

                            JSONObject state_obj = obj.getJSONObject("state");
                            JSONObject district_obj = obj.getJSONObject("district");
                            JSONObject block_obj = obj.getJSONObject("block");
                            wood_name_tv.setText(wood_type_obj.getString("wood_name"));

                            wood_price_tv.setText(obj.getString("price"));
                            wood_quantity_tv.setText(obj.getString("quantity"));
                            wood_additional_details_tv.setText(obj.getString("additional_information"));
                            state_tv.setText(state_obj.getString("state_name"));
                            district_tv.setText(district_obj.getString("district_name"));
                            block_tv.setText(block_obj.getString("block_name"));
                            village_tv.setText(obj.getString("village"));

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
