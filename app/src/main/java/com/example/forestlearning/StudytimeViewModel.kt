package com.example.forestlearning

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.MutableData
import com.google.firebase.database.core.Repo
import javax.security.auth.Subject




class StudytimeViewModel : ViewModel() {

    private val repo = Repo()

    fun fetchData() : LiveData<MutableList<Subjects>> {
        val mutableData = MutableLiveData<MutableList<Subjects>>()

        repo.getData().observeForever {
            mutableData.value = it
        }
        return mutableData
    }

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




    fun updatetime(time : Time){
        _time.value = time
    }

}