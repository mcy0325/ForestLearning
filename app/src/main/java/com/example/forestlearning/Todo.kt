package com.example.forestlearning


//todoitemlist에 들어가는 정보들
data class Todo(
    val id: String = "",
    val check: Boolean = false,
    val content: String = ""
)/*{
    @PrimaryKey(autoGenerate = true) //null을 받으면 id 값을 자동으로 할당해줌
    var id = 0
}
*/
