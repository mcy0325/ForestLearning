package com.example.forestlearning

import androidx.room.Entity
import androidx.room.PrimaryKey

//객체 느낌 근데 객체는 아니고 개체, 하나의 정보 단위 느낌?
//@는 어노테이션=데이터를 설명하는 데이터. 이름을 정해주고 싶다면 @Entity(tableName="userProfile")
@Entity
data class Todo(
    val check : Boolean,
    val content : String
){
    @PrimaryKey(autoGenerate = true) //null을 받으면 id 값을 자동으로 할당해줌
    var id = 0
}
