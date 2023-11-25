package com.example.forestlearning


//todoitemlist에 들어가는 정보들, 이대로 데이터 올라감
data class Todo( //투두 아이템
    val id: String? = null,
    val check: Boolean = false,
    val content: String = "",
    val date: String = ""
)
