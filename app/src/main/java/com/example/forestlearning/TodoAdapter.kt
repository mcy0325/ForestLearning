package com.example.forestlearning

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.forestlearning.databinding.ItemTodoBinding

class TodoAdapter : RecyclerView.Adapter<TodoAdapter.MyViewHolder>() {
    private var todoList = emptyList<Todo>()

    class MyViewHolder(val binding: ItemTodoBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemTodoBinding.inflate(LayoutInflater.from(parent.context),parent, false)
        return  MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = todoList[position]
        val currentContent = currentItem.content
        val currentCheck = currentItem.check

        holder.binding.todoCheckBox.text = currentContent
    }

    override fun getItemCount(): Int {
        return todoList.size
    }

    fun setData(todo : List<Todo>){
        todoList = todo
        notifyDataSetChanged()
    }

}