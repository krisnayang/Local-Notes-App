package com.example.localnotesapp.repository

import com.example.localnotesapp.data.dao.DataDao
import com.example.localnotesapp.data.source.NoteEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ListNoteRepositoryImpl @Inject constructor(
  private val dataDao: DataDao
) : ListNoteRepository {
  //untuk mengambil data yang ada pada local storage
  override suspend fun getNotes(): Flow<List<NoteEntity>> {
    return dataDao.getNotes()
  }

  override suspend fun addNewNote(noteEntity: NoteEntity) {
    dataDao.insertNote(noteEntity)
  }

  override suspend fun findNote(id: Int): Flow<NoteEntity> {
    return dataDao.findNote(id)
  }

  override suspend fun removeNote(id: Int) {
    dataDao.removeNote(id)
  }
}