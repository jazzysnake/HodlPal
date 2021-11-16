package hu.jazzy.hodlpal.repository

import android.util.Log
import hu.jazzy.hodlpal.model.Coin
import hu.jazzy.hodlpal.model.CoinList
import hu.jazzy.hodlpal.network.RetrofitInstance
import retrofit2.Call
import retrofit2.Response


class CoinRepository {
    suspend fun getCoins():Response<CoinList> {
        return RetrofitInstance.api.getCoins(
            skip = 0,
            limit = 1000,
            currency = "USD"
        )
    }
}