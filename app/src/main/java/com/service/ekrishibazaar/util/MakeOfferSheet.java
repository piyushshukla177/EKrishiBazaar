package com.service.ekrishibazaar.util;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.service.ekrishibazaar.R;

public class MakeOfferSheet extends BottomSheetDialogFragment {
    private MakeOfferListener mListener;
    EditText phone_number_et, actual_price_et, offer_price_et;
    Button make_offer_btn;
    public static String actual_price;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.make_offer_sheet_layout, container, false);

        phone_number_et = v.findViewById(R.id.phone_number_et);
        actual_price_et = v.findViewById(R.id.actual_price_et);
        offer_price_et = v.findViewById(R.id.offer_price_et);
        make_offer_btn = v.findViewById(R.id.make_offer_btn);

        make_offer_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mListener.onMakeOffer(phone_number_et.getText().toString(), actual_price_et.getText().toString(), offer_price_et.getText().toString());
            }
        });
        actual_price_et.setText(String.valueOf(actual_price));
        return v;
    }

    public interface MakeOfferListener {
        void onMakeOffer(String phone, String actual_price, String offer_price);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mListener = (MakeOfferListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement BottomSheetListener");
        }
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}