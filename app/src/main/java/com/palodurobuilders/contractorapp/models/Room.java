package com.palodurobuilders.contractorapp.models;

import java.util.ArrayList;
import java.util.List;

public class Room
{
    private String roomID;
    public String getRoomID()
    {
        return roomID;
    }
    public void setRoomID(String _roomID)
    {
        roomID = _roomID;
    }

    private String name;
    public String getName()
    {
        return name;
    }
    public void setName(String value)
    {
        name=value;
    }

    private List<Image> Images;
    public List<Image> getImages()
    {
        Image addImage = new Image();
        addImage.setRoomID(getRoomID());
        if(Images!=null)
        {
            Images.add(addImage);
        }
        else
        {
            Images = new ArrayList<>();
            Images.add(addImage);
        }
        return Images;
    }
    public void setImages(List<Image> imageList)
    {
        Images = imageList;
    }

    public void addImage(Image image)
    {
        if (Images == null)
        {
            Images = new ArrayList<>();
        }
        Images.add(image);
    }

    /*
    public Room(String roomName, List<Image> imageList)
    {
        name = roomName;
        Images = imageList;
    }
     */
    public Room()
    {

    }

}
