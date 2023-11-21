package com.example.forestlearning

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.forestlearning.databinding.FragmentFruitshowBinding

class FruitshowFragment : Fragment() {
    var binding: FragmentFruitshowBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFruitshowBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    
        //데이터 불러오기
        binding?.fruitShowRecycler?.layoutManager = LinearLayoutManager(context)
        binding?.fruitShowRecycler?.adapter = FruitshowAdapter()
        
        //검색 기능 사용 (사용자 이름 검색)
        binding?.searchBtn?.setOnClickListener {
            if (binding?.searchBtn?.text?.isEmpty() == false) {
                //FruitshowAdapter 내에 있는 search 함수 불러서 검색 기능 활성화
            }
        }
    }
}