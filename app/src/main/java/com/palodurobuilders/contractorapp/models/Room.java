package com.palodurobuilders.contractorapp.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Room implements Comparable<Room>
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
            if(isAddButtonPresent())
            {
                Images.add(0, addImage);
            }
        }
        else
        {
            Images = new ArrayList<>();
            if(isAddButtonPresent())
            {
                Images.add(addImage);
            }
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
        Collections.sort(Images);
        Collections.reverse(Images);
    }

    private boolean isAddButtonPresent()
    {
        for(Image image : Images)
        {
            if(image.getRoomID() != null)
            {
                return false;
            }
        }
        return true;
    }

    public Room()
    {

    }

    @Override
    public int compareTo(Room o)
    {
        if(getName() == null || o.getName() == null)
        {
            return 0;
        }
        return getName().compareTo(o.getName());
    }
}
