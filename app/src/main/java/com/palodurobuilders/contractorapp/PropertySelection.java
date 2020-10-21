package com.palodurobuilders.contractorapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.Toast;

public class PropertySelection extends AppCompatActivity
{
    ImageButton mAddProjectButton;
    ImageButton mArchiveDrawerButton;
    Button mLogoutButton;
    FrameLayout mHouseSelectionFrame;

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

        mLogoutButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                testToast();
            }
        });
        mAddProjectButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent propertyUtilitiesIntent = new Intent(getApplicationContext(), PropertyUtilities.class);
                startActivity(propertyUtilitiesIntent);
            }
        });
    }

    private void testToast()
    {
        Toast toast = Toast.makeText(this, R.string.toast_test, Toast.LENGTH_LONG);
        toast.show();
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