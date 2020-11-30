package com.palodurobuilders.contractorapp.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;

import com.palodurobuilders.contractorapp.R;

public class DisplayChangeOrder extends Fragment
{
    TextView mSignatureText;
    WebView mChangeOrderWebview;
    String _html;
    String _signedName;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        _html = getArguments().getString("HTML");
        _signedName = getArguments().getString("signature");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_display_change_order, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        mChangeOrderWebview = view.findViewById(R.id.webview_change_order);
        mSignatureText = view.findViewById(R.id.textview_signature);
        mChangeOrderWebview.loadDataWithBaseURL(null, _html, "text/html", "UTF-8", null);
        if(_signedName != null)
        {
            mSignatureText.setText(_signedName);
        }
    }
}