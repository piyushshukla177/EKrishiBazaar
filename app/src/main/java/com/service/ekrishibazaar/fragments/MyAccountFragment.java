package com.service.ekrishibazaar.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.service.ekrishibazaar.LoginActivity;
import com.service.ekrishibazaar.R;
import com.service.ekrishibazaar.util.PrefsHelper;

public class MyAccountFragment extends Fragment {

    TextView user_name_tv;
    LinearLayout sign_in_linear, root_linear, sign_out_linear;
    String token, firstName, LastName = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_my_account, container, false);
        init(root);
        return root;
    }

    private void init(View root) {
        root_linear = root.findViewById(R.id.root_linear);
        sign_out_linear = root.findViewById(R.id.sign_out_linear);

        sign_in_linear = root.findViewById(R.id.sign_in_linear);
        user_name_tv = root.findViewById(R.id.user_name_tv);
        sign_in_linear.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), LoginActivity.class);
                        startActivity(intent);
                        getActivity().getFragmentManager().popBackStack();
                    }
                }
        );
        sign_out_linear.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        new AlertDialog.Builder(getActivity())
                                // .setTitle("Delete entry")
                                .setMessage("Are you sure you want to logout !!")

                                // Specifying a listener allows you to take an action before dismissing the dialog.
                                // The dialog is automatically dismissed when a dialog button is clicked.
                                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        // Continue with delete operation
                                        PrefsHelper.remove(getActivity(), "token");
                                        PrefsHelper.remove(getActivity(), "first_name");
                                        PrefsHelper.remove(getActivity(), "last_name");
                                        PrefsHelper.remove(getActivity(), "vid");
                                        PrefsHelper.remove(getActivity(), "blocked");
                                        sign_in_linear.setVisibility(View.VISIBLE);
                                        root_linear.setVisibility(View.GONE);
                                    }
                                })
                                // A null listener allows the button to dismiss the dialog and take no further action.
                                .setNegativeButton(android.R.string.no, null)
                                .setIcon(android.R.drawable.ic_dialog_alert)
                                .show();
                    }
                }
        );
        try {
            if (PrefsHelper.getString(getActivity(), "token") != null) {
                token = PrefsHelper.getString(getActivity(), "token");
            }
            if (PrefsHelper.getString(getActivity(), "first_name") != null) {
                firstName = PrefsHelper.getString(getActivity(), "first_name");
            }
            if (PrefsHelper.getString(getActivity(), "last_name") != null) {
                LastName = PrefsHelper.getString(getActivity(), "last_name");
                user_name_tv.setText(firstName + " " + LastName);
            }
            if (!token.isEmpty()) {
                sign_in_linear.setVisibility(View.GONE);
                root_linear.setVisibility(View.VISIBLE);
            } else {
//                logout_linear.setVisibility(View.GONE);
            }
        } catch (Exception ex) {
            sign_in_linear.setVisibility(View.VISIBLE);
            root_linear.setVisibility(View.GONE);
            ex.printStackTrace();
        }
    }
}
