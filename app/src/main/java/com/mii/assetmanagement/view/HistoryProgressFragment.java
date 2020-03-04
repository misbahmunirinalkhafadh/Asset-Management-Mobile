package com.mii.assetmanagement.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mii.assetmanagement.R;
import com.mii.assetmanagement.adapter.HistoryAdapter;
import com.mii.assetmanagement.model.History;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class HistoryProgressFragment extends Fragment {
    private RecyclerView recyclerView;
    private HistoryAdapter adapter;
    private List<History> historyArrayList = new ArrayList<>();

    static HistoryProgressFragment newInstance() {
        return new HistoryProgressFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_history_progress, container, false);
        recyclerView = view.findViewById(R.id.rv_history);
        setupRecyclerView();
        historyData();
        return view;
    }

    private void setupRecyclerView() {
        if (adapter == null) {
            adapter = new HistoryAdapter(getActivity(), historyArrayList);
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), LinearLayoutManager.VERTICAL, 16));
            recyclerView.setAdapter(adapter);
            recyclerView.setItemAnimator(new DefaultItemAnimator());
            recyclerView.setNestedScrollingEnabled(true);
        } else {
            adapter.notifyDataSetChanged();
        }
    }

    private void historyData() {
        History history = new History("434324123", "Request", "ASSUS X550D", "Laptop", "04 Mar 2020");
        historyArrayList.add(history);
        history = new History("434134233", "Maintenance", "ASSUS X550D", "Notebook", "07 Mar 2020");
        historyArrayList.add(history);
        history = new History("080343243", "Exchange", "ASSUS X550D", "Notebook", "07 Mar 2020");
        historyArrayList.add(history);

        adapter.notifyDataSetChanged();
    }
}
