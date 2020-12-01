package com.palodurobuilders.contractorapp.interfaces;

import com.palodurobuilders.contractorapp.models.Room;

import java.util.List;

public interface IQueryRoomsCallback
{
    void onCallback(List<Room> roomList);
}
