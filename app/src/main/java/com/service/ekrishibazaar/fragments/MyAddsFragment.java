package com.service.ekrishibazaar.fragments;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.service.ekrishibazaar.LoginActivity;
import com.service.ekrishibazaar.R;
import com.service.ekrishibazaar.adapter.MyAdsAdapter;
import com.service.ekrishibazaar.model.MyAdsModel;
import com.service.ekrishibazaar.util.PrefsHelper;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MyAddsFragment extends Fragment {

    Context context;
    RecyclerView myads_recyclerview;
    ArrayList<MyAdsModel> myads_list = new ArrayList();
    private MyAdsAdapter agAdapter;
    private RecyclerView.LayoutManager LayoutManager;
    TextView no_record_tv;
    LinearLayout sign_in_linear;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_my_adds, container, false);
        init(root);
        return root;
    }

    String token;

    private void init(View root) {
        context = getActivity();
        token = PrefsHelper.getString(context, "token");

        myads_recyclerview = root.findViewById(R.id.myads_recyclerview);
        sign_in_linear = root.findViewById(R.id.sign_in_linear);
        no_record_tv = root.findViewById(R.id.no_record_tv);
        if (token == null || token.isEmpty()) {
            sign_in_linear.setVisibility(View.VISIBLE);
        } else {
            getAgricultureList();
        }
        sign_in_linear.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), LoginActivity.class);
                        startActivity(intent);
                        getActivity().getFragmentManager().popBackStack();
                    }
                }
        );
    }

    private void getAgricultureList() {
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
        String url = "https://ekrishibazaar.com/api/ads/userads/";
        StringRequest postRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.v("response", response);
                        try {
                            mProgressDialog.dismiss();
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray data_array = jsonObject.getJSONArray("results");

                            MyAdsModel m;
                            for (int i = 0; i < data_array.length(); i++) {
                                JSONObject obj = data_array.getJSONObject(i);
                                Log.e("index==", String.valueOf(i));

                                JSONObject user_obj = obj.getJSONObject("user");
                                JSONObject user_2_obj = user_obj.getJSONObject("user");
                                m = new MyAdsModel();
                                m.setUser_first_name(user_2_obj.getString("first_name"));
                                m.setUser_last_name(user_2_obj.getString("last_name"));
                                m.setDate_joined(user_2_obj.getString("date_joined"));
                                m.setProfile_image(user_obj.getString("image"));
                                JSONObject state_obj = user_obj.getJSONObject("state");
                                JSONObject district_obj = user_obj.getJSONObject("district");
                                JSONObject block_obj = user_obj.getJSONObject("block");

                                m.setState(state_obj.getString("state_name"));
                                m.setDistrict(district_obj.getString("district_name"));
                                m.setBlock(block_obj.getString("block_name"));
                                m.setVid(user_obj.getString("vid"));
                                m.setVillage_name(user_obj.getString("village_name"));
                                m.setMobile_number(user_obj.getString("mobile_number"));
                                m.setPost_id(obj.getString("post_id"));

////                            JSONObject service_machine_obj = obj.getJSONObject("product");
//                                    JSONObject product_status_obj = obj.getJSONObject("product_status");

//                                    m.setStatus(product_status_obj.getString("product_status"));
//                                    JSONObject product_breed_obj = obj.getJSONObject("product_breed");
//                                    m.setProduct_breed(product_breed_obj.getString("product_breed"));
//                                    JSONObject category_obj = service_machine_obj.getJSONObject("category");
//                                    m.setCategory_name(category_obj.getString("category_name"));
//                                    m.setCategory_image(category_obj.getString("image"));
//                                    m.setProduct_quantity(obj.getString("product_quantity"));
//                                    m.setProduct_quantity_by(obj.getString("product_quantity_by"));
//                                    m.setProduct_name(service_machine_obj.getString("product_name"));
//                                    m.setProduct_price_by(obj.getString("product_price_by"));
//                                    m.setPackaging_cost_bearer(obj.getString("packaging_cost_bearer"));
                                m.setPosted_on(obj.getString("posted_on"));
                                m.setPrice(obj.getString("price"));

                                m.setProduct_image1(obj.getString("product_image1"));
                                m.setProduct_image2(obj.getString("product_image2"));
                                m.setProduct_image3(obj.getString("product_image3"));
                                m.setAdditional_info(obj.getString("additional_information"));
                                m.setPost_id(obj.getString("post_id"));
//                                    boolean b = catgeory_type.equals(category_obj.getString("category_name"));
//                                    String x = category_obj.getString("category_name");
//                                    String y = catgeory_type;
//                                    Log.e("catgeory_type", String.valueOf(b));
//                                    if (catgeory_type.equals(category_obj.getString("category_name"))) {
                                myads_list.add(m);
                            }
                            if (myads_list.size() > 0) {
                                myads_recyclerview.setHasFixedSize(true);
                                LayoutManager = new LinearLayoutManager(context);
                                agAdapter = new MyAdsAdapter(context, myads_list);
                                myads_recyclerview.setLayoutManager(LayoutManager);
                                myads_recyclerview.setAdapter(agAdapter);

                            } else {
                                no_record_tv.setVisibility(View.VISIBLE);
                            }
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
}