package com.service.ekrishibazaar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
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
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.service.ekrishibazaar.adapter.AgricultureAdsAdapter;
import com.service.ekrishibazaar.adapter.AgricultureMachinaryAdapter;
import com.service.ekrishibazaar.adapter.CattleAdsAdapter;
import com.service.ekrishibazaar.adapter.FertilizerAdapter;
import com.service.ekrishibazaar.adapter.LabourinRentsAdapter;
import com.service.ekrishibazaar.adapter.OtherAgriAdapter;
import com.service.ekrishibazaar.adapter.ServiceAdsAdapter;
import com.service.ekrishibazaar.adapter.TreeandWoodsAdapter;
import com.service.ekrishibazaar.model.AgricultureAdsModel;
import com.service.ekrishibazaar.model.AgricultureMachinaryModel;
import com.service.ekrishibazaar.model.CattleAdsModel;
import com.service.ekrishibazaar.model.FertilizerListModel;
import com.service.ekrishibazaar.model.LabourInRentModel;
import com.service.ekrishibazaar.model.OtherAgriModel;
import com.service.ekrishibazaar.model.ServiceAdsModel;
import com.service.ekrishibazaar.model.TreeAndWoodsModel;
import com.service.ekrishibazaar.util.PrefsHelper;
import com.service.ekrishibazaar.util.VolleySingleton;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AdsListActivity extends AppCompatActivity {
    int TOTAL_PAGES = 20;
    int limit = 20;
    int offset = 20;
    RelativeLayout customize_relative;
    LinearLayout scan_cardview, searchLinear;
    String language_code = "en";
    TextView no_record_tv;
    ArrayList<CattleAdsModel> cattle_list = new ArrayList();
    ArrayList<ServiceAdsModel> service_rent_list = new ArrayList();
    ArrayList<AgricultureAdsModel> agriculture_list = new ArrayList();
    ArrayList<LabourInRentModel> labour_in_rent_list = new ArrayList();
    ArrayList<OtherAgriModel> other_agri_list = new ArrayList();
    ArrayList<AgricultureMachinaryModel> machinary_list = new ArrayList();
    ArrayList<TreeAndWoodsModel> tree_and_woods_list = new ArrayList();
    ArrayList<FertilizerListModel> fertilizer_list = new ArrayList();
    Context context;
    RecyclerView cattle_ads_recyclerview;
    private CattleAdsAdapter cattleAdapter;
    private ServiceAdsAdapter serviceAdapter;
    private AgricultureAdsAdapter agAdapter;
    private LabourinRentsAdapter labourAdapter;
    private OtherAgriAdapter otherAgriAdapter;
    private AgricultureMachinaryAdapter machinaryAgriAdapter;
    private TreeandWoodsAdapter treeAndWoodsAdapter;
    private FertilizerAdapter fertilizerAdapter;
    private RecyclerView.LayoutManager cattleLayoutManager;
    String category = "";
    EditText search_edittext, min_price_et, max_price_et;
    ImageView menu_imageview, up_imageview;
    ArrayList<String> state_list = new ArrayList();
    ArrayList<String> district_list = new ArrayList();
    ArrayList blocks_list = new ArrayList();
    ArrayList product_list = new ArrayList();
    MaterialBetterSpinner state_spinner, district_spinner, block_spinner, select_product_spinner;
    //    select_product_spinner;
    Button search_btn;

    String search_state, search_district, search_block, min_price, max_price, search_product_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cattle_ads);
        init();
    }

    //    private int currentPage = PAGE_START;
    private boolean isLastPage = false;
    private int totalPage = 10;
    private boolean isLoading = false;
    int itemCount = 0;

    private void init() {
        context = this;
        customize_relative = findViewById(R.id.customize_relative);
        searchLinear = findViewById(R.id.searchLinear);
        up_imageview = findViewById(R.id.up_imageview);
        state_spinner = findViewById(R.id.state_spinner);
        district_spinner = findViewById(R.id.district_spinner);
        block_spinner = findViewById(R.id.block_spinner);
        search_btn = findViewById(R.id.search_btn);
        min_price_et = findViewById(R.id.min_price_et);
        max_price_et = findViewById(R.id.max_price_et);
        select_product_spinner = findViewById(R.id.select_product_spinner);
        String state = PrefsHelper.getString(context, "state");
        if (state != null && !state.isEmpty()) {
            state_spinner.setText(state);
            district_spinner.setText(PrefsHelper.getString(context, "distict"));
            block_spinner.setText(PrefsHelper.getString(context, "block"));
        }
        customize_relative.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (searchLinear.getVisibility() == View.VISIBLE) {
                            up_imageview.setImageResource(R.drawable.up_arrow);
                            searchLinear.setVisibility(View.GONE);
                        } else {
                            up_imageview.setImageResource(R.drawable.down_arrow);
                            searchLinear.setVisibility(View.VISIBLE);
                        }
                    }
                }
        );
        scan_cardview = findViewById(R.id.scan_cardview);
        if (PrefsHelper.getString(this, "lang_code") != null && !PrefsHelper.getString(this, "lang_code").isEmpty()) {
            language_code = PrefsHelper.getString(this, "lang_code");
        }

        no_record_tv = findViewById(R.id.no_record_tv);
        search_edittext = findViewById(R.id.search_edittext);
        cattle_ads_recyclerview = findViewById(R.id.cattle_ads_recyclerview);
        menu_imageview = findViewById(R.id.menu_imageview);
        Intent intent = getIntent();
        category = intent.getStringExtra("category");
        if (category != null && !category.isEmpty() && category.equals("Cattle")) {
            getCattleList("Sellads");
        } else if (category != null && !category.isEmpty() && category.equals("Service in Rent")) {
            getServiceList("Sellads");
        } else if (category != null && !category.isEmpty() && category.equals("Fruits")) {
            getAgricultureList("Fruits", "Sellads");
        } else if (category != null && !category.isEmpty() && category.equalsIgnoreCase("Pulses")) {
            getAgricultureList("Pulses", "Sellads");
        } else if (category != null && !category.isEmpty() && category.equalsIgnoreCase("Medicinal plants")) {
            getAgricultureList("Medicinal plants", "Sellads");
        } else if (category != null && !category.isEmpty() && category.equalsIgnoreCase("Dairy Product")) {
            getAgricultureList("Dairy Product", "Sellads");
        } else if (category != null && !category.isEmpty() && category.equalsIgnoreCase("Vegetable")) {
            getAgricultureList("Vegetable", "Sellads");
        } else if (category != null && !category.isEmpty() && category.equalsIgnoreCase("Grains")) {
            getAgricultureList("Grains", "Sellads");
        } else if (category != null && !category.isEmpty() && category.equalsIgnoreCase("Flower")) {
            getAgricultureList("Flower", "Sellads");
        } else if (category != null && !category.isEmpty() && category.equalsIgnoreCase("oilseeds")) {
            getAgricultureList("oilseeds", "Sellads");
        } else if (category != null && !category.isEmpty() && category.equalsIgnoreCase("Labour in Rent")) {
            getLabourinrentsList("Sellads");
        } else if (category != null && !category.isEmpty() && category.equalsIgnoreCase("Other Agri Product")) {
            getOtherAgriProductList("Sellads");
        } else if (category != null && !category.isEmpty() && category.equalsIgnoreCase("Agricultural machinary")) {
            getOtherAgriMachinary("Sellads");
        } else if (category != null && !category.isEmpty() && category.equalsIgnoreCase("Tree and Woods")) {
            getTreeAndWoodsList("Sellads");
        } else if (category != null && !category.isEmpty() && category.equalsIgnoreCase("Fertilizers and Pesticides")) {
            getFertilizersList("Sellads");
        }
        search_edittext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                if (category.equals("Fruits") || category.equals("Pulses") || category.equals("Medicinal plants") || category.equals("Dairy Product") || category.equals("Vegetable")
                        || category.equals("Grains") || category.equals("Flower") || category.equals("oilseeds")) {
                    AgricultureAdsFilter(s.toString());
                }
                if (category.equals("Cattle")) {
                    CattleAdsFilter(s.toString());
                }
                if (category.equals("Service in Rent")) {
                    ServiceInRentFilter(s.toString());
                }
                if (category.equals("Labour in Rent")) {
                    LabourRentFilter(s.toString());
                }
                if (category.equals("Other Agri Product")) {
                    OtherAgriProductFilter(s.toString());
                }
                if (category.equals("Agricultural machinary")) {
                    AgriculturalmachinaryFilter(s.toString());
                }
                if (category.equals("Tree and Woods")) {
                    TreeAndWoodsFilter(s.toString());
                }
                if (category.equals("Fertilizers and Pesticides")) {
                    FertilizerFilter(s.toString());
                }
            }
        });

        menu_imageview.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        PopupMenu popup = new PopupMenu(context, menu_imageview);
                        //Inflating the Popup using xml file
                        popup.getMenuInflater().inflate(R.menu.filter_ads_menu, popup.getMenu());
                        //registering popup with OnMenuItemClickListener
                        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                            public boolean onMenuItemClick(MenuItem item) {
                                if (item.getTitle().equals("All Ads")) {
                                    if (category != null && !category.isEmpty() && category.equals("Cattle")) {
                                        getCattleList("Sellads");
                                    } else if (category != null && !category.isEmpty() && category.equals("Service in Rent")) {
                                        getServiceList("Sellads");
                                    } else if (category != null && !category.isEmpty() && category.equals("Fruits")) {
                                        getAgricultureList("Fruits", "Sellads");
                                    } else if (category != null && !category.isEmpty() && category.equalsIgnoreCase("Pulses")) {
                                        getAgricultureList("Pulses", "Sellads");
                                    } else if (category != null && !category.isEmpty() && category.equalsIgnoreCase("Medicinal plants")) {
                                        getAgricultureList("Medicinal plants", "Sellads");
                                    } else if (category != null && !category.isEmpty() && category.equalsIgnoreCase("Dairy Product")) {
                                        getAgricultureList("Dairy Product", "Sellads");
                                    } else if (category != null && !category.isEmpty() && category.equalsIgnoreCase("Vegetable")) {
                                        getAgricultureList("Vegetable", "Sellads");
                                    } else if (category != null && !category.isEmpty() && category.equalsIgnoreCase("Grains")) {
                                        getAgricultureList("Grains", "Sellads");
                                    } else if (category != null && !category.isEmpty() && category.equalsIgnoreCase("Flower")) {
                                        getAgricultureList("Flower", "Sellads");
                                    } else if (category != null && !category.isEmpty() && category.equalsIgnoreCase("oilseeds")) {
                                        getAgricultureList("oilseeds", "Sellads");
                                    } else if (category != null && !category.isEmpty() && category.equalsIgnoreCase("Labour in Rent")) {
                                        getLabourinrentsList("Sellads");
                                    } else if (category != null && !category.isEmpty() && category.equalsIgnoreCase("Other Agri Product")) {
                                        getOtherAgriProductList("Sellads");
                                    } else if (category != null && !category.isEmpty() && category.equalsIgnoreCase("Agricultural machinary")) {
                                        getOtherAgriMachinary("Sellads");
                                    } else if (category != null && !category.isEmpty() && category.equalsIgnoreCase("Tree and Woods")) {
                                        getTreeAndWoodsList("Sellads");
                                    } else if (category != null && !category.isEmpty() && category.equalsIgnoreCase("Fertilizers and Pesticides")) {
                                        getFertilizersList("Sellads");
                                    }
                                } else if (item.getTitle().equals("Buy Ads")) {

                                    if (category != null && !category.isEmpty() && category.equals("Cattle")) {
                                        getCattleList("BuyOffer");
                                    } else if (category != null && !category.isEmpty() && category.equals("Service in Rent")) {
                                        getServiceList("BuyOffer");
                                    } else if (category != null && !category.isEmpty() && category.equals("Fruits")) {
                                        getAgricultureList("Fruits", "BuyOffer");
                                    } else if (category != null && !category.isEmpty() && category.equalsIgnoreCase("Pulses")) {
                                        getAgricultureList("Pulses", "BuyOffer");
                                    } else if (category != null && !category.isEmpty() && category.equalsIgnoreCase("Medicinal plants")) {
                                        getAgricultureList("Medicinal plants", "BuyOffer");
                                    } else if (category != null && !category.isEmpty() && category.equalsIgnoreCase("Dairy Product")) {
                                        getAgricultureList("Dairy Product", "BuyOffer");
                                    } else if (category != null && !category.isEmpty() && category.equalsIgnoreCase("Vegetable")) {
                                        getAgricultureList("Vegetable", "BuyOffer");
                                    } else if (category != null && !category.isEmpty() && category.equalsIgnoreCase("Grains")) {
                                        getAgricultureList("Grains", "BuyOffer");
                                    } else if (category != null && !category.isEmpty() && category.equalsIgnoreCase("Flower")) {
                                        getAgricultureList("Flower", "BuyOffer");
                                    } else if (category != null && !category.isEmpty() && category.equalsIgnoreCase("oilseeds")) {
                                        getAgricultureList("oilseeds", "BuyOffer");
                                    } else if (category != null && !category.isEmpty() && category.equalsIgnoreCase("Labour in Rent")) {
                                        getLabourinrentsList("BuyOffer");
                                    } else if (category != null && !category.isEmpty() && category.equalsIgnoreCase("Other Agri Product")) {
                                        getOtherAgriProductList("BuyOffer");
                                    } else if (category != null && !category.isEmpty() && category.equalsIgnoreCase("Agricultural machinary")) {
                                        getOtherAgriMachinary("BuyOffer");
                                    } else if (category != null && !category.isEmpty() && category.equalsIgnoreCase("Tree and Woods")) {
                                        getTreeAndWoodsList("BuyOffer");
                                    } else if (category != null && !category.isEmpty() && category.equalsIgnoreCase("Fertilizers and Pesticides")) {
                                        getFertilizersList("BuyOffer");
                                    }
                                } else if (item.getTitle().equals("BuyBack Ads")) {
                                    if (category != null && !category.isEmpty() && category.equals("Cattle")) {
                                        getCattleList("BuyBackOffer");
                                    } else if (category != null && !category.isEmpty() && category.equals("Service in Rent")) {
                                        getServiceList("BuyBackOffer");
                                    } else if (category != null && !category.isEmpty() && category.equals("Fruits")) {
                                        getAgricultureList("Fruits", "BuyBackOffer");
                                    } else if (category != null && !category.isEmpty() && category.equalsIgnoreCase("Pulses")) {
                                        getAgricultureList("Pulses", "BuyBackOffer");
                                    } else if (category != null && !category.isEmpty() && category.equalsIgnoreCase("Medicinal plants")) {
                                        getAgricultureList("Medicinal plants", "BuyBackOffer");
                                    } else if (category != null && !category.isEmpty() && category.equalsIgnoreCase("Dairy Product")) {
                                        getAgricultureList("Dairy Product", "BuyBackOffer");
                                    } else if (category != null && !category.isEmpty() && category.equalsIgnoreCase("Vegetable")) {
                                        getAgricultureList("Vegetable", "BuyBackOffer");
                                    } else if (category != null && !category.isEmpty() && category.equalsIgnoreCase("Grains")) {
                                        getAgricultureList("Grains", "BuyBackOffer");
                                    } else if (category != null && !category.isEmpty() && category.equalsIgnoreCase("Flower")) {
                                        getAgricultureList("Flower", "BuyBackOffer");
                                    } else if (category != null && !category.isEmpty() && category.equalsIgnoreCase("oilseeds")) {
                                        getAgricultureList("oilseeds", "BuyBackOffer");
                                    } else if (category != null && !category.isEmpty() && category.equalsIgnoreCase("Labour in Rent")) {
                                        getLabourinrentsList("BuyBackOffer");
                                    } else if (category != null && !category.isEmpty() && category.equalsIgnoreCase("Other Agri Product")) {
                                        getOtherAgriProductList("BuyBackOffer");
                                    } else if (category != null && !category.isEmpty() && category.equalsIgnoreCase("Agricultural machinary")) {
                                        getOtherAgriMachinary("BuyBackOffer");
                                    } else if (category != null && !category.isEmpty() && category.equalsIgnoreCase("Tree and Woods")) {
                                        getTreeAndWoodsList("BuyBackOffer");
                                    } else if (category != null && !category.isEmpty() && category.equalsIgnoreCase("Fertilizers and Pesticides")) {
                                        getFertilizersList("BuyBackOffer");
                                    }
                                }
                                return true;
                            }
                        });
                        popup.show(); //showing popup menu
                    }
                }
        );
        cattle_ads_recyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (!recyclerView.canScrollVertically(1)) {
//                    Toast.makeText(AdsListActivity.this, "Last", Toast.LENGTH_LONG).show();
                    if (next_url != null) {
                        offset = offset + 20;
                        if (category != null && !category.isEmpty() && category.equals("Cattle")) {
                            getCattleList("Sellads");
                        } else if (category != null && !category.isEmpty() && category.equals("Service in Rent")) {
                            getServiceList("Sellads");
                        } else if (category != null && !category.isEmpty() && category.equals("Fruits")) {
                            getAgricultureList("Fruits", "Sellads");
                        } else if (category != null && !category.isEmpty() && category.equalsIgnoreCase("Pulses")) {
                            getAgricultureList("Pulses", "Sellads");
                        } else if (category != null && !category.isEmpty() && category.equalsIgnoreCase("Medicinal plants")) {
                            getAgricultureList("Medicinal plants", "Sellads");
                        } else if (category != null && !category.isEmpty() && category.equalsIgnoreCase("Dairy Product")) {
                            getAgricultureList("Dairy Product", "Sellads");
                        } else if (category != null && !category.isEmpty() && category.equalsIgnoreCase("Vegetable")) {
                            getAgricultureList("Vegetable", "Sellads");
                        } else if (category != null && !category.isEmpty() && category.equalsIgnoreCase("Grains")) {
                            getAgricultureList("Grains", "Sellads");
                        } else if (category != null && !category.isEmpty() && category.equalsIgnoreCase("Flower")) {
                            getAgricultureList("Flower", "Sellads");
                        } else if (category != null && !category.isEmpty() && category.equalsIgnoreCase("oilseeds")) {
                            getAgricultureList("oilseeds", "Sellads");
                        } else if (category != null && !category.isEmpty() && category.equalsIgnoreCase("Labour in Rent")) {
                            getLabourinrentsList("Sellads");
                        } else if (category != null && !category.isEmpty() && category.equalsIgnoreCase("Other Agri Product")) {
                            getOtherAgriProductList("Sellads");
                        } else if (category != null && !category.isEmpty() && category.equalsIgnoreCase("Agricultural machinary")) {
                            getOtherAgriMachinary("Sellads");
                        } else if (category != null && !category.isEmpty() && category.equalsIgnoreCase("Tree and Woods")) {
                            getTreeAndWoodsList("Sellads");
                        } else if (category != null && !category.isEmpty() && category.equalsIgnoreCase("Fertilizers and Pesticides")) {
                            getFertilizersList("Sellads");
                        }
                    }
                }
            }
        });

//        cattle_ads_recyclerview.addOnScrollListener(new PaginationListener(new LinearLayoutManager(context)) {
//            @Override
//            protected void loadMoreItems() {
//                isLoading = true;
////              currentPage++;
//                offset = offset + 20;
//                getCattleList(category);
//            }
//
//            @Override
//            public int getTotalPageCount() {
//                return TOTAL_PAGES;
//            }
//
//            @Override
//            public boolean isLastPage() {
//                return isLastPage;
//            }
//
//            @Override
//            public boolean isLoading() {
//                return isLoading;
//            }
//        });

//        cattle_ads_recyclerview.addOnScrollListener(new PaginationListener(new LinearLayoutManager(context)) {
//            @Override
//            protected void loadMoreItems() {
//                isLoading = true;
//                currentPage++;
//                offset = offset + 20;
//                getCattleList(category);
//            }
//
//            @Override
//            public boolean isLastPage() {
//                return isLastPage;
//            }
//
//            @Override
//            public boolean isLoading() {
//                return isLoading;
//            }
//        });

        district_list.add("Select District");
        ArrayAdapter<String> stateAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, district_list);
        district_spinner.setAdapter(stateAdapter);

        blocks_list.add("Block");
        ArrayAdapter<String> x = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, blocks_list);
        block_spinner.setAdapter(x);

        product_list.add("Select Product");
        ArrayAdapter<String> productAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, product_list);
        select_product_spinner.setAdapter(productAdapter);

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
        search_btn.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        search_state = state_spinner.getText().toString();
                        search_district = district_spinner.getText().toString();
                        search_block = block_spinner.getText().toString();
                        min_price = min_price_et.getText().toString();
                        max_price = max_price_et.getText().toString();
                        search_product_name = select_product_spinner.getText().toString();

                        if (category != null && !category.isEmpty() && category.equals("Cattle")) {
                            getCattleList("Sellads");
                        } else if (category != null && !category.isEmpty() && category.equals("Service in Rent")) {
                            getServiceList("Sellads");
                        } else if (category != null && !category.isEmpty() && category.equals("Fruits")) {
                            getAgricultureList("Fruits", "Sellads");
                        } else if (category != null && !category.isEmpty() && category.equalsIgnoreCase("Pulses")) {
                            getAgricultureList("Pulses", "Sellads");
                        } else if (category != null && !category.isEmpty() && category.equalsIgnoreCase("Medicinal plants")) {
                            getAgricultureList("Medicinal plants", "Sellads");
                        } else if (category != null && !category.isEmpty() && category.equalsIgnoreCase("Dairy Product")) {
                            getAgricultureList("Dairy Product", "Sellads");
                        } else if (category != null && !category.isEmpty() && category.equalsIgnoreCase("Vegetable")) {
                            getAgricultureList("Vegetable", "Sellads");
                        } else if (category != null && !category.isEmpty() && category.equalsIgnoreCase("Grains")) {
                            getAgricultureList("Grains", "Sellads");
                        } else if (category != null && !category.isEmpty() && category.equalsIgnoreCase("Flower")) {
                            getAgricultureList("Flower", "Sellads");
                        } else if (category != null && !category.isEmpty() && category.equalsIgnoreCase("oilseeds")) {
                            getAgricultureList("oilseeds", "Sellads");
                        } else if (category != null && !category.isEmpty() && category.equalsIgnoreCase("Labour in Rent")) {
                            getLabourinrentsList("Sellads");
                        } else if (category != null && !category.isEmpty() && category.equalsIgnoreCase("Other Agri Product")) {
                            getOtherAgriProductList("Sellads");
                        } else if (category != null && !category.isEmpty() && category.equalsIgnoreCase("Agricultural machinary")) {
                            getOtherAgriMachinary("Sellads");
                        } else if (category != null && !category.isEmpty() && category.equalsIgnoreCase("Tree and Woods")) {
                            getTreeAndWoodsList("Sellads");
                        } else if (category != null && !category.isEmpty() && category.equalsIgnoreCase("Fertilizers and Pesticides")) {
                            getFertilizersList("Sellads");
                        }
                    }
                }
        );
        getProducts(category);
        getStates();
    }

    private void AgricultureAdsFilter(String text) {
        ArrayList<AgricultureAdsModel> filteredList = new ArrayList<>();
        for (AgricultureAdsModel item : agriculture_list) {
            if (item.getProduct_name().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }
        agAdapter.filterList(filteredList);
    }

    private void CattleAdsFilter(String text) {
        ArrayList<CattleAdsModel> filteredList = new ArrayList<>();
        for (CattleAdsModel item : cattle_list) {
            if (item.getCattle_name().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }
        if (cattleAdapter != null) {
            cattleAdapter.filterList(filteredList);
        }
    }

    private void ServiceInRentFilter(String text) {
        ArrayList<ServiceAdsModel> filteredList = new ArrayList<>();
        for (ServiceAdsModel item : service_rent_list) {
            if (item.getService_machine_name().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }
        serviceAdapter.filterList(filteredList);
    }

    private void LabourRentFilter(String text) {
        ArrayList<LabourInRentModel> filteredList = new ArrayList<>();
        for (LabourInRentModel item : labour_in_rent_list) {
            if (item.getExpertise().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }
        labourAdapter.filterList(filteredList);
    }

    private void OtherAgriProductFilter(String text) {
        ArrayList<OtherAgriModel> filteredList = new ArrayList<>();
        for (OtherAgriModel item : other_agri_list) {
            if (item.getChemical_product_name().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }
        otherAgriAdapter.filterList(filteredList);
    }

    private void AgriculturalmachinaryFilter(String text) {
        ArrayList<AgricultureMachinaryModel> filteredList = new ArrayList<>();
        for (AgricultureMachinaryModel item : machinary_list) {
            if (item.getMachine_name().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }
        machinaryAgriAdapter.filterList(filteredList);
    }

    private void TreeAndWoodsFilter(String text) {
        ArrayList<TreeAndWoodsModel> filteredList = new ArrayList<>();
        for (TreeAndWoodsModel item : tree_and_woods_list) {
            if (item.getWood_name().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }
        treeAndWoodsAdapter.filterList(filteredList);
    }

    private void FertilizerFilter(String text) {
        ArrayList<FertilizerListModel> filteredList = new ArrayList<>();
        for (FertilizerListModel item : fertilizer_list) {
            if (item.getChemical_product_name().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }
        fertilizerAdapter.filterList(filteredList);
    }

    private void getCattleList(String super_category) {
        cattle_list.clear();
        searchLinear.setVisibility(View.GONE);
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
        String url = "https://ekrishibazaar.com/api/ads/filterads/?limit=" + limit + "&offset=" + offset + "&super_category=" + super_category;

//        if (next_url != null) {
//            url = next_url;
//        }
//        else {
//        }
        if (search_state != null && !search_state.isEmpty()) {
            url = url + "&state=" + search_state;
        }
        if (search_district != null && !search_district.isEmpty()) {
            url = url + "&district=" + search_district;
        }
        if (min_price != null && !min_price.isEmpty()) {
            url = url + "&product_price_min=" + min_price;
        }
        if (max_price != null && !max_price.isEmpty()) {
            url = url + "&product_price_max=" + max_price;
        }
        if (search_product_name != null && !search_product_name.isEmpty()) {
            url = url + "&product=" + search_product_name;
        }
        Log.e("url", url);
        StringRequest postRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.v("response", response);
                        try {
                            isLoading = false;
                            mProgressDialog.dismiss();
                            response = new String(response.getBytes("ISO-8859-1"), "UTF-8");
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray data_array = jsonObject.getJSONArray("results");
                            next_url = jsonObject.getString("next");
                            if (next_url == null) {
                                isLastPage = true;
                            }
                            CattleAdsModel m;
                            for (int i = 0; i < data_array.length(); i++) {
                                JSONObject obj = data_array.getJSONObject(i);
                                if (obj.getString("type").equals("cattleads")) {
                                    JSONObject user_obj = obj.getJSONObject("user");
                                    JSONObject cattle_type_obj = obj.getJSONObject("cattle_type");
                                    JSONObject cattle_breed_obj = obj.getJSONObject("cattle_breed");
                                    JSONObject user_2_obj = user_obj.getJSONObject("user");
                                    m = new CattleAdsModel();
                                    m.setUser_first_name(user_2_obj.getString("first_name"));
                                    m.setUser_last_name(user_2_obj.getString("last_name"));
                                    m.setDate_joined(user_2_obj.getString("date_joined"));
                                    m.setProfile_image(user_obj.getString("image"));
                                    JSONObject state_obj = user_obj.getJSONObject("state");
                                    JSONObject district_obj = user_obj.getJSONObject("district");
                                    JSONObject block_obj = user_obj.getJSONObject("block");
                                    m.setIsverified(user_obj.getString("is_verified"));
                                    m.setState(state_obj.getString("state_name"));
                                    m.setDistrict(district_obj.getString("district_name"));
                                    m.setBlock(block_obj.getString("block_name"));
                                    m.setVid(user_obj.getString("vid"));
                                    m.setVillage_name(user_obj.getString("village_name"));
                                    m.setMobile_number(user_obj.getString("mobile_number"));

                                    String cattle_name = cattle_type_obj.getString("cattle_name");
                                    m.setCattle_name(cattle_name);

                                    JSONObject cattle_category = cattle_type_obj.getJSONObject("category");
                                    m.setCattle_name(cattle_category.getString("category_name"));
                                    m.setCategory_image(cattle_category.getString("image"));
                                    JSONObject milk_produced_obj = obj.getJSONObject("milk_produced");

//                                  String category_name = cattle_category.getString("category_name");
                                    m.setCattle_breed(cattle_breed_obj.getString("cattle_breed"));
                                    m.setNumber_of_child(obj.getString("number_of_child"));
                                    m.setMilk_per_day(milk_produced_obj.getString("milk_per_day"));
                                    m.setPosted_on(obj.getString("posted_on"));
                                    m.setPrice(obj.getString("price"));
                                    m.setProduct_image1(obj.getString("product_image1"));
                                    m.setProduct_image2(obj.getString("product_image2"));
                                    m.setProduct_image3(obj.getString("product_image3"));
                                    m.setPost_id(obj.getString("post_id"));
                                    Log.e("PostId", obj.getString("post_id"));
                                    m.setAdditional_info(obj.getString("additional_information"));
                                    cattle_list.add(m);
                                }
                            }
                            if (cattle_list.size() > 0) {
                                cattle_ads_recyclerview.setHasFixedSize(true);
                                cattleLayoutManager = new LinearLayoutManager(context);
                                cattleAdapter = new CattleAdsAdapter(context, cattle_list);
                                cattle_ads_recyclerview.setLayoutManager(cattleLayoutManager);
                                cattle_ads_recyclerview.setAdapter(cattleAdapter);
                                no_record_tv.setVisibility(View.GONE);
                                scan_cardview.setVisibility(View.VISIBLE);
                                customize_relative.setVisibility(View.VISIBLE);

                            } else {
                                no_record_tv.setVisibility(View.VISIBLE);
                                customize_relative.setVisibility(View.GONE);
                                scan_cardview.setVisibility(View.GONE);
                                searchLinear.setVisibility(View.GONE);
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
                params.put("Accept-Language", language_code);
                return params;
            }
        };
        postRequest.setRetryPolicy(new DefaultRetryPolicy(30000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(context).add(postRequest);
    }

    private void getServiceList(String super_category) {
        service_rent_list.clear();
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
        String url = "https://ekrishibazaar.com/api/ads/filterads/?limit=" + limit + "&offset=" + offset + "&super_category=" + super_category;
        ;
//        if (next_url != null) {
//            url = next_url;
//        } else {
//
//        }
        if (search_state != null && !search_state.isEmpty()) {
            url = url + "&state=" + search_state;
        }
        if (search_district != null && !search_district.isEmpty()) {
            url = url + "&district=" + search_district;
        }
        if (min_price != null && !min_price.isEmpty()) {
            url = url + "&product_price_min=" + min_price;
        }
        if (max_price != null && !max_price.isEmpty()) {
            url = url + "&product_price_max=" + max_price;
        }
        StringRequest postRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.v("response", response);
                        try {
                            mProgressDialog.dismiss();
                            response = new String(response.getBytes("ISO-8859-1"), "UTF-8");
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray data_array = jsonObject.getJSONArray("results");

                            ServiceAdsModel m;
                            for (int i = 0; i < data_array.length(); i++) {
                                JSONObject obj = data_array.getJSONObject(i);
                                boolean b = obj.getString("type").equals("serviceinrentads");
                                Log.e("type", String.valueOf(b));
                                next_url = jsonObject.getString("next");
                                if (b) {
                                    JSONObject user_obj = obj.getJSONObject("user");
                                    JSONObject user_2_obj = user_obj.getJSONObject("user");
                                    m = new ServiceAdsModel();
                                    m.setUser_first_name(user_2_obj.getString("first_name"));
                                    m.setUser_last_name(user_2_obj.getString("last_name"));
                                    m.setDate_joined(user_2_obj.getString("date_joined"));
                                    m.setProfile_image(user_obj.getString("image"));
                                    JSONObject state_obj = user_obj.getJSONObject("state");
                                    JSONObject district_obj = user_obj.getJSONObject("district");
                                    JSONObject block_obj = user_obj.getJSONObject("block");
                                    JSONObject reaching_on_time_obj = obj.getJSONObject("reaching_on_time");
                                    m.setIsverified(user_obj.getString("is_verified"));
                                    m.setReaching_on_time(reaching_on_time_obj.getString("service_status"));
                                    m.setState(state_obj.getString("state_name"));
                                    m.setDistrict(district_obj.getString("district_name"));
                                    m.setBlock(block_obj.getString("block_name"));
                                    m.setVid(user_obj.getString("vid"));
                                    m.setVillage_name(user_obj.getString("village_name"));
                                    m.setMobile_number(user_obj.getString("mobile_number"));
                                    m.setPost_id(obj.getString("post_id"));
                                    JSONObject service_machine_obj = obj.getJSONObject("service_machine");
                                    JSONObject select_work_obj = obj.getJSONObject("select_work");
                                    m.setService_machine_name(service_machine_obj.getString("service_machine_name"));
                                    m.setPosted_on(obj.getString("posted_on"));
                                    m.setPrice(obj.getString("price"));
                                    m.setProduct_image1(obj.getString("product_image1"));
                                    m.setProduct_image2(obj.getString("product_image2"));
                                    m.setProduct_image3(obj.getString("product_image3"));
                                    m.setAdditional_info(obj.getString("additional_information"));
                                    m.setWork_name(select_work_obj.getString("work_name"));
                                    m.setCategory_name("Service in Rent");

                                    service_rent_list.add(m);
                                    Log.e("serviceListSize", String.valueOf(service_rent_list.size()));
                                }
                            }
                            if (service_rent_list.size() > 0) {
                                no_record_tv.setVisibility(View.GONE);
                                scan_cardview.setVisibility(View.VISIBLE);
                                customize_relative.setVisibility(View.VISIBLE);
                                cattle_ads_recyclerview.setHasFixedSize(true);
                                cattleLayoutManager = new LinearLayoutManager(context);
                                serviceAdapter = new ServiceAdsAdapter(context, service_rent_list);
                                cattle_ads_recyclerview.setLayoutManager(cattleLayoutManager);
                                cattle_ads_recyclerview.setAdapter(serviceAdapter);

                            } else {
                                no_record_tv.setVisibility(View.VISIBLE);
                                scan_cardview.setVisibility(View.GONE);
                                customize_relative.setVisibility(View.GONE);
                                searchLinear.setVisibility(View.GONE);
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
                params.put("Accept-Language", language_code);
                return params;
            }
        };
        postRequest.setRetryPolicy(new DefaultRetryPolicy(30000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(context).add(postRequest);
    }

    String next_url = null;

    private void getAgricultureList(String catgeory_type, String super_category) {
        agriculture_list.clear();
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
        String url = "https://ekrishibazaar.com/api/ads/filterads/?limit=" + limit + "&offset=" + offset + "&super_category=" + super_category;

//        if (next_url != null) {
//            url = next_url;
//        } else {
//
//        }
        if (search_state != null && !search_state.isEmpty()) {
            url = url + "&state=" + search_state;
        }
        if (search_district != null && !search_district.isEmpty()) {
            url = url + "&district=" + search_district;
        }
        if (min_price != null && !min_price.isEmpty()) {
            url = url + "&product_price_min=" + min_price;
        }
        if (max_price != null && !max_price.isEmpty()) {
            url = url + "&product_price_max=" + max_price;
        }
        Log.e("url", url);
        StringRequest postRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.v("response", response);
                        try {
                            mProgressDialog.dismiss();
                            response = new String(response.getBytes("ISO-8859-1"), "UTF-8");
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray data_array = jsonObject.getJSONArray("results");
                            next_url = jsonObject.getString("next");
                            AgricultureAdsModel m;
                            for (int i = 0; i < data_array.length(); i++) {
                                JSONObject obj = data_array.getJSONObject(i);
                                Log.e("index==", String.valueOf(i));

                                if (obj.getString("type").equals("agriculturalads")) {
                                    JSONObject user_obj = obj.getJSONObject("user");
                                    JSONObject user_2_obj = user_obj.getJSONObject("user");
                                    m = new AgricultureAdsModel();
                                    m.setUser_first_name(user_2_obj.getString("first_name"));
                                    m.setUser_last_name(user_2_obj.getString("last_name"));
                                    m.setIsverified(user_obj.getString("is_verified"));
                                    m.setDate_joined(user_2_obj.getString("date_joined"));
                                    m.setProfile_image(user_obj.getString("image"));
                                    JSONObject state_obj = user_obj.getJSONObject("state");
                                    JSONObject district_obj = user_obj.getJSONObject("district");
                                    JSONObject block_obj = user_obj.getJSONObject("block");

                                    m.setState(state_obj.getString("state_name"));
                                    m.setDistrict(district_obj.getString("district_name"));
                                    m.setBlock(block_obj.getString("block_name"));
                                    m.setVid(user_obj.getString("vid"));
                                    m.setVillage_name(user_obj.getString("village_name"));
                                    m.setMobile_number(user_obj.getString("mobile_number"));
                                    m.setPost_id(obj.getString("post_id"));

                                    JSONObject service_machine_obj = obj.getJSONObject("product");
                                    JSONObject product_status_obj = obj.getJSONObject("product_status");

                                    m.setStatus(product_status_obj.getString("product_status"));
                                    JSONObject product_breed_obj = obj.getJSONObject("product_breed");
                                    m.setProduct_breed(product_breed_obj.getString("product_breed"));
                                    JSONObject category_obj = service_machine_obj.getJSONObject("category");
                                    m.setCategory_name(category_obj.getString("category_name"));
                                    m.setCategory_image(category_obj.getString("image"));
                                    m.setProduct_quantity(obj.getString("product_quantity"));
                                    m.setProduct_quantity_by(obj.getString("product_quantity_by"));
                                    m.setProduct_name(service_machine_obj.getString("product_name"));
                                    m.setProduct_price_by(obj.getString("product_price_by"));
                                    m.setPackaging_cost_bearer(obj.getString("packaging_cost_bearer"));
                                    m.setPosted_on(obj.getString("posted_on"));
                                    m.setPrice(obj.getString("price"));

                                    m.setProduct_image1(obj.getString("product_image1"));
                                    m.setProduct_image2(obj.getString("product_image2"));
                                    m.setProduct_image3(obj.getString("product_image3"));
                                    m.setAdditional_info(obj.getString("additional_information"));
                                    m.setPost_id(obj.getString("post_id"));
                                    boolean b = catgeory_type.equals(category_obj.getString("category_name"));
                                    String x = category_obj.getString("category_name");
                                    String y = catgeory_type;
                                    Log.e("catgeory_type", String.valueOf(b));
                                    if (catgeory_type.equals(category_obj.getString("category_name"))) {
                                        agriculture_list.add(m);
                                    }
                                }
                            }
                            if (agriculture_list.size() > 0) {
                                no_record_tv.setVisibility(View.GONE);
                                scan_cardview.setVisibility(View.VISIBLE);
                                customize_relative.setVisibility(View.VISIBLE);
                                cattle_ads_recyclerview.setHasFixedSize(true);
                                cattleLayoutManager = new LinearLayoutManager(context);
                                agAdapter = new AgricultureAdsAdapter(context, agriculture_list);
                                cattle_ads_recyclerview.setLayoutManager(cattleLayoutManager);
                                cattle_ads_recyclerview.setAdapter(agAdapter);

                            } else {
                                no_record_tv.setVisibility(View.VISIBLE);
                                scan_cardview.setVisibility(View.GONE);
                                customize_relative.setVisibility(View.GONE);
                                searchLinear.setVisibility(View.GONE);
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
                params.put("Accept-Language", language_code);
                return params;
            }
        };
        postRequest.setRetryPolicy(new DefaultRetryPolicy(30000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(context).add(postRequest);
    }

    private void getLabourinrentsList(String super_category) {
        labour_in_rent_list.clear();
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
        String url = "https://ekrishibazaar.com/api/ads/filterads/?limit=" + limit + "&offset=" + offset + "&super_category=" + super_category;
//        if (next_url != null) {
//            url = next_url;
//        } else {
//
//        }
        if (search_state != null && !search_state.isEmpty()) {
            url = url + "&state=" + search_state;
        }
        if (search_district != null && !search_district.isEmpty()) {
            url = url + "&district=" + search_district;
        }
        if (min_price != null && !min_price.isEmpty()) {
            url = url + "&product_price_min=" + min_price;
        }
        if (max_price != null && !max_price.isEmpty()) {
            url = url + "&product_price_max=" + max_price;
        }
        StringRequest postRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.v("response", response);
                        try {

                            mProgressDialog.dismiss();
                            response = new String(response.getBytes("ISO-8859-1"), "UTF-8");
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray data_array = jsonObject.getJSONArray("results");
                            next_url = jsonObject.getString("next");
                            LabourInRentModel m;
                            for (int i = 0; i < data_array.length(); i++) {
                                JSONObject obj = data_array.getJSONObject(i);

                                boolean b = obj.getString("type").equals("labourinrentads");
                                Log.e("type", String.valueOf(b));
                                if (b) {
                                    JSONObject user_obj = obj.getJSONObject("user");
                                    JSONObject user_2_obj = user_obj.getJSONObject("user");
                                    m = new LabourInRentModel();
                                    m.setUser_first_name(user_2_obj.getString("first_name"));
                                    m.setUser_last_name(user_2_obj.getString("last_name"));
                                    m.setDate_joined(user_2_obj.getString("date_joined"));
                                    m.setProfile_image(user_obj.getString("image"));
                                    JSONObject state_obj = user_obj.getJSONObject("state");
                                    JSONObject district_obj = user_obj.getJSONObject("district");
                                    JSONObject block_obj = user_obj.getJSONObject("block");

                                    m.setState(state_obj.getString("state_name"));
                                    m.setDistrict(district_obj.getString("district_name"));
                                    m.setBlock(block_obj.getString("block_name"));
                                    m.setVid(user_obj.getString("vid"));
                                    m.setVillage_name(user_obj.getString("village_name"));
                                    m.setMobile_number(user_obj.getString("mobile_number"));
                                    m.setPost_id(obj.getString("post_id"));
                                    m.setIsverified(user_obj.getString("is_verified"));
                                    JSONObject available_labour_obj = obj.getJSONObject("available_labour");
                                    JSONObject labour_expertise_obj = obj.getJSONObject("labour_expertise");
                                    JSONObject reaching_time_on_site = obj.getJSONObject("reaching_time_on_site");
                                    JSONObject price_by = obj.getJSONObject("price_by");
                                    m.setAvailable_labour(available_labour_obj.getString("available_labour"));
                                    m.setExpertise(labour_expertise_obj.getString("expertise"));
                                    m.setReaching_time_status(reaching_time_on_site.getString("reaching_time_on_site"));
                                    m.setPosted_on(obj.getString("posted_on"));
                                    m.setPrice(obj.getString("price"));
                                    m.setPrice_by(price_by.getString("per"));
                                    m.setProduct_image1(obj.getString("product_image1"));
                                    m.setProduct_image2(obj.getString("product_image2"));
                                    m.setProduct_image3(obj.getString("product_image3"));
                                    m.setAdditional_information(obj.getString("additional_information"));
                                    m.setCatgeory_name("Labour in Rent");
                                    m.setPost_id(obj.getString("post_id"));
                                    Log.e("labour_in_rent_list", labour_in_rent_list.toString());
                                    labour_in_rent_list.add(m);
                                }
                            }
                            if (labour_in_rent_list.size() > 0) {
                                no_record_tv.setVisibility(View.GONE);
                                cattle_ads_recyclerview.setHasFixedSize(true);
                                customize_relative.setVisibility(View.VISIBLE);
                                cattleLayoutManager = new LinearLayoutManager(context);
                                labourAdapter = new LabourinRentsAdapter(context, labour_in_rent_list);
                                cattle_ads_recyclerview.setLayoutManager(cattleLayoutManager);
                                cattle_ads_recyclerview.setAdapter(labourAdapter);
                                scan_cardview.setVisibility(View.VISIBLE);
                            } else {
                                no_record_tv.setVisibility(View.VISIBLE);
                                scan_cardview.setVisibility(View.GONE);
                                customize_relative.setVisibility(View.GONE);
                                searchLinear.setVisibility(View.GONE);
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
                params.put("Accept-Language", language_code);
                return params;
            }
//            @Override
//            protected Map<String, String> getParams() {
//                Map<String, String> params = new HashMap<String, String>();
//                params.put("super_category", super_category);
//                Log.v("request", params.toString());
//                return params;
//            }
        };
        postRequest.setRetryPolicy(new DefaultRetryPolicy(30000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(context).add(postRequest);
    }

    private void getOtherAgriProductList(String super_category) {
        other_agri_list.clear();
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
        String url = "https://ekrishibazaar.com/api/ads/filterads/?limit=" + limit + "&offset=" + offset + "&super_category=" + super_category;
//        if (next_url != null) {
//            url = next_url;
//        } else {
//
//        }
        if (search_state != null && !search_state.isEmpty()) {
            url = url + "&state=" + search_state;
        }
        if (search_district != null && !search_district.isEmpty()) {
            url = url + "&district=" + search_district;
        }
        if (min_price != null && !min_price.isEmpty()) {
            url = url + "&product_price_min=" + min_price;
        }
        if (max_price != null && !max_price.isEmpty()) {
            url = url + "&product_price_max=" + max_price;
        }
        StringRequest postRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.v("response", response);
                        try {
                            mProgressDialog.dismiss();
                            response = new String(response.getBytes("ISO-8859-1"), "UTF-8");
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray data_array = jsonObject.getJSONArray("results");

                            OtherAgriModel m;
                            for (int i = 0; i < data_array.length(); i++) {
                                JSONObject obj = data_array.getJSONObject(i);
                                boolean b = obj.getString("type").equals("otheragriads");
                                next_url = jsonObject.getString("next");
                                Log.e("type", String.valueOf(b));
                                if (b) {
                                    JSONObject user_obj = obj.getJSONObject("user");
                                    JSONObject user_2_obj = user_obj.getJSONObject("user");
                                    m = new OtherAgriModel();
                                    m.setUser_first_name(user_2_obj.getString("first_name"));
                                    m.setUser_last_name(user_2_obj.getString("last_name"));
                                    m.setDate_joined(user_2_obj.getString("date_joined"));
                                    m.setProfile_image(user_obj.getString("image"));
                                    JSONObject state_obj = user_obj.getJSONObject("state");
                                    JSONObject district_obj = user_obj.getJSONObject("district");
                                    JSONObject block_obj = user_obj.getJSONObject("block");

                                    m.setState(state_obj.getString("state_name"));
                                    m.setDistrict(district_obj.getString("district_name"));
                                    m.setBlock(block_obj.getString("block_name"));
                                    m.setVid(user_obj.getString("vid"));
                                    m.setVillage_name(user_obj.getString("village_name"));
                                    m.setMobile_number(user_obj.getString("mobile_number"));
                                    m.setPost_id(obj.getString("post_id"));
                                    m.setIsverified(user_obj.getString("is_verified"));
                                    JSONObject types_obj = obj.getJSONObject("types");
                                    JSONObject category_obj = types_obj.getJSONObject("category");
                                    m.setCategory_name(category_obj.getString("category_name"));
                                    m.setCategory_image(category_obj.getString("image"));

                                    m.setChemical_product_name(types_obj.getString("type_of_product"));
                                    m.setPosted_on(obj.getString("posted_on"));
                                    m.setPrice(obj.getString("price"));
                                    m.setPer_unit(obj.getString("per_unit"));
                                    m.setPaking(obj.getString("packing"));

                                    m.setProduct_image1(obj.getString("product_image1"));
                                    m.setProduct_image2(obj.getString("product_image2"));
                                    m.setProduct_image3(obj.getString("product_image3"));
                                    m.setAdditional_information(obj.getString("additional_information"));
                                    m.setPost_id(obj.getString("post_id"));
                                    Log.e("other_agri_products", String.valueOf(other_agri_list.size()));
                                    other_agri_list.add(m);
                                }
                            }
                            if (other_agri_list.size() > 0) {
                                no_record_tv.setVisibility(View.GONE);
                                cattle_ads_recyclerview.setHasFixedSize(true);
                                customize_relative.setVisibility(View.VISIBLE);
                                cattleLayoutManager = new LinearLayoutManager(context);
                                otherAgriAdapter = new OtherAgriAdapter(context, other_agri_list);
                                cattle_ads_recyclerview.setLayoutManager(cattleLayoutManager);
                                cattle_ads_recyclerview.setAdapter(otherAgriAdapter);
                                mProgressDialog.dismiss();
                                scan_cardview.setVisibility(View.VISIBLE);
                            } else {
                                no_record_tv.setVisibility(View.VISIBLE);
                                scan_cardview.setVisibility(View.GONE);
                                customize_relative.setVisibility(View.GONE);
                                searchLinear.setVisibility(View.GONE);
                            }

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
                params.put("Accept-Language", language_code);
                return params;
            }

//            @Override
//            protected Map<String, String> getParams() {
//                Map<String, String> params = new HashMap<String, String>();
//                params.put("super_category", super_category);
//                Log.v("request", params.toString());
//                return params;
//            }
        };
        postRequest.setRetryPolicy(new DefaultRetryPolicy(30000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(context).add(postRequest);
    }

    private void getOtherAgriMachinary(String super_category) {
        machinary_list.clear();
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
        String url = "https://ekrishibazaar.com/api/ads/filterads/?limit=" + limit + "&offset=" + offset + "&super_category=" + super_category;
//        if (next_url != null) {
//            url = next_url;
//        } else {
//            u
//        }
        if (search_state != null && !search_state.isEmpty()) {
            url = url + "&state=" + search_state;
        }
        if (search_district != null && !search_district.isEmpty()) {
            url = url + "&district=" + search_district;
        }
        if (min_price != null && !min_price.isEmpty()) {
            url = url + "&product_price_min=" + min_price;
        }
        if (max_price != null && !max_price.isEmpty()) {
            url = url + "&product_price_max=" + max_price;
        }

        StringRequest postRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.v("response", response);
                        try {
                            mProgressDialog.dismiss();
                            response = new String(response.getBytes("ISO-8859-1"), "UTF-8");
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray data_array = jsonObject.getJSONArray("results");
                            next_url = jsonObject.getString("next");
                            AgricultureMachinaryModel m;
                            for (int i = 0; i < data_array.length(); i++) {
                                JSONObject obj = data_array.getJSONObject(i);
                                boolean b = obj.getString("type").equals("machinaryads");
                                Log.e("type", String.valueOf(b));
                                if (b) {
                                    JSONObject user_obj = obj.getJSONObject("user");
                                    JSONObject user_2_obj = user_obj.getJSONObject("user");
                                    m = new AgricultureMachinaryModel();
                                    m.setUser_first_name(user_2_obj.getString("first_name"));
                                    m.setUser_last_name(user_2_obj.getString("last_name"));
                                    m.setDate_joined(user_2_obj.getString("date_joined"));
                                    m.setProfile_image(user_obj.getString("image"));
                                    JSONObject state_obj = user_obj.getJSONObject("state");
                                    JSONObject district_obj = user_obj.getJSONObject("district");
                                    JSONObject block_obj = user_obj.getJSONObject("block");

                                    m.setState(state_obj.getString("state_name"));
                                    m.setDistrict(district_obj.getString("district_name"));
                                    m.setBlock(block_obj.getString("block_name"));
                                    m.setVid(user_obj.getString("vid"));
                                    m.setVillage_name(user_obj.getString("village_name"));
                                    m.setMobile_number(user_obj.getString("mobile_number"));
                                    m.setPost_id(obj.getString("post_id"));

                                    JSONObject machine_obj = obj.getJSONObject("machine");
                                    JSONObject category_obj = machine_obj.getJSONObject("category");
                                    m.setMachine_name(machine_obj.getString("machine_name"));
                                    m.setIsverified(user_obj.getString("is_verified"));
                                    JSONObject machine_brand_obj = obj.getJSONObject("machine_brand");
                                    JSONObject machine_status_obj = obj.getJSONObject("machine_status");

                                    m.setMachine_brand(machine_brand_obj.getString("machinery_brand"));
                                    m.setMachine_status(machine_status_obj.getString("machine_status"));
                                    m.setMonths_in_use(obj.getString("months_in_use"));
                                    m.setPosted_on(obj.getString("posted_on"));
                                    m.setPrice(obj.getString("price"));

                                    m.setProduct_image1(obj.getString("product_image1"));
                                    m.setProduct_image2(obj.getString("product_image2"));
                                    m.setProduct_image3(obj.getString("product_image3"));
                                    m.setAdditional_info(obj.getString("additional_information"));
                                    m.setPost_id(obj.getString("post_id"));
                                    machinary_list.add(m);
                                    int size = machinary_list.size();
                                    Log.e("other_agri_products", String.valueOf(machinary_list.size()));
                                }
                            }
                            if (machinary_list.size() > 0) {
                                no_record_tv.setVisibility(View.GONE);
                                cattle_ads_recyclerview.setHasFixedSize(true);
                                customize_relative.setVisibility(View.VISIBLE);
                                cattleLayoutManager = new LinearLayoutManager(context);
                                machinaryAgriAdapter = new AgricultureMachinaryAdapter(context, machinary_list);
                                cattle_ads_recyclerview.setLayoutManager(cattleLayoutManager);
                                cattle_ads_recyclerview.setAdapter(machinaryAgriAdapter);
                                mProgressDialog.dismiss();
                                scan_cardview.setVisibility(View.VISIBLE);
                            } else {
                                no_record_tv.setVisibility(View.VISIBLE);
                                scan_cardview.setVisibility(View.GONE);
                                customize_relative.setVisibility(View.GONE);
                                searchLinear.setVisibility(View.GONE);
                            }

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
                params.put("Accept-Language", language_code);
                return params;
            }

//            @Override
//            protected Map<String, String> getParams() {
//                Map<String, String> params = new HashMap<String, String>();
//                params.put("super_category", super_category);
////              Log.v("request", params.toString());
//                return params;
//            }
        };
        postRequest.setRetryPolicy(new DefaultRetryPolicy(30000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(context).add(postRequest);
    }


    private void getTreeAndWoodsList(String super_category) {
        tree_and_woods_list.clear();
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
        String url = "https://ekrishibazaar.com/api/ads/filterads/?limit=" + limit + "&offset=" + offset + "&super_category=" + super_category;
//        if (next_url != null) {
//            url = next_url;
//        } else {
//
//        }
        if (search_state != null && !search_state.isEmpty()) {
            url = url + "&state=" + search_state;
        }
        if (search_district != null && !search_district.isEmpty()) {
            url = url + "&district=" + search_district;
        }
        if (min_price != null && !min_price.isEmpty()) {
            url = url + "&product_price_min=" + min_price;
        }
        if (max_price != null && !max_price.isEmpty()) {
            url = url + "&product_price_max=" + max_price;
        }
        StringRequest postRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.v("response", response);
                        try {
                            response = new String(response.getBytes("ISO-8859-1"), "UTF-8");
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray data_array = jsonObject.getJSONArray("results");
                            next_url = jsonObject.getString("next");
                            TreeAndWoodsModel m;
                            for (int i = 0; i < data_array.length(); i++) {
                                JSONObject obj = data_array.getJSONObject(i);
                                boolean b = obj.getString("type").equals("treeandwoodsads");
                                Log.e("type", String.valueOf(b));
                                if (b) {
                                    JSONObject user_obj = obj.getJSONObject("user");
                                    JSONObject user_2_obj = user_obj.getJSONObject("user");
                                    m = new TreeAndWoodsModel();
                                    m.setUser_first_name(user_2_obj.getString("first_name"));
                                    m.setUser_last_name(user_2_obj.getString("last_name"));
                                    m.setDate_joined(user_2_obj.getString("date_joined"));
                                    m.setProfile_image(user_obj.getString("image"));
                                    JSONObject state_obj = user_obj.getJSONObject("state");
                                    JSONObject district_obj = user_obj.getJSONObject("district");
                                    JSONObject block_obj = user_obj.getJSONObject("block");
                                    m.setIsverified(user_obj.getString("is_verified"));
                                    m.setState(state_obj.getString("state_name"));
                                    m.setDistrict(district_obj.getString("district_name"));
                                    m.setBlock(block_obj.getString("block_name"));
                                    m.setVid(user_obj.getString("vid"));
                                    m.setVillage_name(user_obj.getString("village_name"));
                                    m.setMobile_number(user_obj.getString("mobile_number"));
                                    m.setPost_id(obj.getString("post_id"));

                                    JSONObject wood_type_obj = obj.getJSONObject("wood_type");
                                    m.setWood_name(wood_type_obj.getString("wood_name"));
                                    m.setQuantity(obj.getString("quantity"));
                                    m.setPosted_on(obj.getString("posted_on"));
                                    m.setPrice(obj.getString("price"));
                                    m.setType(obj.getString("type"));

                                    m.setProduct_image1(obj.getString("product_image1"));
                                    m.setProduct_image2(obj.getString("product_image2"));
                                    m.setProduct_image3(obj.getString("product_image3"));
                                    m.setAdditional_information(obj.getString("additional_information"));
                                    m.setCategory_name("Tree and Woods");
                                    m.setPost_id(obj.getString("post_id"));
                                    tree_and_woods_list.add(m);
                                }
                            }
                            if (tree_and_woods_list.size() > 0) {
                                no_record_tv.setVisibility(View.GONE);
                                scan_cardview.setVisibility(View.VISIBLE);
                                customize_relative.setVisibility(View.VISIBLE);
                                cattle_ads_recyclerview.setHasFixedSize(true);
                                cattleLayoutManager = new LinearLayoutManager(context);
                                treeAndWoodsAdapter = new TreeandWoodsAdapter(context, tree_and_woods_list);
                                cattle_ads_recyclerview.setLayoutManager(cattleLayoutManager);
                                cattle_ads_recyclerview.setAdapter(treeAndWoodsAdapter);
                                mProgressDialog.dismiss();

                            } else {
                                no_record_tv.setVisibility(View.VISIBLE);
                                scan_cardview.setVisibility(View.GONE);
                                customize_relative.setVisibility(View.GONE);
                                searchLinear.setVisibility(View.GONE);
                            }
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
                params.put("Accept-Language", language_code);
                return params;
            }
        };
        postRequest.setRetryPolicy(new DefaultRetryPolicy(30000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(context).add(postRequest);
    }

    private void getFertilizersList(String super_category) {
        fertilizer_list.clear();
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
        String url = "https://ekrishibazaar.com/api/ads/filterads/?limit=" + limit + "&offset=" + offset + "&super_category=" + super_category;
//        if (next_url != null) {
//            url = next_url;
//        } else {
//
//        }
        if (search_state != null && !search_state.isEmpty()) {
            url = url + "&state=" + search_state;
        }
        if (search_district != null && !search_district.isEmpty()) {
            url = url + "&district=" + search_district;
        }
        if (min_price != null && !min_price.isEmpty()) {
            url = url + "&product_price_min=" + min_price;
        }
        if (max_price != null && !max_price.isEmpty()) {
            url = url + "&product_price_max=" + max_price;
        }
        StringRequest postRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.v("response", response);
                        try {
                            mProgressDialog.dismiss();
                            response = new String(response.getBytes("ISO-8859-1"), "UTF-8");
                            JSONObject jsonObject = new JSONObject(response);
                            JSONArray data_array = jsonObject.getJSONArray("results");
                            next_url = jsonObject.getString("next");
                            FertilizerListModel m;
                            for (int i = 0; i < data_array.length(); i++) {
                                JSONObject obj = data_array.getJSONObject(i);
                                boolean b = obj.getString("type").equals("fertilizerandpestisidesads");
                                Log.e("type", String.valueOf(b));
                                if (b) {
                                    JSONObject user_obj = obj.getJSONObject("user");
                                    JSONObject user_2_obj = user_obj.getJSONObject("user");
                                    m = new FertilizerListModel();
                                    m.setUser_first_name(user_2_obj.getString("first_name"));
                                    m.setUser_last_name(user_2_obj.getString("last_name"));
                                    m.setDate_joined(user_2_obj.getString("date_joined"));
                                    m.setProfile_image(user_obj.getString("image"));
                                    JSONObject state_obj = user_obj.getJSONObject("state");
                                    JSONObject district_obj = user_obj.getJSONObject("district");
                                    JSONObject block_obj = user_obj.getJSONObject("block");
                                    m.setIsverified(user_obj.getString("is_verified"));
                                    m.setState(state_obj.getString("state_name"));
                                    m.setDistrict(district_obj.getString("district_name"));
                                    m.setBlock(block_obj.getString("block_name"));
                                    m.setVid(user_obj.getString("vid"));
                                    m.setVillage_name(user_obj.getString("village_name"));
                                    m.setMobile_number(user_obj.getString("mobile_number"));
                                    m.setPost_id(obj.getString("post_id"));

                                    JSONObject chemical_product_obj = obj.getJSONObject("chemical_product");
                                    JSONObject chemical_type = obj.getJSONObject("chemical_type");
                                    m.setChemical_product_name(chemical_product_obj.getString("chemical_product_name"));
                                    m.setType_of_chemical(chemical_type.getString("type_of_chemical"));
                                    m.setPosted_on(obj.getString("posted_on"));
                                    m.setPrice(obj.getString("price"));
                                    m.setType(obj.getString("type"));
                                    m.setPer_unit(obj.getString("per_unit"));
                                    m.setPacking(obj.getString("packing"));
                                    m.setProduct_image1(obj.getString("product_image1"));
                                    m.setProduct_image2(obj.getString("product_image2"));
                                    m.setProduct_image3(obj.getString("product_image3"));
                                    m.setAdditional_information(obj.getString("additional_information"));
                                    m.setCategory_name("Fertilizers and Pesticides");
                                    m.setPost_id(obj.getString("post_id"));
                                    fertilizer_list.add(m);
                                    Log.e("list_size", String.valueOf(fertilizer_list.size()));
                                }
                            }
                            if (fertilizer_list.size() > 0) {
                                no_record_tv.setVisibility(View.GONE);
                                scan_cardview.setVisibility(View.VISIBLE);
                                customize_relative.setVisibility(View.VISIBLE);
                                cattle_ads_recyclerview.setHasFixedSize(true);
                                cattleLayoutManager = new LinearLayoutManager(context);
                                fertilizerAdapter = new FertilizerAdapter(context, fertilizer_list);
                                cattle_ads_recyclerview.setLayoutManager(cattleLayoutManager);
                                cattle_ads_recyclerview.setAdapter(fertilizerAdapter);
                            } else {
                                scan_cardview.setVisibility(View.GONE);
                                no_record_tv.setVisibility(View.VISIBLE);
                                customize_relative.setVisibility(View.GONE);
                                searchLinear.setVisibility(View.GONE);
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
                params.put("Accept-Language", language_code);
                return params;
            }

        };
        postRequest.setRetryPolicy(new DefaultRetryPolicy(30000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        Volley.newRequestQueue(context).add(postRequest);
    }


    void getStates() {
        state_list.clear();
//        final ProgressDialog mProgressDialog = new ProgressDialog(this);
//        mProgressDialog.setIndeterminate(true);
//        mProgressDialog.setMessage("Loading...");
//        mProgressDialog.setCancelable(false);
//        mProgressDialog.setCanceledOnTouchOutside(false);
//        mProgressDialog.setOnCancelListener(new Dialog.OnCancelListener() {
//
//            @Override
//            public void onCancel(DialogInterface dialog) {
//                // DO SOME STUFF HERE
//            }
//        });
//        mProgressDialog.show();
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
//                            mProgressDialog.dismiss();
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
//                            mProgressDialog.dismiss();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                        mProgressDialog.dismiss();
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
        blocks_list.clear();
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
                                blocks_list.add(obj.getString("block_name"));
                            }
                            ArrayAdapter<String> stateAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, blocks_list);
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
                        blocks_list.add("Block");
                        ArrayAdapter<String> stateAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, blocks_list);
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

    void getProducts(String category_name) {

        String url = "";
        if (category.equals("Fruits") || category.equals("Pulses") || category.equals("Medicinal plants") || category.equals("Dairy Product") || category.equals("Vegetable") || category.equals("Grains")
                || category.equals("Flower") || category.equals("oilseeds")) {
            url = "https://ekrishibazaar.com/api/ads/products/?search=" + category_name;
        } else if (category.equalsIgnoreCase("Cattle")) {
            url = "https://ekrishibazaar.com/api/ads/cattletypes/";
        } else if (category.equalsIgnoreCase("Fertilizers and Pesticides")) {
            url = "https://ekrishibazaar.com/api/ads/chemicaltypes/";
        } else if (category.equalsIgnoreCase("Labour in Rent")) {
            url = "https://ekrishibazaar.com/api/ads/labourexpertise/";
        } else if (category.equalsIgnoreCase("Other Agri Product")) {
            url = "https://ekrishibazaar.com/api/ads/otheragriproducttypes/";
        } else if (category.equalsIgnoreCase("Service in Rent")) {
            url = "https://ekrishibazaar.com/api/ads/servicemachinary/";
        } else if (category.equalsIgnoreCase("Tree and Woods")) {
            url = "https://ekrishibazaar.com/api/ads/woodtype/";
        } else if (category.equalsIgnoreCase("Agricultural machinary")) {
            url = "https://ekrishibazaar.com/api/ads/agriculturalmachinary/";
        }
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
                url,
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
                                if (category.equalsIgnoreCase("Cattle")) {
                                    product_list.add((obj.getString("cattle_name")));
                                } else if (category_name.equalsIgnoreCase("Fertilizers and Pesticides")) {
                                    product_list.add((obj.getString("product_name")));
                                } else {
                                    product_list.add((obj.getString("type_of_chemical")));
                                }
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
                        product_list.add("Select Product");
                        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, product_list);
                        select_product_spinner.setAdapter(categoryAdapter);
                        // Do something when error occurred
                    }
                }
        );
        // Add JsonArrayRequest to the RequestQueue
        VolleySingleton.getInstance(context).addToRequestQueue(jsonArrayRequest);
    }
}
