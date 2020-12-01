package com.palodurobuilders.contractorapp.fragments;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.palodurobuilders.contractorapp.R;
import com.palodurobuilders.contractorapp.interfaces.IToolbarEditButton;

import java.util.Objects;

public class ToolbarEditButton extends Fragment
{
    Toolbar mToolbar;
    ImageButton mEditButton;
    IToolbarEditButton buttonInterface;
    boolean _hasEditButton;
    int _buttonType;

    public ToolbarEditButton()
    {

    }
    public ToolbarEditButton(int resourceID)
    {
        _buttonType = resourceID;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_toolbar_edit_button, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        setTitleBar();
        setUpToolbarButton(_buttonType);
    }

    private void setTitleBar()
    {
        mToolbar = Objects.requireNonNull(getView()).findViewById(R.id.toolbar);
        mEditButton = Objects.requireNonNull(getView()).findViewById(R.id.toolbar_edit_button);
        if(_buttonType!=0)
        {
            mEditButton.setImageDrawable(ContextCompat.getDrawable(getActivity(), _buttonType));
        }
        ((AppCompatActivity) Objects.requireNonNull(getActivity())).setSupportActionBar(mToolbar);
        Objects.requireNonNull(((AppCompatActivity) getActivity()).getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        Objects.requireNonNull(((AppCompatActivity) getActivity()).getSupportActionBar()).setDisplayShowHomeEnabled(true);
    }

    public void setInterface(IToolbarEditButton toolbarInterface)
    {
        this.buttonInterface = toolbarInterface;
    }

    private void setUpToolbarButton(int _buttonType)
    {
        if(_buttonType!=0)
        {
            mEditButton.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    buttonInterface.editButtonClicked();
                }
            });
        }
        else
        {
            mEditButton.setVisibility(ImageButton.GONE);
        }
    }
}