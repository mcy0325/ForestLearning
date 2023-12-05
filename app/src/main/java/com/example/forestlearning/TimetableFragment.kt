package com.example.forestlearning

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.forestlearning.databinding.FragmentTimetableBinding
import com.example.forestlearning.viewmodel.TimeTableViewModel

class TimetableFragment : Fragment() {

    private lateinit var binding: FragmentTimetableBinding
    private val viewModel : TimeTableViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentTimetableBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val dayTimeMap = initDayTimeMap()

        viewModel.courses.observe(viewLifecycleOwner) { courses ->
            // 강의 정보 지우기
            dayTimeMap.values.forEach { view ->
                if (view is TextView) {
                    view.text = ""
                }
            }

            // 강의 정보를 설정하기
            courses.forEach { course ->
                setCourseInfo(dayTimeMap, course, course.day1, course.coursePlace1, course.time1?.let { convertTimeToMinute(it) }, course.time2?.let { convertTimeToMinute(it) })
                setCourseInfo(dayTimeMap, course, course.day2, course.coursePlace2, course.time3?.let { convertTimeToMinute(it) }, course.time4?.let { convertTimeToMinute(it) })
            }
        }

        //courseAddButton 클릭 시 courseAddFragment로 이동
        binding.courseAddButton.setOnClickListener {
            findNavController().navigate(R.id.action_timetableFragment_to_courseAddFragment)
        }

    }

    //시간을 분 단위로 반환하는 함수
    private fun convertTimeToMinute(time: String): Int {
        val split = time.split(":")
        val hours = split[0].toInt()
        val minutes = split[1].toInt()
        return hours * 60 + minutes
    }

    //분 단위를 시간 형식으로 변환하는 함수
    private fun convertMinuteToTime(minute: Int): String {
        val hours = minute / 60
        val minutes = minute % 60
        return String.format("%02d:%02d", hours, minutes)
    }

    //요일과 시간을 키로, 연결하는 맵을 초기화하는 함수
    private fun initDayTimeMap() = mapOf(
        "월" to listOf(binding.monday0, binding.monday1, binding.monday2, binding.monday3, binding.monday4, binding.monday5, binding.monday6, binding.monday7, binding.monday8),
        "화" to listOf(binding.tuesday0, binding.tuesday1, binding.tuesday2, binding.tuesday3, binding.tuesday4, binding.tuesday5, binding.tuesday6, binding.tuesday7, binding.tuesday8),
        "수" to listOf(binding.wednesday0, binding.wednesday1, binding.wednesday2, binding.wednesday3, binding.wednesday4, binding.wednesday5, binding.wednesday6, binding.wednesday7, binding.wednesday8),
        "목" to listOf(binding.thursday0, binding.thursday1, binding.thursday2, binding.thursday3, binding.thursday4, binding.thursday5, binding.thursday6, binding.thursday7, binding.thursday8),
        "금" to listOf(binding.friday0, binding.friday1, binding.friday2, binding.friday3, binding.friday4, binding.friday5, binding.friday6, binding.friday7, binding.friday8)
    ).flatMap { entry ->
        val day = entry.key
        val times = entry.value
        times.indices.map { index ->
            val time = convertMinuteToTime(index * 60 + 9 * 60) // 9시부터 시작
            "${day}${time}-${convertMinuteToTime(index * 60 + 10 * 60)}" to times[index] // 1시간 간격
        }
    }.toMap()

    //강의 정보를 시간에 맞게 설정하는 함수
    private fun setCourseInfo(dayTimeMap: Map<String, View>, course: CourseData, day: String?, place: String?, startTime: Int?, endTime: Int?) {
        startTime?.let { start ->
            endTime?.let { end ->
                for (time in start until end step 60) {
                    val key = "$day${convertMinuteToTime(time)}-${convertMinuteToTime(time + 60)}"
                    val textView = dayTimeMap[key] as? TextView
                    textView?.apply {
                        text = "${course.courseName}\n${course.teacherName}\n$place"
                        val context = context // context를 미리 가져옴
                        setOnClickListener {
                            context?.let {
                                AlertDialog.Builder(it)
                                    .setTitle("강의 삭제")  // 대화상자의 제목 설정
                                    .setMessage("${course.courseName} 강의를 삭제하시겠습니까?")  // 대화상자의 메시지 설정
                                    .setPositiveButton("예") { dialog, which ->
                                        // "예" 버튼이 눌렸을 때의 동작 설정
                                        viewModel.resetCourseData(course.courseName ?: "")  // 강의명 전달

                                    }
                                    .setNegativeButton("아니오", null)  // "아니오" 버튼이 눌렸을 때의 동작 설정
                                    .show()  // 대화 상자 표시
                            }
                        }
                    }
                }
            }
        }
    }
}