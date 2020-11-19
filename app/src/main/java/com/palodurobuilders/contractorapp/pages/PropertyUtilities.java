package com.palodurobuilders.contractorapp.pages;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.palodurobuilders.contractorapp.R;
import com.palodurobuilders.contractorapp.fragments.DisplayPropertyDetails;
import com.palodurobuilders.contractorapp.fragments.Messaging;
import com.palodurobuilders.contractorapp.models.Property;

import java.util.Objects;

public class PropertyUtilities extends AppCompatActivity
{
    public enum propertyUtilityFragmentType
    {
        Messaging,
        Photos,
        Files,
        PropertyDetails
    }

    Toolbar mToolbar;
    BottomNavigationView mBottomNav;
    propertyUtilityFragmentType _utilityType;

    String _selectedPropertyName;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_utilities);

        _selectedPropertyName = getIntent().getStringExtra(Property.PROPERTY_NAME);

        mBottomNav = findViewById(R.id.bottomnav_property_utility);

        _utilityType = propertyUtilityFragmentType.PropertyDetails;
        mBottomNav.setSelectedItemId(R.id.property_info);

        setStatusBarColor();
        setTitleBar();
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

    private void setTitleBar()
    {
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    private void setFragment()
    {
        if(_utilityType.equals(propertyUtilityFragmentType.Messaging))
        {
            Fragment messagingFragment = new Messaging();
            updateFragment(messagingFragment);
        }
        else if(_utilityType.equals(propertyUtilityFragmentType.Photos))
        {
            //start photos fragment
        }
        else if(_utilityType.equals(propertyUtilityFragmentType.Files))
        {
            //start files fragment
        }
        else if(_utilityType.equals(propertyUtilityFragmentType.PropertyDetails))
        {
            //start property details fragment
            Bundle args = new Bundle();
            Fragment displayDetailsFragment = new DisplayPropertyDetails();
            args.putString(Property.PROPERTY_NAME, _selectedPropertyName);
            displayDetailsFragment.setArguments(args);
            updateFragment(displayDetailsFragment);
        }
    }

    private void updateFragment(Fragment fragment)
    {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_property_utility, fragment);
        fragmentTransaction.commit();
    }
}