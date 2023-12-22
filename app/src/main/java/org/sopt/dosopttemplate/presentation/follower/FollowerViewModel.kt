package org.sopt.dosopttemplate.presentation.follower

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import org.sopt.dosopttemplate.data.ServicePool
import org.sopt.dosopttemplate.data.model.FollowerState

class FollowerViewModel : ViewModel() {
    private val _followerState = MutableStateFlow<FollowerState>(FollowerState.Loading)
    val followerState = _followerState.asStateFlow()

    fun loadFollowerDataFlow() {
        viewModelScope.launch {
            _followerState.value = FollowerState.Loading
            runCatching {
                ServicePool.followerService.follower()
            }.onSuccess { response ->
                if (response.isSuccessful) {
                    response.body()?.let { body ->
                        _followerState.value = FollowerState.Success(body)
                    } ?: throw Exception("Response body is null")
                } else {
                    _followerState.value = FollowerState.Error
                }
            }.onFailure {
                _followerState.value = FollowerState.Error
            }
        }
    }
}
