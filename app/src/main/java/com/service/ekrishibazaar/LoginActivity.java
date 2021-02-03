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
import android.widget.EditText;
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
import com.service.ekrishibazaar.util.PrefsHelper;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {

    TextView sign_up_tv;
    Button login_btn;
    EditText mobile_number_et, password_et;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
    }

    void init() {
        context = this;
        mobile_number_et = findViewById(R.id.mobile_number_et);
        password_et = findViewById(R.id.password_et);
        sign_up_tv = findViewById(R.id.sign_up_tv);
        login_btn = findViewById(R.id.login_btn);
        login_btn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (validate()) {
                            login();
                        }
                    }
                }
        );
        sign_up_tv.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(LoginActivity.this, RegistrationActivity.class);
                        startActivity(intent);
                    }
                }
        );
    }

    private void login() {
        final ProgressDialog mProgressDialog = new ProgressDialog(this);
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

        StringRequest postRequest = new StringRequest(Request.Method.POST, "https://www.ekrishibazaar.com/api/signin/",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            mProgressDialog.dismiss();
                            JSONObject jsonObject = new JSONObject(response);
                            Log.e("Response", response.toString());
                            if (!jsonObject.getString("token").isEmpty()) {
                                Toast.makeText(getApplicationContext(), "Successfully Logged In", Toast.LENGTH_SHORT).show();
                                PrefsHelper.putString(LoginActivity.this, "token", jsonObject.getString("token"));
                                PrefsHelper.putString(LoginActivity.this, "first_name", jsonObject.getString("first_name"));
                                PrefsHelper.putString(LoginActivity.this, "last_name", jsonObject.getString("last_name"));
                                PrefsHelper.putString(LoginActivity.this, "vid", jsonObject.getString("vid"));
                                PrefsHelper.putString(LoginActivity.this, "blocked", jsonObject.getString("blocked"));

                                //open fragment
                                Intent intent = new Intent(LoginActivity.this,DashboardActivity.class);
                                startActivity(intent);

                                finish();
                            } else {
                                Toast.makeText(getApplicationContext(), jsonObject.getString("detail"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
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
                        Toast.makeText(context, "Enter Valid Details", Toast.LENGTH_SHORT).show();
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("username", mobile_number_et.getText().toString());
                params.put("password", password_et.getText().toString());

                return params;
            }
        };
        postRequest.setRetryPolicy(new DefaultRetryPolicy(30000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(this).add(postRequest);
    }

    boolean validate() {
        boolean b = true;
        if (mobile_number_et.getText().toString().isEmpty()) {
            b = false;
            Toast.makeText(LoginActivity.this, "Enter Registered Mobile Number", Toast.LENGTH_SHORT).show();
            mobile_number_et.requestFocus();
            return b;
        } else if (password_et.getText().toString().isEmpty()) {
            b = false;
            Toast.makeText(LoginActivity.this, "Enter Registered Mobile Number", Toast.LENGTH_SHORT).show();
            password_et.requestFocus();
            return b;
        }
        return b;
    }
}


/*
{"token":"4ce6416d20e05e3610c9210f88e2cc1e30cf3c96","first_name":"Piyush","last_name":"Shukla","vid":100000029,"blocked":false}
 */