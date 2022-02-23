package com.example.databases;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Entity;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.pojo.Quotation;

import java.util.ArrayList;
import java.util.List;

@Database(version = 1, entities = {Quotation.class})
public abstract class DataBase extends RoomDatabase {

    private static DataBase dataBase;

    public abstract QuotationDAO obtainInterface();

    public static synchronized DataBase getInstance(Context context) {
        if (dataBase == null) {
            dataBase = Room.databaseBuilder(context, DataBase.class, "quotation").build();
        }
        return dataBase;
    }

}
