package com.service.ekrishibazaar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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
import com.service.ekrishibazaar.adapter.AgricultureAdsAdapter;
import com.service.ekrishibazaar.adapter.NotificationAdapter;
import com.service.ekrishibazaar.model.AgricultureAdsModel;
import com.service.ekrishibazaar.model.NotificationModel;
import com.service.ekrishibazaar.util.PrefsHelper;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class NotificationActivity extends AppCompatActivity {

    ImageView back_image;
    RecyclerView notification_recylerview;
    Context context;
    ArrayList<NotificationModel> notification_list = new ArrayList();
    TextView no_msg_tv;
    private NotificationAdapter Adapter;
    private RecyclerView.LayoutManager LayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        init();
    }

    String token;

    private void init() {
        context = this;
        token = PrefsHelper.getString(context, "token");
        back_image = findViewById(R.id.back_image);
        notification_recylerview = findViewById(R.id.notification_recylerview);
        no_msg_tv = findViewById(R.id.no_msg_tv);
        back_image.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        NotificationActivity.super.onBackPressed();
                    }
                }
        );
        getNotification();
    }

    private void getNotification() {
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
        String url = "https://www.ekrishibazaar.com/api/usernotification/";
        StringRequest postRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.v("response", response);
                        try {
                            mProgressDialog.dismiss();
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray data_array = jsonObject.getJSONArray("results");

                            NotificationModel m;
                            for (int i = 0; i < data_array.length(); i++) {
                                m = new NotificationModel();
                                JSONObject obj = data_array.getJSONObject(i);
                                m.setFrom(obj.getString("from_user"));
                                m.setMsg(obj.getString("message"));
                                m.setDate(obj.getString("notification_date"));
                                notification_list.add(m);
                            }
                            if (notification_list.size() > 0) {
                                notification_recylerview.setHasFixedSize(true);
                                LayoutManager = new LinearLayoutManager(context);
                                Adapter = new NotificationAdapter(context, notification_list);
                                notification_recylerview.setLayoutManager(LayoutManager);
                                notification_recylerview.setAdapter(Adapter);

                            } else {
                                no_msg_tv.setVisibility(View.VISIBLE);
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