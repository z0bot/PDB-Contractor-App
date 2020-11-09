package com.palodurobuilders.contractorapp.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.palodurobuilders.contractorapp.R;

import java.util.Objects;

import static androidx.appcompat.content.res.AppCompatResources.getDrawable;

public class EditPropertyDetails extends Fragment
{
    ImageButton mStarButton;

    boolean _starToggle = false;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState)
    {
        mStarButton = getView().findViewById(R.id.button_star);

        mStarButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                toggleStar();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_edit_property_details, container, false);
    }

    private void toggleStar()
    {
        if(_starToggle)
        {
            mStarButton.setImageDrawable(getDrawable(Objects.requireNonNull(getContext()), R.drawable.ic_star_gold));
            _starToggle = false;
        }
        else
        {
            mStarButton.setImageDrawable(getDrawable(Objects.requireNonNull(getContext()), R.drawable.ic_star_gray));
            _starToggle = true;
        }
    }
}