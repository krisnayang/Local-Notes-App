package com.example.localnotesapp.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.localnotesapp.data.source.NoteEntity
import kotlinx.coroutines.flow.Flow

//Query untuk mengakses data
@Dao
interface DataDao {

  @Query("SELECT * FROM NoteEntity")
  fun getNotes(): Flow<List<NoteEntity>>

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun insertNote(note: NoteEntity)

  @Query("SELECT * FROM NoteEntity WHERE NoteEntity.id = :id")
  fun findNote(id: Int): Flow<NoteEntity>

  @Query("DELETE FROM NOTEENTITY WHERE NoteEntity.id = :id")
  fun removeNote(id: Int)
}