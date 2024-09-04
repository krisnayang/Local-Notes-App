package com.example.localnotesapp.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.localnotesapp.data.dao.DataDao
import com.example.localnotesapp.data.source.NoteEntity

//membuat local database menggunakan room
@Database(entities = [NoteEntity::class], version = 1, exportSchema = false)
abstract class LocalDatabase: RoomDatabase() {
  abstract val dataDao: DataDao

  companion object {
    @Volatile
    private var INSTANCE: LocalDatabase? = null
  }
}