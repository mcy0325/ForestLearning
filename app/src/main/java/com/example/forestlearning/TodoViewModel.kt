package com.example.forestlearning

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TodoViewModel: ViewModel() { //투두 뷰모델
    val toDoItemList = MutableLiveData<List<Todo>>()
    //23.11.20 추가
    //투두리스트에 선택된 날짜 띄우기 위한 라이브데이ㅌ터
    val selectedDateLiveData = MutableLiveData<String>()

    // 선택된 날짜를 저장하는 변수
    private var selectedDate: String = ""

    // ToDo아이템을 추가하는 메서드
    fun addTodoItem(item: Todo) {
        // 기존의 ToDo아이템 리스트를 가져와 새 아이템을 추가
        val currentList = toDoItemList.value.orEmpty().toMutableList()
        currentList.add(item)

        // LiveData를 통해 변경된 리스트를 UI로 전달
        toDoItemList.value = currentList
    }

    // 선택된 날짜를 설정하는 메서드
    fun setSelectedDate(date: String) {
        selectedDate = date
        selectedDateLiveData.value = date
        Log.d("TodoViewModel", "Selected date is set to $date")
    }

    // 선택된 날짜에 ToDo아이템을 추가하는 메서드
    fun addTodoItemForSelectedDate(item: Todo) {
        // 선택된 날짜를 기준으로 ToDo아이템을 추가
        val newItem = item.copy(date = selectedDate)
        addTodoItem(newItem)
    }

    // 선택된 날짜를 반환하는 메서드
    fun getSelectedDate(): String {
        Log.d("TodoViewModel", "개같은놈아 선택된날짜 왜안돼: $selectedDate")
        return selectedDate
    }

    // 데이터를 갱신하는 메서드
    fun updateTodoItems(newList: List<Todo>) {
        // LiveData에 데이터를 설정하여 UI 갱신
        toDoItemList.value = newList
    }

    /*
    // ToDo아이템을 추가하는 메서드
    fun addTodoItem(item: Todo) {
        // 기존의 ToDo아이템 리스트를 가져와 새 아이템을 추가
        val currentList = toDoItemList.value.orEmpty().toMutableList()
        currentList.add(item)

        // LiveData를 통해 변경된 리스트를 UI로 전달
        toDoItemList.value = currentList
    }

    fun updateTodoItems(newList: List<Todo>) {
        // LiveData에 데이터를 설정하여 UI 갱신
        toDoItemList.value = newList
    }*/
}