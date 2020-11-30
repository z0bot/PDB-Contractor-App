package com.palodurobuilders.contractorapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.palodurobuilders.contractorapp.R;
import com.palodurobuilders.contractorapp.interfaces.IHandleChildRecyclerClick;
import com.palodurobuilders.contractorapp.models.Image;

import java.util.List;

public class GalleryImageViewAdaptor extends RecyclerView.Adapter<GalleryImageViewAdaptor.GalleryImageViewHolder>
{
    private List<Image> _ImageList;
    private IHandleChildRecyclerClick _childclickListener;

    //Constructor
    GalleryImageViewAdaptor(List<Image> imageList)
    {
        this._ImageList = imageList;
    }

    //onCreateViewHolder thing that inflates the actual .xml stuff
    @NonNull
    @Override
    public GalleryImageViewHolder onCreateViewHolder(ViewGroup viewGroup, int i)
    {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_gallery_image, viewGroup, false);
        return new GalleryImageViewHolder(view);
    }
    @Override
    public void onBindViewHolder(GalleryImageViewHolder viewHolder, int position)
    {
        Image image = _ImageList.get(position);
        viewHolder.mImageDate.setText(image.getDate());
        //viewHolder.GalleryImage_RoomImage.setImageDrawable(ContextCompat.getDrawable(viewHolder.GalleryImage_RoomImage.getContext(), image.getRoomImage()));
        if(image.getImageURL() != null && !image.getImageURL().isEmpty())
        {
            Glide.with(viewHolder.mImage.getContext())
                    .load(image.getImageURL())
                    .centerCrop()
                    .into(viewHolder.mImage);
        }
        /*
        else
        {
            holder.mHomeImage.setImageResource(R.drawable.house_placeholder);
        }
        */
    }
    public void setClickListener(IHandleChildRecyclerClick itemClickListener)
    {
        _childclickListener = itemClickListener;
    }
    @Override
    public int getItemCount()
    {
        return _ImageList.size();
    }

    public Image getGalleryImage(int id)
    {
        return _ImageList.get(id);
    }

    public class GalleryImageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        TextView mImageDate;
        ImageView mImage;

        GalleryImageViewHolder(View view)
        {
           super(view);
           mImageDate = view.findViewById(R.id.textView_date);
           mImage = view.findViewById(R.id.imageview_gallery_photo);
           view.setOnClickListener(this);
        }
        @Override
        public void onClick(View view)
        {
            _childclickListener.onClick(view, _ImageList.get(getAdapterPosition()));
        }
    }
}
