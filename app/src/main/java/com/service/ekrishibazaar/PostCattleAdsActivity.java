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
import com.google.android.material.textfield.TextInputLayout;
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

public class PostCattleAdsActivity extends AppCompatActivity {

    String post_id;
    Context context;
    Button submit_btn;
    MaterialBetterSpinner select_cattle_type_spinner, select_cattle_breed_spinner, milk_produced_spinner, price_type_spinner, preganent_spinner, state_spinner, district_spinner, block_spinner;
    EditText number_of_births_et, price_et, village_name_et, additional_info_et, pregnancy_month_et;
    ImageView back_image, product_image_imageview1, product_image_imageview2, product_image_imageview3, clear_imageview1, clear_imageview2, clear_imageview3;

    ArrayList cattle_list = new ArrayList();
    ArrayList cattle_breed_list = new ArrayList();
    ArrayList price_type_list = new ArrayList();
    ArrayList state_list = new ArrayList();
    ArrayList district_list = new ArrayList();
    ArrayList block_list = new ArrayList();
    ArrayList preganent_list = new ArrayList();
    ArrayList milk_produced_per_day_list = new ArrayList();

    String[] appPermissions = {Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
    String super_category, category;
    TextInputLayout preganency_month_inputLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_cattle_ads);
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
        back_image = findViewById(R.id.back_image);
        select_cattle_type_spinner = findViewById(R.id.select_cattle_type_spinner);
        select_cattle_breed_spinner = findViewById(R.id.select_cattle_breed_spinner);
        milk_produced_spinner = findViewById(R.id.milk_produced_spinner);
        price_type_spinner = findViewById(R.id.price_type_spinner);
        state_spinner = findViewById(R.id.state_spinner);
        district_spinner = findViewById(R.id.district_spinner);
        block_spinner = findViewById(R.id.block_spinner);
        preganent_spinner = findViewById(R.id.preganent_spinner);

        price_et = findViewById(R.id.price_et);
        village_name_et = findViewById(R.id.village_name_et);
        additional_info_et = findViewById(R.id.additional_info_et);
        number_of_births_et = findViewById(R.id.number_of_births_et);
        pregnancy_month_et = findViewById(R.id.pregnancy_month_et);
        preganency_month_inputLayout = findViewById(R.id.preganency_month_inputLayout);


        product_image_imageview1 = findViewById(R.id.product_image_imageview1);
        clear_imageview1 = findViewById(R.id.clear_imageview1);
        product_image_imageview2 = findViewById(R.id.product_image_imageview2);
        clear_imageview2 = findViewById(R.id.clear_imageview2);
        product_image_imageview3 = findViewById(R.id.product_image_imageview3);
        clear_imageview3 = findViewById(R.id.clear_imageview3);
        submit_btn = findViewById(R.id.submit_btn);
        product_image_imageview1.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (checkPermissions()) {
                            TedBottomPicker.with(PostCattleAdsActivity.this)
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
                        PostCattleAdsActivity.super.onBackPressed();
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
                            TedBottomPicker.with(PostCattleAdsActivity.this)
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
                            TedBottomPicker.with(PostCattleAdsActivity.this)
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


        cattle_list.add("Select Cattle Type");
        ArrayAdapter<String> productAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, cattle_list);
        select_cattle_type_spinner.setAdapter(productAdapter);

        preganent_list.add("Yes");
        preganent_list.add("No");
        ArrayAdapter<String> preganent_listAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, preganent_list);
        preganent_spinner.setAdapter(preganent_listAdapter);

        cattle_breed_list.add("Select Cattle Breed");
        ArrayAdapter<String> breedAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, cattle_breed_list);
        select_cattle_breed_spinner.setAdapter(breedAdapter);

        price_type_list.add("Fixed");
        price_type_list.add("Negotiable");
        ArrayAdapter<String> statausAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, price_type_list);
        price_type_spinner.setAdapter(statausAdapter);

        milk_produced_per_day_list.add("2 Liter");
        milk_produced_per_day_list.add("3 Liter");
        milk_produced_per_day_list.add("4 Liter");
        milk_produced_per_day_list.add("5 Liter");
        milk_produced_per_day_list.add("6 Liter");
        milk_produced_per_day_list.add("7 Liter");
        milk_produced_per_day_list.add("8 Liter");
        milk_produced_per_day_list.add("9 Liter");
        milk_produced_per_day_list.add("10 Liter");
        milk_produced_per_day_list.add("11 Liter");
        milk_produced_per_day_list.add("12 Liter");
        milk_produced_per_day_list.add("13 Liter");
        milk_produced_per_day_list.add("14 Liter");
        milk_produced_per_day_list.add("15 Liter");
        milk_produced_per_day_list.add("16 Liter");
        milk_produced_per_day_list.add("17 Liter");
        milk_produced_per_day_list.add("18 Liter");
        milk_produced_per_day_list.add("19 Liter");
        milk_produced_per_day_list.add("20 Liter");
        milk_produced_per_day_list.add("21 Liter");
        milk_produced_per_day_list.add("22 Liter");
        milk_produced_per_day_list.add("23 Liter");
        milk_produced_per_day_list.add("24 Liter");
        milk_produced_per_day_list.add("25 Liter");
        milk_produced_per_day_list.add("26 Liter");
        milk_produced_per_day_list.add("27 Liter");
        milk_produced_per_day_list.add("28 Liter");
        milk_produced_per_day_list.add("29 Liter");
        milk_produced_per_day_list.add("30 Liter");
        milk_produced_per_day_list.add("N/A");
        ArrayAdapter<String> avaibilityAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, milk_produced_per_day_list);
        milk_produced_spinner.setAdapter(avaibilityAdapter);


        district_list.add("Select District");
        ArrayAdapter<String> districtAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, district_list);
        district_spinner.setAdapter(districtAdapter);

        block_list.add("Select Block");
        ArrayAdapter<String> blkAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, block_list);
        block_spinner.setAdapter(blkAdapter);

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
        select_cattle_type_spinner.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s != null && !s.toString().isEmpty()) {
                    getCattleBreed(s.toString());
//                    getProductStatus(s.toString());
                }
            }
        });
        preganent_spinner.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s != null && !s.toString().isEmpty() && s.toString().equalsIgnoreCase("Yes")) {
                    preganency_month_inputLayout.setVisibility(View.VISIBLE);
                } else {
                    preganency_month_inputLayout.setVisibility(View.GONE);
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
        getCattleList();
        getStates();
    }

    void getCattleList() {
        cattle_list.clear();
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
                "https://ekrishibazaar.com/api/ads/cattletypes/",
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
                                cattle_list.add((obj.getString("cattle_name")));
                            }
                            ArrayAdapter<String> productAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, cattle_list);
                            select_cattle_type_spinner.setAdapter(productAdapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            cattle_list.add("Select Cattle Type");
                            ArrayAdapter<String> productAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, cattle_list);
                            select_cattle_type_spinner.setAdapter(productAdapter);
                            mProgressDialog.dismiss();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        cattle_list.add("Select Cattle Type");
                        ArrayAdapter<String> productAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, cattle_list);
                        select_cattle_type_spinner.setAdapter(productAdapter);
                        mProgressDialog.dismiss();
                        // Do something when error occurred
                    }
                }
        );
        // Add JsonArrayRequest to the RequestQueue
        VolleySingleton.getInstance(context).addToRequestQueue(jsonArrayRequest);
    }

    void getCattleBreed(String cattle_name) {
        cattle_breed_list.clear();
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
                "https://ekrishibazaar.com/api/ads/cattlebreeds/?search=" + cattle_name,
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
                                cattle_breed_list.add((obj.getString("cattle_breed")));
                            }
                            ArrayAdapter<String> productAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, cattle_breed_list);
                            select_cattle_breed_spinner.setAdapter(productAdapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            cattle_breed_list.add("Select Cattle Breed");
                            ArrayAdapter<String> productAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, cattle_breed_list);
                            select_cattle_breed_spinner.setAdapter(productAdapter);
                            mProgressDialog.dismiss();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        cattle_breed_list.add("Select Cattle Breed");
                        ArrayAdapter<String> productAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, cattle_breed_list);
                        select_cattle_breed_spinner.setAdapter(productAdapter);
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

    boolean check() {
        boolean b = true;
        if (select_cattle_type_spinner.getText().toString().isEmpty()) {
            b = false;
            select_cattle_type_spinner.setError("Required");
            select_cattle_type_spinner.requestFocus();
            Toast.makeText(this, "Select Cattle Type", Toast.LENGTH_SHORT).show();
            return b;
        } else if (select_cattle_breed_spinner.getText().toString().isEmpty()) {
            b = false;
            select_cattle_breed_spinner.setError("Required");
            select_cattle_breed_spinner.requestFocus();
            Toast.makeText(this, "Select Cattle Breed", Toast.LENGTH_SHORT).show();
            return b;
        } else if (number_of_births_et.getText().toString().isEmpty()) {
            b = false;
            number_of_births_et.setError("Required");
            number_of_births_et.requestFocus();
            Toast.makeText(this, "Enter No off Births", Toast.LENGTH_SHORT).show();
            return b;
        } else if (milk_produced_spinner.getText().toString().isEmpty()) {
            b = false;
            milk_produced_spinner.setError("Required");
            milk_produced_spinner.requestFocus();
            Toast.makeText(this, "Select Milk Produced Per day", Toast.LENGTH_SHORT).show();
            return b;
        } else if (price_et.getText().toString().isEmpty()) {
            b = false;
            price_et.setError("Required");
            price_et.requestFocus();
            Toast.makeText(this, "Enter Product Price", Toast.LENGTH_SHORT).show();
            return b;
        } else if (price_type_spinner.getText().toString().isEmpty()) {
            b = false;
            price_type_spinner.setError("Required");
            price_type_spinner.requestFocus();
            Toast.makeText(this, "Select Price Type", Toast.LENGTH_SHORT).show();
            return b;
        } else if (preganent_spinner.getText().toString().isEmpty()) {
            b = false;
            preganent_spinner.setError("Required");
            preganent_spinner.requestFocus();
            Toast.makeText(this, "Select if Preganent", Toast.LENGTH_SHORT).show();
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
        } else if (!preganent_spinner.getText().toString().isEmpty() && preganent_spinner.getText().equals("Yes")) {
            if (pregnancy_month_et.getText().toString().isEmpty()) {
                b = false;
                pregnancy_month_et.setError("Required");
                pregnancy_month_et.requestFocus();
                Toast.makeText(this, "Enter Preganency Month", Toast.LENGTH_SHORT).show();
                return b;
            }
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
        String url = "https://ekrishibazaar.com/api/ads/cattleads/";
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
//                          String id = obj.getString("id");
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
//
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
                params.put("category", category);
                params.put("cattle_name", select_cattle_type_spinner.getText().toString());
                params.put("cattle_breed", select_cattle_breed_spinner.getText().toString());

                if (!number_of_births_et.getText().toString().isEmpty()) {
                    params.put("number_of_child", number_of_births_et.getText().toString());
                } else {
                    params.put("number_of_child", String.valueOf(0));
                }
                params.put("milk_produced", milk_produced_spinner.getText().toString());
                params.put("product_price", price_et.getText().toString());
                params.put("if_preganent", price_et.getText().toString());
                if (!pregnancy_month_et.getText().toString().isEmpty()) {
                    params.put("preganency_month", pregnancy_month_et.getText().toString());
                } else {
                    params.put("preganency_month", String.valueOf(0));
                }
                params.put("price_status", price_type_spinner.getText().toString());
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

        String url = "https://ekrishibazaar.com/api/ads/cattleads/" + post_id + "/";
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
                params.put("category", category);
                params.put("cattle_name", select_cattle_type_spinner.getText().toString());
                params.put("cattle_breed", select_cattle_breed_spinner.getText().toString());

                if (!number_of_births_et.getText().toString().isEmpty()) {
                    params.put("number_of_child", number_of_births_et.getText().toString());
                } else {
                    params.put("number_of_child", String.valueOf(0));
                }
                params.put("milk_produced", milk_produced_spinner.getText().toString());
                params.put("product_price", price_et.getText().toString());
                params.put("if_preganent", price_et.getText().toString());
                if (!pregnancy_month_et.getText().toString().isEmpty()) {
                    params.put("preganency_month", pregnancy_month_et.getText().toString());
                } else {
                    params.put("preganency_month", String.valueOf(0));
                }
                params.put("price_status", price_type_spinner.getText().toString());
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
        String url = "https://ekrishibazaar.com/api/ads/cattleads/" + post_id + "/?toedit=" + post_id;
        StringRequest postRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.v("response", response);
                        try {
                            mProgressDialog.dismiss();
                            JSONObject obj = new JSONObject(response);
                            JSONObject product_obj = obj.getJSONObject("cattle_type");
                            JSONObject milk_produced_obj = obj.getJSONObject("milk_produced");
                            JSONObject product_breed_obj = obj.getJSONObject("cattle_breed");
                            JSONObject price_type_obj = obj.getJSONObject("price_fixed");
//                          JSONObject product_packaging_type_obj = obj.getJSONObject("product_packaging_type");
                            JSONObject state_obj = obj.getJSONObject("state");
                            JSONObject district_obj = obj.getJSONObject("district");
                            JSONObject block_obj = obj.getJSONObject("block");
                            select_cattle_type_spinner.setText(product_obj.getString("cattle_name"));
                            select_cattle_breed_spinner.setText(product_breed_obj.getString("cattle_breed"));
                            number_of_births_et.setText(obj.getString("number_of_child"));
                            milk_produced_spinner.setText(milk_produced_obj.getString("milk_per_day"));
                            price_type_spinner.setText(price_type_obj.getString("price_status"));
                            preganent_spinner.setText(obj.getString("if_preganent"));
                            pregnancy_month_et.setText(obj.getString("preganency_month"));
                            price_et.setText(obj.getString("price"));
                            price_type_spinner.setText(price_type_obj.getString("price_status"));

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

    public byte[] getFileDataFromDrawable(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
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
}
