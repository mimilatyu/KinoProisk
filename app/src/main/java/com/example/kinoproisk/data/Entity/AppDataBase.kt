package com.example.kinoproisk.data.Entity

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.kinoproisk.data.DAO.FilmDao

@Database(entities = [Film::class], version = 1, exportSchema = true)
abstract class AppDataBase: RoomDatabase() {
    abstract  fun filmDao():FilmDao
}