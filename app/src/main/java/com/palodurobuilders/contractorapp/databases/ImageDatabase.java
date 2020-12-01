package com.palodurobuilders.contractorapp.databases;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.palodurobuilders.contractorapp.interfaces.ImageDao;
import com.palodurobuilders.contractorapp.models.Image;

@Database(entities = Image.class, exportSchema = false, version = 1)
public abstract class ImageDatabase extends RoomDatabase
{
    private static final String DB_NAME = "image_db";
    private static com.palodurobuilders.contractorapp.databases.ImageDatabase instance;

    public static synchronized com.palodurobuilders.contractorapp.databases.ImageDatabase getInstance(Context context)
    {
        if(instance == null)
        {
            instance = Room.databaseBuilder(context.getApplicationContext(), com.palodurobuilders.contractorapp.databases.ImageDatabase.class, DB_NAME)
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }

    public abstract ImageDao imageDao();

}
