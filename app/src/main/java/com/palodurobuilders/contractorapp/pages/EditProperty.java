package com.palodurobuilders.contractorapp.pages;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.palodurobuilders.contractorapp.R;
import com.palodurobuilders.contractorapp.databases.PropertyDatabase;
import com.palodurobuilders.contractorapp.models.Property;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

public class EditProperty extends AppCompatActivity
{
    ImageButton mStarButton;
    ImageButton mChooseImageButton;
    ImageView mThumbnail;
    FloatingActionButton mFabCreateProperty;
    EditText mPropertyNameEntry;
    EditText mOwnerEntry;
    EditText mEmailEntry;
    EditText mAddressEntry;
    Toolbar mToolbar;

    boolean _starToggle = false;
    Uri _imagePath;
    Bitmap _bitmap;
    String _firebaseImageUrl;
    Property _property;

    FirebaseStorage _storage;
    StorageReference _storageReference;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_property);

        findViews();
        setListeners();
        setStatusBarColor();
        setTitleBar();

        _storage = FirebaseStorage.getInstance();
        _storageReference = _storage.getReference();
    }

    private void setListeners()
    {
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
                beginPropertyUpload();
            }
        });
    }

    private void findViews()
    {
        mStarButton = findViewById(R.id.button_star);
        mChooseImageButton = findViewById(R.id.button_add_cover_image);
        mThumbnail = findViewById(R.id.image_property_thumbnail);
        mFabCreateProperty = findViewById(R.id.fab_create_new_property);
        mPropertyNameEntry = findViewById(R.id.edittext_property_name);
        mOwnerEntry = findViewById(R.id.edittext_owner);
        mEmailEntry = findViewById(R.id.edittext_email);
        mAddressEntry = findViewById(R.id.edittext_address);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK)
        {
            if(requestCode == 1)
            {
                _imagePath = data.getData();
                setThumbnail();
            }
        }
    }

    private void beginPropertyUpload()
    {
        if(mPropertyNameEntry.getText().toString().isEmpty() || mOwnerEntry.getText().toString().isEmpty())
        {
            Toast.makeText(this, "Please enter a property name, and owner", Toast.LENGTH_SHORT).show();
            return;
        }
        if(_imagePath == null)
        {
            _imagePath = Uri.parse("android.resource://com.palodurobuilders.contractorapp/drawable/house_placeholder");
        }

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Uploading...");
        progressDialog.show();

        final StorageReference storageReference = _storageReference.child("images/" + UUID.randomUUID().toString());

        //upload thumbnail image
        storageReference.putFile(_imagePath)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>()
                {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot)
                    {
                        storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>()
                        {
                            @Override
                            public void onSuccess(Uri uri)
                            {
                                progressDialog.dismiss();
                                _firebaseImageUrl = uri.toString();
                                uploadToFirestore();
                            }
                        });
                    }
                })
                .addOnFailureListener(new OnFailureListener()
                {
                    @Override
                    public void onFailure(@NonNull Exception e)
                    {
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(), "Error creating property", Toast.LENGTH_SHORT).show();
                    }
                });


    }

    private void uploadToFirestore()
    {
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Uploading...");
        progressDialog.show();

        //create property object
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        Map<String, Object> property = new HashMap<>();
        property.put("name", mPropertyNameEntry.getText().toString());
        property.put("imageURL", _firebaseImageUrl);
        property.put("starred", _starToggle);
        if(!mOwnerEntry.getText().toString().isEmpty())
        {
            property.put("owner", mOwnerEntry.getText().toString());
        }
        if(!mEmailEntry.getText().toString().isEmpty())
        {
            property.put("email", mEmailEntry.getText().toString());
        }
        if(!mAddressEntry.getText().toString().isEmpty())
        {
            property.put("address", mAddressEntry.getText().toString());
        }

        db.collection("Projects")
                .add(property)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>()
                {
                    @Override
                    public void onSuccess(DocumentReference documentReference)
                    {
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(), "Property created", Toast.LENGTH_SHORT).show();
                        _property = new Property(mPropertyNameEntry.getText().toString(), mOwnerEntry.getText().toString(), mAddressEntry.getText().toString(), mEmailEntry.getText().toString(), _firebaseImageUrl, _starToggle);
                        pushToPropertyUtilities();
                    }
                })
                .addOnFailureListener(new OnFailureListener()
                {
                    @Override
                    public void onFailure(@NonNull Exception e)
                    {
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(), "Error creating property", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private void pushToPropertyUtilities()
    {
        PropertyDatabase propertyDatabase = PropertyDatabase.getInstance(this);
        try
        {
            propertyDatabase.propertyDao().insertProperty(_property);
        }
        catch(Exception e)
        {
            propertyDatabase.propertyDao().updateProperty(_property);
        }
        Intent propertyUtilityIntent = new Intent(this, PropertyUtilities.class);
        propertyUtilityIntent.putExtra(Property.PROPERTY_NAME, _property.getName());
        startActivity(propertyUtilityIntent);
        finish();
    }

    private void setThumbnail()
    {
        InputStream inputStream;
        try
        {
            inputStream = getContentResolver().openInputStream(_imagePath);
            _bitmap = BitmapFactory.decodeStream(inputStream);
            mThumbnail.setImageBitmap(_bitmap);
            mThumbnail.setVisibility(ImageView.VISIBLE);
        }
        catch(FileNotFoundException e)
        {
            Toast.makeText(this, "An error occurred.", Toast.LENGTH_SHORT).show();
        }
    }

    private void toggleStar()
    {
        if(_starToggle)
        {
            mStarButton.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_star_gray));
            _starToggle = false;
        }
        else
        {
            mStarButton.setImageDrawable(ContextCompat.getDrawable(this, R.drawable.ic_star_gold));
            _starToggle = true;
        }
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
}