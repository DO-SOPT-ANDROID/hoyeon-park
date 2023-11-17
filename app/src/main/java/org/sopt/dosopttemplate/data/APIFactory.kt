package org.sopt.dosopttemplate.data

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import org.sopt.dosopttemplate.BuildConfig
import org.sopt.dosopttemplate.data.auth.AuthService
import org.sopt.dosopttemplate.data.follower.FollowerService
import retrofit2.Retrofit

/*object ApiFactory {
    //private const val BASE_URL = BuildConfig.AUTH_BASE_URL
    lateinit var url: String

    val retrofit: Retrofit by lazy{
        Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .build()
    }

    inline fun <reified T> create(url: String): T{
        this.url = url
        return retrofit.create<T>(T::class.java)
    }
}

object ServicePool {
    private const val BASE_URL = BuildConfig.AUTH_BASE_URL
    private const val REQRES_BASE_URL = BuildConfig.REQRES_BASE_URL

    val authService = ApiFactory.create<AuthService>(BASE_URL)
    val followerService = ApiFactory.create<FollowerService>(REQRES_BASE_URL)
}*/
object ApiFactory {
    private const val BASE_URL = BuildConfig.AUTH_BASE_URL

    val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .build()
    }

    inline fun <reified T> create(): T = retrofit.create(T::class.java)
}

object ReqresApiFactory {
    private const val REQRES_BASE_URL = BuildConfig.REQRES_BASE_URL

    val Reqretrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(REQRES_BASE_URL)
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .build()
    }

    inline fun <reified T> create(): T = Reqretrofit.create<T>(T::class.java)
}

object ServicePool {
    val authService = ApiFactory.create<AuthService>()
    val followerService = ReqresApiFactory.create<FollowerService>()
}