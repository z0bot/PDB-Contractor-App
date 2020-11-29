package com.palodurobuilders.contractorapp.models;

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
    public String getRoomTitle()
    {
        return name;
    }
    public void setRoomTitle(String value)
    {
        name=value;
    }

    private List<Image> Images;
    public List<Image> getImageList()
    {
        return Images;
    }
    public void setImageList(List<Image> imageList)
    {
        Images = imageList;
    }

    public Room(String roomName, List<Image> imageList)
    {
        name = roomName;
        Images = imageList;
    }

}
