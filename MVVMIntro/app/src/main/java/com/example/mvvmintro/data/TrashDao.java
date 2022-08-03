package com.example.mvvmintro.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.mvvmintro.entities.Trash;

import java.util.List;

@Dao
public interface TrashDao {

    @Insert
    void insert(Trash trash);

    @Update
    void update(Trash trash);

    @Delete
    void delete(Trash trash);

    @Query("DELETE FROM trash_table")
    void deleteAllTrash();

    @Query("SELECT * FROM trash_table ORDER BY trash_id")
    LiveData<List<Trash>> getAllTrash();
}
