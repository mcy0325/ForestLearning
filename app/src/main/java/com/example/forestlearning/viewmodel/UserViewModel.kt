package com.example.forestlearning.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.forestlearning.repository.UserRepository

class UserViewModel: ViewModel() {
    //내부적으로 바꿀 수 있는 라이브데이터
    private val _name = MutableLiveData("")
    //밖에서는 바꿀 수 없는 라이브데이터
    val name : MutableLiveData<String> get() = _name
    private val _email = MutableLiveData("")
    val email : MutableLiveData<String> get() = _email
    private val _password = MutableLiveData("")

    init { //앱 시작시 realtime 에서 사용자 데이터 가져오기
        UserRepository().getRealTimeName(_name)
        UserRepository().getRealTimeEmail(_email)
    }

    //로그인 시 realtime 으로 개인 정보 전달
    fun userInfo(name: String, email: String, password: String) {
        _name.value = name
        _email.value = email
        _password.value = password
        UserRepository().postUserInfo(_name.value.toString(), _email.value.toString(), _password.value.toString())
    }
}