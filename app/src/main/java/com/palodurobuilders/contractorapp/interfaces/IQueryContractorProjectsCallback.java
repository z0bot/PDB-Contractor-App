package com.palodurobuilders.contractorapp.interfaces;

import com.palodurobuilders.contractorapp.models.Property;

import java.util.List;

public interface IQueryContractorProjectsCallback
{
    void onCallback(List<Property> projectList);
}
