package com.mii.assetmanagement.view;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.mii.assetmanagement.R;
import com.mii.assetmanagement.SharedPrefManager;
import com.mii.assetmanagement.adapter.HistoryProgressAdapter;
import com.mii.assetmanagement.model.HistoryResult;
import com.mii.assetmanagement.viewmodel.HistoryViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * A simple {@link Fragment} subclass.
 */
public class HistoryProgressFragment extends Fragment {
    private RecyclerView recyclerView;
    private ProgressBar progressBar;
    private ImageView imgEmptyList;
    private FrameLayout layout;
    private Snackbar snackbar;
    private HistoryProgressAdapter adapter;
    private HistoryViewModel historyViewModel;
    private ArrayList<HistoryResult> historyArrayList = new ArrayList<>();
    private static String REQUEST_TYPE = "";
    private static final String REQUEST_STATUS = "inprogress";

    static HistoryProgressFragment newInstance(String reqType) {
        REQUEST_TYPE = reqType;
        return new HistoryProgressFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_history_progress, container, false);
        initComponent(view);

        SharedPrefManager sharedPrefManager = new SharedPrefManager(Objects.requireNonNull(getActivity()));
        int nik = Integer.parseInt(sharedPrefManager.getSpNik());
        historyViewModel = ViewModelProviders.of(Objects.requireNonNull(getActivity()), new ViewModelProvider.NewInstanceFactory()).get(HistoryViewModel.class);
        if (isNetworkAvailable()){
            historyViewModel.setHistoryProgress(REQUEST_TYPE, nik, REQUEST_STATUS);
            progressBar.setVisibility(View.VISIBLE);
        } else {
            showSnackBar();
            imgEmptyList.setVisibility(View.VISIBLE);
        }

        setupRecyclerView();
        return view;
    }

    private void initComponent(View view) {
        recyclerView = view.findViewById(R.id.rv_history);
        progressBar = view.findViewById(R.id.progressbar);
        imgEmptyList = view.findViewById(R.id.img_empty_list);
        layout = view.findViewById(R.id.main_view);

        imgEmptyList.setVisibility(View.GONE);
        progressBar.setVisibility(View.GONE);
    }

    private void setupRecyclerView() {
        if (adapter == null) {
            adapter = new HistoryProgressAdapter(getActivity(), historyArrayList);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerView.addItemDecoration(new CustomDividerItemDecoration(recyclerView.getContext(), LinearLayoutManager.VERTICAL, 16));
            recyclerView.setAdapter(adapter);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setNestedScrollingEnabled(true);
        } else {
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        historyViewModel.getHistoryProgress().observe(Objects.requireNonNull(getActivity()), histories -> {
            if (histories.getResult() != null) {
                List<HistoryResult> resultList = histories.getResult();
                historyArrayList.addAll(resultList);
                adapter.notifyDataSetChanged();
            } else {
                imgEmptyList.setVisibility(View.VISIBLE);
            }
            progressBar.setVisibility(View.GONE);
        });
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private void showSnackBar() {
        snackbar = Snackbar
                .make(layout, "No Internet Connection", Snackbar.LENGTH_LONG)
                .setAction("DISMISS", v -> snackbar.dismiss());
        snackbar.show();
    }
}
