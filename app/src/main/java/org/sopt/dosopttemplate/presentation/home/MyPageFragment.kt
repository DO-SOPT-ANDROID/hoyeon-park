package org.sopt.dosopttemplate.presentation.home

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import org.sopt.dosopttemplate.databinding.FragmentMypageBinding


class MyPageFragment: Fragment() {
    private var _binding: FragmentMypageBinding? = null
    private val binding: FragmentMypageBinding
        get() = requireNotNull(_binding) { "바인딩 객체가 생성되지 않았습니다." }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMypageBinding.inflate(inflater, container, false)
        return binding.root
    }

    /*override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }*/

    override fun onViewCreated(view: View, savedInstanceState: Bundle?, ) {
        super.onViewCreated(view, savedInstanceState)

        val bundle = arguments
        val userId = bundle?.getString("idValue")
        val userName = bundle?.getString("nameValue")
        val userMbti = bundle?.getString("mbtiValue")

        binding.run {
            idValue.text = userId
            nameValue.text = userName
            mbtiValue.text = userMbti
        }
        Log.d("MyPageFragment", "전달된 데이터 - userId: $userId, userName: $userName, userMbti: $userMbti")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

