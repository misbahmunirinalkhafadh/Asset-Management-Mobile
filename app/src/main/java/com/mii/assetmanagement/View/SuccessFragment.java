package com.mii.assetmanagement.View;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.mii.assetmanagement.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class SuccessFragment extends Fragment {
    Button btnHome;
    public SuccessFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_success, container, false);
        btnHome = view.findViewById(R.id.btn_continue);
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent backToHome = new Intent(getActivity(), MainActivity.class);
                getActivity().startActivity(backToHome);
                getActivity().finish();
            }
        });
        return view;
    }

}
