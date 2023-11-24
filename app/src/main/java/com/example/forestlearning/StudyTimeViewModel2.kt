package com.example.forestlearning

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@RequiresApi(Build.VERSION_CODES.O)
class StudyTimeViewModel2 : ViewModel() {

    private val repo = Repo()


    private val _totaltime = MutableLiveData<Time>()
    val totaltime: MutableLiveData<Time> get() = _totaltime

    val subjectsLiveList: LiveData<MutableList<Subjects>> get() = _subjectsLiveList
    private val _subjectsLiveList = MutableLiveData<MutableList<Subjects>>()

    private val _date = MutableLiveData<LocalDate>()
    var date: LiveData<LocalDate> = _date

    val subjectsList: MutableList<Subjects> get() = _subjectsList
    private val _subjectsList = mutableListOf<Subjects>()

    val todaydate: LocalDate = LocalDate.now()
    val todaytotaltime: MutableLiveData<Time> get() = _todaytotaltime
    private val _todaytotaltime = MutableLiveData<Time>()
    val treefruit: LiveData<MutableMap<Int, Int>> get() = _treefruit
    private val _treefruit = MutableLiveData<MutableMap<Int, Int>>()
    init {
        _date.value = LocalDate.now()
        repo.getSubjectsFromFirebase(_subjectsLiveList)
        repo.gettreefruitFromFirebase(_treefruit, date.value!!)
        repo.gettotaltime(_date.value!!, _todaytotaltime)
        repo.gettotaltime(_date.value!!, _totaltime)
    }

    fun incrementDate(){
        _date.value = _date.value?.plusDays(1)
        _totaltime.value = Time(0,0,0)
        repo.gettotaltime(_date.value!!, _totaltime)
    }
    fun decrementDate(){
        _date.value = _date.value?.minusDays(1)
        _totaltime.value = Time(0,0,0)
        repo.gettotaltime(_date.value!!, _totaltime)
    }

    fun update_todaytreefruit(position: Int, fruit: Int) {
        val updateMap = _treefruit.value ?: mutableMapOf()
        updateMap[position] = fruit
        _treefruit.value = updateMap
        val updateFirebaseMap = updateMap.mapKeys { it.key.toString() }.toMutableMap()
        repo.updatefruitToFirebase(updateFirebaseMap)
    }
    fun addSubjects(subjects: Subjects) {
        val updateList = _subjectsLiveList.value ?: mutableListOf()
        updateList.add(subjects)
        _subjectsLiveList.value = updateList
        repo.updateSubjectsToFirebase(updateList)
    }

    fun updatetotaltime() {
        _subjectsLiveList.value?.let { subjectsList ->
            var hour = 0
            var minute = 0
            var sec = 0
            for (i in subjectsList.indices) {
                hour += subjectsList[i].time.hour
                minute += subjectsList[i].time.minute
                sec += subjectsList[i].time.sec
            }
            minute += sec / 60
            sec %= 60
            hour += minute / 60
            minute %= 60
            _todaytotaltime.value = Time(hour, minute, sec)
        }
        repo.updatetotaltime(todaytotaltime.value!!)
    }

    fun updateTime(position: Int, time: Time) {
        val updateList = _subjectsLiveList.value ?: mutableListOf()
        if (position < updateList.size) {
            updateList[position].update_time(time)
            _subjectsLiveList.value = updateList
            repo.updateSubjectsToFirebase(updateList)
        }
    }
}