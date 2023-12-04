package com.example.forestlearning

import androidx.multidex.MultiDexApplication
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class Authentication: MultiDexApplication() {
    //companion 블록 = 클래스의 인스턴스를 생성하지 않고도 접근할 수 있는 정적 멤버를 정의하는데 사용
    companion object {
        var auth: FirebaseAuth = Firebase.auth

        //로그인 확인 여부로 사용하는 email 변수
        var email: String? = null

        //로그인 여부를 확인하는 함수
        fun checkLogin(): Boolean {
            //firebase에 등록한 사용자 정보 불러오기
            val currentUser = auth.currentUser
            return currentUser?.let {
                //유저 정보가 있으면 email 가져오기
                email = currentUser.email

                //로그인 되어있는 경우
                currentUser.isEmailVerified
            } ?: let {
                false
            }
        }
    }
}