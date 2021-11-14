package hu.jazzy.hodlpal.network

import hu.jazzy.hodlpal.model.CoinList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface Api {
    //coins?skip=0&limit=5&currency=USD
    @GET("coins")
    suspend fun getCoins(
        @Query("skip")skip: Int,
        @Query("limit")limit:Int,
        @Query("currency")currency:String
    ):Response<CoinList>

//    @GET("coins/list/{id}")
//    suspend fun getCoinData(
//        @Path("id")id :String,
////        @Query("localization")localization: String,
////        @Query("tickers")tickers:Boolean,
////        @Query("community_data")community_data:Boolean,
////        @Query("developer_data")developer_data:Boolean,
////        @Query("sparkline")sparkline:Boolean
//    ):Call<CoinData>
}