package com.example.forestlearning.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.forestlearning.repository.UserRepository

class UserViewModel: ViewModel() {
    //내부적으로 바꿀수있는 라이브데이터
    private val _name = MutableLiveData("")
    //하지만 밖에서는 바꿀 수 없는 라이브데이터
    val name : MutableLiveData<String> get() = _name
    val _email = MutableLiveData("")
    val email : MutableLiveData<String> get() = _email
    val _password = MutableLiveData("")

    init { //앱 시작시 realtime 에서 사용자 데이터 가져오기
        UserRepository().getRealTimeName(_name)
        UserRepository().getRealTimeEmail(_email)
    }
}