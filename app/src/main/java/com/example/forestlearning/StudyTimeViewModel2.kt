package com.example.forestlearning

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class StudyTimeViewModel2 : ViewModel() {

    private val repo = Repo()

    /**fun fetchData() : LiveData<MutableList<Subjects>> {
        val mutableData = MutableLiveData<MutableList<Subjects>>()

        repo.getData().observeForever {
            mutableData.value = it
        }
        return mutableData
    }**/


    private val _subject_name = MutableLiveData<String>()
    val subject_name: LiveData<String> get() = _subject_name

    private val _info = MutableLiveData<String>()
    val info : LiveData<String> get() = _info

    private val _fruit = MutableLiveData<Int> ()
    val fruit : LiveData<Int> get() = _fruit

    private val _time = MutableLiveData(Time(0,0,0))
    val time :LiveData<Time> get() = _time

    private val _subjectsMap = MutableLiveData<MutableMap<String, MutableList<Subjects>>>()
    val subjectsMap : LiveData<MutableMap<String, MutableList<Subjects>>> get() = _subjectsMap

    private var tempSubjectsMap: MutableMap<String, MutableList<Subjects>> = mutableMapOf()


    fun add_subject(subjects: Subjects) {
        if (subjectsMap.value?.get(subjects.name) == null) {
            tempSubjectsMap[subjects.name] = mutableListOf(subjects)
        }

        _subjectsMap.value = tempSubjectsMap

        repo.postSubjects(_subjectsMap.value!!)
    }

    fun get_subject_name() : String? {
        return _subject_name.value
    }

    fun get_info() : String? {
        return _info.value
    }

    fun get_fruit() : Int? {
        return _fruit.value
    }

    fun set_time(time : Time){
        _time.value = time
    }

    fun update_time(time : Time){
        _time.value = Time(
            _time.value!!.hour + time.hour,
            _time.value!!.minute + time.minute,
            _time.value!!.sec + time.sec)
    }
}