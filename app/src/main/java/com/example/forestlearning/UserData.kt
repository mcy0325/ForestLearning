package com.example.forestlearning

data class UserData(
    var name: String,
    var email: String,
    var uId: String
){
    constructor(): this("", "", "")
}
