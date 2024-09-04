package com.example.localnotesapp.repository

import com.example.localnotesapp.data.source.NoteEntity
import kotlinx.coroutines.flow.Flow

interface ListNoteRepository {

  suspend fun getNotes(): Flow<List<NoteEntity>>

  suspend fun addNewNote(noteEntity: NoteEntity)

  suspend fun findNote(id: Int): Flow<NoteEntity>

  suspend fun removeNote(id: Int)
}