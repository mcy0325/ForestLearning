package com.example.forestlearning

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class StudyTimeViewModel2 : ViewModel() {

    private val repo = Repo()


    private val _time = MutableLiveData(Time(0,0,0))
    val time :LiveData<Time> get() = _time

    val subjectsLiveList : LiveData<MutableList<Subjects>> get() = _subjectsLiveList
    private val _subjectsLiveList = MutableLiveData<MutableList<Subjects>>()

    private var tempSubjectsMap: MutableList<Subjects> = mutableListOf()

    fun getSubjectsList() : LiveData<MutableList<Subjects>> {
        return _subjectsLiveList
    }
    fun addSubjects(subjects: Subjects) {
        val updateList = _subjectsLiveList.value ?: mutableListOf()
        updateList.add(subjects)
        _subjectsLiveList.value = updateList

        // firebase에 저장
        repo.saveSubjects(_subjectsLiveList.value!!)

    }

    fun updateSujectsFromFirebase() : LiveData<MutableList<Subjects>> {
        repo.getSubjectsFromFirebase().observeForever {
            viewModelScope.launch {
                _subjectsLiveList.value = it
            }
        }
        return _subjectsLiveList
    }


    fun update_time(time : Time){
        _time.value = Time(
            _time.value!!.hour + time.hour,
            _time.value!!.minute + time.minute,
            _time.value!!.sec + time.sec)
    }
}