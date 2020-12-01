package com.palodurobuilders.contractorapp.models;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

public class Message
{
    public Message()
    {

    }

    public Message(String messageText, String messageSender, String messageSenderID)
    {
        date = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss", Locale.getDefault()).format(new Date());
        date = date.replace('_', 'T');
        text = messageText;
        sender = messageSender;
        senderID = messageSenderID;
    }

    public Message(String messageText, String messageSender, String messageSenderID, String messageMediaUrl)
    {
        date = new SimpleDateFormat("yyyy-MM-dd_HH:mm:ss", Locale.getDefault()).format(new Date());
        date = date.replace('_', 'T');
        text = messageText;
        sender = messageSender;
        senderID = messageSenderID;
        mediaURL = messageMediaUrl;
    }

    //ID Property
    private String id;
    public String getId()
    {
        return id;
    }
    public void setId(String value)
    {
        id = value;
    }

    //Date Property
    private String date;
    public String getDate()
    {
        return date;
    }
    public void setDate(String value)
    {
        date = value;
    }

    //Sender Property
    private String sender;
    public String getSender()
    {
        if(sender == null)
        {
            return "Unknown";
        }
        return sender;
    }
    public void setSender(String value)
    {
        sender = value;
    }

    //SenderID Property
    private String senderID;
    public String getSenderID()
    {
        return senderID;
    }
    public void setSenderID(String value)
    {
        senderID = value;
    }

    //Text Property
    private String text;
    public String getText()
    {
        return text;
    }
    public void setText(String value)
    {
        text = value;
    }

    //Media url property
    private String mediaURL;
    public String getMediaURL()
    {
        return mediaURL;
    }
    public void setMediaURL(String value)
    {
        mediaURL = value;
    }
}
