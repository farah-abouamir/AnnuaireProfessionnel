package com.example.annuaire;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Personne.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase baseDonnee;

    private static String DATABASE_NAME="baseDonnee";

    public synchronized static AppDatabase getInstance(Context context)
    {

        if(baseDonnee==null)
        {

            baseDonnee= Room.databaseBuilder(context.getApplicationContext(),AppDatabase.class,DATABASE_NAME)
                    .allowMainThreadQueries().fallbackToDestructiveMigration().build();
        }

        return baseDonnee;
    }

    public abstract PersonneDao personneDao();
}
