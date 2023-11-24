package com.example.forestlearning

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler

class IntroActivity : AppCompatActivity() {
    //액티비티가 생성될 때 호출되는 매소드
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //layout에서 activity_intro를 뷰로 설정
        setContentView(R.layout.activity_intro)
        //핸들러 객체 생성
        val handler = Handler()
        //핸들러에게 3초 후 실행할 작업 전달
        handler.postDelayed({
            //새로운 인텐트 생성, 이 인텐트로 hostActivity 실행
            val intent = Intent(applicationContext, hostActivity::class.java)
            startActivity(intent)
            //현재 액티비티 종료
            finish()
        }, 3000) //3초 후 실행
    }

    //액티비티 일시 중지될 때 호출되는 메소드
    override fun onPause() {
        super.onPause()
        //액티비티 일시 중지 되면 액티비티 종료
        finish()
    }
}