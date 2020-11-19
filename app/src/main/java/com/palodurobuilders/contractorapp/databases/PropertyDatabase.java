package com.palodurobuilders.contractorapp.databases;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.palodurobuilders.contractorapp.interfaces.PropertyDao;
import com.palodurobuilders.contractorapp.models.Property;

@Database(entities = Property.class, exportSchema = false, version = 1)
public abstract class PropertyDatabase extends RoomDatabase
{
    private static final String DB_NAME = "property_db";
    private static PropertyDatabase instance;

    public static synchronized PropertyDatabase getInstance(Context context)
    {
        if(instance == null)
        {
            instance = Room.databaseBuilder(context.getApplicationContext(), PropertyDatabase.class, DB_NAME)
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }

    public abstract PropertyDao propertyDao();
}
