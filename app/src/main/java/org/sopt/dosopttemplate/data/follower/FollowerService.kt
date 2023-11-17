package org.sopt.dosopttemplate.data.follower

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface FollowerService {
    @GET("api/users")
    fun follower(
        @Query("page") num: Int = 2
    ): Call<ResponseFollowerDto>
}