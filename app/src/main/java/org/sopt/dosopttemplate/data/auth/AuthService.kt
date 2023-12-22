package org.sopt.dosopttemplate.data.auth

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    @POST("api/v1/members/sign-in")
    suspend fun login(
        @Body request: RequestLoginDto,
    ): Response<ResponseLoginDto>

    @POST("api/v1/members")
    suspend fun signup(
        @Body request: RequestSignUpDto,
    ): Response<Unit> // 회원가입은 반환하는 데이터가 없음
}
