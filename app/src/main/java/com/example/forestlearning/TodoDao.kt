package com.example.forestlearning

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface TodoDao { //쿼리를 만들어 쥰대 이게 뭔소리여
    @Insert(onConflict = OnConflictStrategy.IGNORE) //중복된 데이터의 경우 무시
    suspend fun addTodo(todo : Todo)

    @Update
    suspend fun updateTodo(todo : Todo)

    @Delete
    suspend fun deleteTodo(todo : Todo)

    @Query("SELECT * FROM Todo ORDER BY id ASC") //오류가 나자나요.
    fun readAllData() : Flow<List<Todo>>

    @Query("SELECT * FROM Todo WHERE content LIKE :searchQuery")
    fun searchDatabase(searchQuery : String) : Flow<List<Todo>>

}