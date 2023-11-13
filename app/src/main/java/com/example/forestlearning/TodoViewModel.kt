package com.example.forestlearning

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TodoViewModel: ViewModel() {
    val toDoItemList = MutableLiveData<List<Todo>>()

    // ToDo아이템을 추가하는 메서드
    fun addTodoItem(item: Todo) {
        // 기존의 ToDo아이템 리스트를 가져와 새 아이템을 추가
        val currentList = toDoItemList.value.orEmpty().toMutableList()
        currentList.add(item)

        // LiveData를 통해 변경된 리스트를 UI로 전달
        toDoItemList.value = currentList
    }
}