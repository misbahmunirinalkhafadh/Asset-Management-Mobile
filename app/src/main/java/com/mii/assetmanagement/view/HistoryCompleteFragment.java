package com.mii.assetmanagement.view;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mii.assetmanagement.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class HistoryCompleteFragment extends Fragment {

    static HistoryCompleteFragment newInstance() {
        return new HistoryCompleteFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_history_complete, container, false);
    }
}
