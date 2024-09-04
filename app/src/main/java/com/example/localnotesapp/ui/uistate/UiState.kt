package com.example.localnotesapp.ui.uistate

//agar dapat memperoleh state saat pengambilan data
//apabila terjadi error saat pengambilan data, state akan mengiriman error message ke UI
//apabila succes, state akan mengirimkan value yang didapat
sealed class UiState
object Loading: UiState()
class Error(val errorMessage: String): UiState()
class Success<T>(val value: T): UiState()