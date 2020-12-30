package com.palodurobuilders.contractorapp.models;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "property")
public class Property implements Comparable<Property>
{
    public static final String PROPERTY_ID = "property_id";

    public Property()
    {

    }

    public Property(@NonNull String generatedPropertyID, @NonNull String propertyName, @NonNull String propertyOwner, String propertyAddress, String ownerEmail, String propertyImageURL, boolean propertyStarred)
    {
        propertyID = generatedPropertyID;
        name = propertyName;
        owner = propertyOwner;
        address = propertyAddress;
        imageURL = propertyImageURL;
        email = ownerEmail;
        starred = propertyStarred;
    }

    //PropertyID property
    @PrimaryKey
    @NonNull
    private String propertyID;
    public String getPropertyID()
    {
        return propertyID;
    }
    public void setPropertyID(String value)
    {
        propertyID = value;
    }

    //Address property
    @ColumnInfo(name = "address")
    private String address;
    public String getAddress()
    {
        return address;
    }
    public void setAddress(String value)
    {
        address = value;
    }

    //Builder email property
    @ColumnInfo(name = "email")
    private String email;
    public String getEmail()
    {
        return email;
    }
    public void setEmail(String value)
    {
        email = value;
    }

    //Image URL property
    @ColumnInfo(name = "imageURL")
    private String imageURL;
    public String getImageURL()
    {
        return imageURL;
    }
    public void setImageURL(String value)
    {
        imageURL = value;
    }

    //Name property
    @ColumnInfo(name = "name")
    private String name;
    public String getName()
    {
        return name;
    }
    public void setName(String value)
    {
        name = value;
    }

    //starred property
    @ColumnInfo(name = "starred")
    private boolean starred;
    public boolean getStarred()
    {
        return starred;
    }
    public void setStarred(boolean value)
    {
        starred = value;
    }

    //Owner property
    @ColumnInfo(name = "owner")
    private String owner;
    public String getOwner()
    {
        return owner;
    }
    public void setOwner(String value)
    {
        owner = value;
    }

    public String getDisplayablePropertyID()
    {
        return "Property ID: " + propertyID;
    }

    @Override
    public int compareTo(Property o)
    {
        if(getName() == null || o.getName() == null)
        {
            return 0;
        }
        return getName().compareTo(o.getName());
    }
}
