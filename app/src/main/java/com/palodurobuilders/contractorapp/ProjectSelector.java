package com.palodurobuilders.contractorapp;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ProjectSelector extends Fragment
{
    RecyclerView _recyclerView;
    ProjectSelectorViewAdaptor _recyclerViewAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_project_selector, container, false);
    }
    //use this as your onCreate method
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        _recyclerView = getView().findViewById(R.id.recyclerview_project_selector);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        _recyclerView.setHasFixedSize(true);

        // use a linear layout manager
        int numberOfColumns = 2;
        //_recyclerView.setLayoutManager(new GridLayoutManager(getActivity()));
        _recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), numberOfColumns));

        // specify an adapter (see also next example)
        _recyclerViewAdapter = new ProjectSelectorViewAdaptor(getActivity(), TestPropertyUtils.CreateImageList());
        _recyclerView.setAdapter(_recyclerViewAdapter);

    }
}