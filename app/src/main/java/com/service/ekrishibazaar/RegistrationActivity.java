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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
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
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.service.ekrishibazaar.util.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class RegistrationActivity extends AppCompatActivity {

    Button reg_btn;
    Context context;
    TextView sign_in;
    ImageView back_image;
    Spinner state_spinner, district_spinner, block_spinner;
    ArrayList<String> state_list = new ArrayList<>();
    ArrayList<String> district_list = new ArrayList<>();
    ArrayList<String> block_list = new ArrayList<>();
    EditText first_name_et, last_name_et, mobile_number_et, password_et, villege_et;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        init();
    }

    private void init() {
        context = this;
        state_spinner = findViewById(R.id.state_spinner);
        district_spinner = findViewById(R.id.district_spinner);
        block_spinner = findViewById(R.id.block_spinner);
        back_image = findViewById(R.id.back_image);
        sign_in = findViewById(R.id.sign_in);
        reg_btn = findViewById(R.id.reg_btn);
        first_name_et = findViewById(R.id.first_name_et);
        last_name_et = findViewById(R.id.last_name_et);
        mobile_number_et = findViewById(R.id.mobile_number_et);
        password_et = findViewById(R.id.password_et);
        villege_et = findViewById(R.id.villege_et);
        state_list.add(getString(R.string.Select_State));
        district_list.add(getString(R.string.Select_District));
        block_list.add(getString(R.string.Select_Block));
        back_image.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        RegistrationActivity.super.onBackPressed();
                    }
                }
        );
        sign_in.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }
                }
        );
        state_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    getDistrict(state_list.get(position));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        district_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position != 0) {
                    getBlocks(district_list.get(position));
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        reg_btn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (validate()) {
                            registration();
                        }
                    }
                }
        );
        ArrayAdapter<String> district_adapter = new ArrayAdapter<>(context,
                R.layout.spinner_layout, district_list);
        district_adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_layout);
        district_spinner.setAdapter(district_adapter);

        ArrayAdapter<String> state_adapter = new ArrayAdapter<>(context,
                R.layout.spinner_layout, state_list);
        district_adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_layout);
        state_spinner.setAdapter(state_adapter);

        ArrayAdapter<String> block_adapter = new ArrayAdapter<>(context,
                R.layout.spinner_layout, block_list);
        district_adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_layout);
        block_spinner.setAdapter(block_adapter);

        getStates();
    }

    void getStates() {

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
        // Initialize a new RequestQueue instance

        // Initialize a new JsonArrayRequest instance
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                "https://ekrishibazaar.com/api/state/",
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        // Do something with response
                        //mTextView.setText(response.toString());
                        // Process the JSON
                        try {
                            mProgressDialog.dismiss();
                            // Loop through the array elements
                            for (int i = 0; i < response.length(); i++) {
                                // Get current json object
                                JSONObject obj = response.getJSONObject(i);
                                state_list.add(obj.getString("state_name"));
                            }
                            ArrayAdapter<String> state_adapter = new ArrayAdapter<>(context,
                                    R.layout.spinner_layout, state_list);
                            state_adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_layout);
                            state_spinner.setAdapter(state_adapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            mProgressDialog.dismiss();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        mProgressDialog.dismiss();
                        // Do something when error occurred

                    }
                }
        );
        // Add JsonArrayRequest to the RequestQueue
        VolleySingleton.getInstance(context).addToRequestQueue(jsonArrayRequest);
    }

    void getDistrict(String state_name) {

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
        // Initialize a new RequestQueue instance

        // Initialize a new JsonArrayRequest instance
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                "https://ekrishibazaar.com/api/district/",
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        // Do something with response
                        //mTextView.setText(response.toString());
                        // Process the JSON
                        try {
                            mProgressDialog.dismiss();
                            // Loop through the array elements
                            for (int i = 0; i < response.length(); i++) {
                                // Get current json object
                                JSONObject obj = response.getJSONObject(i);
                                district_list.add(obj.getString("district_name"));
                            }
                            ArrayAdapter<String> district_adapter = new ArrayAdapter<>(context,
                                    R.layout.spinner_layout, district_list);
                            district_adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_layout);
                            district_spinner.setAdapter(district_adapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            mProgressDialog.dismiss();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        mProgressDialog.dismiss();
                        // Do something when error occurred

                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("search", state_name);
                return params;
            }
        };

        // Add JsonArrayRequest to the RequestQueue
        VolleySingleton.getInstance(context).addToRequestQueue(jsonArrayRequest);
    }

    void getBlocks(String districtName) {

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
        // Initialize a new RequestQueue instance
        // Initialize a new JsonArrayRequest instance
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                "https://ekrishibazaar.com/api/block/",
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        // Do something with response
                        //mTextView.setText(response.toString());
                        // Process the JSON
                        try {
                            mProgressDialog.dismiss();
                            // Loop through the array elements
                            for (int i = 0; i < response.length(); i++) {
                                // Get current json object
                                JSONObject obj = response.getJSONObject(i);
                                block_list.add(obj.getString("block_name"));
                            }
                            ArrayAdapter<String> block_adapter = new ArrayAdapter<>(context,
                                    R.layout.spinner_layout, block_list);
                            block_adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_layout);
                            block_spinner.setAdapter(block_adapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            mProgressDialog.dismiss();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        mProgressDialog.dismiss();
                        // Do something when error occurred

                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("search", districtName);
                return params;
            }
        };
        // Add JsonArrayRequest to the RequestQueue
        VolleySingleton.getInstance(context).addToRequestQueue(jsonArrayRequest);
    }

    private void registration() {
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

        StringRequest postRequest = new StringRequest(Request.Method.POST, "https://ekrishibazaar.com/api/signup/",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.e("response", response.toString());
                            Object json = new JSONTokener(response).nextValue();
                            if (json instanceof JSONObject) {
                                JSONObject obj = new JSONObject(response);
                                JSONArray x = obj.getJSONArray("detail");
                                String y = x.getString(0);
                                Toast.makeText(getApplicationContext(), y, Toast.LENGTH_SHORT).show();
                            }
                            //you have an object
                            else if (json instanceof JSONArray) {
                                JSONArray res_array = new JSONArray(response);
                                String z = res_array.getString(0);
                                if (z.equals("Success")) {
                                    Toast.makeText(getApplicationContext(), "Registered Successfully", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }
                            mProgressDialog.dismiss();
                        } catch (
                                JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Log.i("jsonObjectRequest", "Error, Status Code " + error.networkResponse.statusCode);

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
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put("first_name", first_name_et.getText().toString());
                params.put("last_name", last_name_et.getText().toString());
                params.put("username", mobile_number_et.getText().toString());
                params.put("mobile_number", mobile_number_et.getText().toString());
                params.put("state", state_spinner.getSelectedItem().toString());
                params.put("password", password_et.getText().toString());
                params.put("block", block_spinner.getSelectedItem().toString());
                params.put("district", district_spinner.getSelectedItem().toString());
                params.put("village_name", villege_et.getText().toString());
                return params;
            }
        };
        postRequest.setRetryPolicy(new

                DefaultRetryPolicy(30000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(this).

                add(postRequest);

    }

    boolean validate() {
        boolean b = true;
        if (first_name_et.getText().toString().isEmpty()) {
            b = false;
            Toast.makeText(context, "Enter First Name", Toast.LENGTH_SHORT).show();
            first_name_et.requestFocus();
            return b;
        } else if (last_name_et.getText().toString().isEmpty()) {
            b = false;
            Toast.makeText(context, "Enter Last Name", Toast.LENGTH_SHORT).show();
            last_name_et.requestFocus();
            return b;
        } else if (mobile_number_et.getText().toString().isEmpty()) {
            b = false;
            Toast.makeText(context, "Enter Mobile Number", Toast.LENGTH_SHORT).show();
            mobile_number_et.requestFocus();
            return b;
        } else if (password_et.getText().toString().isEmpty()) {
            b = false;
            Toast.makeText(context, "Enter Password", Toast.LENGTH_SHORT).show();
            password_et.requestFocus();
            return b;
        } else if (mobile_number_et.getText().length() < 10) {
            b = false;
            Toast.makeText(context, "Enter Valid Mobile No", Toast.LENGTH_SHORT).show();
            mobile_number_et.requestFocus();
            return b;
        } else if (state_spinner.getSelectedItemPosition() == 0) {
            b = false;
            Toast.makeText(context, "Select State", Toast.LENGTH_SHORT).show();
            state_spinner.requestFocus();
            return b;
        } else if (district_spinner.getSelectedItemPosition() == 0) {
            b = false;
            Toast.makeText(context, "Select District", Toast.LENGTH_SHORT).show();
            district_spinner.requestFocus();
            return b;
        } else if (block_spinner.getSelectedItemPosition() == 0) {
            b = false;
            Toast.makeText(context, "Select Block", Toast.LENGTH_SHORT).show();
            block_spinner.requestFocus();
            return b;
        }
        return b;
    }
}