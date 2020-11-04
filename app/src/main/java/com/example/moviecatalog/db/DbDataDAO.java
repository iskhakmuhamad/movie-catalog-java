package com.example.moviecatalog.db;

import android.database.Cursor;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface DbDataDAO {
    @Insert
    void insertFavData(DbDataModel dbDataModel);

    @Query("SELECT * FROM table_favorite")
    List<DbDataModel> getFavoriteData();

    @Query("SELECT * FROM table_favorite WHERE id= :idFav LIMIT 1")
    List<DbDataModel> getById(int idFav);

    @Query("SELECT * FROM table_favorite WHERE category= :categoryFav")
    List<DbDataModel> getByCategory(String categoryFav);

    @Query("DELETE FROM table_favorite WHERE id= :idFavorite")
    void deleteFavdat(int idFavorite);

    //content provider

    @Query("SELECT COUNT(*) FROM table_favorite" )
    int count();

    @Insert
    void insert(DbDataModel dataModel);

    @Query("SELECT * FROM table_favorite")
    Cursor getAllDataCursor();

    @Query("SELECT * FROM table_favorite WHERE id = :idFav LIMIT 1")
    Cursor getDataById(long idFav);

}
