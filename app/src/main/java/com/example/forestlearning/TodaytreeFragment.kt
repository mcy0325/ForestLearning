package com.example.forestlearning

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.navigation.navGraphViewModels
import com.example.forestlearning.databinding.FragmentTodaytreeBinding
import java.lang.IllegalArgumentException
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.util.Calendar
import java.util.Locale


@RequiresApi(Build.VERSION_CODES.O)
class TodaytreeFragment : Fragment() {
    private lateinit var binding: FragmentTodaytreeBinding
    private val viewModel: StudyTimeViewModel2 by lazy {
        StudyTimeViewModel2()
    }
    private val repo = Repo()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentTodaytreeBinding.inflate(inflater, container, false)

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // observe로 날짜가 바뀔때마다 업데이트
        viewModel.totaltime.observe(viewLifecycleOwner, Observer {
            binding.totalTime.text = formatTime(it)
        })
        viewModel.date.observe(viewLifecycleOwner, Observer {
            val setdate = repo.setDate(it)
            val calendar = Calendar.getInstance()
            val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
            val date = dateFormat.parse(setdate)
            if (date != null) {
                calendar.time = date
            }
            val dayOfWeek = when(calendar.get(Calendar.DAY_OF_WEEK)){
                1 -> "일요일"
                2 -> "월요일"
                3 -> "화요일"
                4 -> "수요일"
                5 -> "목요일"
                6 -> "금요일"
                7 -> "토요일"
                else -> throw IllegalArgumentException("Unknown day of week")
            }
            val text = getString(R.string.date_with_day, setdate, dayOfWeek)
            binding.todayDate.text = text
        })
        viewModel.treefruit.observe(viewLifecycleOwner, Observer {
            updateTree()
        })

        // 방향 버튼을 누르면 날짜 변경
        binding.leftArrow.setOnClickListener {
            viewModel.decrementDate()
        }
        binding.rightArrow.setOnClickListener {
            viewModel.incrementDate()
        }

    }

    // tree에 fruit drawable을 업로드하기 위한 함수
    private fun updateTree() {
        val treefruitMap = viewModel.treefruit.value ?: mutableMapOf()

        val treeBitmap = BitmapFactory.decodeResource(resources, R.drawable.tree)
        val mutableBitmap = treeBitmap.copy(Bitmap.Config.ARGB_8888, true)
        val canvas = Canvas(mutableBitmap)

        treefruitMap.forEach { (fruitNum, fruitcount) ->
            val limitfruitcount: Int
            if (fruitcount > 3) {
                limitfruitcount = 3
            } else {
                limitfruitcount = fruitcount
            }
            val fruitBitmap = when (fruitNum) {
                0 -> BitmapFactory.decodeResource(resources, R.drawable.apple)
                1 -> BitmapFactory.decodeResource(resources, R.drawable.banana)
                2 -> BitmapFactory.decodeResource(resources, R.drawable.grape)
                else -> throw IllegalArgumentException("Unknown fruit index: $fruitNum")
            }
            repeat(limitfruitcount) {
                var x: Int
                var y: Int
                do {
                    x = (0 until canvas.width).random()
                    y = (0 until canvas.height).random()

                } while (treeBitmap.getPixel(x, y) == Color.TRANSPARENT)

                canvas.drawBitmap(fruitBitmap, x.toFloat(), y.toFloat(), null)
            } }
        binding.treeContainer.background = BitmapDrawable(resources, mutableBitmap)
    }

    private fun formatTime(time: Time): String {
        return String.format("%02d:%02d:%02d", time.hour, time.minute, time.sec)
    }
}