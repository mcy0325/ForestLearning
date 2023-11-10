package com.example.forestlearning

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import javax.security.auth.Subject

class StudytimeViewModel : ViewModel() {
    private val subjectList = MutableLiveData<Array<Subjects>>()
    private var subject: Subjects? = null
    private var elapsedtime: Time = Time(0, 0, 0)

    private var totaltime =  MutableLiveData<Long>()


    fun setSubjectList(subjects: Array<Subjects>) {
        subjectList.value = subjects
    }

    fun getSubjectList(): MutableLiveData<Array<Subjects>> {
        return subjectList
    }

    fun setSubject(subject: Subjects) {
        this.subject = subject
        subjectList.value?.plus(subject)?.let { setSubjectList(it) }
    }

    fun set_fruit(fruit: Int) {
        this.subject?.fruit = fruit
    }

    fun updatetime(time: Time) {
        elapsedtime = time
    }

    fun getSubject(): Subjects? {
        return subject
    }

    fun getElapsedtime(): Time {
        return elapsedtime
    }

    fun addTime(timeInSeconds: Long) {
        val currentTotalTime = totaltime.value ?: 0L
        totaltime.value = currentTotalTime + timeInSeconds
    }
}