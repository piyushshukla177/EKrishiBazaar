package com.service.ekrishibazaar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NetworkResponse;
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
import com.service.ekrishibazaar.util.PrefsHelper;
import com.service.ekrishibazaar.util.VolleyMultipartRequest;
import com.service.ekrishibazaar.util.VolleySingleton;
import com.squareup.picasso.Picasso;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import gun0912.tedbottompicker.TedBottomPicker;
import gun0912.tedbottompicker.TedBottomSheetDialogFragment;

public class PostAgriculturalMachinaryAdsActivity extends AppCompatActivity {


    String post_id;
    Context context;
    Button submit_btn;
    MaterialBetterSpinner machinary_type_spinner, brand_spinner, product_status_spinner, warrenty_status_spinner, who_pay_pkg_charges_spinner, state_spinner, district_spinner, block_spinner;
    EditText used_in_months_et, warranty_et, price_et, village_name_et, additional_info_et;
    ImageView back_image, product_image_imageview1, product_image_imageview2, product_image_imageview3, clear_imageview1, clear_imageview2, clear_imageview3;

    ArrayList machinary_type_list = new ArrayList();
    ArrayList brand_list = new ArrayList();
    ArrayList product_status_list = new ArrayList();
    ArrayList warrenty_status_list = new ArrayList();
    ArrayList who_will_pay_pkg_list = new ArrayList();
    ArrayList state_list = new ArrayList();
    ArrayList district_list = new ArrayList();
    ArrayList block_list = new ArrayList();

    String[] appPermissions = {Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
    String super_category, category;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_agricultural_machinary_ads);
        init();
    }

    public File imageFile1, imageFile2, imageFile3;
    Uri uri1, uri2, uri3;
    String token;

    void init() {
        context = this;
        token = PrefsHelper.getString(context, "token");
        checkPermissions();
        super_category = getIntent().getStringExtra("super_category");
        if (getIntent().hasExtra("post_id")) {
            post_id = getIntent().getStringExtra("post_id");
            getAdsDetails(post_id);
        }
        if (getIntent().hasExtra("category")) {
            category = getIntent().getStringExtra("category");
        }
        machinary_type_spinner = findViewById(R.id.machinary_type_spinner);
        brand_spinner = findViewById(R.id.brand_spinner);
        product_status_spinner = findViewById(R.id.product_status_spinner);
        warrenty_status_spinner = findViewById(R.id.warrenty_status_spinner);
        state_spinner = findViewById(R.id.state_spinner);
        district_spinner = findViewById(R.id.district_spinner);
        block_spinner = findViewById(R.id.block_spinner);
        submit_btn = findViewById(R.id.submit_btn);
        who_pay_pkg_charges_spinner = findViewById(R.id.who_pay_pkg_charges_spinner);
        price_et = findViewById(R.id.price_et);
        village_name_et = findViewById(R.id.village_name_et);
        additional_info_et = findViewById(R.id.additional_info_et);
        used_in_months_et = findViewById(R.id.used_in_months_et);
        warranty_et = findViewById(R.id.warranty_et);
        back_image = findViewById(R.id.back_image);
        product_image_imageview1 = findViewById(R.id.product_image_imageview1);
        clear_imageview1 = findViewById(R.id.clear_imageview1);
        product_image_imageview2 = findViewById(R.id.product_image_imageview2);
        clear_imageview2 = findViewById(R.id.clear_imageview2);
        product_image_imageview3 = findViewById(R.id.product_image_imageview3);
        clear_imageview3 = findViewById(R.id.clear_imageview3);

        product_image_imageview1.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (checkPermissions()) {
                            TedBottomPicker.with(PostAgriculturalMachinaryAdsActivity.this)
                                    .show(new TedBottomSheetDialogFragment.OnImageSelectedListener() {
                                        @Override
                                        public void onImageSelected(Uri uri) {
                                            uri1 = uri;
                                            imageFile1 = new File(uri.getPath());
                                            product_image_imageview1.setImageURI(uri);
                                            clear_imageview1.setVisibility(View.VISIBLE);
                                        }
                                    });
                        }

                    }
                }
        );
        back_image.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        PostAgriculturalMachinaryAdsActivity.super.onBackPressed();
                    }
                }
        );
        clear_imageview1.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        product_image_imageview1.setImageResource(R.drawable.add_image);
                        clear_imageview1.setVisibility(View.GONE);
                    }
                }
        );
        product_image_imageview2.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (checkPermissions()) {
                            TedBottomPicker.with(PostAgriculturalMachinaryAdsActivity.this)
                                    .show(new TedBottomSheetDialogFragment.OnImageSelectedListener() {
                                        @Override
                                        public void onImageSelected(Uri uri) {
                                            uri2 = uri;
                                            imageFile2 = new File(uri.getPath());
                                            product_image_imageview2.setImageURI(uri);
                                            clear_imageview2.setVisibility(View.VISIBLE);
                                        }
                                    });
                        }

                    }
                }
        );
        clear_imageview2.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        product_image_imageview2.setImageResource(R.drawable.add_image);
                        clear_imageview2.setVisibility(View.GONE);
                    }
                }
        );
        product_image_imageview3.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (checkPermissions()) {
                            TedBottomPicker.with(PostAgriculturalMachinaryAdsActivity.this)
                                    .show(new TedBottomSheetDialogFragment.OnImageSelectedListener() {
                                        @Override
                                        public void onImageSelected(Uri uri) {
                                            uri3 = uri;
                                            imageFile3 = new File(uri.getPath());
                                            product_image_imageview3.setImageURI(uri);
                                            clear_imageview3.setVisibility(View.VISIBLE);
                                        }
                                    });
                        }
                    }
                }
        );
        clear_imageview3.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        product_image_imageview1.setImageResource(R.drawable.add_image);
                        clear_imageview1.setVisibility(View.GONE);
                    }
                }
        );
        who_will_pay_pkg_list.add("Buyer");
        who_will_pay_pkg_list.add("Seller");

        ArrayAdapter<String> productAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, who_will_pay_pkg_list);
        who_pay_pkg_charges_spinner.setAdapter(productAdapter);
        state_spinner.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                getDistrict(s.toString());
            }
        });

        district_spinner.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s != null && !s.equals("Select District")) {
                    getBlocks(s.toString());
                }
            }
        });
        submit_btn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (check()) {
                            if (super_category != null && super_category.equalsIgnoreCase("edit")) {
                                Edit();
                            } else {
                                Save();
                            }
                        }
                    }
                }
        );

        state_spinner.setText(PrefsHelper.getString(this, "state"));
        district_spinner.setText(PrefsHelper.getString(context, "distict"));
        block_spinner.setText(PrefsHelper.getString(context, "block"));
        getAgricultrMachinaryList();
        getBrands();
        getProductStatus();
        getWarrentyStatus();
        getStates();
    }

    void getAgricultrMachinaryList() {
        machinary_type_list.clear();
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
        // Initialize a new JsonArrayRequest instance
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                "https://ekrishibazaar.com/api/ads/agriculturalmachinary/",
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
                                machinary_type_list.add((obj.getString("machine_name")));
                            }
                            ArrayAdapter<String> productAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, machinary_type_list);
                            machinary_type_spinner.setAdapter(productAdapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            mProgressDialog.dismiss();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        machinary_type_list.add("Select Chemical Type");
                        ArrayAdapter<String> productAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, machinary_type_list);
                        machinary_type_spinner.setAdapter(productAdapter);
                        mProgressDialog.dismiss();
                        // Do something when error occurred
                    }
                }
        );
        // Add JsonArrayRequest to the RequestQueue
        VolleySingleton.getInstance(context).addToRequestQueue(jsonArrayRequest);
    }

    void getBrands() {
        brand_list.clear();
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
        // Initialize a new JsonArrayRequest instance
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                "https://ekrishibazaar.com/api/ads/machinebrand/",
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
                                brand_list.add((obj.getString("machinery_brand")));
                            }
                            ArrayAdapter<String> productAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, brand_list);
                            brand_spinner.setAdapter(productAdapter);

                        } catch (JSONException e) {
                            e.printStackTrace();

                            mProgressDialog.dismiss();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        brand_list.add("Select Brand");
                        ArrayAdapter<String> productAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, brand_list);
                        brand_spinner.setAdapter(productAdapter);
                        mProgressDialog.dismiss();
                    }
                }
        );
        // Add JsonArrayRequest to the RequestQueue
        VolleySingleton.getInstance(context).addToRequestQueue(jsonArrayRequest);
    }

    void getProductStatus() {
        product_status_list.clear();
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
        // Initialize a new JsonArrayRequest instance
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                "https://ekrishibazaar.com/api/ads/machinestatus/",
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
                                product_status_list.add((obj.getString("machine_status")));
                            }
                            ArrayAdapter<String> productAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, product_status_list);
                            product_status_spinner.setAdapter(productAdapter);

                        } catch (JSONException e) {
                            e.printStackTrace();

                            mProgressDialog.dismiss();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        product_status_list.add("Select Product Status");
                        ArrayAdapter<String> productAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, product_status_list);
                        product_status_spinner.setAdapter(productAdapter);
                        mProgressDialog.dismiss();
                    }
                }
        );
        // Add JsonArrayRequest to the RequestQueue
        VolleySingleton.getInstance(context).addToRequestQueue(jsonArrayRequest);
    }

    void getWarrentyStatus() {
        warrenty_status_list.clear();
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
        // Initialize a new JsonArrayRequest instance
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                "https://ekrishibazaar.com/api/ads/warrantstatus/",
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
                                warrenty_status_list.add((obj.getString("warranty_status")));
                            }
                            ArrayAdapter<String> productAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, warrenty_status_list);
                            warrenty_status_spinner.setAdapter(productAdapter);

                        } catch (JSONException e) {
                            e.printStackTrace();

                            mProgressDialog.dismiss();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        warrenty_status_list.add("Select Reaching Time");
                        ArrayAdapter<String> productAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, warrenty_status_list);
                        warrenty_status_spinner.setAdapter(productAdapter);
                        mProgressDialog.dismiss();
                    }
                }
        );
        // Add JsonArrayRequest to the RequestQueue
        VolleySingleton.getInstance(context).addToRequestQueue(jsonArrayRequest);
    }

    void getStates() {
        state_list.clear();
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
                            ArrayAdapter<String> stateAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, state_list);
                            state_spinner.setAdapter(stateAdapter);
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
                        state_list.add("Select State");
                        ArrayAdapter<String> stateAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, state_list);
                        state_spinner.setAdapter(stateAdapter);
                        // Do something when error occurred

                    }
                }
        );
        // Add JsonArrayRequest to the RequestQueue
        VolleySingleton.getInstance(context).addToRequestQueue(jsonArrayRequest);
    }

    void getDistrict(String state_name) {
        district_list.clear();
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
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                "https://ekrishibazaar.com/api/district/?search=" + state_name,
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                            mProgressDialog.dismiss();
                            // Loop through the array elements
                            for (int i = 0; i < response.length(); i++) {
                                // Get current json object
                                JSONObject obj = response.getJSONObject(i);
                                district_list.add(obj.getString("district_name"));
                            }
                            ArrayAdapter<String> stateAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, district_list);
                            district_spinner.setAdapter(stateAdapter);
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
                        district_list.add("Select District");
                        ArrayAdapter<String> stateAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, district_list);
                        district_spinner.setAdapter(stateAdapter);
                        // Do something when error occurred

                    }
                }
        ) {
        };
        // Add JsonArrayRequest to the RequestQueue
        VolleySingleton.getInstance(context).addToRequestQueue(jsonArrayRequest);
    }

    void getBlocks(String districtName) {
        block_list.clear();
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
                "https://ekrishibazaar.com/api/block/?search=" + districtName,
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
                            ArrayAdapter<String> stateAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, block_list);
                            block_spinner.setAdapter(stateAdapter);
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
                        block_list.add("Block");
                        ArrayAdapter<String> stateAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, block_list);
                        block_spinner.setAdapter(stateAdapter);
                        // Do something when error occurred
                    }
                }
        ) {
        };
        // Add JsonArrayRequest to the RequestQueue
        VolleySingleton.getInstance(context).addToRequestQueue(jsonArrayRequest);
    }

    public int PERMISSION_CODE = 100;

    public boolean checkPermissions() {
        List<String> lsitPermissionsNeeded = new ArrayList<>();
        for (String perm : appPermissions) {
            if (ContextCompat.checkSelfPermission(this, perm) != PackageManager.PERMISSION_GRANTED) {
                lsitPermissionsNeeded.add(perm);
            }
        }
        //check for non granted permissions
        if (!lsitPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, lsitPermissionsNeeded.toArray(new String[lsitPermissionsNeeded.size()]), PERMISSION_CODE);
            return false;
        }
        //app has all permissions proceed ahead
        return true;
    }

    public void Save() {
        final ProgressDialog mProgressDialog = new ProgressDialog(context);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage("Posting Ads...");
        mProgressDialog.setCancelable(false);
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.setOnCancelListener(new Dialog.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                // DO SOME STUFF HERE
            }
        });
        mProgressDialog.show();
        String url = "https://ekrishibazaar.com/api/ads/agricultralmachinaryads/";
        //our custom volley request
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, url,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        try {
                            mProgressDialog.hide();

                            JSONObject obj = new JSONObject(new String(response.data));
                            if (obj.has("id")) {
                                Toast.makeText(context, "Ad Posted Successfully", Toast.LENGTH_SHORT).show();
                                finish();
                            } else if (obj.has("detail")) {
                                Toast.makeText(context, obj.getString("detail"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            mProgressDialog.hide();
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        mProgressDialog.hide();
//                        Toast.makeText(context, "In Failure", Toast.LENGTH_SHORT).show();
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            /*
             * If you want to add more parameters with the image
             * you can do it here
             * here we have only one parameter with the image
             * which is tags
             * */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "Token " + token);
                return params;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();
                params.put("machine_name", machinary_type_spinner.getText().toString());
                params.put("machine_brand", brand_spinner.getText().toString());
                params.put("machine_status", product_status_spinner.getText().toString());

                params.put("months_in_use", used_in_months_et.getText().toString());
                params.put("warranty_status", warrenty_status_spinner.getText().toString());
                params.put("remaining_warranty_month", warranty_et.getText().toString());
                params.put("product_price", price_et.getText().toString());
                params.put("additional_information", additional_info_et.getText().toString());
                params.put("packed_by", additional_info_et.getText().toString());
                params.put("super_category", super_category);
                params.put("state", state_spinner.getText().toString());
                params.put("district", district_spinner.getText().toString());
                params.put("block", block_spinner.getText().toString());
                params.put("village", village_name_et.getText().toString());
                Log.e("post_ads_params", params.toString());
                if (imageFile1 == null) {
                    params.put("photo1", "undefined");
                }
                if (imageFile2 == null) {
                    params.put("photo2", "undefined");
                }
                if (imageFile3 == null) {
                    params.put("photo3", "undefined");
                }
                return params;
            }

            /*
             * Here we are passing image by renaming it with a unique name
             */
            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                long imagename = System.currentTimeMillis();

                DataPart dp1 = null, dp2 = null, dp3 = null;
                File imgFile1;
                if (uri1 != null) {
                    imgFile1 = new File(uri1.toString());
                    Bitmap test_image_bitmap1 = BitmapFactory.decodeFile(imageFile1.getAbsolutePath());
                    if (test_image_bitmap1 != null) {
                        dp1 = new DataPart(imagename + ".png", getFileDataFromDrawable(test_image_bitmap1));
                        params.put("photo1", dp1);
                    }
                }
                File imgFile2;
                if (uri2 != null) {
                    imgFile2 = new File(uri2.toString());
                    Bitmap test_image_bitmap2 = BitmapFactory.decodeFile(imageFile2.getAbsolutePath());
                    if (test_image_bitmap2 != null) {
                        dp2 = new DataPart(imagename + ".png", getFileDataFromDrawable(test_image_bitmap2));
                        params.put("photo2", dp2);
                    }
                }

                File imgFile3;
                if (uri3 != null) {
                    imgFile3 = new File(uri3.toString());
                    Bitmap test_image_bitmap3 = BitmapFactory.decodeFile(imageFile3.getAbsolutePath());
                    if (test_image_bitmap3 != null) {
                        dp3 = new DataPart(imagename + ".png", getFileDataFromDrawable(test_image_bitmap3));
                        params.put("photo3", dp3);
                    }
                }
                Log.e("photo_params", params.toString());
                return params;
            }
        };
        volleyMultipartRequest.setRetryPolicy(new DefaultRetryPolicy(10 * DefaultRetryPolicy.DEFAULT_TIMEOUT_MS, 0, 0));
        //adding the request to volley
        Volley.newRequestQueue(this).add(volleyMultipartRequest);
    }


    boolean check() {
        boolean b = true;
        if (machinary_type_spinner.getText().toString().isEmpty()) {
            b = false;
            machinary_type_spinner.setError("Required");
            machinary_type_spinner.requestFocus();
            Toast.makeText(this, "Select machinary Type", Toast.LENGTH_SHORT).show();
            return b;
        } else if (brand_spinner.getText().toString().isEmpty()) {
            b = false;
            brand_spinner.setError("Required");
            brand_spinner.requestFocus();
            Toast.makeText(this, "Select Brand", Toast.LENGTH_SHORT).show();
            return b;
        } else if (product_status_spinner.getText().toString().isEmpty()) {
            b = false;
            product_status_spinner.setError("Required");
            product_status_spinner.requestFocus();
            Toast.makeText(this, "Select Product Status", Toast.LENGTH_SHORT).show();
            return b;
        } else if (used_in_months_et.getText().toString().isEmpty()) {
            b = false;
            used_in_months_et.setError("Required");
            used_in_months_et.requestFocus();
            Toast.makeText(this, "Enter Used in Months", Toast.LENGTH_SHORT).show();
            return b;
        } else if (warrenty_status_spinner.getText().toString().isEmpty()) {
            b = false;
            warrenty_status_spinner.setError("Required");
            warrenty_status_spinner.requestFocus();
            Toast.makeText(this, "Select Warranty Status", Toast.LENGTH_SHORT).show();
            return b;
        } else if (who_pay_pkg_charges_spinner.getText().toString().isEmpty()) {
            b = false;
            who_pay_pkg_charges_spinner.setError("Required");
            who_pay_pkg_charges_spinner.requestFocus();
            Toast.makeText(this, "Select WHo will Pay Packaging", Toast.LENGTH_SHORT).show();
            return b;
        } else if (price_et.getText().toString().isEmpty()) {
            b = false;
            price_et.setError("Required");
            price_et.requestFocus();
            Toast.makeText(this, "Enter Product Price", Toast.LENGTH_SHORT).show();
            return b;
        } else if (state_spinner.getText().toString().isEmpty()) {
            b = false;
            state_spinner.setError("Required");
            state_spinner.requestFocus();
            Toast.makeText(this, "Select State", Toast.LENGTH_SHORT).show();
            return b;
        } else if (district_spinner.getText().toString().isEmpty()) {
            b = false;
            district_spinner.setError("Required");
            district_spinner.requestFocus();
            Toast.makeText(this, "Select District", Toast.LENGTH_SHORT).show();
            return b;
        } else if (block_spinner.getText().toString().isEmpty()) {
            b = false;
            block_spinner.setError("Required");
            block_spinner.requestFocus();
            Toast.makeText(this, "Select Block", Toast.LENGTH_SHORT).show();
            return b;
        }
        if (uri1 != null && uri1.toString().isEmpty()) {
            b = false;
            Toast.makeText(this, "Select Image1", Toast.LENGTH_SHORT).show();
            return b;
        }
        if (uri2 != null && uri2.toString().isEmpty()) {
            b = false;
            Toast.makeText(this, "Select Image2", Toast.LENGTH_SHORT).show();
            return b;

        }
        if (uri3 != null && uri3.toString().isEmpty()) {
            b = false;
            Toast.makeText(this, "Select Image3", Toast.LENGTH_SHORT).show();
            return b;
        }
        return b;
    }

    public byte[] getFileDataFromDrawable(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
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

                            machinary_type_spinner.setText(machine_obj.getString("machine_name"));
                            brand_spinner.setText(machine_brand_obj.getString("machinery_brand"));
                            product_status_spinner.setText(machine_status_obj.getString("machine_status"));
                            used_in_months_et.setText(obj.getString("months_in_use"));
                            warrenty_status_spinner.setText(warranty_status_obj.getString("warranty_status"));
                            warranty_et.setText(obj.getString("remaining_warranty_month"));
                            who_pay_pkg_charges_spinner.setText(obj.getString("packed_by"));
                            price_et.setText(obj.getString("price"));


                            state_spinner.setText(state_obj.getString("state_name"));
                            district_spinner.setText(district_obj.getString("district_name"));
                            block_spinner.setText(block_obj.getString("block_name"));
                            village_name_et.setText(obj.getString("village"));
                            additional_info_et.setText(obj.getString("additional_information"));
                            Picasso.get().load(obj.getString("product_image1")).resize(60, 60).into(product_image_imageview1);
                            Picasso.get().load(obj.getString("product_image2")).resize(60, 60).into(product_image_imageview2);
                            Picasso.get().load(obj.getString("product_image3")).resize(60, 60).into(product_image_imageview3);
                            try {
                                uri1 = Uri.parse(obj.getString("product_image1"));
                                uri2 = Uri.parse(obj.getString("product_image2"));
                                uri3 = Uri.parse(obj.getString("product_image3"));

                            } catch (Exception e) {
                                e.printStackTrace();
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
        };
        postRequest.setRetryPolicy(new DefaultRetryPolicy(30000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(context).add(postRequest);
    }

    public void Edit() {
        final ProgressDialog mProgressDialog = new ProgressDialog(context);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage("Updating Ads...");
        mProgressDialog.setCancelable(false);
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.setOnCancelListener(new Dialog.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                // DO SOME STUFF HERE
            }
        });
        mProgressDialog.show();

        String url = "https://ekrishibazaar.com/api/ads/agricultralmachinaryads/" + post_id + "/?toedit=optm/";
        //our custom volley request
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.PUT, url,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        try {
                            mProgressDialog.hide();

                            JSONObject obj = new JSONObject(new String(response.data));
                            String id = obj.getString("id");
                            Toast.makeText(context, "Ad Updated Successfully", Toast.LENGTH_SHORT).show();
                            finish();
                        } catch (Exception e) {
                            mProgressDialog.hide();
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        mProgressDialog.hide();
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {
            /*
             * If you want to add more parameters with the image
             * you can do it here
             * here we have only one parameter with the image
             * which is tags
             * */
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> params = new HashMap<String, String>();
                params.put("Authorization", "Token " + token);
                return params;
            }

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String, String> params = new HashMap<>();

                params.put("machine_name", machinary_type_spinner.getText().toString());
                params.put("machine_brand", brand_spinner.getText().toString());
                params.put("machine_status", product_status_spinner.getText().toString());

                params.put("months_in_use", used_in_months_et.getText().toString());
                params.put("warranty_status", warrenty_status_spinner.getText().toString());
                params.put("remaining_warranty_month", warranty_et.getText().toString());
                params.put("product_price", price_et.getText().toString());
                params.put("additional_information", additional_info_et.getText().toString());
                params.put("packed_by", additional_info_et.getText().toString());
                params.put("super_category", super_category);
                params.put("state", state_spinner.getText().toString());
                params.put("district", district_spinner.getText().toString());
                params.put("block", block_spinner.getText().toString());
                params.put("village", village_name_et.getText().toString());
                if (imageFile1 == null) {
                    params.put("photo1", "undefined");
                }
                if (imageFile2 == null) {
                    params.put("photo2", "undefined");
                }
                if (imageFile3 == null) {
                    params.put("photo3", "undefined");
                }

                Log.e("post_ads_params", params.toString());
                return params;
            }

            /*
             * Here we are passing image by renaming it with a unique name
             */
            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                long imagename = System.currentTimeMillis();

                DataPart dp1 = null, dp2 = null, dp3 = null;

                if (imageFile1 != null) {
                    Bitmap test_image_bitmap1 = BitmapFactory.decodeFile(imageFile1.getAbsolutePath());
                    if (test_image_bitmap1 != null) {
                        dp1 = new DataPart(imagename + ".png", getFileDataFromDrawable(test_image_bitmap1));
                        params.put("photo1", dp1);
                    }
                }

                if (imageFile2 != null) {
                    Bitmap test_image_bitmap2 = BitmapFactory.decodeFile(imageFile2.getAbsolutePath());
                    if (test_image_bitmap2 != null) {
                        dp2 = new DataPart(imagename + ".png", getFileDataFromDrawable(test_image_bitmap2));
                        params.put("photo2", dp2);
                    }
                }

                if (imageFile3 != null) {
                    Bitmap test_image_bitmap3 = BitmapFactory.decodeFile(imageFile3.getAbsolutePath());
                    if (test_image_bitmap3 != null) {
                        dp3 = new DataPart(imagename + ".png", getFileDataFromDrawable(test_image_bitmap3));
                        params.put("photo3", dp3);
                    }
                }

                Log.e("photo_params", params.toString());
                return params;
            }
        };
        volleyMultipartRequest.setRetryPolicy(new DefaultRetryPolicy(10 * DefaultRetryPolicy.DEFAULT_TIMEOUT_MS, 0, 0));
        //adding the request to volley
        Volley.newRequestQueue(this).add(volleyMultipartRequest);
    }
}
