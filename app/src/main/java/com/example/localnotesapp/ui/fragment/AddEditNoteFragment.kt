package com.example.localnotesapp.ui.fragment

import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.example.localnotesapp.MainActivity
import com.example.localnotesapp.R
import com.example.localnotesapp.data.source.NoteEntity
import com.example.localnotesapp.databinding.AddEditNoteFragmentBinding
import com.example.localnotesapp.ui.uistate.Success
import com.example.localnotesapp.viewmodel.ListNoteViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AddEditNoteFragment: Fragment(R.layout.add_edit_note_fragment) {

  private val navigationArgs: AddEditNoteFragmentArgs by navArgs()

  private val viewBinding: AddEditNoteFragmentBinding
    get() = _viewBinding!!

  private var _viewBinding: AddEditNoteFragmentBinding? = null

  private val viewModel by viewModels<ListNoteViewModel>()

  private var title = ""
  private var desc = ""

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    _viewBinding = AddEditNoteFragmentBinding.inflate(inflater, container, false)

    val id = navigationArgs.id
    if(id != -1) {
      //Set Toolbar ketika ditemukan id (ketika user klik salah satu note)
      getCurrentActivity()?.setToolbar("Edit Note")
      viewModel.findNote(id)
      setupEditText(viewModel)

      viewBinding.btnSave.setOnClickListener {
        viewModel.addNewdata(
          NoteEntity(
            id = id,
            noteTitle = title,
            noteDesc = desc ))
        view?.findNavController()?.navigateUp()
      }
    } else {
      getCurrentActivity()?.setToolbar("Add Note")

      viewBinding.btnSave.setOnClickListener {
        viewModel.addNewdata(
          NoteEntity(
            noteTitle = title,
            noteDesc = desc ))
        view?.findNavController()?.navigateUp()
      }
    }
    setButton()

    return viewBinding.root
  }

  private fun getCurrentActivity(): MainActivity?{
    return (activity as? MainActivity)
  }

  private fun setButton() {
    checkFieldIsEmpty(title, desc)
    viewBinding.apply {
      viewLifecycleOwner.lifecycleScope.launch {

        etTitle.afterTextChanged {
          title = it
          checkFieldIsEmpty(title, desc)
        }

        etDesc.afterTextChanged {
          desc = it
          checkFieldIsEmpty(title, desc)
        }
      }
    }
  }

  private fun setupEditText(viewModel: ListNoteViewModel) {
    viewLifecycleOwner.lifecycleScope.launch {
      viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
        launch {
          viewModel.note.collect { state ->
            when (state) {
              is Success<*> -> {
                viewBinding.etTitle.setText((state.value as NoteEntity).noteTitle)
                viewBinding.etDesc.setText((state.value as NoteEntity).noteDesc)
              }

              else -> {

              }
            }
          }
        }
      }
    }
  }

  private fun checkFieldIsEmpty(
    title: String,
    desc: String,
  ) {
    viewBinding.apply {
      btnSave.isEnabled = title.isNotEmpty() || desc.isNotEmpty()
    }
  }

  private fun EditText.afterTextChanged(afterTextChanged: (String) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
      override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
      }

      override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
      }

      override fun afterTextChanged(editable: Editable?) {
        afterTextChanged.invoke(editable.toString())
      }
    })
  }
}