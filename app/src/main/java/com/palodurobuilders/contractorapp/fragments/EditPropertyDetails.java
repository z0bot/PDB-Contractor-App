package com.palodurobuilders.contractorapp.fragments;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.palodurobuilders.contractorapp.R;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static android.app.Activity.RESULT_OK;
import static androidx.appcompat.content.res.AppCompatResources.getDrawable;

public class EditPropertyDetails extends Fragment
{
    ImageButton mStarButton;
    ImageButton mChooseImageButton;
    ImageView mThumbnail;
    FloatingActionButton mFabCreateProperty;
    EditText mAddressEntry;
    EditText mOwnerEntry;
    EditText mEmailEntry;
    EditText mNotesEntry;

    boolean _starToggle = false;
    Uri _imagePath;
    Bitmap _bitmap;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState)
    {
        mStarButton = view.findViewById(R.id.button_star);
        mChooseImageButton = view.findViewById(R.id.button_add_cover_image);
        mThumbnail = view.findViewById(R.id.image_property_thumbnail);
        mFabCreateProperty = view.findViewById(R.id.fab_create_new_property);
        mAddressEntry = view.findViewById(R.id.edittext_property_name);
        mOwnerEntry = view.findViewById(R.id.edittext_owner);
        mEmailEntry = view.findViewById(R.id.edittext_email);
        mNotesEntry = view.findViewById(R.id.edittext_notes);

        mStarButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                toggleStar();
            }
        });
        mChooseImageButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/jpeg");
                startActivityForResult(Intent.createChooser(intent, "Select a JPEG Image"), 1);
            }
        });
        mFabCreateProperty.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Map<String, Object> property = new HashMap<>();
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_edit_property_details, container, false);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if(resultCode == RESULT_OK)
        {
            if(requestCode == 1)
            {
                _imagePath = data.getData();
                setThumbnail();
            }
        }
    }

    private void setThumbnail()
    {
        InputStream inputStream;
        try
        {
            inputStream = Objects.requireNonNull(getActivity()).getContentResolver().openInputStream(_imagePath);
            _bitmap = BitmapFactory.decodeStream(inputStream);
            mThumbnail.setImageBitmap(_bitmap);
            mThumbnail.setVisibility(ImageView.VISIBLE);
        }
        catch(FileNotFoundException e)
        {
            Toast.makeText(getActivity(), "An error occurred.", Toast.LENGTH_SHORT).show();
        }
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