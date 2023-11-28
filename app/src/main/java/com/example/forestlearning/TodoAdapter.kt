package com.example.forestlearning

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.forestlearning.databinding.ItemTodoBinding

class TodoAdapter(private val viewModel: TodoViewModel) : ListAdapter<Todo, TodoAdapter.ViewHolder>(DiffCallback()) { //투두 어댑터

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemTodoBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding, viewModel)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val toDoItem = getItem(position)
        holder.bind(toDoItem)
    }

    class ViewHolder(private val binding: ItemTodoBinding,  private val viewModel: TodoViewModel) : RecyclerView.ViewHolder(binding.root) {

        fun bind(toDoItem: Todo) {
            //binding.titleTextView.text = toDoItem.content
            binding.contentTextView.setText(toDoItem.content)
            binding.checkbox.isChecked = toDoItem.check

            // ToDo아이템의 나머지 데이터를 바인딩할 수 있음
            binding.btnEdit.setOnClickListener {
                // 투두 아이템 수정 코드
                val newContent = binding.contentTextView.text.toString() // 수정된 내용
                val newCheck = binding.checkbox.isChecked // 수정된 체크 상태

                // 새로운 투두 아이템 생성
                val updatedTodo = Todo(
                    id = toDoItem.id,
                    check = newCheck,
                    content = newContent,
                    date = toDoItem.date
                )

                // 투두 아이템 업데이트
                viewModel.updateTodoItem(updatedTodo)
                Toast.makeText(binding.root.context, "오늘의 할일이 수정되었습니다.", Toast.LENGTH_SHORT).show()
            }

            binding.btnDelete.setOnClickListener {
                viewModel.deleteTodoItem(toDoItem)
                Toast.makeText(binding.root.context, "오늘의 할일이 삭제되었습니다.", Toast.LENGTH_SHORT).show()
            }
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