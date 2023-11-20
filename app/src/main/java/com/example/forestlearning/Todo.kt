package com.example.forestlearning


//todoitemlist에 들어가는 정보들, 이대로 데이터 올라감
data class Todo(
    val id: String? = null,
    val check: Boolean = false,
    val content: String = "",
    val date: String = ""
)/*{
    @PrimaryKey(autoGenerate = true) //null을 받으면 id 값을 자동으로 할당해줌
    var id = 0
}
*/
