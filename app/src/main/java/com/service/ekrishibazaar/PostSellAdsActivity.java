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
import com.service.ekrishibazaar.model.CategoryListModel;
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

public class PostSellAdsActivity extends AppCompatActivity {

    String post_id;
    Context context;
    Button submit_btn;
    MaterialBetterSpinner select_product_category_spinner, select_product_spinner, select_product_breed_spinner, select_product_status_spinner, select_uom_spinner_for_qty, packaging_availability_spinner, who_pay_charges_spinner, state_spinner, district_spinner, block_spinner, select_uom_spinner_for_price, packaging_type_spinner;
    EditText quantity_et, price_et, village_name_et, additional_info_et;
    ImageView back_image, product_image_imageview1, product_image_imageview2, product_image_imageview3, clear_imageview1, clear_imageview2, clear_imageview3;
    ArrayList category_list = new ArrayList();
    ArrayList product_list = new ArrayList();
    ArrayList product_breed_list = new ArrayList();
    ArrayList product_status_list = new ArrayList();
    ArrayList uom_list = new ArrayList();
    ArrayList packiging_availability_list = new ArrayList();
    ArrayList who_pay_extra_list = new ArrayList();
    ArrayList state_list = new ArrayList();
    ArrayList district_list = new ArrayList();
    ArrayList block_list = new ArrayList();
    ArrayList packaging_type_list = new ArrayList();
//  final private int REQUEST_CODE_ASK_PERMISSIONS = 111;
//  public File imageFile;

    String[] appPermissions = {Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
    String super_category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_sell_ads);
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
        back_image = findViewById(R.id.back_image);
        select_product_category_spinner = findViewById(R.id.select_product_category_spinner);
        select_product_spinner = findViewById(R.id.select_product_spinner);
        select_product_breed_spinner = findViewById(R.id.select_product_breed_spinner);
        select_product_status_spinner = findViewById(R.id.select_product_status_spinner);
        select_uom_spinner_for_qty = findViewById(R.id.select_uom_spinner_for_qty);
        packaging_availability_spinner = findViewById(R.id.packaging_availability_spinner);
        packaging_type_spinner = findViewById(R.id.packaging_type_spinner);
        who_pay_charges_spinner = findViewById(R.id.who_pay_charges_spinner);
        state_spinner = findViewById(R.id.state_spinner);
        district_spinner = findViewById(R.id.district_spinner);
        block_spinner = findViewById(R.id.block_spinner);
        quantity_et = findViewById(R.id.quantity_et);
        price_et = findViewById(R.id.price_et);
        village_name_et = findViewById(R.id.village_name_et);
        additional_info_et = findViewById(R.id.additional_info_et);
        select_uom_spinner_for_price = findViewById(R.id.select_uom_spinner_for_price);

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
                            TedBottomPicker.with(PostSellAdsActivity.this)
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
                        PostSellAdsActivity.super.onBackPressed();
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
                            TedBottomPicker.with(PostSellAdsActivity.this)
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
                            TedBottomPicker.with(PostSellAdsActivity.this)
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
        category_list.add("Select Product Category");
        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, category_list);
        select_product_category_spinner.setAdapter(categoryAdapter);

        product_list.add("Select Product");
        ArrayAdapter<String> productAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, product_list);
        select_product_spinner.setAdapter(productAdapter);

        product_breed_list.add("Select Product Breed");
        ArrayAdapter<String> breedAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, product_breed_list);
        select_product_breed_spinner.setAdapter(breedAdapter);

        product_status_list.add("Select Product Breed");
        ArrayAdapter<String> statausAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, product_status_list);
        select_product_status_spinner.setAdapter(statausAdapter);

        packiging_availability_list.add("Yes");
        packiging_availability_list.add("No");
        ArrayAdapter<String> avaibilityAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, packiging_availability_list);
        packaging_availability_spinner.setAdapter(avaibilityAdapter);

        who_pay_extra_list.add("Buyer");
        who_pay_extra_list.add("Seller");
        ArrayAdapter<String> whoAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, who_pay_extra_list);
        who_pay_charges_spinner.setAdapter(whoAdapter);

        packaging_type_list.add("Box");
        packaging_type_list.add("Jute Bag");
        packaging_type_list.add("Polythene");
        packaging_type_list.add("Cartoons");
        ArrayAdapter<String> typeAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, packaging_type_list);
        packaging_type_spinner.setAdapter(typeAdapter);

        uom_list.add("Quintal");
        uom_list.add("Kg");
        uom_list.add("Pieces");
        uom_list.add("Litre");
        uom_list.add("Gram");
        uom_list.add("Ton");
        uom_list.add("Trolley");
        uom_list.add("Truck");
        ArrayAdapter<String> uomAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, uom_list);
        select_uom_spinner_for_qty.setAdapter(uomAdapter);
//      ArrayAdapter<String> uomAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, uom_list);
        select_uom_spinner_for_price.setAdapter(uomAdapter);

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

        select_product_category_spinner.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s != null && !s.toString().isEmpty()) {
                    getProducts(s.toString());
                }
            }
        });

        select_product_spinner.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s != null && !s.toString().isEmpty()) {
                    getProductBreed(s.toString());
                    getProductStatus(s.toString());
                }
            }
        });

        packaging_availability_spinner.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String a = s.toString();
                boolean b = s != null;
                boolean c = !s.toString().isEmpty();
                boolean d = s.toString().equals("Yes");
                if (b && c && d) {
                    packaging_type_spinner.setVisibility(View.VISIBLE);
                } else {
                    packaging_type_spinner.setVisibility(View.GONE);
                }
            }
        });
        submit_btn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (check()) {
//                          upload_pic();
                            if (super_category.equalsIgnoreCase("edit")) {
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
//      state_spinner.setText(PrefsHelper.getString());
        getStates();
        getAllCategories();
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
        // Initialize a new RequestQueue instance
        // Initialize a new JsonArrayRequest instance
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                "https://ekrishibazaar.com/api/district/?search=" + state_name,
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
//            @Override
//            protected Map<String, String> getParams() {
//                Map<String, String> params = new HashMap<String, String>();
//                params.put("search", state_name);
//                return params;
//            }
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
//            @Override
//            protected Map<String, String> getParams() {
//                Map<String, String> params = new HashMap<String, String>();
//                params.put("search", districtName);
//                return params;
//            }
        };
        // Add JsonArrayRequest to the RequestQueue
        VolleySingleton.getInstance(context).addToRequestQueue(jsonArrayRequest);
    }

    void getAllCategories() {
        category_list.clear();
//      view_all_tv.setVisibility(View.GONE);
        product_list.clear();
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
                " https://ekrishibazaar.com/api/ads/allcategory/",
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
                            CategoryListModel m;
                            for (int i = 0; i < response.length(); i++) {
                                // Get current json object
                                m = new CategoryListModel();
                                JSONObject obj = response.getJSONObject(i);
                                category_list.add((obj.getString("category_name")));
//                                m.setProduct_image(obj.getString("image"));
//                                product_list.add(m);
                            }
                            ArrayAdapter<String> categoryAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, category_list);
                            select_product_category_spinner.setAdapter(categoryAdapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            category_list.add("Select Product Category");
                            ArrayAdapter<String> categoryAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, category_list);
                            select_product_category_spinner.setAdapter(categoryAdapter);
                            mProgressDialog.dismiss();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        mProgressDialog.dismiss();
                        category_list.add("Select Product Category");
                        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, category_list);
                        select_product_category_spinner.setAdapter(categoryAdapter);
                        // Do something when error occurred
                    }
                }
        );
        // Add JsonArrayRequest to the RequestQueue
        VolleySingleton.getInstance(context).addToRequestQueue(jsonArrayRequest);
    }

    void getProducts(String category_name) {
        product_list.clear();
        product_list.clear();
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
                "https://ekrishibazaar.com/api/ads/products/?search=" + category_name,
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
                                product_list.add((obj.getString("product_name")));
                            }
                            ArrayAdapter<String> productAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, product_list);
                            select_product_spinner.setAdapter(productAdapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            product_list.add("Select Product");
                            ArrayAdapter<String> productAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, product_list);
                            select_product_spinner.setAdapter(productAdapter);
                            mProgressDialog.dismiss();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        mProgressDialog.dismiss();
                        category_list.add("Select Product Category");
                        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, category_list);
                        select_product_category_spinner.setAdapter(categoryAdapter);
                        // Do something when error occurred
                    }
                }
        );
        // Add JsonArrayRequest to the RequestQueue
        VolleySingleton.getInstance(context).addToRequestQueue(jsonArrayRequest);
    }

    void getProductBreed(String product_name) {
        product_breed_list.clear();
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
                "https://ekrishibazaar.com/api/ads/productbreed/?search=" + product_name,
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
                                product_breed_list.add((obj.getString("product_breed")));
                            }
                            ArrayAdapter<String> productAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, product_breed_list);
                            select_product_breed_spinner.setAdapter(productAdapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            product_breed_list.add("Select Product Breed");
                            ArrayAdapter<String> productAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, product_breed_list);
                            select_product_breed_spinner.setAdapter(productAdapter);
                            mProgressDialog.dismiss();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        mProgressDialog.dismiss();
                        category_list.add("Select Product Category");
                        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, category_list);
                        select_product_category_spinner.setAdapter(categoryAdapter);
                        // Do something when error occurred
                    }
                }
        );
        // Add JsonArrayRequest to the RequestQueue
        VolleySingleton.getInstance(context).addToRequestQueue(jsonArrayRequest);
    }

    void getProductStatus(String product_name) {
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
                "https://ekrishibazaar.com/api/ads/productstatus/?search" + product_name,
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
                                product_status_list.add((obj.getString("product_status")));
                            }
                            ArrayAdapter<String> productAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, product_status_list);
                            select_product_status_spinner.setAdapter(productAdapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            product_breed_list.add("Select Product Status");
                            ArrayAdapter<String> productAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, product_status_list);
                            select_product_status_spinner.setAdapter(productAdapter);
                            mProgressDialog.dismiss();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        mProgressDialog.dismiss();
                        category_list.add("Select Product Category");
                        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, category_list);
                        select_product_category_spinner.setAdapter(categoryAdapter);
                        // Do something when error occurred
                    }
                }
        );
        // Add JsonArrayRequest to the RequestQueue
        VolleySingleton.getInstance(context).addToRequestQueue(jsonArrayRequest);
    }

    boolean check() {
        boolean b = true;
        if (select_product_category_spinner.getText().toString().isEmpty()) {
            b = false;
            select_product_category_spinner.setError("Required");
            select_product_category_spinner.requestFocus();
            Toast.makeText(this, "Select Category", Toast.LENGTH_SHORT).show();
            return b;
        } else if (select_product_spinner.getText().toString().isEmpty()) {
            b = false;
            select_product_spinner.setError("Required");
            select_product_spinner.requestFocus();
            Toast.makeText(this, "Select Product", Toast.LENGTH_SHORT).show();
            return b;
        } else if (select_product_breed_spinner.getText().toString().isEmpty()) {
            b = false;
            select_product_breed_spinner.setError("Required");
            select_product_breed_spinner.requestFocus();
            Toast.makeText(this, "Select Product Breed", Toast.LENGTH_SHORT).show();
            return b;
        } else if (select_product_status_spinner.getText().toString().isEmpty()) {
            b = false;
            select_product_status_spinner.setError("Required");
            select_product_status_spinner.requestFocus();
            Toast.makeText(this, "Select Product Status", Toast.LENGTH_SHORT).show();
            return b;
        } else if (quantity_et.getText().toString().isEmpty()) {
            b = false;
            quantity_et.setError("Required");
            quantity_et.requestFocus();
            Toast.makeText(this, "Enter Product Quantity", Toast.LENGTH_SHORT).show();
            return b;
        } else if (price_et.getText().toString().isEmpty()) {
            b = false;
            price_et.setError("Required");
            price_et.requestFocus();
            Toast.makeText(this, "Enter Product Price", Toast.LENGTH_SHORT).show();
            return b;
        } else if (select_uom_spinner_for_qty.getText().toString().isEmpty()) {
            b = false;
            select_uom_spinner_for_qty.setError("Required");
            select_uom_spinner_for_qty.requestFocus();
            Toast.makeText(this, "Select Qty UOM", Toast.LENGTH_SHORT).show();
            return b;
        } else if (select_uom_spinner_for_price.getText().toString().isEmpty()) {
            b = false;
            select_uom_spinner_for_price.setError("Required");
            select_uom_spinner_for_price.requestFocus();
            Toast.makeText(this, "Select Price UOM", Toast.LENGTH_SHORT).show();
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
        String url = "https://ekrishibazaar.com/api/ads/agriads/";
        //our custom volley request
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, url,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        try {
                            mProgressDialog.hide();

                            JSONObject obj = new JSONObject(new String(response.data));
                            String id = obj.getString("id");
                            Toast.makeText(context, "Ad Posted Successfully", Toast.LENGTH_SHORT).show();
                            finish();
                        } catch (Exception e) {
//                            Toast.makeText(context, "In Catch", Toast.LENGTH_SHORT).show();
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
                params.put("product_name", select_product_spinner.getText().toString());
                params.put("product_breed", select_product_breed_spinner.getText().toString());
                params.put("product_status", select_product_status_spinner.getText().toString());
                params.put("product_quantity", quantity_et.getText().toString());
                params.put("product_quantity_by", select_uom_spinner_for_qty.getText().toString());
                params.put("product_price", price_et.getText().toString());
                params.put("product_price_by", select_uom_spinner_for_price.getText().toString());
                params.put("packing_availibility", packaging_availability_spinner.getText().toString());
                params.put("product_packing_type", packaging_type_spinner.getText().toString());
                params.put("packing_charges", who_pay_charges_spinner.getText().toString());
                params.put("additional_information", additional_info_et.getText().toString());
                params.put("super_category", super_category);
                params.put("state", state_spinner.getText().toString());
                params.put("district", district_spinner.getText().toString());
                params.put("block", block_spinner.getText().toString());
                params.put("village", village_name_et.getText().toString());
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
//                String path1 = FileUtils.getPath(context, uri1);
                File imgFile1 = new File(uri1.toString());
                Bitmap test_image_bitmap1 = BitmapFactory.decodeFile(imageFile1.getAbsolutePath());
                if (test_image_bitmap1 != null) {
                    dp1 = new DataPart(imagename + ".png", getFileDataFromDrawable(test_image_bitmap1));
                    params.put("photo1", dp1);
                }
//              String path2 = FileUtils.getPath(context, uri2);
                File imgFile2 = new File(uri2.toString());
                Bitmap test_image_bitmap2 = BitmapFactory.decodeFile(imageFile2.getAbsolutePath());
                if (test_image_bitmap2 != null) {
                    dp2 = new DataPart(imagename + ".png", getFileDataFromDrawable(test_image_bitmap2));
                    params.put("photo2", dp2);
                }
//              String path3 = FileUtils.getPath(context, uri3);
                File imgFile3 = new File(uri3.toString());
                Bitmap test_image_bitmap3 = BitmapFactory.decodeFile(imageFile3.getAbsolutePath());
                if (test_image_bitmap3 != null) {
                    dp3 = new DataPart(imagename + ".png", getFileDataFromDrawable(test_image_bitmap3));
                    params.put("photo3", dp3);
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
        String url = "https://ekrishibazaar.com/api/ads/agriads/" + post_id + "/";
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
                params.put("product_name", select_product_spinner.getText().toString());
                params.put("product_breed", select_product_breed_spinner.getText().toString());
                params.put("product_status", select_product_status_spinner.getText().toString());
                params.put("product_quantity", quantity_et.getText().toString());
                params.put("product_quantity_by", select_uom_spinner_for_qty.getText().toString());
                params.put("product_price", price_et.getText().toString());
                params.put("product_price_by", select_uom_spinner_for_price.getText().toString());
                params.put("packing_availibility", packaging_availability_spinner.getText().toString());
                params.put("product_packing_type", packaging_type_spinner.getText().toString());
                params.put("packing_charges", who_pay_charges_spinner.getText().toString());
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

                            select_product_category_spinner.setText(category_obj.getString("category_name"));
                            select_product_spinner.setText(product_obj.getString("product_name"));
                            select_product_breed_spinner.setText(product_breed_obj.getString("product_breed"));
                            select_product_status_spinner.setText(product_status_obj.getString("product_status"));
                            quantity_et.setText(obj.getString("product_quantity"));
                            price_et.setText(obj.getString("price"));
                            select_uom_spinner_for_qty.setText(obj.getString("product_quantity_by"));
                            select_uom_spinner_for_price.setText(obj.getString("product_price_by"));
                            packaging_availability_spinner.setText(obj.getString("product_packaging_available"));
                            packaging_type_spinner.setText(product_packaging_type_obj.getString("product_packaging_type"));
                            state_spinner.setText(state_obj.getString("state_name"));
                            district_spinner.setText(district_obj.getString("district_name"));
                            block_spinner.setText(block_obj.getString("block_name"));
                            village_name_et.setText(obj.getString("village"));
                            additional_info_et.setText(obj.getString("additional_information"));
                            who_pay_charges_spinner.setText(obj.getString("packaging_cost_bearer"));
                            Picasso.get().load(obj.getString("product_image1")).resize(60, 60).into(product_image_imageview1);
                            Picasso.get().load(obj.getString("product_image2")).resize(60, 60).into(product_image_imageview2);
                            Picasso.get().load(obj.getString("product_image3")).resize(60, 60).into(product_image_imageview3);
                            try {
                                uri1 = Uri.parse(obj.getString("product_image1"));
                                uri2 = Uri.parse(obj.getString("product_image2"));
                                uri3 = Uri.parse(obj.getString("product_image3"));
//                                imageFile1 = new File(uri1.getPath());
//                                imageFile2 = new File(uri2.getPath());
//                                imageFile3 = new File(uri3.getPath());
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
/*
https://ekrishibazaar.com/api/ads/agriads/106/?toedit=106
 */
