package com.example.forestlearning

import androidx.lifecycle.LiveData
import androidx.room.Query
import kotlinx.coroutines.flow.Flow


class TodoRepository(private val todoDao: TodoDao) {

    val readAllData : Flow<List<Todo>> = todoDao.readAllData()

    suspend fun addTodo(todo: Todo) {
        todoDao.addTodo(todo)
    }

    suspend fun updateTodo(todo: Todo) {
        todoDao.updateTodo(todo)
    }

    suspend fun deleteTodo(todo: Todo){
        todoDao.deleteTodo(todo)
    }

    fun searchDatabase(searchQuery: String): Flow<List<Todo>> {
        return todoDao.searchDatabase(searchQuery)
    }
    //서치 어쩌구 관련 코드 안넣음 근데 날짜 못할 거면 이걸로 하는것도 나쁘지 않을듯..
}