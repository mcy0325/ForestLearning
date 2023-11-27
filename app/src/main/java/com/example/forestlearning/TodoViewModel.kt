//투두 뷰 모델

package com.example.forestlearning

import android.content.ContentValues.TAG
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase

class TodoViewModel: ViewModel() {
    val toDoItemList = MutableLiveData<List<Todo>>()

    //23.11.20 추가
    //투두리스트에 선택된 날짜 띄우기 위한 라이브데이터
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
        Log.d("TodoViewModel", "선택된 날짜는 $date 로 설정 되었다람쥐")
    }

    // 선택된 날짜에 ToDo아이템을 추가하는 메서드
    fun addTodoItemForSelectedDate(item: Todo) {
        // 선택된 날짜를 기준으로 ToDo아이템을 추가
        val newItem = item.copy(date = selectedDate)
        addTodoItem(newItem)
    }

    // 선택된 날짜를 반환하는 메서드
    fun getSelectedDate(): String {
        Log.d("TodoViewModel", "선택된 날짜: $selectedDate")
        return selectedDate
    }

    // 데이터를 갱신하는 메서드
    fun updateTodoItems(newList: List<Todo>) {
        // LiveData에 데이터를 설정하여 UI 갱신
        toDoItemList.value = newList
    }

    fun updateTodoItem(item: Todo) {
        val databaseReference = FirebaseDatabase.getInstance().getReference("todos")
        val currentUser = FirebaseAuth.getInstance().currentUser
        val uid = currentUser?.uid

        uid?.let { userUid ->
            databaseReference.child(userUid).child(item.id!!)
                .setValue(item)
                .addOnSuccessListener {
                    Log.d(TAG, "투두 업데이트 성공!")
                }
                .addOnFailureListener { e ->
                    Log.w(TAG, "투두 업데이트 실패 우우", e)
                }
        }
    }

    fun deleteTodoItem(toDoItem: Todo) {
        // 파이어베이스 데이터베이스 참조 가져오기
        val databaseReference = FirebaseDatabase.getInstance().getReference("todos")

        // 현재 사용자의 UID 가져오기
        val currentUser = FirebaseAuth.getInstance().currentUser
        val uid = currentUser?.uid

        // 사용자 데이터베이스 참조에서 투두 아이템 제거
        uid?.let { userUid ->
            val userDatabaseReference = databaseReference.child(userUid).child(toDoItem.id.toString())
            userDatabaseReference.removeValue()
        }
    }
}