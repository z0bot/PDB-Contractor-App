package com.palodurobuilders.contractorapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;

import java.util.Objects;

public class PropertyUtilities extends AppCompatActivity
{
    public enum propertyUtilityFragmentType
    {
        Messaging,
        Photos,
        Files,
        PropertyDetails,
        EditPropertyDetails
    }

    Toolbar mToolbar;
    ImageButton mMessagingButton;
    ImageButton mPhotosButton;
    ImageButton mFilesButton;
    ImageButton mPropertyDetailsButton;

    propertyUtilityFragmentType _utilityType;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_utilities);

        mMessagingButton = findViewById(R.id.button_messaging);
        mPhotosButton = findViewById(R.id.button_gallery);
        mFilesButton = findViewById(R.id.button_files);
        mPropertyDetailsButton = findViewById(R.id.button_home_details);

        _utilityType = getPropertyUtilityFragmentType();

        setStatusBarColor();
        setTitleBar();
        setFragment();
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
            //start messaging fragment
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
        }
        else if(_utilityType.equals(propertyUtilityFragmentType.EditPropertyDetails))
        {
            Fragment editDetailsFragment = new EditPropertyDetails();
            updateFragment(editDetailsFragment);
        }
    }

    private void updateFragment(Fragment fragment)
    {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_property_utility, fragment);
        fragmentTransaction.commit();
    }

    //fix this later
    private propertyUtilityFragmentType getPropertyUtilityFragmentType()
    {
        propertyUtilityFragmentType utilityType;
        try
        {
            utilityType = (propertyUtilityFragmentType)getIntent().getExtras().get("PropertyUtilityFragmentType");
        }
        catch(Exception e)
        {
            return propertyUtilityFragmentType.EditPropertyDetails;
        }
        return utilityType;
    }
}