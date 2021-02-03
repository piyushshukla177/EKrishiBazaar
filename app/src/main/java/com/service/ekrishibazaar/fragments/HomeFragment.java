package com.service.ekrishibazaar.fragments;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.service.ekrishibazaar.AboutActivity;
import com.service.ekrishibazaar.MoreServicesActivity;
import com.service.ekrishibazaar.NotificationActivity;
import com.service.ekrishibazaar.R;
import com.service.ekrishibazaar.adapter.CategoryListAdapter;
import com.service.ekrishibazaar.model.CattleAdsModel;
import com.service.ekrishibazaar.model.CategoryListModel;
import com.service.ekrishibazaar.util.MyGridView;
import com.service.ekrishibazaar.util.PrefsHelper;
import com.service.ekrishibazaar.util.VolleySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class HomeFragment extends Fragment {

    Context context;
    ArrayList<CategoryListModel> product_list = new ArrayList<>();
    //  ArrayList<CattleAdsModel> fresh_product_list = new ArrayList<>();
    MyGridView category_grid;
    EditText search_edittext;
    TextView location_tv, change_location_tv;
    LinearLayout more_services_linear;
    private CategoryListAdapter mAdapter;
    LinearLayout notification_linear;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        init(root);
        return root;
    }

    //
//    CategoryListModel m;
//    CattleAdsModel f;
    String block, state, district;

    private void init(View root) {
        context = getActivity();
        search_edittext = root.findViewById(R.id.search_edittext);
        category_grid = root.findViewById(R.id.category_grid);
        notification_linear = root.findViewById(R.id.notification_linear);
        more_services_linear = root.findViewById(R.id.more_services_linear);
        location_tv = root.findViewById(R.id.location_tv);
        change_location_tv = root.findViewById(R.id.change_location_tv);
        String token = PrefsHelper.getString(context, "token");

        getAllCategories();

        search_edittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });

        notification_linear.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), NotificationActivity.class);
                        startActivity(intent);
                    }
                }
        );
        more_services_linear.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), MoreServicesActivity.class);
                        intent.putExtra("url", "https://www.ekrishibazaar.in/");
                        startActivity(intent);
                    }
                }
        );
        change_location_tv.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), AboutActivity.class);
                        startActivity(intent);
                        getActivity().finish();
                    }
                }
        );
        block = PrefsHelper.getString(getActivity(), "block");
        district = PrefsHelper.getString(getActivity(), "distict");
        state = PrefsHelper.getString(getActivity(), "state");
        location_tv.setText(block + ", " + district + ", " + state);
    }

    private void filter(String text) {
        if (mAdapter != null) {
            ArrayList<CategoryListModel> filteredList = new ArrayList<>();
            for (CategoryListModel item : product_list) {
                if (item.getProduct_name().toLowerCase().contains(text.toLowerCase())) {
                    filteredList.add(item);
                }
            }
            mAdapter.filterList(filteredList);
        }
    }

    void getSixCategories() {
        product_list.clear();
        final ProgressDialog mProgressDialog = new ProgressDialog(context);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage("Loading...");
        mProgressDialog.show();
        // Initialize a new RequestQueue instance

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
                                m.setProduct_name(obj.getString("category_name"));
                                m.setProduct_image(obj.getString("image"));
                                product_list.add(m);
                                if (i == 5) {
                                    break;
                                }
                            }
                            mAdapter = new CategoryListAdapter(context, product_list);
                            category_grid.setAdapter(mAdapter);

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

    void getAllCategories() {
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
                                m.setProduct_name(obj.getString("category_name"));
                                m.setProduct_image(obj.getString("image"));
                                product_list.add(m);
                            }
                            mAdapter = new CategoryListAdapter(context, product_list);
                            category_grid.setAdapter(mAdapter);

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
}
