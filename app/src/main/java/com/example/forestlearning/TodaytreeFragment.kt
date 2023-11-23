package com.example.forestlearning

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.lifecycle.MutableLiveData
import androidx.navigation.navGraphViewModels
import com.example.forestlearning.databinding.FragmentTodaytreeBinding
import java.lang.IllegalArgumentException
import java.time.LocalDate


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
        binding= FragmentTodaytreeBinding.inflate(inflater, container, false)
        // Inflate the layout for this fragment
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.leftArrow.setOnClickListener {
            viewModel.decrementDate()
            updateTreeAndTime()
        }
        binding.rightArrow.setOnClickListener {
            viewModel.incrementDate()
            updateTreeAndTime()
        }
        updateTreeAndTime()
    }

    private fun updateTreeAndTime() {
        binding.todayDate.text = repo.setDate(viewModel.date.value!!)

        val treefruitMap = viewModel.treefruit.value ?: mutableMapOf()

        val treeBitmap = BitmapFactory.decodeResource(resources, R.drawable.tree)
        val mutableBitmap = treeBitmap.copy(Bitmap.Config.ARGB_8888, true)
        val canvas = Canvas(mutableBitmap)

        treefruitMap.forEach { (fruitNum, fruitcount) ->
            val fruitBitmap = when (fruitNum) {
                0 -> BitmapFactory.decodeResource(resources, R.drawable.apple)
                1 -> BitmapFactory.decodeResource(resources, R.drawable.banana)
                2 -> BitmapFactory.decodeResource(resources, R.drawable.grape)
                else -> throw IllegalArgumentException("Unknown fruit index: $fruitNum")
            }

            repeat(fruitcount) {
                val x = (0 until canvas.width).random().toFloat()
                val y = (0 until canvas.height).random().toFloat()

                canvas.drawBitmap(fruitBitmap, x, y, null)
            }

        }

        val totaltime = viewModel.totaltime.value!!
        binding.treeContainer.background = BitmapDrawable(resources, mutableBitmap)
        binding.totalTime.text = formatTime(totaltime)
        }

    private fun formatTime(time: Time): String {
        return String.format("%02d:%02d:%02d", time.hour, time.minute, time.sec)
    }



companion object {

    }
}