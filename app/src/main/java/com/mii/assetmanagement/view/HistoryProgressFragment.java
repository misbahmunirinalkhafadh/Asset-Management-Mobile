package com.mii.assetmanagement.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
    private ImageView imgListSearch;
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
        recyclerView = view.findViewById(R.id.rv_history);
        progressBar = view.findViewById(R.id.progressbar);
        imgListSearch = view.findViewById(R.id.img_search_list);
        imgListSearch.setVisibility(View.GONE);

        SharedPrefManager sharedPrefManager = new SharedPrefManager(Objects.requireNonNull(getActivity()));
        int nik = Integer.parseInt(sharedPrefManager.getSpNik());
        historyViewModel = ViewModelProviders.of(Objects.requireNonNull(getActivity()), new ViewModelProvider.NewInstanceFactory()).get(HistoryViewModel.class);
        historyViewModel.setHistoryProgress(REQUEST_TYPE, nik, REQUEST_STATUS);

        setupRecyclerView();
        return view;
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
                imgListSearch.setVisibility(View.VISIBLE);
            }
            progressBar.setVisibility(View.GONE);
        });
    }
}
