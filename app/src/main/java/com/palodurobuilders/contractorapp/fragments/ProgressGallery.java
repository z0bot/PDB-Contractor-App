package com.palodurobuilders.contractorapp.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.palodurobuilders.contractorapp.R;
import com.palodurobuilders.contractorapp.adapters.GalleryRoomViewAdapter;
import com.palodurobuilders.contractorapp.models.GalleryImage;
import com.palodurobuilders.contractorapp.models.GalleryRoom;

import java.util.ArrayList;
import java.util.List;

public class ProgressGallery extends Fragment
{
    RecyclerView _recyclerView;
    GalleryRoomViewAdapter _recyclerViewAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_progress_gallery, container, false);
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        _recyclerView = getView().findViewById(R.id.recyclerview_progress_gallery_parent);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());

        _recyclerViewAdapter = new GalleryRoomViewAdapter(getActivity(), GalleryRoomList());

        _recyclerView.setAdapter(_recyclerViewAdapter);;
        _recyclerView.setLayoutManager(layoutManager);
    }
    private List<GalleryRoom> GalleryRoomList()
    {
        List<GalleryRoom> galleryRoomList = new ArrayList<>();

        GalleryRoom galleryRoom0 = new GalleryRoom("Living Room", GalleryImageList());
        galleryRoomList.add(galleryRoom0);

        GalleryRoom galleryRoom1 = new GalleryRoom("Foyer", GalleryImageList());
        galleryRoomList.add(galleryRoom1);

        GalleryRoom galleryRoom2 = new GalleryRoom("Garage", GalleryImageList());
        galleryRoomList.add(galleryRoom2);

        GalleryRoom galleryRoom3 = new GalleryRoom("Master Bedroom", GalleryImageList());
        galleryRoomList.add(galleryRoom3);

        GalleryRoom galleryRoom4 = new GalleryRoom("Kitchen", GalleryImageList());
        galleryRoomList.add(galleryRoom4);

        GalleryRoom galleryRoom5 = new GalleryRoom("Spare Bedroom", GalleryImageList());
        galleryRoomList.add(galleryRoom5);

        GalleryRoom galleryRoom6 = new GalleryRoom("Storage", GalleryImageList());
        galleryRoomList.add(galleryRoom6);

        return galleryRoomList;
    }

    private List<GalleryImage> GalleryImageList()
    {
        List<GalleryImage> galleryImageList = new ArrayList<>();

       galleryImageList.add(new GalleryImage("9/11/2020", R.drawable.house));
       galleryImageList.add(new GalleryImage("9/11/2020", R.drawable.house));
       galleryImageList.add(new GalleryImage("9/11/2020", R.drawable.house));
       galleryImageList.add(new GalleryImage("9/11/2020", R.drawable.house));
       galleryImageList.add(new GalleryImage("9/11/2020", R.drawable.house));
       galleryImageList.add(new GalleryImage("9/11/2020", R.drawable.house));
       galleryImageList.add(new GalleryImage("9/11/2020", R.drawable.house));

       return galleryImageList;
    }
}