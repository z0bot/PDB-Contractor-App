package com.palodurobuilders.contractorapp.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.palodurobuilders.contractorapp.R;
import com.palodurobuilders.contractorapp.models.Property;
import com.palodurobuilders.contractorapp.utilities.HTMLGenerator;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class CreateNewChangeOrder extends Fragment
{
    EditText mTitleEntry;
    EditText mSummaryEntry;
    EditText mOldPriceEntry;
    EditText mNewPriceEntry;
    FloatingActionButton mCreateFab;

    String _selectedPropertyID;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        _selectedPropertyID = getArguments().getString(Property.PROPERTY_ID);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_create_new_change_order, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        mTitleEntry = view.findViewById(R.id.edittext_change_order_title);
        mSummaryEntry = view.findViewById(R.id.edittext_change_order_summary);
        mOldPriceEntry = view.findViewById(R.id.edittext_change_order_previous_price);
        mNewPriceEntry = view.findViewById(R.id.edittext_change_order_new_price);
        mCreateFab = view.findViewById(R.id.fab_create_change_order);
        mCreateFab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(mTitleEntry.getText().toString().isEmpty() || mSummaryEntry.getText().toString().isEmpty() || mOldPriceEntry.getText().toString().isEmpty() || mNewPriceEntry.getText().toString().isEmpty())
                {
                    Toast.makeText(getActivity(), "Please enter all fields", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    createChangeOrder();
                }
            }
        });
    }

    private void createChangeOrder()
    {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        HashMap<String, Object> changeOrder = new HashMap<>();
        changeOrder.put("html", HTMLGenerator.generateHTMLChangeOrder(mTitleEntry.getText().toString(), mSummaryEntry.getText().toString(), mOldPriceEntry.getText().toString(), mNewPriceEntry.getText().toString()));
        changeOrder.put("signed", false);
        changeOrder.put("title", mTitleEntry.getText().toString());
        String date = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss", Locale.getDefault()).format(new Date());
        date = date.replace('_', 'T');
        changeOrder.put("date", date);
        db.collection("Projects").document(_selectedPropertyID).collection("Forms").add(changeOrder)
                .addOnCompleteListener(new OnCompleteListener<DocumentReference>()
                {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task)
                    {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(getActivity(), "Change order created successfully", Toast.LENGTH_LONG).show();
                            Bundle args = new Bundle();
                            args.putString(Property.PROPERTY_ID, _selectedPropertyID);
                            ChangeOrder changeOrderFragment = new ChangeOrder();
                            changeOrderFragment.setArguments(args);
                            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                            fragmentTransaction.replace(R.id.frame_property_utility, changeOrderFragment);
                            fragmentTransaction.commit();
                        }
                        else
                        {
                            Toast.makeText(getActivity(), "Change order creation failed", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }
}