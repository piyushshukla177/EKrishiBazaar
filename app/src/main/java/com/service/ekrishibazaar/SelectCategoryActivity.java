package com.service.ekrishibazaar;
import androidx.appcompat.app.AppCompatActivity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.service.ekrishibazaar.model.CategoryListModel;
import com.service.ekrishibazaar.util.VolleySingleton;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

public class SelectCategoryActivity extends AppCompatActivity {

    MaterialBetterSpinner select_product_category_spinner;
    ArrayList category_list = new ArrayList();
    Context context;
    String super_category;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seelct_category);
        context = this;
        select_product_category_spinner = findViewById(R.id.select_product_category_spinner);

        super_category = getIntent().getStringExtra("super_category");
        category_list.add("Select Product Category");
        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, category_list);
        select_product_category_spinner.setAdapter(categoryAdapter);

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
                    if (s.toString().equals("Fruits") || s.toString().equals("Pulses") || s.toString().equals("Medicinal plants") || s.toString().equals("Dairy Product") || s.toString().equals("Vegetable") || s.toString().equals("Grains")
                            || s.toString().equals("Flower") || s.toString().equals("oilseeds")) {
                        Intent intent = new Intent(context, PostSellAdsActivity.class);
                        intent.putExtra("super_category", super_category);
                        intent.putExtra("category", s.toString());
                        startActivity(intent);
                    } else if (s.toString().equals("Cattle")) {
                        Intent intent = new Intent(context, PostCattleAdsActivity.class);
                        intent.putExtra("super_category", super_category);
                        intent.putExtra("category", s.toString());
                        startActivity(intent);
                    }
                }
            }
        });
        getAllCategories();
    }

    void getAllCategories() {
        category_list.clear();
//      view_all_tv.setVisibility(View.GONE);
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
}