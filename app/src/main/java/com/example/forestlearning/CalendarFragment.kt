package com.example.forestlearning

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.forestlearning.databinding.ActivityMainBinding
import com.example.forestlearning.databinding.FragmentCalendarBinding
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Date

class CalendarFragment : Fragment() {
    var binding: FragmentCalendarBinding? = null
    //var dayText: String? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCalendarBinding.inflate(inflater)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        // View Binding을 통해 캘린더 뷰와 텍스트뷰 초기화
        //객체 생성
        val dayText = binding?.dayText
        val calendarView = binding?.calendarView

        //날짜 형태
        val dateFormat: DateFormat = SimpleDateFormat("yyyy년MM월dd일")

        //date타입
        val date: Date = Date(calendarView!!.date)

        //현재 날짜 담기
        dayText?.text = dateFormat.format(date)

        //CalendarView 날짜 변환 이벤트
        calendarView.setOnDateChangeListener { calendarView, year, month, dayOfMonth ->

            //날짜 변수에 담기
            var day: String = "${year}년 ${month}월 ${dayOfMonth}일"

            //변수 텍스트뷰에 담기
            dayText?.text = day

        }
        /*
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            arguments?.let {
                param1 = it.getString(ARG_PARAM1)
                param2 = it.getString(ARG_PARAM2)
            }
        }
        */
        /*
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCalendarBinding.inflate(inflater)
        return binding?.root
    }*/
        /*
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding?.btnAbout?.setOnClickListener{
            findNavController()
        }
    }
    버튼 클릭 이벤트
 */
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}