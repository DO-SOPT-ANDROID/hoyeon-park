package org.sopt.dosopttemplate.presentation.follower

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.sopt.dosopttemplate.data.ServicePool
import org.sopt.dosopttemplate.data.follower.ResponseFollowerDto
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowerViewModel : ViewModel() {

    private var _followerData = MutableLiveData<List<ResponseFollowerDto.FollowerData>?>()
    var followerData = _followerData


    fun loadFollowerData() {
        ServicePool.followerService.follower().enqueue(object : Callback<ResponseFollowerDto> {
            override fun onResponse(
                call: Call<ResponseFollowerDto>,
                response: Response<ResponseFollowerDto>
            ) {
                if (response.isSuccessful) {
                    Log.e("서버 통신 성공", response.body()?.data.toString())
                    val followerList: List<ResponseFollowerDto.FollowerData>? =
                        response.body()?.data
                    _followerData.value = followerList
                }
                else{
                    Log.e("어딘가 문제가 생겼다", response.body()?.data.toString())
                    Log.e("FollowerViewModel", "Unsuccessful response body: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<ResponseFollowerDto>, t: Throwable) {
                Log.e("서버 통신 실패", t.message.toString())
            }
        })
    }
}