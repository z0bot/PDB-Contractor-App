package com.palodurobuilders.contractorapp.pages;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import com.google.firebase.firestore.FirebaseFirestore;
import com.palodurobuilders.contractorapp.fragments.ProjectSelector;
import com.palodurobuilders.contractorapp.R;

public class PropertySelection extends AppCompatActivity
{
    ImageButton mAddProjectButton;
    ImageButton mArchiveDrawerButton;
    Button mLogoutButton;
    FrameLayout mHouseSelectionFrame;
    private static final String TAG = "Property Selection";
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_property_selection);

        setStatusBarColor();

        mAddProjectButton = findViewById(R.id.button_add_house);
        mArchiveDrawerButton = findViewById(R.id.button_archive_drawer);
        mLogoutButton = findViewById(R.id.button_logout);
        mHouseSelectionFrame = findViewById(R.id.frame_house_selection);
        //setStartingFragment();

        mLogoutButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                closeActivity();
            }
        });
        mAddProjectButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent addPropertyIntent = new Intent(getApplicationContext(), EditProperty.class);
                startActivity(addPropertyIntent);
            }
        });
    }

    private void closeActivity()
    {
        this.finish();
    }

    public void setStartingFragment()
    {
        ProjectSelector projectSelector = new ProjectSelector();
        //Allows us to switch out fragments
        FragmentManager fragManager = getSupportFragmentManager();
        //Opens up an instance of switching out a fragment
        FragmentTransaction fragTransaction = fragManager.beginTransaction();
        //switching out the frame on the page with the init fragment
        fragTransaction.replace(R.id.frame_house_selection, projectSelector);
        fragTransaction.commit();
    }

    @Override
    public void onBackPressed()
    {
    }

    @Override
    public void onResume()
    {
        setStartingFragment();
        super.onResume();
    }

    public void setStatusBarColor()
    {
        Window window = getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(Color.parseColor("#FFFFFF"));
        window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
    }
}