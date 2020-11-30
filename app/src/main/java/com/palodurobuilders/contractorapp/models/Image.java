package com.palodurobuilders.contractorapp.models;
import java.util.StringTokenizer;

public class Image
{
    private String date;
    public String getDate()
    {
        if(date!=null)
        {
            int index = date.indexOf("-");
            String year = date.substring(0,index);
            date = date.substring(index+1);
            index = date.indexOf("T");
            date = date.substring(0, index);
            date = date.replace('-','/') + '/' + year;
        }
       return date;
    }
    public void setDate(String value)
    {
        date=value;
    }

    private String imageURL;
    public String getImageURL()
    {
        return imageURL;
    }
    public void setImageURL(String value)
    {
        imageURL=value;
    }

    public Boolean is360;
    public Boolean getIs360()
    {
        return is360;
    }
    public void setIs360(Boolean value)
    {
        is360 = value;
    }
    public String roomID;
    public String getRoomID()
    {
        return roomID;
    }
    public void setRoomID(String value)
    {
        roomID = value;
    }
}
