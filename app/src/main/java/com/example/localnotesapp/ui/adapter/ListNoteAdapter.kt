package com.example.localnotesapp.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.localnotesapp.data.source.NoteEntity
import com.example.localnotesapp.databinding.NoteListItemBinding
import kotlinx.android.synthetic.main.note_list_item.view.btn_remove

class ListNoteAdapter(
  private val editClickListener: (NoteEntity) -> Unit,
  private val removeClickListener: (NoteEntity) -> Unit,
):
    ListAdapter<NoteEntity, ListNoteAdapter.viewHolder>(DiffCallback) {
  private lateinit var context: Context

  private val viewBinding: NoteListItemBinding
    get() = _viewBinding!!

  private var _viewBinding: NoteListItemBinding? = null

  companion object DiffCallback : DiffUtil.ItemCallback<NoteEntity>() {
    override fun areItemsTheSame(oldItem: NoteEntity, newItem: NoteEntity): Boolean {
      return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: NoteEntity, newItem: NoteEntity): Boolean {
      return oldItem == newItem
    }
  }

  class viewHolder(private val viewBinding: NoteListItemBinding): RecyclerView.ViewHolder(viewBinding.root) {
    fun bind(item: NoteEntity) {
      //Menampilkan data yang ada ke dalam list
      viewBinding.tvTitle.text = item.noteTitle
      viewBinding.tvDesc.text = item.noteDesc
    }
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): viewHolder {
    _viewBinding = NoteListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    context = parent.context
    return viewHolder(viewBinding)
  }

  override fun onBindViewHolder(holder: viewHolder, position: Int) {
    val item = getItem(position)
    holder.itemView.setOnClickListener{
      editClickListener(item)
    }
    holder.itemView.btn_remove.setOnClickListener {
      removeClickListener(item)
    }
    holder.bind(item)
  }
}