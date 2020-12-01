package com.palodurobuilders.contractorapp.pages;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebView;
import android.widget.TextView;

import com.palodurobuilders.contractorapp.R;

import java.util.Objects;

public class DisplayChangeOrderForm extends AppCompatActivity
{
    Toolbar mToolbar;

    TextView mSignatureText;
    WebView mChangeOrderWebview;
    String _html;
    String _signedName;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_change_order_form);

        _html = getIntent().getExtras().getString("HTML");
        _signedName = getIntent().getExtras().getString("signature");

        mChangeOrderWebview = findViewById(R.id.webview_change_order);
        mSignatureText = findViewById(R.id.textview_signature);
        mToolbar = findViewById(R.id.toolbar);
        setTitleBar();
        setStatusBarColor();

        mChangeOrderWebview.loadDataWithBaseURL(null, _html, "text/html", "UTF-8", null);
        if(_signedName != null)
        {
            mSignatureText.setText(_signedName);
        }
    }

    private void setTitleBar()
    {
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
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
}