package com.palodurobuilders.contractorapp.models;
import java.util.StringTokenizer;

public class Image
{
    private String date;
    public String getDate()
    {
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

    public String getFormattedDate()
    {
        String formattedDate = date;
        if(formattedDate!=null&&!(formattedDate.isEmpty()))
        {
            int index = formattedDate.indexOf("-");
            String year = formattedDate.substring(0,index);
            formattedDate = formattedDate.substring(index+1);
            index = formattedDate.indexOf("T");
            formattedDate = formattedDate.substring(0, index);
            formattedDate = formattedDate.replace('-','/') + '/' + year;
        }
        else
        {
            formattedDate = "";
        }
        return formattedDate;
    }
}
