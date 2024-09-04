package com.example.localnotesapp.data.source

import androidx.room.Entity
import androidx.room.PrimaryKey

// Sebagai entitas/class penampung data
@Entity
data class NoteEntity (
  @PrimaryKey(autoGenerate = true) val id: Int = 0,
  val noteTitle: String,
  val noteDesc: String
)