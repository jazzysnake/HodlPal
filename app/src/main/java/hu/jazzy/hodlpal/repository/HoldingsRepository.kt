package hu.jazzy.hodlpal.repository

import androidx.lifecycle.LiveData
import hu.jazzy.hodlpal.database.Dao
import hu.jazzy.hodlpal.database.CoinTransaction

class HoldingsRepository(private val dao: Dao) {
    val readAllCoinsTransaction:LiveData<List<CoinTransaction>> = dao.readAllCoinTxs()

    suspend fun addCoinTx(coinTransaction: CoinTransaction){
        dao.addCoinTx(coinTransaction)
    }

    suspend fun getCoinTxById(coinId:String):List<CoinTransaction>{
        return dao.getCoinTxById(coinId)
    }

    suspend fun updateCoinTx(coinTransaction: CoinTransaction){
        dao.updateCoinTx(coinTransaction)
    }
}