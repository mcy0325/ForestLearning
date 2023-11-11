package com.example.forestlearning

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import javax.security.auth.Subject



class Time(var hour: Long, var minute: Long, var sec: Long)

class StudytimeViewModel : ViewModel() {

    private var totaltime =  MutableLiveData<Long>()

    private val _subject_name = MutableLiveData<String>()
    val subject_name: LiveData<String> get() = _subject_name

    private val _info = MutableLiveData<String>()
    val info : LiveData<String> get() = _info

    private val _fruit = MutableLiveData<Int> ()
    val fruit : LiveData<Int> get() = _fruit

    private val _time = MutableLiveData(Time(0,0,0))
    val time :LiveData<Time> get() = _time




    fun addTime(timeInSeconds: Long) {
        val currentTotalTime = totaltime.value ?: 0L
        totaltime.value = currentTotalTime + timeInSeconds
    }
}