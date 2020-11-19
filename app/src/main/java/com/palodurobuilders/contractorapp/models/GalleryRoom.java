package com.palodurobuilders.contractorapp.models;

import java.util.List;

public class GalleryRoom
{
    private String _roomTitle;
    public String getRoomTitle()
    {
        return _roomTitle;
    }
    public void setRoomTitle(String value)
    {
        _roomTitle=value;
    }
    private List<GalleryImage> _galleryImageList;
    public List<GalleryImage> getGalleryImageList()
    {
        return _galleryImageList;
    }
    public void setGalleryImageList(List<GalleryImage> galleryImageList)
    {
        _galleryImageList = galleryImageList;
    }

    public GalleryRoom(String roomTitle, List<GalleryImage> galleryImageList)
    {
        _roomTitle = roomTitle;
        _galleryImageList = galleryImageList;
    }

}
