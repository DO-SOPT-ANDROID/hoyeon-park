package org.sopt.dosopttemplate.presentation.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import org.sopt.dosopttemplate.data.follower.ResponseFollowerDto
import org.sopt.dosopttemplate.data.model.FollowerState
import org.sopt.dosopttemplate.databinding.FragmentHomeBinding
import org.sopt.dosopttemplate.presentation.follower.FollowerAdapter
import org.sopt.dosopttemplate.presentation.follower.FollowerViewModel

class HomeFragment : Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding: FragmentHomeBinding
        get() = requireNotNull(_binding) { "바인딩 객체가 생성되지 않았습니다" }

    private val viewModel by viewModels<FollowerViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.loadFollowerDataFlow()
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.followerState.collect { state ->
                when (state) {
                    is FollowerState.Loading -> {
                        // 로딩은 빈칸
                    }

                    is FollowerState.Success -> {
                        setAdapter(state.data.data)
                        Log.d("뷰모델", state.data.data.toString())
                    }

                    is FollowerState.Error -> {
                        // 에러는 빈칸
                    }
                }
            }
        }
    }

    private fun setAdapter(followerList: List<ResponseFollowerDto.FollowerData>?) {
        Log.e("어댑터 연결 성공", followerList.toString())
        val followerAdapter = FollowerAdapter()
        binding.rvFollower.adapter = followerAdapter
        followerAdapter.submitList(followerList)
    }

    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}
