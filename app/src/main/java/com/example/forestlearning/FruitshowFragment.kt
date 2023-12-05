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
    // 바인딩 객체. UI 컴포넌트에 접근할 수 있게 함
    var binding: FragmentFruitshowBinding? = null

    // ViewModel 객체. UI에서 사용할 데이터를 제공
    private val viewModel: FruitshowViewModel by viewModels()

    // 뷰를 생성하는 메소드
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // 바인딩 객체를 초기화하고 루트 뷰를 반환
        binding = FragmentFruitshowBinding.inflate(inflater, container, false)
        return binding?.root
    }

    // 뷰가 생성된 후 호출되는 메소드
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // RecyclerView의 Adapter를 생성하고 설정
        val adapter = FruitshowAdapter()
        binding?.fruitShowRecycler?.apply {
            layoutManager = LinearLayoutManager(context)
            this.adapter = adapter
        }

        // ViewModel의 fruitData를 관찰하여 데이터가 변경될 때마다 Adapter에 반영
        viewModel.fruitData.observe(viewLifecycleOwner) { data ->
            adapter.fruitData = data
        }

        // 검색 버튼의 클릭 이벤트를 설정
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

    // 뷰가 파괴될 때 호출되는 메소드
    override fun onDestroyView() {
        super.onDestroyView()
        // 뷰가 파괴될 때 바인딩 객체를 null로 설정
        binding = null
    }
}
