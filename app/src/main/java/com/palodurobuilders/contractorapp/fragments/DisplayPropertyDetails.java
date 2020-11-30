package com.palodurobuilders.contractorapp.fragments;

import android.os.Bundle;

import androidx.core.content.ContextCompat;
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
    TextView mPropertyID;
    ImageView mStarred;

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
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_display_property_details, container, false);
    }

    @Override
    public void onResume()
    {
        super.onResume();
        mPropertyImage = getView().findViewById(R.id.imageview_property_image);
        mPropertyName = getView().findViewById(R.id.textview_property_name);
        mOwnerName = getView().findViewById(R.id.textview_owner_name);
        mAddress = getView().findViewById(R.id.textview_address);
        mEmail = getView().findViewById(R.id.textview_email);
        mPropertyID = getView().findViewById(R.id.textview_property_id);
        mStarred = getView().findViewById(R.id.image_star);

        PropertyDatabase propertyDatabase = PropertyDatabase.getInstance(getActivity());
        Property selectedProperty = propertyDatabase.propertyDao().findPropertyById(_selectedPropertyID).get(0);

        mPropertyName.setText(selectedProperty.getName());
        mOwnerName.setText(selectedProperty.getOwner());
        mAddress.setText(selectedProperty.getAddress());
        mEmail.setText(selectedProperty.getEmail());
        mPropertyID.setText(selectedProperty.getDisplayablePropertyID());
        if(selectedProperty.getStarred())
        {
            mStarred.setImageDrawable(ContextCompat.getDrawable(Objects.requireNonNull(getActivity()), R.drawable.ic_star_gold));
        }
        else
        {
            mStarred.setImageDrawable(ContextCompat.getDrawable(Objects.requireNonNull(getActivity()), R.drawable.ic_star_gray));
        }

        Glide.with(Objects.requireNonNull(getContext()))
                .load(selectedProperty.getImageURL())
                .centerCrop()
                .into(mPropertyImage);
    }
}