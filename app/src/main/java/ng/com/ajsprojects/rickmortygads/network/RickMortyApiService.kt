package ng.com.ajsprojects.rickmortygads.network

import ng.com.ajsprojects.rickmortygads.data.model.RickMortyResponse
import ng.com.ajsprojects.rickmortygads.utils.API_CONNECT_TIMEOUT
import ng.com.ajsprojects.rickmortygads.utils.API_READ_TIMEOUT
import ng.com.ajsprojects.rickmortygads.utils.BASE_URL
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import java.util.concurrent.TimeUnit

interface RickMortyApiService {

    /*Get Base url
    appends what is the bracket of GET below
    * */
    @GET("/character")
    suspend fun getAllCharacters(): RickMortyResponse
}

// The function is to return the RickMortyApi Service
fun createApiService(): RickMortyApiService{
    val  retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(createHttpClient())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    return retrofit.create(RickMortyApiService::class.java)
}

private fun createHttpClient(): OkHttpClient{
    val interceptor = createLoggingInterceptor()
    return OkHttpClient.Builder()
        .addInterceptor(interceptor)
        .connectTimeout(API_CONNECT_TIMEOUT, TimeUnit.SECONDS)
        .readTimeout(API_READ_TIMEOUT, TimeUnit.SECONDS)
        .build()
}

private fun createLoggingInterceptor(): HttpLoggingInterceptor  {
    val interceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    return interceptor
}