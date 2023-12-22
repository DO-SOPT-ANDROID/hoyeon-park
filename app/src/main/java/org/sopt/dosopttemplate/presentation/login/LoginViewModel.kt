package org.sopt.dosopttemplate.presentation.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.sopt.dosopttemplate.data.ServicePool.authService
import org.sopt.dosopttemplate.data.auth.RequestLoginDto
import org.sopt.dosopttemplate.data.auth.ResponseLoginDto
import retrofit2.Call
import retrofit2.Response

class LoginViewModel : ViewModel() {

    private val _loginResult = MutableLiveData<String>()
    val loginResult: LiveData<String>
        get() = _loginResult

    fun login(id: String, password: String) {
        authService.login(RequestLoginDto(id, password))
            .enqueue(object : retrofit2.Callback<ResponseLoginDto> {
                override fun onResponse(
                    call: Call<ResponseLoginDto>,
                    response: Response<ResponseLoginDto>,
                ) {
                    if (response.isSuccessful) {
                        val data: ResponseLoginDto = response.body()!!
                        val userId = data.id
                        _loginResult.value = "로그인이 성공하였습니다! 유저의 ID는 $userId 입니다."
                    } else {
                        _loginResult.value = "로그인에 실패하였습니다"
                    }
                }

                override fun onFailure(call: Call<ResponseLoginDto>, t: Throwable) {
                    val errorMessage = "서버 통신 실패: ${t.message}"
                    _loginResult.value = errorMessage
                }
            })
    }
}
