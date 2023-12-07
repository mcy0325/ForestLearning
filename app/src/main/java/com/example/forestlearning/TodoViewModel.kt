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

    // ToDo아이템을 추가하는 메서드, 뷰모델에서 선택된 날짜에 대한 투두 아이템 추가할때 사용
    fun addTodoItem(item: Todo) {
        // 기존의 ToDo아이템 리스트를 가져와 새 아이템을 추가
        val currentList = toDoItemList.value.orEmpty().toMutableList()
        currentList.add(item)

        // LiveData를 통해 변경된 리스트를 UI로 전달
        toDoItemList.value = currentList
    }

    // 선택된 날짜를 설정하는 메서드, 투두 캘린더에서 날짜를 뷰모델로 옮겨 줄때 UI에 알릴때 사용
    fun setSelectedDate(date: String) {
        selectedDate = date
        selectedDateLiveData.value = date
        Log.d("TodoViewModel", "선택된 날짜는 $date 로 설정 되었다람쥐")
    }

    // 선택된 날짜에 ToDo아이템을 추가하는 메서드, 투두추가화면에서 사용
    fun addTodoItemForSelectedDate(item: Todo) {
        // 선택된 날짜를 기준으로 ToDo아이템을 추가
        val newItem = item.copy(date = selectedDate)
        addTodoItem(newItem)
    }

    // 선택된 날짜 변수 저장,반환 하는 메서드. 캘린더와 리스트 프래그먼트에서 선택된 날짜가 필요할 때 사용
    fun getSelectedDate(): String {
        Log.d("TodoViewModel", "선택된 날짜: $selectedDate")
        return selectedDate
    }

    // 데이터를 갱신하는 메서드
    fun updateTodoItems(newList: List<Todo>) { //다수
        // LiveData에 데이터를 설정하여 UI 갱신
        toDoItemList.value = newList
    }

    fun updateTodoItem(item: Todo) { //투두 어댑터를 통해 변경될 아이템을 업데이트. 개인
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

    fun deleteTodoItem(toDoItem: Todo) { //투두 어댑터를 통해 삭제할 아이템 업데이트
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