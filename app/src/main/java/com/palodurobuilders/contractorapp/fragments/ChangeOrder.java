package com.palodurobuilders.contractorapp.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;
import com.palodurobuilders.contractorapp.R;
import com.palodurobuilders.contractorapp.adapters.ChangeOrderViewAdapter;
import com.palodurobuilders.contractorapp.interfaces.IQueryChangeOrdersCallback;
import com.palodurobuilders.contractorapp.models.ChangeOrderForm;
import com.palodurobuilders.contractorapp.models.Property;
import com.palodurobuilders.contractorapp.pages.CreateChangeOrderForm;
import com.palodurobuilders.contractorapp.pages.DisplayChangeOrderForm;

import java.util.ArrayList;
import java.util.List;

public class ChangeOrder extends Fragment implements ChangeOrderViewAdapter.ItemClickListener
{
    RecyclerView _recyclerView;
    ChangeOrderViewAdapter _recyclerViewAdapter;
    String _selectedPropertyID;
    FloatingActionButton mAddChangeOrderFab;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        _selectedPropertyID = getArguments().getString(Property.PROPERTY_ID);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_change_order, container, false);
    }

    @Override
    public void onResume()
    {
        super.onResume();
        mAddChangeOrderFab = getView().findViewById(R.id.fab_add_change_order);
        mAddChangeOrderFab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                Intent createForm = new Intent(getActivity(), CreateChangeOrderForm.class);
                createForm.putExtra(Property.PROPERTY_ID, _selectedPropertyID);
                startActivity(createForm);
            }
        });

        _recyclerView = getView().findViewById(R.id.recycler_change_order);
        _recyclerView.setHasFixedSize(true);

        int numberOfColumns = 2;

        _recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), numberOfColumns));

        pullFormsFromFirebase(new IQueryChangeOrdersCallback()
        {
            @Override
            public void onCallback(List<ChangeOrderForm> changeOrderList)
            {
                setRecyclerAdapter(changeOrderList);
            }
        });
    }

    private void pullFormsFromFirebase(final IQueryChangeOrdersCallback callback)
    {
        final List<ChangeOrderForm> forms = new ArrayList<>();
        final FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("Projects").document(_selectedPropertyID).collection("Forms").get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>()
                {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task)
                    {
                        if(task.isSuccessful())
                        {
                            for(QueryDocumentSnapshot document : task.getResult())
                            {
                                forms.add(document.toObject(ChangeOrderForm.class));
                            }
                            callback.onCallback(forms);
                        }
                    }
                });
    }

    private void setRecyclerAdapter(List<ChangeOrderForm> forms)
    {
        _recyclerViewAdapter = new ChangeOrderViewAdapter(getActivity(), forms);
        _recyclerViewAdapter.setClickListener(this);
        _recyclerView.setAdapter(_recyclerViewAdapter);
    }

    @Override
    public void onItemClick(View view, int position)
    {
        ChangeOrderForm form = _recyclerViewAdapter.getForm(position);
        Intent displayForm = new Intent(getActivity(), DisplayChangeOrderForm.class);
        displayForm.putExtra("HTML", form.getHtml());
        if(form.getSignedname() != null)
        {
            displayForm.putExtra("signature", form.getSignedname());
        }
        startActivity(displayForm);
    }
}