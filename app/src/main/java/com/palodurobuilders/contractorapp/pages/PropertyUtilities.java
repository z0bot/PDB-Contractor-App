package com.palodurobuilders.contractorapp.pages;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.palodurobuilders.contractorapp.R;
import com.palodurobuilders.contractorapp.fragments.ChangeOrder;
import com.palodurobuilders.contractorapp.fragments.DisplayPropertyDetails;
import com.palodurobuilders.contractorapp.fragments.Messaging;
import com.palodurobuilders.contractorapp.fragments.ToolbarEditButton;
import com.palodurobuilders.contractorapp.interfaces.IToolbarEditButton;
import com.palodurobuilders.contractorapp.models.Property;
import com.palodurobuilders.contractorapp.fragments.ProgressGallery;

public class PropertyUtilities extends AppCompatActivity implements IToolbarEditButton
{
    @Override
    public void editButtonClicked()
    {
        Intent addPropertyIntent = new Intent(getApplicationContext(), EditProperty.class);
        addPropertyIntent.putExtra(EditProperty.ACTIVITY_SOURCE, PropertyUtilities.class.getSimpleName());
        addPropertyIntent.putExtra(Property.PROPERTY_ID, _selectedPropertyID);
        startActivity(addPropertyIntent);
    }

    public enum propertyUtilityFragmentType
    {
        Messaging,
        Photos,
        Files,
        PropertyDetails
    }

    BottomNavigationView mBottomNav;

    propertyUtilityFragmentType _utilityType;

    String _selectedPropertyID;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_utilities);

        _selectedPropertyID = getIntent().getStringExtra(Property.PROPERTY_ID);

        mBottomNav = findViewById(R.id.bottomnav_property_utility);

        _utilityType = propertyUtilityFragmentType.PropertyDetails;
        mBottomNav.setSelectedItemId(R.id.property_info);

        setStatusBarColor();
        setEditButtonToolbar(true);
        setFragment();

        mBottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener()
        {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item)
            {
                switch(item.getItemId())
                {
                    case R.id.messaging:
                        _utilityType = propertyUtilityFragmentType.Messaging;
                        setFragment();
                        return true;
                    case R.id.gallery:
                        _utilityType = propertyUtilityFragmentType.Photos;
                        setFragment();
                        return true;
                    case R.id.documents:
                        _utilityType = propertyUtilityFragmentType.Files;
                        setFragment();
                        return true;
                    case R.id.property_info:
                        _utilityType = propertyUtilityFragmentType.PropertyDetails;
                        setFragment();
                        return true;
                }
                return false;
            }
        });
    }

    public void setStatusBarColor()
    {
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.parseColor("#FFFFFF"));
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
    }

    @Override
    public boolean onSupportNavigateUp()
    {
        onBackPressed();
        return true;
    }

    private void setFragment()
    {
        if(_utilityType.equals(propertyUtilityFragmentType.Messaging))
        {
            Bundle args = new Bundle();
            args.putString(Property.PROPERTY_ID, _selectedPropertyID);
            Fragment messagingFragment = new Messaging();
            messagingFragment.setArguments(args);
            updateFragment(messagingFragment);
            setEditButtonToolbar(false);
        }
        else if(_utilityType.equals(propertyUtilityFragmentType.Photos))
        {
            //start photos fragment
            Fragment progressGalleryFrag = new ProgressGallery();
            updateFragment(progressGalleryFrag);
            setEditButtonToolbar(false);
        }
        else if(_utilityType.equals(propertyUtilityFragmentType.Files))
        {
            //start files fragment
            Bundle args = new Bundle();
            args.putString(Property.PROPERTY_ID, _selectedPropertyID);
            Fragment changeOrderFragment = new ChangeOrder();
            changeOrderFragment.setArguments(args);
            updateFragment(changeOrderFragment);
            setEditButtonToolbar(false);
        }
        else if(_utilityType.equals(propertyUtilityFragmentType.PropertyDetails))
        {
            //start property details fragment
            Bundle args = new Bundle();
            args.putString(Property.PROPERTY_ID, _selectedPropertyID);
            Fragment displayDetailsFragment = new DisplayPropertyDetails();
            displayDetailsFragment.setArguments(args);
            updateFragment(displayDetailsFragment);
            setEditButtonToolbar(true);
        }
    }

    private void setEditButtonToolbar(boolean hasEditButton)
    {
        ToolbarEditButton editToolbar = new ToolbarEditButton(hasEditButton);
        editToolbar.setInterface(this);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_toolbar, editToolbar);
        fragmentTransaction.commit();
    }

    private void updateFragment(Fragment fragment)
    {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_property_utility, fragment);
        fragmentTransaction.commit();
    }
}