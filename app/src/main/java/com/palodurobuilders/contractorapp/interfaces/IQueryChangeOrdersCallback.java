package com.palodurobuilders.contractorapp.interfaces;

import com.palodurobuilders.contractorapp.models.ChangeOrderForm;

import java.util.List;

public interface IQueryChangeOrdersCallback
{
    void onCallback(List<ChangeOrderForm> changeOrderList);
}
