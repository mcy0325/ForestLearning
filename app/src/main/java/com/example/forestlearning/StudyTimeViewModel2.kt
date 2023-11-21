package com.example.forestlearning

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.O)
class StudyTimeViewModel2 : ViewModel() {

    private val repo = Repo()


    private val _currenttime = MutableLiveData(Time(0, 0, 0))
    val currenttime: LiveData<Time> get() = _currenttime

    val subjectsLiveList: LiveData<MutableList<Subjects>> get() = _subjectsLiveList
    private val _subjectsLiveList = MutableLiveData<MutableList<Subjects>>()

    val subjectsList: MutableList<Subjects> get() = _subjectsList
    private val _subjectsList = mutableListOf<Subjects>()
    init {
        repo.getSubjectsFromFirebase(_subjectsLiveList)
    }
    fun getSubjectsList(): LiveData<MutableList<Subjects>> {
        return _subjectsLiveList
    }

    val todaytreefruit: LiveData<MutableMap<Int, Int>> get() = _todaytreefruit
    private val _todaytreefruit = MutableLiveData<MutableMap<Int, Int>>()

    fun gettodaytreefruit(): LiveData<MutableMap<Int, Int>> {
        return _todaytreefruit
    }

    fun update_todaytreefruit(newMap: MutableMap<Int, Int>) {
        _todaytreefruit.value = newMap
    }


    fun addSubjects(subjects: Subjects) {
        val updateList = _subjectsLiveList.value ?: mutableListOf()
        updateList.add(subjects)
        _subjectsLiveList.value = updateList
        repo.updateSubjectsToFirebase(updateList)
    }

    fun updateSubjects(newList: MutableList<Subjects>) {
        _subjectsLiveList.value = newList
    }

    fun updatecurrenttime(time: Time) {
        _currenttime.value = time
    }

    fun updateTime(position: Int, time: Time) {
        val updateList = _subjectsLiveList.value ?: mutableListOf()
        if (updateList != null && position < updateList.size) {
            updateList[position].update_time(time)
            _subjectsLiveList.value = updateList
        }
    }
}