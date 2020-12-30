package com.palodurobuilders.contractorapp.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.palodurobuilders.contractorapp.R;
import com.palodurobuilders.contractorapp.models.ChangeOrderForm;

import java.util.List;

public class ChangeOrderViewAdapter extends RecyclerView.Adapter<ChangeOrderViewAdapter.ChangeOrderViewHolder>
{
    private final List<ChangeOrderForm> _data;
    private final LayoutInflater _inflater;
    private ItemClickListener _clickListener;

    public class ChangeOrderViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        public final View mView;
        public final TextView mTitleText;
        public final ImageView mChangeOrderThumbnail;

        public ChangeOrderViewHolder(View view)
        {
            super(view);
            mView = view;
            mTitleText = view.findViewById(R.id.textview_change_order_title);
            mChangeOrderThumbnail = view.findViewById(R.id.image_change_order_thumbnail);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v)
        {
            _clickListener.onItemClick(getAdapterPosition());
        }
    }

    public ChangeOrderViewAdapter(Context context, List<ChangeOrderForm> data)
    {
        _inflater = LayoutInflater.from(context);
        _data = data;
    }

    @NonNull
    @Override
    public ChangeOrderViewAdapter.ChangeOrderViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = _inflater.inflate(R.layout.item_change_order, parent, false);
        return new ChangeOrderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ChangeOrderViewHolder holder, int position)
    {
        ChangeOrderForm form = _data.get(position);
        holder.mTitleText.setText(form.getTitle());
    }

    @Override
    public int getItemCount()
    {
        return _data.size();
    }

    public ChangeOrderForm getForm(int id)
    {
        return _data.get(id);
    }

    public void setClickListener(ItemClickListener itemClickListener)
    {
        _clickListener = itemClickListener;
    }

    public interface ItemClickListener
    {
        void onItemClick(int position);
    }
}
