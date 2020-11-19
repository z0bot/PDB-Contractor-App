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
    private String builderEmail;
    public String getBuilderEmail()
    {
        return builderEmail;
    }
    public void setBuilderEmail(String value)
    {
        builderEmail = value;
    }

    //Image URL property
    private String imageUrl;
    public String getImageUrl()
    {
        return imageUrl;
    }
    public void setImageUrl(String value)
    {
        imageUrl = value;
    }
}
