package com.service.ekrishibazaar;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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
import com.service.ekrishibazaar.util.PrefsHelper;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ViewProfileActivity extends AppCompatActivity {

    Context context;
    ImageView profile_imageview, back_image;
    TextView name_tv, vid_tv, mobile_number_tv, block_tv, district_tv, state_tv;
    String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);
        context = this;
        token = PrefsHelper.getString(context, "token");
        profile_imageview = findViewById(R.id.profile_imageview);
        back_image = findViewById(R.id.back_image);
        name_tv = findViewById(R.id.name_tv);
        vid_tv = findViewById(R.id.vid_tv);
        mobile_number_tv = findViewById(R.id.mobile_number_tv);
        block_tv = findViewById(R.id.block_tv);
        district_tv = findViewById(R.id.district_tv);
        state_tv = findViewById(R.id.state_tv);

        Intent intent = getIntent();
        if (intent.hasExtra("name")) {
            Glide.with(context)
                    .load(intent.getStringExtra("profile_url"))
                    .fitCenter()
                    .into(profile_imageview);
            name_tv.setText("Name : " + intent.getStringExtra("name"));
            vid_tv.setText("VID  : " + intent.getStringExtra("vid"));
            mobile_number_tv.setText("Mobile Number : " + intent.getStringExtra("mobile_number"));
            block_tv.setText("Block :" + intent.getStringExtra("block"));
            district_tv.setText("District : " + intent.getStringExtra("district"));
            state_tv.setText("State : " + intent.getStringExtra("state"));
        } else {
            getProfile();
        }

        back_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ViewProfileActivity.super.onBackPressed();
            }
        });
    }

    private void getProfile() {
        final ProgressDialog mProgressDialog = new ProgressDialog(context);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage("Loading...");
        mProgressDialog.show();
        String url = "https://ekrishibazaar.com/api/edituserinfo/";
        StringRequest postRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.v("response", response);
                        try {
                            mProgressDialog.dismiss();
                            JSONObject obj = new JSONObject(response);
                            name_tv.setText("Name : " + obj.getString("first_name") + " " + obj.getString("last_name"));
                            vid_tv.setText("VID  : " + obj.getString("vid"));
                            mobile_number_tv.setText("Mobile Number : " + obj.getString("phone_number"));
                            block_tv.setText("Block :" + obj.getString("block"));
                            district_tv.setText("District : " + obj.getString("district"));
                            state_tv.setText("State : " + obj.getString("state"));
                            Glide.with(context)
                                    .load(obj.getString("image"))
                                    .fitCenter()
                                    .into(profile_imageview);
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