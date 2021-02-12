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

public class PostLabourAdsActivity extends AppCompatActivity {
    String post_id;
    Context context;
    Button submit_btn;
    MaterialBetterSpinner no_of_labour_spinner, labour_expertise_spinner, reaching_time_spinner, price_option_spinner, state_spinner, district_spinner, block_spinner;
    EditText paking_et, price_et, village_name_et, additional_info_et;
    ImageView back_image, product_image_imageview1, product_image_imageview2, product_image_imageview3, clear_imageview1, clear_imageview2, clear_imageview3;

    ArrayList labour_expertise_list = new ArrayList();
    ArrayList reaching_time_list = new ArrayList();
    ArrayList number_of_labour_list = new ArrayList();
    ArrayList pricing_options_list = new ArrayList();
    ArrayList state_list = new ArrayList();
    ArrayList district_list = new ArrayList();
    ArrayList block_list = new ArrayList();

    String[] appPermissions = {Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
    String super_category, category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_labour_ads);
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
        no_of_labour_spinner = findViewById(R.id.no_of_labour_spinner);
        labour_expertise_spinner = findViewById(R.id.labour_expertise_spinner);
        reaching_time_spinner = findViewById(R.id.reaching_time_spinner);
        price_option_spinner = findViewById(R.id.price_option_spinner);
        state_spinner = findViewById(R.id.state_spinner);
        district_spinner = findViewById(R.id.district_spinner);
        block_spinner = findViewById(R.id.block_spinner);
        submit_btn = findViewById(R.id.submit_btn);
        paking_et = findViewById(R.id.paking_et);
        price_et = findViewById(R.id.price_et);
        village_name_et = findViewById(R.id.village_name_et);
        additional_info_et = findViewById(R.id.additional_info_et);
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
                            TedBottomPicker.with(PostLabourAdsActivity.this)
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
                        PostLabourAdsActivity.super.onBackPressed();
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
                            TedBottomPicker.with(PostLabourAdsActivity.this)
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
                            TedBottomPicker.with(PostLabourAdsActivity.this)
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

//        number_of_labour_list.add("Less than 5");
//        number_of_labour_list.add("Less than 10");
//        number_of_labour_list.add("More than 10");
//        number_of_labour_list.add("More than 20");
//
//
//        ArrayAdapter<String> productAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, number_of_labour_list);
//        no_of_labour_spinner.setAdapter(productAdapter);

//        reaching_time_list.add("Next Day");
//        reaching_time_list.add("Within 48 hours");
//        reaching_time_list.add("Within 72 hours");
//
//        ArrayAdapter<String> reachingtimeAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, reaching_time_list);
//        reaching_time_spinner.setAdapter(reachingtimeAdapter);

//        select_product_type_spinner.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                if (s != null && !s.toString().isEmpty() && !s.equals("Select Chemical Type")) {
//                    getChemicalTypeList(s.toString());
//                }
//            }
//        });
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
        getLabourExperstiseList();
        getStates();
        getPricingOptoins();
        getReachingTimeStatus();
        getNumberoflabour();
    }

    void getLabourExperstiseList() {
        labour_expertise_list.clear();
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
                "https://ekrishibazaar.com/api/ads/labourexpertise/",
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
                                labour_expertise_list.add((obj.getString("expertise")));
                            }
                            ArrayAdapter<String> productAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, labour_expertise_list);
                            labour_expertise_spinner.setAdapter(productAdapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            mProgressDialog.dismiss();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        labour_expertise_list.add("Select Chemical Type");
                        ArrayAdapter<String> productAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, labour_expertise_list);
                        labour_expertise_spinner.setAdapter(productAdapter);
                        mProgressDialog.dismiss();
                        // Do something when error occurred
                    }
                }
        );
        // Add JsonArrayRequest to the RequestQueue
        VolleySingleton.getInstance(context).addToRequestQueue(jsonArrayRequest);
    }

    void getPricingOptoins() {
        pricing_options_list.clear();
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
                "https://ekrishibazaar.com/api/ads/labourcharges/",
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
                                pricing_options_list.add((obj.getString("per")));
                            }
                            ArrayAdapter<String> productAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, pricing_options_list);
                            price_option_spinner.setAdapter(productAdapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            pricing_options_list.add("Select Pricing Options");
                            ArrayAdapter<String> productAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, pricing_options_list);
                            price_option_spinner.setAdapter(productAdapter);
                            mProgressDialog.dismiss();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        pricing_options_list.add("Select Pricing Options");
                        ArrayAdapter<String> productAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, pricing_options_list);
                        price_option_spinner.setAdapter(productAdapter);
                        mProgressDialog.dismiss();
                    }
                }
        );
        // Add JsonArrayRequest to the RequestQueue
        VolleySingleton.getInstance(context).addToRequestQueue(jsonArrayRequest);
    }

    void getNumberoflabour() {
        number_of_labour_list.clear();
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
                "https://ekrishibazaar.com/api/ads/labouravailable/",
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
                                number_of_labour_list.add((obj.getString("available_labour")));
                            }
                            ArrayAdapter<String> productAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, number_of_labour_list);
                            no_of_labour_spinner.setAdapter(productAdapter);

                        } catch (JSONException e) {
                            e.printStackTrace();

                            mProgressDialog.dismiss();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        number_of_labour_list.add("Select Number of Labour");
                        ArrayAdapter<String> productAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, number_of_labour_list);
                        no_of_labour_spinner.setAdapter(productAdapter);
                        mProgressDialog.dismiss();
                    }
                }
        );
        // Add JsonArrayRequest to the RequestQueue
        VolleySingleton.getInstance(context).addToRequestQueue(jsonArrayRequest);
    }

    void getReachingTimeStatus() {
        reaching_time_list.clear();
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
                "https://ekrishibazaar.com/api/ads/labourreachingtimeonsite/",
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
                                reaching_time_list.add((obj.getString("reaching_time_status")));
                            }
                            ArrayAdapter<String> productAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, reaching_time_list);
                            reaching_time_spinner.setAdapter(productAdapter);

                        } catch (JSONException e) {
                            e.printStackTrace();

                            mProgressDialog.dismiss();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        reaching_time_list.add("Select Reaching Time");
                        ArrayAdapter<String> productAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, reaching_time_list);
                        reaching_time_spinner.setAdapter(productAdapter);
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
        String url = "https://ekrishibazaar.com/api/ads/labourinrentads/";
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
                params.put("labour_expertise", labour_expertise_spinner.getText().toString());
                params.put("available_labour", no_of_labour_spinner.getText().toString());
                params.put("reaching_on_time", reaching_time_spinner.getText().toString());

                params.put("product_price", price_et.getText().toString());
                params.put("'price_by", price_option_spinner.getText().toString());
                params.put("additional_information", additional_info_et.getText().toString());
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
        if (no_of_labour_spinner.getText().toString().isEmpty()) {
            b = false;
            no_of_labour_spinner.setError("Required");
            no_of_labour_spinner.requestFocus();
            Toast.makeText(this, "Select Numbero Labour", Toast.LENGTH_SHORT).show();
            return b;
        } else if (labour_expertise_spinner.getText().toString().isEmpty()) {
            b = false;
            labour_expertise_spinner.setError("Required");
            labour_expertise_spinner.requestFocus();
            Toast.makeText(this, "SelectLabour Expertise", Toast.LENGTH_SHORT).show();
            return b;
        } else if (reaching_time_spinner.getText().toString().isEmpty()) {
            b = false;
            reaching_time_spinner.setError("Required");
            reaching_time_spinner.requestFocus();
            Toast.makeText(this, "Select Reaching Time", Toast.LENGTH_SHORT).show();
            return b;
        } else if (price_option_spinner.getText().toString().isEmpty()) {
            b = false;
            price_option_spinner.setError("Required");
            price_option_spinner.requestFocus();
            Toast.makeText(this, "Enter Pricing Options", Toast.LENGTH_SHORT).show();
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
        String url = "https://ekrishibazaar.com/api/ads/labourinrentads/" + post_id + "/";
        StringRequest postRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.v("response", response);
                        try {
                            mProgressDialog.dismiss();
                            JSONObject obj = new JSONObject(response);
                            JSONObject labour_expertise_obj = obj.getJSONObject("labour_expertise");
                            JSONObject price_by_obj = obj.getJSONObject("price_by");
                            JSONObject available_labour_obj = obj.getJSONObject("available_labour");

                            JSONObject state_obj = obj.getJSONObject("state");
                            JSONObject district_obj = obj.getJSONObject("district");
                            JSONObject block_obj = obj.getJSONObject("block");
                            labour_expertise_spinner.setText(labour_expertise_obj.getString("expertise"));
                            reaching_time_spinner.setText(obj.getString("reaching_time_on_site"));
                            price_option_spinner.setText(price_by_obj.getString("per"));
                            no_of_labour_spinner.setText(available_labour_obj.getString("available_labour"));
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

        String url = "https://ekrishibazaar.com/api/ads/labourinrentads/" + post_id + "/?toedit=optm/";
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
                params.put("labour_expertise", labour_expertise_spinner.getText().toString());
                params.put("available_labour", no_of_labour_spinner.getText().toString());
                params.put("reaching_on_time", reaching_time_spinner.getText().toString());

                params.put("product_price", price_et.getText().toString());
                params.put("'price_by", price_option_spinner.getText().toString());
                params.put("additional_information", additional_info_et.getText().toString());
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
