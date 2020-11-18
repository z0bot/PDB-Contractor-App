package com.palodurobuilders.contractorapp.models;

public class Property
{
    //Address property
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
    private String owner;
    public String getOwner()
    {
        return owner;
    }
    public void setOwner(String value)
    {
        owner = value;
    }
}
