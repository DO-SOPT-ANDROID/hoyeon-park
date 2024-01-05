package org.sopt.dosopttemplate.presentation.follower

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.viewpager2.widget.ViewPager2
import kotlinx.coroutines.launch
import org.sopt.dosopttemplate.R
import org.sopt.dosopttemplate.data.model.FollowerState

class FollowerViewPagerFragment : Fragment() {

    private lateinit var viewPager: ViewPager2
    private lateinit var adapter: FollowerPagerAdapter
    private val viewModel: FollowerViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        val view = inflater.inflate(R.layout.fragment_viewpager, container, false)
        viewPager = view.findViewById(R.id.viewPager)
        adapter = FollowerPagerAdapter()
        viewPager.adapter = adapter
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
        viewModel.loadFollowerDataFlow()
    }

    private fun observeViewModel() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.followerState.collect { state ->
                when (state) {
                    is FollowerState.Success -> adapter.submitList(state.data.data)
                    is FollowerState.Error -> {
                        // 에러의 경우
                    }

                    is FollowerState.Loading -> {
                        // 로딩의 경우
                    }
                }
            }
        }
    }
}
