package com.palodurobuilders.contractorapp.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.palodurobuilders.contractorapp.R;
import com.palodurobuilders.contractorapp.databases.PropertyDatabase;
import com.palodurobuilders.contractorapp.models.Property;

import java.util.Objects;

public class DisplayPropertyDetails extends Fragment
{
    TextView mPropertyName;
    ImageView mPropertyImage;
    TextView mOwnerName;
    TextView mEmail;
    TextView mAddress;

    String _selectedPropertyName;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        _selectedPropertyName = getArguments().getString(Property.PROPERTY_NAME);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_display_property_details, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        mPropertyImage = view.findViewById(R.id.imageview_property_image);
        mPropertyName = view.findViewById(R.id.textview_property_name);
        mOwnerName = view.findViewById(R.id.textview_owner_name);
        mAddress = view.findViewById(R.id.textview_address);
        mEmail = view.findViewById(R.id.textview_email);

        PropertyDatabase propertyDatabase = PropertyDatabase.getInstance(getActivity());
        Property selectedProperty = propertyDatabase.propertyDao().findPropertyById(_selectedPropertyName).get(0);

        mPropertyName.setText(selectedProperty.getName());
        mOwnerName.setText(selectedProperty.getOwner());
        mAddress.setText(selectedProperty.getAddress());
        mEmail.setText(selectedProperty.getEmail());

        Glide.with(Objects.requireNonNull(getContext()))
                .load(selectedProperty.getImageURL())
                .centerCrop()
                .into(mPropertyImage);
    }
}