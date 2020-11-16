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
        date = date.replace('T', ' ');
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
    public String getSenderId()
    {
        return senderID;
    }
    public void setSenderId(String value)
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
}
