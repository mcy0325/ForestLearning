package com.example.forestlearning

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.room.Query
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TodolistViewModel(application: Application) : AndroidViewModel(application) {

    val readAllData : LiveData<List<Todo>>
    private val repository : TodoRepository
    init {
        val todoDao = TodoDatabase.getDatabase(application)!!.todoDao()
        repository = TodoRepository(todoDao)
        readAllData = repository.readAllData.asLiveData()
    }

    fun addTodo(todo: Todo) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addTodo(todo)
        }
    }
    fun updateTodo(todo: Todo) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateTodo(todo)
        }
    }
    fun deleteTodo(todo: Todo) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteTodo(todo)
        }
    }

    fun searchDatabase(searchQuery: String): LiveData<List<Todo>> {
        return repository.searchDatabase(searchQuery).asLiveData()
    }
}