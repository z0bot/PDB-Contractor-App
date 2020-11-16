package com.palodurobuilders.contractorapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.palodurobuilders.contractorapp.models.DummyHouse;
import com.palodurobuilders.contractorapp.R;

import java.util.List;

public class ProjectSelectorViewAdaptor extends RecyclerView.Adapter<ProjectSelectorViewAdaptor.MyViewHolder>
{
    private List<DummyHouse> _dataList;
    private LayoutInflater _inflator;
    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder
    {
        // each data item is just a string in this case
        public View mView;
        public TextView mHomeAddressText;
        public ImageView mHomeImage;

        public MyViewHolder(View v) {
            super(v);
            mView = v;
            mHomeAddressText = (TextView) v.findViewById(R.id.textview_house_address);
            mHomeImage = (ImageView) v.findViewById(R.id.imageview_house);
        }
    }

    public ProjectSelectorViewAdaptor(Context context, List<DummyHouse> data)
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
        DummyHouse dh = _dataList.get(position);
        holder.mHomeImage.setImageResource(dh.getHomeImage());
        holder.mHomeAddressText.setText(dh.getHomeAddress());
    }
    @Override
    public int getItemCount()
    {
        return _dataList.size();
    }
}
