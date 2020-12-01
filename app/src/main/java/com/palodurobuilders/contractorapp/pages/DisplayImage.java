package com.palodurobuilders.contractorapp.pages;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.github.chrisbanes.photoview.PhotoView;
import com.google.vr.sdk.widgets.pano.VrPanoramaView;
import com.palodurobuilders.contractorapp.R;
import com.palodurobuilders.contractorapp.databases.ImageDatabase;
import com.palodurobuilders.contractorapp.models.Image;

public class DisplayImage extends AppCompatActivity
{
    Image _image;
    //ImageView mNormalImage;
    PhotoView mNormalImage;
    VrPanoramaView mPanoImage;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_image);

        mNormalImage = (PhotoView) findViewById(R.id.photo_view);
        mPanoImage = (VrPanoramaView) findViewById(R.id.vrPanoramaView);

        setImage();
    }

    private void setImage()
    {
        //for pano image
        String imageURL = getIntent().getStringExtra("imageURL");
        ImageDatabase imageDatabase = ImageDatabase.getInstance(this);
        _image = imageDatabase.imageDao().findImageById(imageURL).get(0);

        if(!_image.is360)
        {
            Glide.with(this)
                    .load(_image.getImageURL())
                    .centerCrop()
                    .into(mNormalImage);
        }
        else
        {
            final VrPanoramaView.Options options = new VrPanoramaView.Options();
            options.inputType = VrPanoramaView.Options.TYPE_MONO;
            mPanoImage.setInfoButtonEnabled(false);

            Glide.with(this)
                    .asBitmap()
                    .load(_image.getImageURL())
                    .into(new CustomTarget<Bitmap>()
                    {
                        @Override
                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition)
                        {
                            mPanoImage.loadImageFromBitmap(resource, options);
                        }

                        @Override
                        public void onLoadCleared(@Nullable Drawable placeholder)
                        {
                        }
                    });
            mPanoImage.setVisibility(View.VISIBLE);
        }
    }
}