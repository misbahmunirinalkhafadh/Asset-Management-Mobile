package com.mii.assetmanagement.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

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
    private HistoryProgressAdapter adapter;
    private HistoryViewModel historyViewModel;
    private ArrayList<HistoryResult> historyArrayList = new ArrayList<>();
    private static final String REQUEST_STATUS = "inprogress";

    static HistoryProgressFragment newInstance() {
        return new HistoryProgressFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_history_progress, container, false);
        recyclerView = view.findViewById(R.id.rv_history);
        progressBar = view.findViewById(R.id.progressbar);

        SharedPrefManager sharedPrefManager = new SharedPrefManager(Objects.requireNonNull(getActivity()));
        String nik = sharedPrefManager.getSpNik();
        historyViewModel = ViewModelProviders.of(Objects.requireNonNull(getActivity()), new ViewModelProvider.NewInstanceFactory()).get(HistoryViewModel.class);
        historyViewModel.setHistoryProgress(Integer.parseInt(nik), REQUEST_STATUS);

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
            if (histories.isError()) {
                Toast.makeText(getActivity(), "Data not found", Toast.LENGTH_SHORT).show();
            } else {
                List<HistoryResult> resultList = histories.getResult();
                historyArrayList.addAll(resultList);
                adapter.notifyDataSetChanged();
            }
            progressBar.setVisibility(View.GONE);
        });
    }
}
