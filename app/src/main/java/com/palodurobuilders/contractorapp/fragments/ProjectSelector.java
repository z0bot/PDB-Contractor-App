package com.palodurobuilders.contractorapp.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.palodurobuilders.contractorapp.adapters.ProjectSelectorViewAdaptor;
import com.palodurobuilders.contractorapp.R;
import com.palodurobuilders.contractorapp.databases.PropertyDatabase;
import com.palodurobuilders.contractorapp.interfaces.IQueryContractorProjectsCallback;
import com.palodurobuilders.contractorapp.models.ContractorProjects;
import com.palodurobuilders.contractorapp.models.Property;
import com.palodurobuilders.contractorapp.pages.PropertyUtilities;
import com.palodurobuilders.contractorapp.utilities.TestPropertyUtils;

import java.util.ArrayList;
import java.util.List;

public class ProjectSelector extends Fragment implements ProjectSelectorViewAdaptor.ItemClickListener
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

        setUpAdapterFromFirebase(new IQueryContractorProjectsCallback()
        {
            @Override
            public void onCallback(List<Property> projectList)
            {
                setPropertyRecyclerAdapter(projectList);
            }
        });
    }

    //a very confusing and delicate piece of code. don't touch this
    private void setUpAdapterFromFirebase(final IQueryContractorProjectsCallback callback)
    {
        final List<Property> properties = new ArrayList<>();
        final FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseAuth auth = FirebaseAuth.getInstance();
        db.collection("Contractors").document(auth.getCurrentUser().getUid()).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>()
                {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot)
                    {
                        List<String> propertyIDList = documentSnapshot.toObject(ContractorProjects.class).getProjects();
                        if(propertyIDList != null)
                        {
                            for(String id : propertyIDList)
                            {
                                db.collection("Projects").document(id).get()
                                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>()
                                        {
                                            @Override
                                            public void onComplete(@NonNull Task<DocumentSnapshot> task)
                                            {
                                                if(task.isSuccessful())
                                                {
                                                    Property property = task.getResult().toObject(Property.class);
                                                    if(property != null)
                                                    {
                                                        properties.add(property);
                                                        callback.onCallback(properties);
                                                    }
                                                }
                                            }
                                        });
                            }
                        }
                    }
                });
    }

    private void setPropertyRecyclerAdapter(List<Property> properties)
    {
        _recyclerViewAdapter = new ProjectSelectorViewAdaptor(getActivity(), properties);
        _recyclerViewAdapter.setClickListener(this);
        _recyclerView.setAdapter(_recyclerViewAdapter);
    }

    @Override
    public void onItemClick(View view, int position)
    {
        Property selectedProperty = _recyclerViewAdapter.getProperty(position);
        PropertyDatabase propertyDatabase = PropertyDatabase.getInstance(getActivity());
        try
        {
            propertyDatabase.propertyDao().insertProperty(selectedProperty);
        }
        catch(Exception e)
        {
            propertyDatabase.propertyDao().updateProperty(selectedProperty);
        }
        Intent propertyUtilityIntent = new Intent(getActivity(), PropertyUtilities.class);
        propertyUtilityIntent.putExtra(Property.PROPERTY_ID, selectedProperty.getPropertyID());
        startActivity(propertyUtilityIntent);
    }
}