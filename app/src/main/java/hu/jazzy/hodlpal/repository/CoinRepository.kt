package hu.jazzy.hodlpal.repository


import hu.jazzy.hodlpal.model.Chart
import hu.jazzy.hodlpal.model.CoinList
import hu.jazzy.hodlpal.model.Fiat
import hu.jazzy.hodlpal.network.RetrofitInstance
import retrofit2.Response


class CoinRepository {
    suspend fun getCoins():Response<CoinList> {
        return RetrofitInstance.api.getCoins(
            skip = 0,
            limit = 1000,
            currency = "USD"
        )
    }

    suspend fun getFiats():Response<List<Fiat>>{
        return RetrofitInstance.api.getFiats()
    }

    suspend fun getChart(period: String, coinId:String):Response<Chart>{
        return RetrofitInstance.api.getChart(period = period,coinId=coinId)
    }
}