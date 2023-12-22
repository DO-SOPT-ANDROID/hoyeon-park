package org.sopt.dosopttemplate.presentation.follower

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import org.sopt.dosopttemplate.data.ServicePool
import org.sopt.dosopttemplate.data.ServicePool.followerService
import org.sopt.dosopttemplate.data.auth.RequestLoginDto
import org.sopt.dosopttemplate.data.follower.ResponseFollowerDto
import org.sopt.dosopttemplate.data.model.FollowerState
import org.sopt.dosopttemplate.data.model.LoginState
import retrofit2.HttpException

/*class FollowerViewModel : ViewModel() {

    private var _followerData = MutableLiveData<List<ResponseFollowerDto.FollowerData>?>()
    var followerData: LiveData<List<ResponseFollowerDto.FollowerData>?> = _followerData

    fun loadFollowerDataFlow() {
        viewModelScope.launch {
            flow {
                val response = ServicePool.followerService.follower()
                if (response.isSuccessful) {
                    emit(response.body()?.data ?: emptyList())
                } else {
                    throw HttpException(response)
                }
            }.catch { exception ->
                Log.e("서버 통신 실패", exception.message.toString())
            }.collect { followerList ->
                _followerData.value = followerList
            }
        }
    }
}*/

class FollowerViewModel : ViewModel() {
    private val _followerState = MutableStateFlow<FollowerState>(FollowerState.Loading)
    val followerState = _followerState.asStateFlow()

    fun loadFollowerDataFlow() {
        viewModelScope.launch {
            _followerState.value = FollowerState.Loading
            try {
                val response = ServicePool.followerService.follower()
                if (response.isSuccessful) {
                    response.body()?.let { body ->
                        _followerState.value = FollowerState.Success(body)
                    } ?: throw Exception("Response body is null")
                } else {
                    _followerState.value = FollowerState.Error
                }
            } catch (e: Exception) {
                _followerState.value = FollowerState.Error
            }
        }
    }
}
