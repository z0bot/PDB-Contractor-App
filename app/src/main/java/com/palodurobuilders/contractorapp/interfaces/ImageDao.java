package com.palodurobuilders.contractorapp.interfaces;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.palodurobuilders.contractorapp.models.Image;

import java.util.List;

@Dao
public interface ImageDao
{
    @Query("SELECT * FROM image WHERE imageURL LIKE :search")
    List<Image> findImageById(String search);

    @Insert
    void insertImage(Image image);

    @Update
    void updateImage(Image image);

}
