package com.example.forestlearning

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.forestlearning.databinding.ItemTodoBinding

class TodoAdapter : ListAdapter<Todo, TodoAdapter.ViewHolder>(DiffCallback()) { //어댑터

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemTodoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val toDoItem = getItem(position)
        holder.bind(toDoItem)
    }

    class ViewHolder(private val binding: ItemTodoBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(toDoItem: Todo) {
            binding.titleTextView.text = toDoItem.content
            binding.checkbox.isChecked = toDoItem.check
            binding.checkbox.text = toDoItem.content
            // ToDo아이템의 나머지 데이터를 바인딩할 수 있음
        }
    }

    private class DiffCallback : DiffUtil.ItemCallback<Todo>() {
        override fun areItemsTheSame(oldItem: Todo, newItem: Todo): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Todo, newItem: Todo): Boolean {
            return oldItem == newItem
        }
    }
}