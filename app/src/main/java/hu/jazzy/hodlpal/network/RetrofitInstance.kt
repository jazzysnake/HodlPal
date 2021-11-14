package hu.jazzy.hodlpal.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    //https://api.coinstats.app/public/v1/coins?skip=0&limit=5&currency=USD
    const val BASE_URL = "https://api.coinstats.app/public/v1/"

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api:Api by lazy {
        retrofit.create(Api::class.java)
    }

}