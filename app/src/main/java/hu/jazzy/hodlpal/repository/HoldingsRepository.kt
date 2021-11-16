package hu.jazzy.hodlpal.repository

import androidx.lifecycle.LiveData
import hu.jazzy.hodlpal.database.Dao
import hu.jazzy.hodlpal.database.HeldCoin

class HoldingsRepository(private val dao: Dao) {
    val readAllHeldCoins:LiveData<List<HeldCoin>> = dao.readAllHeldCoins()

    suspend fun addHeldCoin(heldCoin: HeldCoin){
        dao.addHeldCoin(heldCoin)
    }
}