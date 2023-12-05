package com.example.forestlearning

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.forestlearning.databinding.FragmentFruitshowBinding
import com.example.forestlearning.viewmodel.FruitshowViewModel

class FruitshowFragment : Fragment() {
    var binding: FragmentFruitshowBinding? = null
    private val viewModel: FruitshowViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFruitshowBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = FruitshowAdapter()
        binding?.fruitShowRecycler?.apply {
            layoutManager = LinearLayoutManager(context)
            this.adapter = adapter
        }

        viewModel.fruitData.observe(viewLifecycleOwner) { data ->
            adapter.fruitData = data
        }

        binding?.searchBtn?.setOnClickListener {
            val searchName = binding?.searchName?.text.toString()
            if (searchName.isNotEmpty()) {
                // 검색어가 비어있지 않을 경우 검색 수행
                adapter.search(searchName)
            } else {
                // 검색어가 비어있을 경우 전체 목록 출력
                adapter.search("")
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // 뷰가 파괴될 때 바인딩 객체를 null로 설정
        binding = null
    }
}
