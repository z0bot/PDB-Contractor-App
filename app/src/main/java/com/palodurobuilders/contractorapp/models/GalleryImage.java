package com.palodurobuilders.contractorapp.models;

public class GalleryImage
{
    private String imageDate;
    public String getImageDate()
    {
        return imageDate;
    }
    public void setImageDate(String value)
    {
        imageDate=value;
    }

    private int roomImage;
    public int getHomeImage()
    {
        return roomImage;
    }
    public void setHomeImage(int value)
    {
        roomImage=value;
    }

    public GalleryImage(String imageDate, int roomImage)
    {
        this.imageDate = imageDate;
        this.roomImage = roomImage;
    }
}
