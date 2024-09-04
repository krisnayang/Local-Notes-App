package com.example.localnotesapp.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.localnotesapp.data.source.NoteEntity
import com.example.localnotesapp.repository.ListNoteRepository
import com.example.localnotesapp.ui.uistate.Error
import com.example.localnotesapp.ui.uistate.Loading
import com.example.localnotesapp.ui.uistate.Success
import com.example.localnotesapp.ui.uistate.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListNoteViewModel @Inject constructor(
  private val notesRepository: ListNoteRepository
): ViewModel() {
  private var _list: MutableStateFlow<UiState> = MutableStateFlow(Loading)
  val list = _list

  private var _note: MutableStateFlow<UiState> = MutableStateFlow(Loading)
  val note = _note

  fun getList() = viewModelScope.launch {
    try {
      _list.value = Loading
      notesRepository.getNotes().collect {
        _list.value = Success(value = it)
      }
    } catch (e: Exception) {
      _list.value = Error(errorMessage = e.toString())
    }
  }

  fun addNewdata(noteEntity: NoteEntity) {
    viewModelScope.launch(Dispatchers.IO) {
      notesRepository.addNewNote(noteEntity)
    }
  }

  fun findNote(id: Int) {
    viewModelScope.launch (Dispatchers.IO){
      try {
        _note.value = Loading
        notesRepository.findNote(id).collect {
          _note.value = Success(value = it)
        }
      } catch (e: Exception) {
        _note.value = Error(errorMessage = e.toString())
      }
    }
  }

  fun removeNote(id: Int) {
    viewModelScope.launch (Dispatchers.IO){
      try {
        notesRepository.removeNote(id)
      } catch (e: Exception) { }
    }
  }
}