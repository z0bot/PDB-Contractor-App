package com.palodurobuilders.contractorapp.adapters;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.palodurobuilders.contractorapp.models.DummyHouse;
import com.palodurobuilders.contractorapp.R;
import com.palodurobuilders.contractorapp.models.Property;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

public class ProjectSelectorViewAdaptor extends RecyclerView.Adapter<ProjectSelectorViewAdaptor.MyViewHolder>
{
    private final List<Property> _dataList;
    private final LayoutInflater _inflator;
    private ItemClickListener _clickListener;
    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        // each data item is just a string in this case
        public View mView;
        public TextView mHomeName;
        public ImageView mHomeImage;

        public MyViewHolder(View v)
        {
            super(v);
            mView = v;
            mHomeName = v.findViewById(R.id.textview_house_address);
            mHomeImage = v.findViewById(R.id.imageview_house);
            v.setOnClickListener(this);
        }

        @Override
        public void onClick(View view)
        {
            _clickListener.onItemClick(view, getAdapterPosition());
        }
    }

    public ProjectSelectorViewAdaptor(Context context, List<Property> data)
    {
        _inflator = LayoutInflater.from(context);
        _dataList = data;
    }
    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public ProjectSelectorViewAdaptor.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        // create a new view
        View view = _inflator.inflate(R.layout.project_selector_item, parent, false);
        // set the view's size, margins, paddings and layout parameters
        return new MyViewHolder(view);
    }
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        Property property = _dataList.get(position);

        if(property.getImageURL() != null && !property.getImageURL().isEmpty())
        {
            Glide.with(holder.mHomeImage.getContext())
                    .load(property.getImageURL())
                    .centerCrop()
                    .into(holder.mHomeImage);
        }
        else
        {
            holder.mHomeImage.setImageResource(R.drawable.house_placeholder);
        }
        holder.mHomeName.setText(property.getName());
    }

    @Override
    public int getItemCount()
    {
        return _dataList.size();
    }

    public Property getProperty(int id)
    {
        return _dataList.get(id);
    }

    public void setClickListener(ItemClickListener itemClickListener)
    {
        _clickListener = itemClickListener;
    }

    public interface ItemClickListener
    {
        void onItemClick(View view, int position);
    }
}
