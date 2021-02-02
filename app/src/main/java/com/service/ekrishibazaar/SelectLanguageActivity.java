package com.service.ekrishibazaar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.service.ekrishibazaar.adapter.SelectLanguageAdapter;
import com.service.ekrishibazaar.model.SelectLanguageModel;
import com.service.ekrishibazaar.util.LocaleHelper;
import com.service.ekrishibazaar.util.PrefsHelper;
import com.service.ekrishibazaar.util.VolleySingleton;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

public class SelectLanguageActivity extends AppCompatActivity {

    TextView select_lang_tv;
    Context context;
    RecyclerView languages_recylerview;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    ArrayList<SelectLanguageModel> language_list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_language);

        init();
    }

    SelectLanguageModel m;

    private void init() {
        context = this;
        languages_recylerview = findViewById(R.id.languages_recylerview);
        select_lang_tv = findViewById(R.id.select_lang_tv);
        select_lang_tv.setVisibility(View.GONE);

        getLanguages();
    }

    void getLanguages() {
        language_list.clear();
        final ProgressDialog mProgressDialog = new ProgressDialog(this);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.setMessage("Loading...");
        mProgressDialog.show();
        // Initialize a new RequestQueue instance

        // Initialize a new JsonArrayRequest instance
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                "https://ekrishibazaar.com/api/supportedlanguages/",
                null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        // Do something with response
                        //mTextView.setText(response.toString());
                        // Process the JSON
                        try {
                            mProgressDialog.dismiss();
                            select_lang_tv.setVisibility(View.VISIBLE);
                            SelectLanguageModel m;
                            // Loop through the array elements
                            for (int i = 0; i < response.length(); i++) {
                                // Get current json object
                                JSONObject obj = response.getJSONObject(i);
                                if (obj.getString("eng_name") != null && !obj.getString("eng_name").isEmpty() && !obj.getString("eng_name").equals("null")) {
                                    m = new SelectLanguageModel();
                                    m.setEng_name(obj.getString("eng_name"));
                                    m.setLang_code(obj.getString("lang_code"));
                                    m.setLang_name(obj.getString("lang_name"));
                                    language_list.add(m);
                                }
                            }
                            languages_recylerview.setHasFixedSize(true);
                            mLayoutManager = new LinearLayoutManager(context);
                            mAdapter = new SelectLanguageAdapter(context, language_list);
                            languages_recylerview.setLayoutManager(mLayoutManager);
                            languages_recylerview.setAdapter(mAdapter);
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

    public void setLanguage(String lang_code, String lang_name) {
        PrefsHelper.putString(this, "lang_code", lang_code);
        PrefsHelper.putString(this, "lang_name", lang_name);

        LocaleHelper.setLocale(SelectLanguageActivity.this, lang_code);

        Intent intent = new Intent(SelectLanguageActivity.this, AboutActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base));
    }
}

/*
https://ekrishibazaar.com/api/supportedlanguages/
 */