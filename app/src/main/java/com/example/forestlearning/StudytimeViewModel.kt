package com.example.forestlearning

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import javax.security.auth.Subject

class StudytimeViewModel : ViewModel() {
    private var selectedSubject = MutableLiveData<Subjects>()
    private var totaltime = MutableLiveData<Long>()

    fun updateSubject(subject: Subjects) {
        selectedSubject.value = subject
    }
    fun addTime(timeInSeconds: Long) {
        val currentTotalTime = totaltime.value ?: 0L
        totaltime.value = currentTotalTime + timeInSeconds
    }
}