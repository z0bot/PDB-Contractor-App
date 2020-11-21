package com.palodurobuilders.contractorapp.interfaces;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.palodurobuilders.contractorapp.models.Property;

import java.util.List;

@Dao
public interface PropertyDao
{
    @Query("Select * from property")
    List<Property> getPropertyList();

    @Query("SELECT * FROM property WHERE propertyID LIKE :search")
    List<Property> findPropertyById(String search);

    @Insert
    void insertProperty(Property property);

    @Update
    void updateProperty(Property property);

    @Delete
    void deleteProperty(Property property);
}
