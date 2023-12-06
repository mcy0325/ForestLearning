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

    // 사용자가 얻고자 하는 data가 저장된 date 저장 LiveData
    val date: LiveData<LocalDate> get() = _date
    private val _date = MutableLiveData<LocalDate>()

    // 현재 설정된 date에 저장된 totaltime 저장 LiveData
    val totaltime: LiveData<Time> get() = _totaltime
    private val _totaltime = MutableLiveData<Time>()

    // 오늘 내가 저장한 subject들을 list로 저장하여 업데이트하는 LiveData
    val subjectsLiveList: LiveData<MutableList<Subjects>> get() = _subjectsLiveList
    private val _subjectsLiveList = MutableLiveData<MutableList<Subjects>>()

    // 오늘 생성한 subject들의 time들을 더한 LiveData
    val todaytotaltime: LiveData<Time> get() = _todaytotaltime
    private val _todaytotaltime = MutableLiveData<Time>()

    // date에 획득한 fruit들의 갯수를 저장하는 LiveData (fruitnum, fruitcount)2
    val treefruit: LiveData<MutableMap<Int, Int>> get() = _treefruit
    private val _treefruit = MutableLiveData<MutableMap<Int, Int>>()

    // 앱 시작시 data setting
    init {
        _date.value = LocalDate.now()
        repo.getSubjectsFromFirebase(_subjectsLiveList)
        repo.gettreefruitFromFirebase(_treefruit, _date.value!!)
        repo.gettotaltimeFromFirebase(_date.value!!, _todaytotaltime)
        repo.gettotaltimeFromFirebase(_date.value!!, _totaltime)
    }

    // date + 1 함수
    fun incrementDate(){
        _date.value = _date.value?.plusDays(1)
        _totaltime.value = Time(0,0,0)
        repo.gettotaltimeFromFirebase(_date.value!!, _totaltime)
        repo.gettreefruitFromFirebase(_treefruit,_date.value!!)
    }
    // date - 1 함수
    fun decrementDate(){
        _date.value = _date.value?.minusDays(1)
        _totaltime.value = Time(0,0,0)
        repo.gettotaltimeFromFirebase(_date.value!!, _totaltime)
        repo.gettreefruitFromFirebase(_treefruit,_date.value!!)
    }

    // 시간이 업데이트 되면서 fruit를 추가하는 함수
    fun update_todaytreefruit(position: Int, fruit: Int) {
        val updateMap = _treefruit.value ?: mutableMapOf()
        updateMap[position] = fruit
        _treefruit.value = updateMap
        val updateFirebaseMap = updateMap.mapKeys { it.key.toString() }.toMutableMap()
        repo.updatefruitToFirebase(updateFirebaseMap)
    }

    // subjectadder에서 subject 추가 함수
    fun addSubjects(subjects: Subjects) {
        val updateList = _subjectsLiveList.value ?: mutableListOf()
        updateList.add(subjects)
        _subjectsLiveList.value = updateList
        repo.updateSubjectsToFirebase(updateList)
    }

    // subjectList들의 time 합계 업데이트 함수
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
        repo.updatetotaltimeToFirebase(todaytotaltime.value!!)
    }

    // time이 업데이트 될때마다 불러오는 함수
    fun updateTime(position: Int, time: Time) {
        val updateList = _subjectsLiveList.value ?: mutableListOf()
        if (position < updateList.size) {
            updateList[position].update_time(time)
            _subjectsLiveList.value = updateList
            repo.updateSubjectsToFirebase(updateList)
        } }
}