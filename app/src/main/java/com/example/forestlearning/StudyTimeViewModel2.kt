package com.example.forestlearning

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class StudyTimeViewModel2 : ViewModel() {

    private val repo = Repo()


    private val _time = MutableLiveData(Time(0, 0, 0))
    val time: LiveData<Time> get() = _time

    val subjectsLiveList: LiveData<MutableList<Subjects>> get() = _subjectsLiveList
    private val _subjectsLiveList = MutableLiveData<MutableList<Subjects>>()

    fun getSubjectsList(): LiveData<MutableList<Subjects>> {
        return _subjectsLiveList
    }

    val todaytreefruit: LiveData<MutableList<Int>> get() = _todaytreefruit
    private val _todaytreefruit = MutableLiveData<MutableList<Int>>()

    fun gettodaytreefruit(): LiveData<MutableList<Int>> {
        return _todaytreefruit
    }

    fun update_todaytreefruit(newList: MutableList<Int>) {
        _todaytreefruit.value = newList
    }


    fun addSubjects(subjects: Subjects) {
        val updateList = _subjectsLiveList.value ?: mutableListOf()
        updateList.add(subjects)
        _subjectsLiveList.value = updateList

    }

    fun updateSubjects(newList: MutableList<Subjects>) {
        _subjectsLiveList.value = newList
    }


    fun update_time(subjects: Subjects, time: Time) {

    }
}