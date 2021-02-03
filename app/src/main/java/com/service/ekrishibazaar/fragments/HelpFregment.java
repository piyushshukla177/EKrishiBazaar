package com.service.ekrishibazaar.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.service.ekrishibazaar.AboutusActivity;
import com.service.ekrishibazaar.ContactUsActivity;
import com.service.ekrishibazaar.MoreServicesActivity;
import com.service.ekrishibazaar.R;

public class HelpFregment extends Fragment {

    LinearLayout terms_and_condition_linear, ads_posting_guidelines_linear, legal_privacy_policy_linear, trust_and_safety_linear, buyers_faqs_linear,
            general_faqs_linear, be_varified_user_linear, about_us_linear, contact_us_linear;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_help, container, false);
        init(root);
        return root;
    }

    private void init(View root) {
        terms_and_condition_linear = root.findViewById(R.id.terms_and_condition_linear);
        ads_posting_guidelines_linear = root.findViewById(R.id.ads_posting_guidelines_linear);
        legal_privacy_policy_linear = root.findViewById(R.id.legal_privacy_policy_linear);
        trust_and_safety_linear = root.findViewById(R.id.trust_and_safety_linear);
        buyers_faqs_linear = root.findViewById(R.id.buyers_faqs_linear);
        general_faqs_linear = root.findViewById(R.id.general_faqs_linear);
        be_varified_user_linear = root.findViewById(R.id.be_varified_user_linear);
        about_us_linear = root.findViewById(R.id.about_us_linear);
        contact_us_linear = root.findViewById(R.id.contact_us_linear);
        terms_and_condition_linear.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), MoreServicesActivity.class);
                        intent.putExtra("url", "https://www.ekrishibazaar.com/terms-to-this-platform");
                        startActivity(intent);

//                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.ekrishibazaar.com/terms-to-this-platform"));
//                        startActivity(browserIntent);
                    }
                }
        );
        ads_posting_guidelines_linear.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), MoreServicesActivity.class);
                        intent.putExtra("url", "https://www.ekrishibazaar.com/adpostingguidelines");
                        startActivity(intent);

//                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.ekrishibazaar.com/adpostingguidelines"));
//                        startActivity(browserIntent);
                    }
                }
        );
        legal_privacy_policy_linear.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), MoreServicesActivity.class);
                        intent.putExtra("url", "https://www.ekrishibazaar.com/legalandprivacypolicy");
                        startActivity(intent);
//                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.ekrishibazaar.com/legalandprivacypolicym"));
//                        startActivity(browserIntent);
                    }
                }
        );
        trust_and_safety_linear.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), MoreServicesActivity.class);
                        intent.putExtra("url", "https://www.ekrishibazaar.com/trust-and-safety");
                        startActivity(intent);
//                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.ekrishibazaar.com/trust-and-safety"));
//                        startActivity(browserIntent);
                    }
                }
        );
        buyers_faqs_linear.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), MoreServicesActivity.class);
                        intent.putExtra("url", "https://www.ekrishibazaar.com/buyerfaq");
                        startActivity(intent);

//                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.ekrishibazaar.com/buyerfaq"));
//                        startActivity(browserIntent);
                    }
                }
        );
        general_faqs_linear.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), MoreServicesActivity.class);
                        intent.putExtra("url", "https://www.ekrishibazaar.com/generalfaq");
                        startActivity(intent);

//                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.ekrishibazaar.com/generalfaq"));
//                        startActivity(browserIntent);
                    }
                }
        );
        be_varified_user_linear.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), MoreServicesActivity.class);
                        intent.putExtra("url", "https://www.ekrishibazaar.com/beverifieduser");
                        startActivity(intent);

//                        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.ekrishibazaar.com/beverifieduser"));
//                        startActivity(browserIntent);
                    }
                }
        );
        about_us_linear.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), AboutusActivity.class);
                        startActivity(intent);
                    }
                }
        );
        contact_us_linear.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), ContactUsActivity.class);
                        startActivity(intent);
                    }
                }
        );
    }
}
