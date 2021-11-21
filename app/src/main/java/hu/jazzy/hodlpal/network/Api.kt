package hu.jazzy.hodlpal.network

import hu.jazzy.hodlpal.model.CoinList
import hu.jazzy.hodlpal.model.Fiat
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {
    @GET("coins")
    suspend fun getCoins(
        @Query("skip")skip: Int,
        @Query("limit")limit:Int,
        @Query("currency")currency:String
    ):Response<CoinList>

    @GET("fiats")
    suspend fun getFiats():Response<List<Fiat>>
}