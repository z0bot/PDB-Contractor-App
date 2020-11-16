package com.palodurobuilders.contractorapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.palodurobuilders.contractorapp.R;
import com.palodurobuilders.contractorapp.models.GalleryImage;

import java.util.List;

public class GalleryImageViewAdaptor extends RecyclerView.Adapter<GalleryImageViewAdaptor.GalleryImageViewHolder>
{
    private List<GalleryImage> _GalleryImageList;

    //Constructor
    GalleryImageViewAdaptor(List<GalleryImage> galleryImageList)
    {
        this._GalleryImageList = galleryImageList;
    }

    //onCreateViewHolder thing that inflates the actual .xml stuff
    @Override
    public GalleryImageViewHolder onCreateViewHolder(ViewGroup viewGroup, int i)
    {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_gallery_image, viewGroup, false);
        return new GalleryImageViewHolder(view);
    }
    @Override
    public void onBindViewHolder(GalleryImageViewHolder viewHolder, int position)
    {
        GalleryImage galleryImage = _GalleryImageList.get(position);
        viewHolder.GalleryImage_ImageDate.setText(galleryImage.getImageDate());


    }
    @Override
    public int getItemCount()
    {
        return _GalleryImageList.size();
    }

    public static class GalleryImageViewHolder extends RecyclerView.ViewHolder
    {
        TextView GalleryImage_ImageDate;
        ImageView GalleryImage_RoomImage;

        GalleryImageViewHolder(View view)
        {
           super(view);
           GalleryImage_ImageDate = view.findViewById(R.id.textView_date);
           GalleryImage_RoomImage = view.findViewById(R.id.imageview_gallery_photo);
        }
    }
}
