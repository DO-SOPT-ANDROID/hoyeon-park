package org.sopt.dosopttemplate.data.auth

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    @POST("api/v1/members/sign-in")
    fun login(
        @Body request: RequestLoginDto,
    ): Call<ResponseLoginDto>

    @POST("api/v1/members")
    fun signup(
        @Body request: RequestSignUpDto,
    ): Call<Unit> // 회원가입은 반환하는 데이터가 없음
}