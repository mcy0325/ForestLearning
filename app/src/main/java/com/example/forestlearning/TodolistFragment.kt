package com.example.forestlearning

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.forestlearning.databinding.FragmentTodolistBinding

class TodolistFragment : Fragment() { //투두
    private var binding : FragmentTodolistBinding? =null
    private val todolistViewModel: TodolistViewModel by viewModels()
    private val adapter : TodoAdapter by lazy { TodoAdapter() } //어댑터 선언
    override fun onCreateView (
        inflater :LayoutInflater, container: ViewGroup?,
        savedInstanceState : Bundle?
    ): View? { //바인딩
        binding = FragmentTodolistBinding.inflate(inflater,container,false)
        binding!!.addButton.setOnClickListener {
            Toast.makeText(activity, "테스트",Toast.LENGTH_SHORT).show()
            onFabCliked()
        } //ㅌㅔ스트
        binding!!.todoRecyclerView.layoutManager = LinearLayoutManager(activity,LinearLayoutManager.VERTICAL,false)
        binding!!.todoRecyclerView.adapter = adapter

        todolistViewModel.readAllData.observe(viewLifecycleOwner, Observer {
            adapter.setData(it)
        })

        return binding!!.root
    }//온크리에잇뷰

    fun onFabCliked() {
        val todo = Todo(false, "개어려워")
        todolistViewModel.addTodo(todo)
    }
}