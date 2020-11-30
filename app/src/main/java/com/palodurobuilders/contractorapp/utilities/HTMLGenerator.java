package com.palodurobuilders.contractorapp.utilities;

public class HTMLGenerator
{
    public static String generateHTMLChangeOrder(String summary, String change, String previousCost, String newCost)
    {
        return "<html><body><div><p><strong>Summary: </strong>" + summary + "</p><p><strong>Change: </strong>" + change + "</p><p><strong>Old Cost: $</strong>" + previousCost + "</p><p><strong>New Cost: $</strong>" + newCost + "</p></div></body></html>";
    }
}
