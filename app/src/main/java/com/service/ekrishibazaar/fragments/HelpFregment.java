package com.service.ekrishibazaar.fragments;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.service.ekrishibazaar.R;

public class HelpFregment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_help, container, false);
        init(root);
        return root;
    }

    private void init(View root) {
    }
}