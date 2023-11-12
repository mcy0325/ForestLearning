package com.example.forestlearning

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.example.forestlearning.databinding.FragmentTodolistBinding

class TodolistFragment : Fragment() {
    private var binding : FragmentTodolistBinding? =null
    private val todolistViewModel: TodolistViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTodolistBinding.inflate(inflater,container,false)

        binding!!.addButton.setOnClickListener {
            Toast.makeText(activity, "테스트",Toast.LENGTH_SHORT).show()
            onFabCliked()
        }

        return binding!!.root
    }

    fun onFabCliked() {
        val todo = Todo(false, "개어려워")
        todolistViewModel.addTodo(todo)
    }
}