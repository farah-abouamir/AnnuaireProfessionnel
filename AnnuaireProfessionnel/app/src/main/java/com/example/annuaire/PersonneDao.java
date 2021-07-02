package com.example.annuaire;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface PersonneDao {
    @Query("SELECT * FROM Personne")
    List<Personne> getAll();

    @Insert
    void insert(Personne contact);

    @Query("SELECT * FROM Personne WHERE name LIKE :name")
    List<Personne> findByName(String name);

    @Query("SELECT * FROM Personne WHERE ID=:ID")
    Personne findByID(int ID);

    @Delete
    void delete(Personne contact);

    @Update
    void update(Personne contact);
}
