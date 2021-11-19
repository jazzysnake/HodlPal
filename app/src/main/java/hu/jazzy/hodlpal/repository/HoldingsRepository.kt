package hu.jazzy.hodlpal.repository

import android.util.Log
import androidx.lifecycle.LiveData
import hu.jazzy.hodlpal.database.CoinHolding
import hu.jazzy.hodlpal.database.Dao
import hu.jazzy.hodlpal.database.CoinTransaction
import kotlinx.coroutines.flow.Flow

class HoldingsRepository(private val dao: Dao) {
     fun readAllCoinsTransaction():LiveData<List<CoinTransaction>>{
        return dao.readAllCoinTxs()
    }

    suspend fun addCoinTx(coinTransaction: CoinTransaction){
        dao.addCoinTx(coinTransaction)
    }

    fun getCoinTxById(coinId:String):List<CoinTransaction>{
        return dao.getCoinTxById(coinId)
    }

    suspend fun updateCoinTx(coinTransaction: CoinTransaction){
        dao.updateCoinTx(coinTransaction)
    }

     fun getCoinHoldingByCoinID(coinId: String): LiveData<CoinHolding> {
        return  dao.getCoinHoldingByCoinID(coinId)
    }

    suspend fun addCoinHolding(coinHolding: CoinHolding):Long{
        return dao.addCoinHolding(coinHolding)
    }

    fun readAllCoinHoldings():LiveData<List<CoinHolding>>{
        return dao.readAllCoinHoldings()
    }

    suspend fun updateCoinHolding(coinHolding: CoinHolding):Int {
        return dao.updateCoinHolding(coinHolding.coin.id,coinHolding.amount)
    }
}