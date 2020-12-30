package com.palodurobuilders.contractorapp.pages;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.palodurobuilders.contractorapp.R;
import com.palodurobuilders.contractorapp.models.Property;
import com.palodurobuilders.contractorapp.utilities.HTMLGenerator;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Objects;

public class CreateChangeOrderForm extends AppCompatActivity
{
    Toolbar mToolbar;
    EditText mTitleEntry;
    EditText mSummaryEntry;
    EditText mOldPriceEntry;
    EditText mNewPriceEntry;
    FloatingActionButton mCreateFab;

    String _selectedPropertyID;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_change_order_form);

        _selectedPropertyID = getIntent().getExtras().getString(Property.PROPERTY_ID);

        mToolbar = findViewById(R.id.toolbar);
        setTitleBar();
        setStatusBarColor();
        mTitleEntry = findViewById(R.id.edittext_change_order_title);
        mSummaryEntry = findViewById(R.id.edittext_change_order_summary);
        mOldPriceEntry = findViewById(R.id.edittext_change_order_previous_price);
        mNewPriceEntry = findViewById(R.id.edittext_change_order_new_price);
        mCreateFab = findViewById(R.id.fab_create_change_order);
        mCreateFab.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if(mTitleEntry.getText().toString().isEmpty() || mSummaryEntry.getText().toString().isEmpty() || mOldPriceEntry.getText().toString().isEmpty() || mNewPriceEntry.getText().toString().isEmpty())
                {
                    Toast.makeText(CreateChangeOrderForm.this, "Please enter all fields", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    createChangeOrder();
                }
            }
        });
    }

    private void createChangeOrder()
    {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        HashMap<String, Object> changeOrder = new HashMap<>();
        changeOrder.put("html", HTMLGenerator.generateHTMLChangeOrder(mTitleEntry.getText().toString(), mSummaryEntry.getText().toString(), mOldPriceEntry.getText().toString(), mNewPriceEntry.getText().toString()));
        changeOrder.put("signed", false);
        changeOrder.put("title", mTitleEntry.getText().toString());
        String date = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss", Locale.getDefault()).format(new Date());
        date = date.replace('_', 'T');
        changeOrder.put("date", date);
        db.collection("Projects").document(_selectedPropertyID).collection("Forms").add(changeOrder)
                .addOnCompleteListener(new OnCompleteListener<DocumentReference>()
                {
                    @Override
                    public void onComplete(@NonNull Task<DocumentReference> task)
                    {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(CreateChangeOrderForm.this, "Change order created successfully", Toast.LENGTH_LONG).show();
                            finish();
                        }
                        else
                        {
                            Toast.makeText(CreateChangeOrderForm.this, "Change order creation failed", Toast.LENGTH_LONG).show();
                        }
                    }
                });
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