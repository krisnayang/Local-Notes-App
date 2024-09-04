package com.example.localnotesapp.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.localnotesapp.MainActivity
import com.example.localnotesapp.R
import com.example.localnotesapp.data.source.NoteEntity
import com.example.localnotesapp.databinding.NotesListFragmentBinding
import com.example.localnotesapp.ui.adapter.ListNoteAdapter
import com.example.localnotesapp.ui.uistate.Success
import com.example.localnotesapp.viewmodel.ListNoteViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NotesListFragment: Fragment(R.layout.notes_list_fragment) {

  private val viewBinding: NotesListFragmentBinding
    get() = _viewBinding!!

  private var _viewBinding: NotesListFragmentBinding? = null

  private var listAdapter: ListNoteAdapter? = null

  private val viewModel by viewModels<ListNoteViewModel>()

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
  }

  override fun onStart() {
    super.onStart()
  }

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {

    _viewBinding = NotesListFragmentBinding.inflate(inflater, container, false)

    getCurrentActivity()?.setToolbar("My Notes")

    //Membuat adapter untuk menampilkan list/RecyclerView
    listAdapter = ListNoteAdapter (
      editClickListener = { item ->
        val action = NotesListFragmentDirections.actionNotesListFragmentToAddEditNoteFragment(item.id)
        findNavController().navigate(action)
      },
      removeClickListener = { item ->
        viewModel.removeNote(item.id)
      }
    )

    //Mengambil data dari local storage
    viewModel.getList()

    //Menampilkan UI
    setupUi(viewModel)

    viewBinding.rvList.apply {
      layoutManager = LinearLayoutManager(context)
      adapter = listAdapter
    }

    //button untuk menambah data
    viewBinding.fabAdd.setOnClickListener {
      val action = NotesListFragmentDirections.actionNotesListFragmentToAddEditNoteFragment()
      findNavController().navigate(action)
    }

    return viewBinding.root
  }

  private fun getCurrentActivity(): MainActivity?{
    return (activity as? MainActivity)
  }

  private fun setupUi(viewModel: ListNoteViewModel) {
    viewLifecycleOwner.lifecycleScope.launch {
      viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
        launch {
          viewModel.list.collect { state ->
            when (state) {
              is Error -> {
                viewBinding.loader.text = "Error Found"
                viewBinding.loader.visibility = View.VISIBLE
                viewBinding.rvList.visibility = View.GONE
              }

              is Success<*> -> {
                if ((state.value as List<NoteEntity>).isEmpty()) {
                  viewBinding.loader.text = "No Data Found"
                  viewBinding.loader.visibility = View.VISIBLE
                  viewBinding.rvList.visibility = View.GONE
                } else {
                  viewBinding.loader.visibility = View.GONE
                  viewBinding.rvList.visibility = View.VISIBLE
                  listAdapter?.submitList((state.value))
                }
              }

              else -> {
                viewBinding.loader.text = "Loading"
                viewBinding.loader.visibility = View.VISIBLE
                viewBinding.rvList.visibility = View.GONE
              }
            }
          }
        }
      }
    }
  }
}