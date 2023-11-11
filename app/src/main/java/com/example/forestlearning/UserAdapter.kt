package com.example.forestlearning

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class UserAdapter(private val context: Context, private val userList: ArrayList<User>) : RecyclerView.Adapter<UserAdapter.UserViewHolder>(){

    //user_layout 연결하는 기능
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view: View = LayoutInflater.from(context).inflate(R.layout.user_list, parent, false)
        return UserViewHolder(view)
    }

    //데이터 전달 받아 user_layout에 보여주는 기능
    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val currentUser = userList[position]
        holder.nameText.text = currentUser.name
    }

    //userList의 개수 돌려주는 기능
    override fun getItemCount(): Int {
        return userList.size
    }


    //user_layout 텍스트뷰 객체 만들기
    class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameText = itemView.findViewById<TextView>(R.id.userName)
        val fruitNum = itemView.findViewById<TextView>(R.id.userFruit)
    }
}