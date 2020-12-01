package com.palodurobuilders.contractorapp.models;
import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.StringTokenizer;

@Entity(tableName = "image")
public class Image implements Comparable<Image>
{
    @ColumnInfo(name = "date")
    private String date;
    public String getDate()
    {
       return date;
    }
    public void setDate(String value)
    {
        date=value;
    }

    @PrimaryKey
    @NonNull
    private String imageURL;
    public String getImageURL()
    {
        return imageURL;
    }
    public void setImageURL(String value)
    {
        imageURL=value;
    }

    @ColumnInfo(name = "is360")
    public Boolean is360;
    public Boolean getIs360()
    {
        return is360;
    }
    public void setIs360(Boolean value)
    {
        is360 = value;
    }

    @ColumnInfo(name = "roomID")
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

    @Override
    public int compareTo(Image o)
    {
        if(getFormattedDate() == null || o.getFormattedDate() == null)
        {
            return 0;
        }
        return getFormattedDate().compareTo(o.getFormattedDate());
    }
}
