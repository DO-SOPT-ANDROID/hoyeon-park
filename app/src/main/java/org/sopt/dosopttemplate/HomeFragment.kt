package org.sopt.dosopttemplate

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ConcatAdapter
import org.sopt.dosopttemplate.databinding.FragmentHomeBinding
import org.sopt.dosopttemplate.FriendAdapter
import org.sopt.dosopttemplate.MyProfileAdapter


class HomeFragment: Fragment() {
    private var _binding: FragmentHomeBinding? = null
    private val binding: FragmentHomeBinding
        get() = requireNotNull(_binding) { "바인딩 객체가 생성되지 않았습니다" }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) { // 실제 프레그넌트 연결 후 보이는 곳
        super.onViewCreated(view, savedInstanceState)

        val profileData = MyProfile(
            profileImage = R.drawable.profile,
            name = "박호연",
            self_description = "상태메시지",
        )
        val profileAdapter = MyProfileAdapter(requireContext()).apply {
            setMyProfile(profileData)
        }

        val friendAdapter = FriendAdapter(requireContext()).apply {
            setFriendList(mockFriendList)
        }

        val concatAdapter = ConcatAdapter(profileAdapter, friendAdapter)
        binding.rvFriends.adapter = concatAdapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private val mockFriendList =listOf<Friend>(
        Friend(
            profileImage = R.drawable.jjangu,
            name = "신짱구",
            self_description = "",
        ),
        Friend(
            profileImage = R.drawable.bongmiseon,
            name = "봉미선",
            self_description = "서른즈음에 - 김광석",
        ),
        Friend(
            profileImage = R.drawable.shinhyeongshik,
            name = "신영식",
            self_description = "",
        ),
        Friend(
            profileImage = R.drawable.jjanga,
            name = "신짱아",
            self_description = "",
        ),
        Friend(
            profileImage = R.drawable.huindoongi,
            name = "흰둥이",
            self_description = "",
        ),
        Friend(
            profileImage = R.drawable.cheolsu,
            name = "김철수",
            self_description = "",
        ),
        Friend(
            profileImage = R.drawable.yuri,
            name = "한유리",
            self_description = "",
        ),
        Friend(
            profileImage = R.drawable.huni,
            name = "이훈이",
            self_description = "",
        ),
        Friend(
            profileImage = R.drawable.maenggu,
            name = "맹구",
            self_description = "",
        ),
    )
}
