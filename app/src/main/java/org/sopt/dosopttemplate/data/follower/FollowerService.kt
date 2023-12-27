package org.sopt.dosopttemplate.data.follower

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface FollowerService {
    @GET("api/users")
    suspend fun follower(
        @Query("page") num: Int = 2,
    ): Response<ResponseFollowerDto>
}
